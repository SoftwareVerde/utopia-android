package com.softwareverde.utopia.util.android;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.softwareverde.utopia.util.Base64Util;
import com.softwareverde.utopia.util.JavaScriptExecutor;

public class AndroidJavaScriptExecutor implements JavaScriptExecutor {
    private final Object _mutex = new Object();

    private Handler _mainThreadHandler;
    private WebView _webView;
    private Base64Util _base64Util = new AndroidBase64Util();
    private Callback _currentCallback;

    private Boolean _isMainThread() {
        return (Thread.currentThread() == _mainThreadHandler.getLooper().getThread());
    }

    private void _waitForWebView() {
        Boolean webViewIsSet;

        synchronized (_mutex) {
            webViewIsSet = (_webView != null);
        }

        while (! webViewIsSet) {
            try { Thread.sleep(100L); } catch (Exception e) { }

            synchronized (_mutex) {
                webViewIsSet = (_webView != null);
            }
        }
    }

    private void _initWebView(final Context context) {
        _webView = new WebView(context.getApplicationContext());
        _webView.getSettings().setJavaScriptEnabled(true);
        _webView.addJavascriptInterface(AndroidJavaScriptExecutor.this, "android");

        final String html = "<!DOCTYPE HTML>\n<html><head></head><body></body></html>";
        _webView.loadData(html, "text/html", "UTF-8");
    }

    public AndroidJavaScriptExecutor(final Context context) {
        _mainThreadHandler = new Handler(Looper.getMainLooper());

        if (_isMainThread()) {
            _initWebView(context);
        }
        else {
            _mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    synchronized (_mutex) {
                        _initWebView(context);
                    }
                }
            });

            _waitForWebView();
        }
    }

    @JavascriptInterface
    public void onJsResult(final String value) {
        final Callback callback = _currentCallback;
        _currentCallback = null;

        if (callback != null) {
            callback.run(value);
        }
    }

    @Override
    public void loadScript(final String javaScript) {
        _waitForWebView();

        _mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final String base64 = _base64Util.encodeString("<script>"+ javaScript +"</script>");
                _webView.loadUrl("data:text/html;charset=utf-8;base64," + base64);
            }
        });
    }

    @Override
    public void executeJavaScript(final String javaScript, final String variableNameOfResult, final Callback callback) {
        _waitForWebView();

        _mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                _currentCallback = callback;
                _webView.loadUrl("javascript:"+ javaScript + "android.onJsResult("+ variableNameOfResult +");");
            }
        });
    }
}