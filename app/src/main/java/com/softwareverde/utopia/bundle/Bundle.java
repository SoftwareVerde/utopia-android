package com.softwareverde.utopia.bundle;

import com.softwareverde.util.Json;
import com.softwareverde.util.Jsonable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Bundle implements Jsonable {
    public static final String BUNDLE_TYPE = "BUNDLE";
    public static class Keys {
        public static final String DATE = "DATE";
        public static final String CURRENT_MONEY = "CURRENT_MONEY";
        public static final String CURRENT_PEASANTS = "CURRENT_PEASANTS";
        public static final String CURRENT_FOOD = "CURRENT_FOOD";
        public static final String CURRENT_RUNES = "CURRENT_RUNES";
        public static final String CURRENT_NETWORTH = "CURRENT_NETWORTH";
        public static final String CURRENT_LAND = "CURRENT_LAND";
    }

    protected List<String> _requiredKeys = Arrays.asList(Keys.DATE, Keys.CURRENT_MONEY, Keys.CURRENT_PEASANTS, Keys.CURRENT_FOOD, Keys.CURRENT_RUNES, Keys.CURRENT_NETWORTH, Keys.CURRENT_LAND);

    protected Map<String, String> _values = new HashMap<String, String>();
    protected Map<String, Bundle> _bundles = new HashMap<String, Bundle>();
    protected Map<String, List<Bundle>> _groupedBundles = new HashMap<String, List<Bundle>>();

    protected List<String> _appendRequiredKeys(List<String> appendedKeys) {
        List<String> newKeys = new LinkedList<String>();

        for (String key : _requiredKeys) {
            newKeys.add(key);
        }
        for (String key : appendedKeys) {
            newKeys.add(key);
        }

        return newKeys;
    }

    public String getBundleType() { return BUNDLE_TYPE; }

    public List<String> getRequiredKeys() {
        return _requiredKeys;
    }

    public void put(String key, String value) {
        _values.put(key, value);
    }

    public void put(String key, Bundle value) {
        _bundles.put(key, value);
    }

    public void addToGroup(String key, Bundle value) {
        if (! _groupedBundles.containsKey(key)) {
            _groupedBundles.put(key, new ArrayList<Bundle>());
        }
        _groupedBundles.get(key).add(value);
    }

    public String get(String key) {
        return _values.get(key);
    }

    public Boolean hasKey(String key) {
        return _values.containsKey(key);
    }

    public Boolean isValid() {
        for (final String key : _requiredKeys) {
            if (! (_values.containsKey(key) || _bundles.containsKey(key) || _groupedBundles.containsKey(key))) {
                return false;
            }
        }

        for (final String subBundleKeys : _bundles.keySet()) {
            if (! _bundles.get(subBundleKeys).isValid()) {
                return false;
            }
        }

        for (final String groupedSubBundleKeys : _groupedBundles.keySet()) {
            final List<Bundle> groupedSubBundles = _groupedBundles.get(groupedSubBundleKeys);
            for (final Bundle subBundle : groupedSubBundles) {
                if (! subBundle.isValid()) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<String> getMissingKeys() {
        final List<String> missingKeys = new ArrayList<String>();
        for (String key : _requiredKeys) {
            if (! (_values.containsKey(key) || _bundles.containsKey(key) || _groupedBundles.containsKey(key))) {
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }

    public Bundle getBundle(String key) {
        return _bundles.get(key);
    }

    public Boolean hasBundleKey(String key) {
        return _bundles.containsKey(key);
    }

    public List<Bundle> getGroup(String key) {
        return _groupedBundles.get(key);
    }

    public Boolean hasGroupKey(String key) {
        return _groupedBundles.containsKey(key);
    }

    public void setJsonParameters(final String key, final String value) {
        // Nothing.
    }

    public Json toJson() {
        final Json json = new Json();
        final String type = this.getBundleType();

        final Json jsonValues = new Json();
        for (final String key : _values.keySet()) {
            final String value = _values.get(key);
            jsonValues.put(key, value);
        }

        final Json jsonBundles = new Json();
        for (final String key : _bundles.keySet()) {
            final Bundle value = _bundles.get(key);
            jsonBundles.put(key, value.toJson());
        }

        final Json jsonGroupedBundles = new Json();
        for (final String key : _groupedBundles.keySet()) {
            final Json jsonGroupedBundle = new Json();
            final List<Bundle> groupedBundle = _groupedBundles.get(key);
            for (final Bundle bundle : groupedBundle) {
                jsonGroupedBundle.add(bundle.toJson());
            }
            jsonGroupedBundles.put(key, jsonGroupedBundle);
        }

        json.put("TYPE", type);
        json.put("VALUES", jsonValues);
        json.put("BUNDLES", jsonBundles);
        json.put("GROUPED_BUNDLES", jsonGroupedBundles);

        return json;
    }

    @Override
    public boolean equals(final Object rhs) {
        if (this == rhs) { return true; }
        if (! (rhs instanceof Bundle)) { return false; }

        final Bundle rhsBundle = (Bundle) rhs;

        if (_requiredKeys != null ? (! _requiredKeys.equals(rhsBundle._requiredKeys)) : rhsBundle._requiredKeys != null) { return false; }
        if (_values != null ? (! _values.equals(rhsBundle._values)) : rhsBundle._values != null) { return false; }
        if (_bundles != null ? (! _bundles.equals(rhsBundle._bundles)) : rhsBundle._bundles != null) { return false; }
        if (_groupedBundles != null ? (! _groupedBundles.equals(rhsBundle._groupedBundles)) : rhsBundle._groupedBundles != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = _requiredKeys != null ? _requiredKeys.hashCode() : 0;
        result = 31 * result + (_values != null ? _values.hashCode() : 0);
        result = 31 * result + (_bundles != null ? _bundles.hashCode() : 0);
        result = 31 * result + (_groupedBundles != null ? _groupedBundles.hashCode() : 0);
        return result;
    }
}
