package com.softwareverde.utopia.intelsync;


import com.softwareverde.util.WebRequest;

import org.jsoup.Jsoup;

import java.util.List;

public class UmunkUtil implements IntelSync {
    private static final String _uMunkTopDomain = ".umunk.net/";

    private String _domain;
    private List<String> _cookies;
    private ProvinceData _provinceData;

    private String _getUrl() {
        return "http://"+ _domain + _uMunkTopDomain;
    }
    private String _getLoginUrl(String domain) {
        return "http://"+ domain + _uMunkTopDomain +"index.php";
    }
    private String _getParseUrl() {
        return _getUrl() +"parse/parse.php";
    }

    private void _onFailure(String message, Callback callback) {
        if (callback != null) {
            callback.run(new Response(false, message));
        }
    }

    private Boolean _isLoggedOut(String html) {
        return html.contains("START OF LOGIN FORM");
    }

    public UmunkUtil() { }

    @Override
    public void login(String username, String password, final Callback callback) {
        WebRequest loginRequest = new WebRequest();
        loginRequest.setUrl(_getLoginUrl(_domain));
        loginRequest.setType(WebRequest.RequestType.POST);
        loginRequest.setPostParam("user", username);
        loginRequest.setPostParam("pass", password);
        loginRequest.setPostParam("remember", "1");
        loginRequest.setFollowsRedirects(false);
        loginRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                _cookies = request.getCookies();
                if (_isLoggedOut(request.getRawResult())) {
                    _cookies = null;
                    _onFailure("Invalid credentials.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
            }
        });
    }

    @Override
    public Boolean canProcessIntel(String html, IntelSync.Extra extra) {
        return true;
    }

    @Override
    public void submitIntel(String html, Extra extra, final Callback callback) {
        if (_cookies == null || _cookies.size() == 0) {
            _onFailure("Invalid auth tokens.", callback);
            return;
        }

        String data = Jsoup.parse(html).text();
        WebRequest postIntelRequest = new WebRequest();
        postIntelRequest.setUrl(_getParseUrl());
        postIntelRequest.setType(WebRequest.RequestType.POST);
        if (extra != null && extra.url != null) {
            postIntelRequest.setPostParam("url", extra.url);
        }
        postIntelRequest.setPostParam("data", data);
        postIntelRequest.setPostParam("version", "2.2");

        if (_provinceData != null && _provinceData.provinceName != null) {
            postIntelRequest.setPostParam("prov", _provinceData.provinceName);
        }
        if (extra != null) {
            if (extra.intelType.equals(IntelType.COMBAT_SPELL) || extra.intelType.equals(IntelType.SELF_SPELL)) {
                String extraString = (extra.wasSuccess ? "good" : "bad") + "|" + extra.targetKingdomId + "|" + extra.targetIslandId + "|" + extra.targetProvinceName + "|" + extra.spellIdentifier.toLowerCase() + "|0|" + extra.resultText;
                postIntelRequest.setPostParam("extra", extraString);
            }
            else if (extra.intelType.equals(IntelType.THIEVERY_OPERATION)) {
                String extraString = (extra.wasSuccess ? "good" : "bad") + "|" + extra.targetKingdomId + "|" + extra.targetIslandId + "|" + extra.targetProvinceName + "|" + extra.operationIdentifier.toLowerCase() + "|" + extra.thievesSent.toString() + "|" + extra.resultText;
                postIntelRequest.setPostParam("extra", extraString);
            }
        }

        for (String cookie : _cookies) {
            postIntelRequest.setCookie(cookie);
        }
        postIntelRequest.setFollowsRedirects(false);
        postIntelRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                String html = request.getRawResult();
                if (_isLoggedOut(html)) {
                    System.out.println("Intel Submitted: LOGGED OUT DETECTED");
                }
                else {
                    System.out.println("Intel Submitted: "+ html);
                }

                if (_isLoggedOut(request.getRawResult())) {
                    _cookies = null;
                    _onFailure("Logged out.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
            }
        });
    }

    @Override
    public Boolean isLoggedIn() {
        return (_cookies != null && _cookies.size() > 0);
    }

    @Override
    public void setProvinceData(ProvinceData provinceData) { _provinceData = provinceData; }

    @Override
    public void setSubdomain(String subdomain) {
        _domain = subdomain;
    }
}
