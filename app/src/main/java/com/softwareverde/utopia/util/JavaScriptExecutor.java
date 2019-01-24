package com.softwareverde.utopia.util;

public interface JavaScriptExecutor {
    interface Callback {
        void run(String value);
    }

    void loadScript(String javaScript);
    void executeJavaScript(String javaScript, String variableNameOfResult, Callback callback);
}