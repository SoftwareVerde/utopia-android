package com.softwareverde.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

public class WebRequest {
    private synchronized static void debug(String str) {
        System.out.println("com.softwareverde.util :: WebRequest :: "+ str);
    }

    private static String streamToString(final InputStream is) {
        final Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return (s.hasNext() ? s.next() : "");
    }

    public enum RequestType {
        POST, GET
    };

    public interface Callback {
        void run(WebRequest request);
    };

    private String _url;
    private Map<String, String> _getParams;
    private Map<String, String> _postParams;
    private Map<String, List<String>> _arrayGetParams;
    private Map<String, List<String>> _arrayPostParams;
    private boolean _isPost = true;
    private List<String> _setCookies = new LinkedList<String>();
    private Map<String, String> _setHeaders = new HashMap<String, String>();

    private Map<String, List<String>> _headers = null;

    private boolean _resultReady = false;
    private String _rawResult;
    private Integer _responseCode;
    private boolean _followsRedirects = false;

    // NOTE: Handles both android-formatted and ios-formatted cookie strings.
    //  iOS concatenates their cookies into one string, delimited by commas;
    //  Android cookies are separate cookie-records.
    private List<String> _parseCookies(final String cookie) {
        final List<String> cookies = new LinkedList<String>();

        if (cookie.contains(";")) {
            Boolean skipNext = false;
            for (final String cookieSegment : cookie.replaceAll(",", ";").split(";")) {
                if (skipNext) {
                    skipNext = false;
                    continue;
                }

                final String cleanedCookie = cookieSegment.trim();

                if (cleanedCookie.toLowerCase().contains("expires=")) {
                    skipNext = true;
                    continue;
                }
                if (cleanedCookie.toLowerCase().contains("max-age=")) {
                    continue;
                }
                if (cleanedCookie.toLowerCase().contains("path=")) {
                    continue;
                }
                if (cleanedCookie.toLowerCase().contains("httponly")) {
                    continue;
                }

                cookies.add(cleanedCookie);
            }
        }
        else {
            cookies.add(cookie.trim());
        }

        return cookies;
    }

    public WebRequest() {
        _getParams = new HashMap<String, String>();
        _postParams = new HashMap<String, String>();

        _arrayGetParams = new HashMap<String, List<String>>();
        _arrayPostParams = new HashMap<String, List<String>>();
    }
    public void setUrl(String url) {
        _url = url;
    }
    public String getUrl() { return _url; }

    public void setGetParam(String key, String value) {
        if (key == null) { return; }

        if (value == null) {
            if (_getParams.containsKey(key)) {
                _getParams.remove(key);
            }
        }
        else {
            _getParams.put(key, value);
        }
    }

    public void setPostParam(String key, String value) {
        if (key == null) { return; }

        if (value == null) {
            if (_postParams.containsKey(key)) {
                _postParams.remove(key);
            }
        }
        else {
            _postParams.put(key, value);
        }
    }

    public void addGetParam(String key, String value) {
        if (! _arrayGetParams.containsKey(key)) {
            _arrayGetParams.put(key, new ArrayList<String>());
        }

        if (_getParams.containsKey(key)) {
            _getParams.remove(key);
        }

        final List<String> array = _arrayGetParams.get(key);
        array.add(value);
    }

    public void addPostParam(String key, String value) {
        if (! _arrayPostParams.containsKey(key)) {
            _arrayPostParams.put(key, new ArrayList<String>());
        }

        final List<String> array = _arrayPostParams.get(key);
        array.add(value);
    }

    public void setCookie(String cookie) {
        if (cookie.contains(";")) {
            cookie = cookie.substring(0, cookie.indexOf(";"));
        }
        _setCookies.add(cookie);
    }
    public void setHeader(String key, String value) {
        _setHeaders.put(key, value);
    }

    public void setFollowsRedirects(boolean followsRedirects) {
        _followsRedirects = followsRedirects;
    }

    public void setType(WebRequest.RequestType type) {
        switch (type) {
            case POST: {
                    _isPost = true;
                } break;
            case GET: {
                    _isPost = false;
                } break;
            default: break;
        }
    }

    public RequestType getType() {
        return (_isPost ? RequestType.POST : RequestType.GET);
    }

    public Map<String, String> getGetParams() {
        final Map<String, String> getParams = new HashMap<String, String>();
        for (final String key : _getParams.keySet()) {
            final String value = _getParams.get(key);
            getParams.put(key, value);
        }
        return getParams;
    }

    public Map<String, String> getPostParams() {
        final Map<String, String> postParams = new HashMap<String, String>();
        for (final String key : _postParams.keySet()) {
            final String value = _postParams.get(key);
            postParams.put(key, value);
        }
        return postParams;
    }

    public boolean hasResult() {
        return _resultReady;
    }
    public Integer getResponseCode() { return _responseCode; }

    public synchronized Json getJsonResult() {
        if (! _resultReady) return null;

        return Json.fromString(_rawResult);
    }
    public synchronized String getRawResult() { return _rawResult; }
    private synchronized void _setResult(String result) {
        _rawResult = result;
        _resultReady = true;
    }

    public Map<String, List<String>> getHeaders() {
        if (! _resultReady) return null;

        return _headers;
    }

    public List<String> getCookies() {
        if (! _resultReady) return null;
        if (_headers.containsKey("Set-Cookie")) {
            List<String> cookies = new LinkedList<String>();
            for (final String cookie : _headers.get("Set-Cookie")) {
                cookies.addAll(_parseCookies(cookie));
            }

            return cookies;
        }

        return new LinkedList<String>();
    }

    public void execute(boolean nonblocking) {
        this.execute(nonblocking, null);
    }
    public void execute(boolean nonblocking, Callback callback) {
        this._resultReady = false;
        this._rawResult = null;

        if (_url != null) {
            WebRequest.debug("Executing WebRequest: "+ this._url);
            ConnectionThread thread = new ConnectionThread(this, callback);
            if (nonblocking) {
                thread.start();
            }
            else {
                thread.run();
            }
        }
    }

	private class ConnectionThread extends Thread {
        private boolean _isPost;
        private WebRequest _webRequest;
        private Callback _callback;

		public ConnectionThread(WebRequest webRequest, Callback callback) {
            _webRequest = webRequest;
            _callback = callback;

            _isPost = _webRequest._isPost;
		}

		public void run() {
			if (_webRequest._url == null) return;

            URL url = null;
            String get = "";
            try {
                for (String key : _getParams.keySet()) {
                    String value = _getParams.get(key);

                    get += "&";
                    get += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
                }

                for (String key : _arrayGetParams.keySet()) {
                    List<String> values = _arrayGetParams.get(key);

                    for (String value : values) {
                        if (value == null) {
                            continue;
                        }

                        get += "&";
                        get += URLEncoder.encode(key + "[]", "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
                    }
                }
            }
            catch (java.io.UnsupportedEncodingException e) { WebRequest.debug("Exception 1: "+ e.getMessage()); }

			try {
                String address = _webRequest._url;
                if (! address.contains("?")) {
                    address += "?";
                }
				url = new URL(address + get);
			} catch (MalformedURLException e) { WebRequest.debug("Exception 2: "+ e.getMessage()); }

			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(_followsRedirects);
				connection.setUseCaches(false);
                String cookies = "";
                for (String cookie : _setCookies) {
                    cookies += cookie +"; ";
                }
                connection.setRequestProperty("Cookie", cookies);
                // WebRequest.debug("Sending Cookies: "+ cookies);

                for (String key : _setHeaders.keySet()) {
                    String value = _setHeaders.get(key);
                    connection.setRequestProperty(key, value);
                    // WebRequest.debug("Sending Header: "+ key +"="+ value);
                }

                if (_isPost) {
                    String post = "";
                    boolean first_param = true;

                    for (String key : _postParams.keySet()) {
                        String value = _postParams.get(key);

                        if (value == null) {
                            continue;
                        }

                        if (! first_param) post += "&";
                        post += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");

                        first_param = false;
                    }

                    for (String key : _arrayPostParams.keySet()) {
                        List<String> values = _arrayPostParams.get(key);

                        for (String value : values) {
                            if (value == null) {
                                continue;
                            }

                            if (!first_param) post += "&";
                            post += URLEncoder.encode(key + "[]", "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");

                            first_param = false;
                        }
                    }

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Length", Integer.toString(post.length()));
                    connection.setDoOutput(true);

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(post);
                    out.flush();
                    out.close();
                }
                else {
                    connection.setRequestMethod("GET");
                }

                _responseCode = connection.getResponseCode();
                WebRequest.debug("Response Code: " + _responseCode);

                if (_responseCode >= 400) {
                    final String data = streamToString(connection.getErrorStream()).trim();
                    _webRequest._setResult(data);

                    WebRequest.debug("Error Response: "+ _url +": "+ _rawResult);
                }
                else {
                    final String data = streamToString(connection.getInputStream()).trim();
                    _webRequest._setResult(data);
                }

                _headers = connection.getHeaderFields();

				// Close Connection
				connection.disconnect();
			}
            catch (UnknownHostException e) { WebRequest.debug("Exception 3: "+ e.getMessage()); }
            catch (SSLException e) { WebRequest.debug("Exception 4: "+ e.getMessage()); }
            catch (IOException e) { WebRequest.debug("Exception 5: "+ e.getMessage()); }
            catch (Exception e) { WebRequest.debug("Exception 6: "+ e.getMessage()); }

            if (_callback != null) {
                _callback.run(_webRequest);
            }
		}
	}

    @SuppressWarnings("unused")
    private static final Class<?>[] unused = {
        HttpsURLConnection.class
    };
}
