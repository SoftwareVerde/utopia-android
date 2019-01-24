package com.softwareverde.utopia.util.android;

import android.util.Base64;

import com.softwareverde.utopia.util.Base64Util;

import java.io.UnsupportedEncodingException;

public class AndroidBase64Util implements Base64Util {
    @Override
    public String encodeString(final String string) {
        return Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public String decodeString(String string) {
        try {
            return new String(Base64.decode(string, Base64.DEFAULT), "UTF-8");
        }
        catch (final UnsupportedEncodingException e) {
            return "";
        }
    }
}
