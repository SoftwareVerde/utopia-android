package com.softwareverde.utopia.database;

import android.content.Context;
import android.content.SharedPreferences;

public class AndroidKeyValueStore implements KeyValueStore {
    public static class Stores {
        public static final String SESSION = "session";
        public static final String MAIN_ACTIVITY = "main_activity";
        public static final String TUTORIALS = "tutorials";
        public static final String COMMUNICATION_FRAGMENT = "communication_fragment";
        public static final String NEWS_PARSER = "news_parser";
    }

    public static final String[] STORES = new String[] {
            Stores.SESSION, Stores.MAIN_ACTIVITY, Stores.TUTORIALS, Stores.COMMUNICATION_FRAGMENT, Stores.NEWS_PARSER
    };

    public static void clearAll(final Context context) {
        for (final String storeName : STORES) {
            (new AndroidKeyValueStore(context, storeName)).clear();
        }
    }

    private Context _context;
    private SharedPreferences _store;
    private String _schemaName;

    public AndroidKeyValueStore(Context context, String schemaName) {
        _context = context;
        _schemaName = schemaName;
        _store = _context.getSharedPreferences(_schemaName, Context.MODE_PRIVATE);
    }

    @Override
    public String getString(String key) {
        return _store.getString(key, "");
    }

    @Override
    public void putString(String key, String value) {
        if (value == null) value = "";

        _store.edit().putString(key, value).apply();
    }

    @Override
    public boolean hasKey(String key) {
        return _store.contains(key);
    }

    @Override
    public void removeKey(String key) {
        _store.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        _store.edit().clear().commit();
    }

    @Override
    public void clearAllStores() {
        AndroidKeyValueStore.clearAll(_context);
    }
}
