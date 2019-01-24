package com.softwareverde.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

public class WebAppView extends WebView { // implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private synchronized static void debug(String str) {
        System.out.println("com.softwareverde.util :: WebAppView :: "+ str);
    }

    private static String streamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null) {
            return (connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected());
        }

        return false;
    }

    // Execute Param "onConnect" after an internet connection has been confirmed.
    //  Will execute asynchronously if async is true.
    public static void waitForConnection(final Activity activity, final Runnable onConnect) {
        Dialog.setActivity(activity);

        (new Thread() {
            public void run() {
                if (! WebAppView.isConnected(activity)) {
                    Dialog.showProgress("Connecting", "Waiting for an internet connection...\n\nPlease ensure you're connected to the internet.");

                    while (! WebAppView.isConnected(activity)) {
                        try { Thread.sleep(1000); } catch (Exception e) { }
                    }
                    Dialog.hideProgress("Connecting");
                }

                if (onConnect != null) {
                    activity.runOnUiThread(onConnect);
                }
            }
        }).start();
    }

    // DESCRIPTION: A java method invoked by javascript.
    // NOTE:        This class should be considered an interface.
    // CAUTION:     This class isn't executed from the UI Thread.
    public static class JavaScriptMethod {
        public Jsonable run(Json jsonParam) { return null; }
        public String run(String str) { return null; }

        private boolean _useJsonMethod() {
            // Determine which signature was overridden...
            Boolean useJson = false;
            try {
                useJson = (! JavaScriptMethod.class.equals(this.getClass().getMethod("run", Json.class).getDeclaringClass())); // If run(Json) wasn't defined by the anonymous class...
            } catch (Exception e) { debug("jsMethod Exception: "+ e.getMessage()); }
            return useJson;
        }
    }

    private class JsInterface {
        private Map<String, JavaScriptMethod> _methods;

        public JsInterface() {
            _methods = new HashMap<String, JavaScriptMethod>();
        }

        public void defineMethod(String methodName, JavaScriptMethod method) {
            _jsInterface._methods.put(methodName, method);
        }
        public boolean methodExists(String methodName) {
            return _jsInterface._methods.containsKey(methodName);
        }

        @JavascriptInterface
        public String execute(String command, String jsonParam) {
            debug("Executing: "+ command);
            if (_methods.containsKey(command)) {
                JavaScriptMethod method = _methods.get(command);

                String value = "false";
                try {
                    if (method._useJsonMethod()) {
                        Jsonable result;

                        if (jsonParam != null && jsonParam.length() > 0) {
                            result = method.run(Json.fromString(jsonParam));
                        }
                        else {
                            result = method.run(new Json());
                        }

                        if (result != null) {
                            value = result.toString();
                        }
                    }
                    else {
                        value = method.run(jsonParam);
                    }
                } catch (Exception e) { debug("Error executing: \""+ command +"\". An exception occurred: "+ e.getMessage()); }

                return value;
            }
            else {
                debug("WARNING: jsMethod doesn't exist.");
                return "false";
            }
        }
        @JavascriptInterface
        public String execute(String command) {
            return this.execute(command, null);
        }
    }

    private Context _context;
    // private GestureDetectorCompat _gestureDetector;
    private Boolean _jsSwipe;
    private String _jsSwipeFunction;
    private String _jsBackFunction;
    private Float _jsSwipeVelocity; // The minimum velocity required to trigger a swipe.
    private Float _jsSwipeRatio;    // The maximum ratio of X/Y required to trigger a horizontal swipe.(1.0 would be 45 degrees)

    private String _errorPage;
    private String _domain;
    private String _url;

    private Integer _minHistoryIndex;
    volatile private Boolean _isLoading;
    volatile private Runnable _onFinished;
    volatile private Integer _onFinishedLock = new Integer(0);

    private JsInterface _jsInterface;

    private void _configure() {
        this.setPadding(0, 0, 0, 0);
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);

        /*
            _gestureDetector.setOnDoubleTapListener(this);
        */

        WebSettings webSettings = super.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);

        // Added for performance hacking...
        webSettings.setSupportZoom(false);
        // webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setAppCacheEnabled(false);
        webSettings.setAppCachePath("");
        webSettings.setAppCacheMaxSize(5*1024*1024);

        if (android.os.Build.VERSION.SDK_INT >= 19) {   // android.os.Build.VERSION_CODES.KITKAT
            this.setInitialScale(1);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setSupportZoom(false);
        }
        else {
            this.setInitialScale(100);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);        // Deprecated
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH); // Deprecated
        }

        // allow GPS request
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Toast.makeText(_context, consoleMessage.message(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void _init(Context context) {
        _context = context;

        // _gestureDetector = new GestureDetectorCompat(_context, this);
        _jsSwipe = false;
        _jsSwipeFunction = "onSwipe";
        _jsSwipeVelocity = 1000.0f;
        _jsSwipeRatio       = 1.0f;     // (1.0 would be 45 degrees. 2.0 is approximately from corner-to-corner.)

        _jsBackFunction = "onBack";

        _errorPage = null;
        _domain = null;
        _url = null;

        _minHistoryIndex = 0;
        _isLoading = false;
        _onFinished = null;

        _configure();
    }

    private void _setWebViewClient(String url) {
        final String domain = Uri.parse(url).getHost();


        this.setWebViewClient(
            new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url == null) {
                        return false;
                    }

                    if (Uri.parse(url).getHost().endsWith(domain)) {
                        return false;
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }

                @Override
                public void onReceivedError(WebView webView, int errorCode, String description, String url) {
                    if (_errorPage != null) {
                        // WebView.loadData() has a bug and won't always load the resource correctly.
                        //  Using WebView.loadDataWithBaseURL() fixes this bug.
                        //  We cannot use WebAppView.loadData(String) method because its not in the interface.
                        webView.loadDataWithBaseURL(null, _errorPage.replace("__URL__", _url).replace("__DOMAIN__", _domain), "text/html", "UTF-8", (_url!=null?_url:_domain));
                        debug("Error received; error page brought to front.");
                    }
                    else {
                        debug("Error received, but no error page set.");
                    }
                }

                @Override
                public void onLoadResource(WebView webView, String url) {
                    synchronized (_onFinishedLock) {
                        _isLoading = true;
                    }
                }
                @Override
                public void onPageFinished(WebView webView, String url) {
                    synchronized (_onFinishedLock) {
                        if (_onFinished != null) {
                            final Runnable callback = _onFinished;
                            _onFinished = null;
                            _isLoading = false;

                            (new Thread() {
                                @Override
                                public void run() {
                                    synchronized(_onFinishedLock) {
                                        callback.run();
                                    }
                                }
                            }).start();

                        }
                        else {
                            _isLoading = false;
                        }
                    }
                }
            }
        );
    }

    public WebAppView(Context context) {
        super(context);
        _init(context);
    }
    public WebAppView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init(context);
    }
    public WebAppView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        _init(context);
    }
//    public WebAppView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
//        super(context, attrs, defStyle, privateBrowsing);
//        _init(context);
//    }

    // URLs clicked within this domain will stay within the WebView.
    public void setDomain(String url) {
        _domain = url;
        _setWebViewClient(_domain);
    }

    @Override
    public void loadUrl(String url) {
        if (url.indexOf("javascript:") < 0) {
            // Don't override URL's with JavaScript requests.
            //  This is good reason to create an executeJavascript() function...
            _url = url;
        }
        super.loadUrl(url);
    }

    // Execute the callback asynchronously once the current page has finished loading.
    //  If no page is loading, then the callback is executed asynchronously immediately.
    public void onPageFinished(final Runnable callback) {
        synchronized (_onFinishedLock) {
            final Runnable previousOnFinished  = _onFinished;

            if (_isLoading) {
                (new Thread() {
                    @Override
                    public void run() {
                        synchronized (_onFinishedLock) {
                            if (previousOnFinished != null) {
                                previousOnFinished.run();
                            }

                            callback.run();
                            _onFinished = null;
                        }
                    }
                }).start();
            }
            else {
                _onFinished = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (_onFinishedLock) {
                            if (previousOnFinished != null) {
                                previousOnFinished.run();
                            }

                            callback.run();
                            _onFinished = null;
                        }
                    }
                };
            }
        }
    }

    // Will translate swipe motions into JavaScript calls within the web app.
    // Default JS Signature: onSwipe(velocityX, velocityY);
    public void jsSwipe(boolean jsSwipe) {
        _jsSwipe = jsSwipe;
    }

    // The JavaScript function call invoked when a Swipe is detected.
    // The JS function is given two parameters: velocityX, velocityY
    // The parameter value expected is just the function name, not the full signature.
    // Default JS Signature: onSwipe(velocityX, velocityY);
    public void setSwipeFunction(String functionName) {
        _jsSwipeFunction = functionName;
    }

    // Set a custom error page obtained via /res/raw/...
    // __URL__ and __DOMAIN__ will be replaced with this WebView's values.
    public void setErrorPage(int resourceId) {
        try {
            _errorPage = WebAppView.streamToString(getResources().openRawResource(resourceId));
        }
        catch (Exception e) {
            debug("Error defining error-page. ("+ e.getMessage() +")");
        }
    }

    // WebView.loadData() has a bug and won't always load the resource correctly.
    //  Using WebView.loadDataWithBaseURL() fixes this bug.
    public void loadData(String data) {
        this.loadDataWithBaseURL(null, data, "text/html", "UTF-8", (_url!=null?_url:_domain));
    }

    // Assumes the resourceId is a raw HTML file...
    public void loadData(int resourceId) {
        String data = null;
        try {
            data = WebAppView.streamToString(getResources().openRawResource(resourceId));
        }
        catch (Exception e) {
            debug("Error loading data resource. ("+ e.getMessage() +")");
            return;
        }

        if (_url != null) {
            data = data.replace("__URL__", _url);
        }
        if (_domain != null) {
            data = data.replace("__DOMAIN__", _domain);
        }

        // this.loadData(data, "text/html", null);
        this.loadDataWithBaseURL(null, data, "text/html", "UTF-8", (_url!=null?_url:_domain));
    }

    @Override
    public void clearHistory() {
        WebBackForwardList list = this.copyBackForwardList();
        _minHistoryIndex = list.getCurrentIndex();
        debug("Clear History Index: "+ list.getCurrentIndex());

        super.clearHistory();
    }
    @Override
    public boolean canGoBack() {
        WebBackForwardList list = this.copyBackForwardList();
        debug("Can Go Back : "+ list.getCurrentIndex() +" Min: "+ _minHistoryIndex);
        return (list.getCurrentIndex() > _minHistoryIndex);
    }

    public void jsGoBack() {
        this.loadUrl("javascript:(function() { if(! window."+ _jsBackFunction +") window.history.back(); else "+ _jsBackFunction +"(); } )();");
        // this.loadUrl("javascript:"+ _jsBackFunction +"();");
    }

    public void jsDefineMethod(String methodName, JavaScriptMethod method) {
        if (_jsInterface == null) {
            _jsInterface = new JsInterface();
            this.addJavascriptInterface(_jsInterface, "Jackalope");
            debug("Defined interface.");
        }

        debug("Defined method: "+ methodName);
        _jsInterface.defineMethod(methodName, method);
    }
    public boolean jsMethodExists(String methodName) {
        if (_jsInterface == null) {
            return false;
        }

        return _jsInterface.methodExists(methodName);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // _gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    /*
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            if (_jsSwipe) {
                float ratio = ((float) Math.abs(velocityY)) / ((float) Math.abs(velocityX));
                if (Math.abs(velocityX) > _jsSwipeVelocity && ratio < _jsSwipeRatio) {
                    // Swipe Left/Right
                    this.loadUrl("javascript:"+ _jsSwipeFunction +"("+ velocityX +", "+ velocityY +");");
                }
            }

            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) { }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) { }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return true;
        }
    */

    @Override
    public WebSettings getSettings() {
        return super.getSettings();
    }
}
