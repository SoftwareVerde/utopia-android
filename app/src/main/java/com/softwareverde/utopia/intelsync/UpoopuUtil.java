package com.softwareverde.utopia.intelsync;

import com.softwareverde.json.Json;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.util.Base64Util;
import com.softwareverde.utopia.util.JavaScriptExecutor;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UpoopuUtil implements IntelSync {
    public static class Dependencies {
        private String _upoopuLibrary;
        private JavaScriptExecutor _javaScriptExecutor;
        private Base64Util _base64Util;

        public void setUpoopuLibrary(final String upoopuLibrary) { _upoopuLibrary = upoopuLibrary; }
        public void setJavaScriptExecutor(final JavaScriptExecutor javaScriptExecutor) { _javaScriptExecutor = javaScriptExecutor; }
        public void setBase64Util(final Base64Util base64Util) { _base64Util = base64Util; }
    }

    private static class Province {
        public Integer id;
        public String name;
        public String hash;
    }

    private JavaScriptExecutor _javaScriptExecutor;
    private Base64Util _base64Util;
    private Boolean _isLoggedIn = false;
    private String _cookie = null;
    private String _selectedProvinceHash = null;
    private List<Province> _provinces = new ArrayList<Province>();
    private Province _selectedProvince = null;
    private Queue<Callback> _submitIntelCallbackQueue = new ConcurrentLinkedQueue<Callback>();

    private void _submitFormattedIntel(final String intelData) {
        final Callback callback = _submitIntelCallbackQueue.poll();

        if (_cookie == null || _selectedProvince == null) {
            if (callback != null) {
                callback.run(new Response(false, "Not logged in."));
            }

            return;
        }

        final WebRequest upoopuRequest = new WebRequest();
        upoopuRequest.setType(WebRequest.RequestType.POST);
        upoopuRequest.setUrl("https://www.upoopu.com/formatter/");

        upoopuRequest.setHeader("origin", "https://www.upoopu.com");
        upoopuRequest.setHeader("accept-encoding", "gzip, deflate");
        upoopuRequest.setHeader("x-requested-with", "XMLHttpRequest");
        upoopuRequest.setHeader("accept-language", "en-US,en;q=0.8");
        upoopuRequest.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
        upoopuRequest.setHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        upoopuRequest.setHeader("accept", "*/*");
        upoopuRequest.setHeader("referer", "https://www.upoopu.com/formatter/");
        upoopuRequest.setHeader("authority", "www.upoopu.com");

        upoopuRequest.setCookie(_cookie);

        upoopuRequest.setPostParam("prov_id", _selectedProvince.id.toString());
        upoopuRequest.setPostParam("base64", intelData);
        upoopuRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                System.out.println(request.getRawResult());

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
                return;
            }
        });
    }

    public UpoopuUtil(final Dependencies dependencies) {
        _base64Util = dependencies._base64Util;
        _javaScriptExecutor = dependencies._javaScriptExecutor;
        _javaScriptExecutor.loadScript(dependencies._upoopuLibrary);
    }

    @Override
    public void setProvinceData(ProvinceData provinceData) {
        _selectedProvinceHash = "utopia:"+ provinceData.provinceName +":"+ provinceData.islandId +":"+ provinceData.kingdomId;
        for (final Province province : _provinces) {
            if (province.hash.contains(_selectedProvinceHash)) {
                _selectedProvince = province;
                break;
            }
        }

    }

    @Override
    public void login(final String username, final String password, final Callback callback) {
        final WebRequest upoopuLoginRequest = new WebRequest();
        upoopuLoginRequest.setUrl("https://www.upoopu.com/login/");
        upoopuLoginRequest.setType(WebRequest.RequestType.POST);

        upoopuLoginRequest.setHeader("x-requested-with", "XMLHttpRequest");

        upoopuLoginRequest.setPostParam("name", username);
        upoopuLoginRequest.setPostParam("pw", password);
        upoopuLoginRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new Response(false, "No response from server."));
                        return;
                    }
                }

                final Json result = request.getJsonResult();
                if (! result.get("succes", Json.Types.STRING).equalsIgnoreCase("true") && ! result.get("success", Json.Types.STRING).equalsIgnoreCase("true")) { // NOTE: "Success" spelled incorrectly.
                    _isLoggedIn = false;
                    if (callback != null) {
                        callback.run(new Response(false, "Invalid credentials."));
                    }
                    return;
                }

                final List<String> cookies = request.getCookies();
                for (final String cookie : cookies) {
                    if (cookie.contains("PLAY_SESSION")) {
                        _isLoggedIn = true;
                        _cookie = cookie;
                        break;
                    }
                }

                if (! _isLoggedIn) {
                    if (callback != null) {
                        callback.run(new Response(false, "Invalid credentials."));
                    }
                    return;
                }

                final Json provincesJson = result.get("provs");
                _provinces.clear();
                _selectedProvince = null;
                for (Integer i=0; i<provincesJson.length(); ++i) {
                    final Json provinceJson = provincesJson.get(i);

                    final Province province = new Province();
                    province.id = provinceJson.get("id", Json.Types.INTEGER);
                    province.name = provinceJson.get("province", Json.Types.STRING);
                    province.hash = provinceJson.get("hash", Json.Types.STRING);
                    _provinces.add(province);

                    if (_selectedProvinceHash != null) {
                        if (province.hash.contains(_selectedProvinceHash)) {
                            _selectedProvince = province;
                            break;
                        }
                    }
                }
                if (_selectedProvince == null) {
                    if (callback != null) {
                        callback.run(new Response(false, "Province not registered within UpoOpu."));
                    }
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
            }
        });
    }

    @Override
    public Boolean canProcessIntel(final String html, final Extra extra) {
        return true;
    }

    @Override
    public void submitIntel(final String html, final Extra extra, final Callback callback) {
        _submitIntelCallbackQueue.add(callback);

        org.jsoup.nodes.Document dom = Jsoup.parse(html);
        final String htmlTextAsBase64 = _base64Util.encodeString(dom.text());

        final String script =
            "var out = false;" +
            "try { var parser = upoopu.parsers.findParser(atob(\""+ htmlTextAsBase64 +"\"), null);" +
            "parser.parse();" +
            "var bundle = parser.toPb();" +
            "var stream = new PROTO.Base64Stream();" +
            "var combined = upoopu.parsers.mergeToBulk(bundle, null);" +
            "combined.current_time = (new Date).valueOf() * 0.001;" +
            "combined.SerializeToStream(stream);" +
            "out = stream.getString();" +
            "} catch (error) { out = false; }"
        ;

        _javaScriptExecutor.executeJavaScript(script, "out", new JavaScriptExecutor.Callback() {
            @Override
            public void run(final String value) {
                _submitFormattedIntel(value);
            }
        });
    }

    @Override
    public Boolean isLoggedIn() {
        return _isLoggedIn;
    }

    @Override
    public void setSubdomain(String subdomain) { }
}
