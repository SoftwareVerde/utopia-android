package com.softwareverde.utopia.intelsync;

import com.softwareverde.util.Json;
import com.softwareverde.util.Util;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.Settings;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.BundleFactory;
import com.softwareverde.utopia.util.Base64Util;

import java.util.ArrayList;
import java.util.List;

public class VerdeIntelUtil implements IntelSync {
    private static final Integer _sharedPrime = 1;

    public static class Dependencies {
        private Base64Util _base64Util;
        private BundleFactory _bundleFactory;

        public void setBase64Util(final Base64Util base64Util) { _base64Util = base64Util; }
        public void setBundleFactory(final BundleFactory bundleFactory) { _bundleFactory = bundleFactory; }
    }

    public static class DownloadIntelResponse extends IntelSync.Response {
        private List<Bundle> _bundles = new ArrayList<Bundle>();

        public DownloadIntelResponse(final Boolean wasSuccess, final String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public List<Bundle> getBundles() { return _bundles; }
    }

    public static class DownloadIntelAvailableCountResponse extends IntelSync.Response {
        private List<AvailableIntel> _availableIntel = new ArrayList<AvailableIntel>();

        public DownloadIntelAvailableCountResponse(final Boolean wasSuccess, final String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public List<AvailableIntel> getAvailableIntel() { return _availableIntel; }
    }

    public static class AvailableIntel {
        private static AvailableIntel fromJson(final Json json) {
            final AvailableIntel availableIntel = new AvailableIntel();
            availableIntel._provinceName = json.get("province_name", Json.Types.STRING);
            availableIntel._kingdomIdentifier = new Kingdom.Identifier(json.get("kingdom", Json.Types.INTEGER), json.get("island", Json.Types.INTEGER));
            availableIntel._intelCount = json.get("intel_count", Json.Types.INTEGER);

            final String datetimeString = json.get("date", Json.Types.STRING);
            availableIntel._lastIntelUpdateTime = Util.datetimeToTimestamp(datetimeString) * 1000L;
            return availableIntel;
        }

        private String _provinceName;
        private Kingdom.Identifier _kingdomIdentifier;
        private Integer _intelCount;
        private Long _lastIntelUpdateTime;

        private AvailableIntel() { }

        public String getProvinceName() { return _provinceName; }
        public Kingdom.Identifier getKingdomIdentifier() { return _kingdomIdentifier; }
        public Integer getIntelCount() { return _intelCount; }
        public Long getLastUpdateTime() { return _lastIntelUpdateTime; }
    }

    public interface DownloadIntelCallback {
        void run(DownloadIntelResponse downloadIntelResponse);
    }

    public interface DownloadAvailableIntelCountCallback {
        void run(DownloadIntelAvailableCountResponse downloadIntelResponse);
    }

    private final Base64Util _base64Util;
    private final BundleFactory _bundleFactory;
    private Boolean _isLoggedIn;
    private ProvinceData _provinceData;

    private Integer _stringToInt(final String string) {
        Integer sum = 0;
        for (Integer i=0; i<string.length(); ++i) {
            sum += string.charAt(i);
        }
        return sum;
    }

    public VerdeIntelUtil(final Dependencies dependencies) {
        _base64Util = dependencies._base64Util;
        _bundleFactory = dependencies._bundleFactory;

        _isLoggedIn = false;
    }

    public void downloadIntelAvailableCount(final Kingdom.Identifier kingdomIdentifier, final DownloadAvailableIntelCountCallback callback) {
        if (_provinceData == null) {
            if (callback != null) {
                callback.run(new DownloadIntelAvailableCountResponse(false, "Province not set."));
            }
            return;
        }

        final Integer targetKingdomId = kingdomIdentifier.getKingdomId();
        final Integer targetIslandId = kingdomIdentifier.getIslandId();

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getVerdeAvailableIntelUrl());

        webRequest.setPostParam("source_province", _provinceData.provinceName);
        webRequest.setPostParam("source_kingdom", _provinceData.kingdomId.toString());
        webRequest.setPostParam("source_island", _provinceData.islandId.toString());

        webRequest.setPostParam("target_kingdom", targetKingdomId.toString());
        webRequest.setPostParam("target_island", targetIslandId.toString());

        final Integer key = 1;
        webRequest.setPostParam("key", key.toString());

        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new DownloadIntelAvailableCountResponse(false, "Unable to connect."));
                    }
                    return;
                }

                final Json jsonResponse = request.getJsonResult();
                if (jsonResponse.get("was_success", Json.Types.INTEGER) <= 0) {
                    if (callback != null) {
                        final String errorMessage = jsonResponse.get("error_message", Json.Types.STRING);
                        callback.run(new DownloadIntelAvailableCountResponse(false, errorMessage));
                    }
                    return;
                }

                final DownloadIntelAvailableCountResponse downloadIntelResponse = new DownloadIntelAvailableCountResponse(true, null);
                final Json bundlesJson = jsonResponse.get("available_intel");
                for (Integer i=0; i<bundlesJson.length(); ++i) {
                    final Json availableIntelCountJson = bundlesJson.get(i);
                    final AvailableIntel availableIntel = AvailableIntel.fromJson(availableIntelCountJson);
                    downloadIntelResponse._availableIntel.add(availableIntel);
                }

                if (callback != null) {
                    callback.run(downloadIntelResponse);
                }
            }
        });
    }


    public void downloadProvinceIntel(final ProvinceData targetProvince, final DownloadIntelCallback callback) {
        if (_provinceData == null) {
            if (callback != null) {
                callback.run(new DownloadIntelResponse(false, "Province not set."));
            }
            return;
        }

        final String targetProvinceName = targetProvince.provinceName;
        final Integer targetKingdomId = targetProvince.kingdomId;
        final Integer targetIslandId = targetProvince.islandId;

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getVerdeProvinceIntelUrl());

        webRequest.setPostParam("source_province", _provinceData.provinceName);
        webRequest.setPostParam("source_kingdom", _provinceData.kingdomId.toString());
        webRequest.setPostParam("source_island", _provinceData.islandId.toString());

        webRequest.setPostParam("target_province", targetProvinceName);
        webRequest.setPostParam("target_kingdom", targetKingdomId.toString());
        webRequest.setPostParam("target_island", targetIslandId.toString());

        final Integer key = 1;
        webRequest.setPostParam("key", key.toString());

        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new DownloadIntelResponse(false, "Unable to connect."));
                    }
                    return;
                }

                final Json jsonResponse = request.getJsonResult();
                if (jsonResponse.get("was_success", Json.Types.INTEGER) <= 0) {
                    if (callback != null) {
                        final String errorMessage = jsonResponse.get("error_message", Json.Types.STRING);
                        callback.run(new DownloadIntelResponse(false, errorMessage));
                    }
                    return;
                }

                final DownloadIntelResponse downloadIntelResponse = new DownloadIntelResponse(true, null);
                final Json bundlesJson = jsonResponse.get("bundles");
                for (Integer i=0; i<bundlesJson.length(); ++i) {
                    final String bundleAsString = _base64Util.decodeString(bundlesJson.get(i, Json.Types.STRING));
                    final Bundle bundle = _bundleFactory.createBundle(bundleAsString);
                    downloadIntelResponse._bundles.add(bundle);
                }

                if (callback != null) {
                    callback.run(downloadIntelResponse);
                }
            }
        });
    }

    public void logout() { _isLoggedIn = false; }

    @Override
    public void setProvinceData(final ProvinceData provinceData) {
        _provinceData = provinceData;
    }

    @Override
    public void login(final String username, final String password, final IntelSync.Callback callback) {
        _isLoggedIn = true;
        if (callback != null) {
            callback.run(new IntelSync.Response(true, null));
        }
    }

    @Override
    public Boolean canProcessIntel(final String html, final Extra extra) {
        return (
            (extra.bundle != null) && (extra.intelType != null)
        );
    }

    @Override
    public void submitIntel(final String html, final Extra extra, final IntelSync.Callback callback) {
        if (_provinceData == null) { return; }

        final String targetProvinceName;
        final Integer targetKingdomId;
        final Integer targetIslandId;

        if (extra.hasTargetSet()) {
            targetProvinceName = extra.targetProvinceName;
            targetKingdomId = extra.targetKingdomId;
            targetIslandId = extra.targetIslandId;
        }
        else {
            targetProvinceName = _provinceData.provinceName;
            targetKingdomId = _provinceData.kingdomId;
            targetIslandId = _provinceData.islandId;
        }

        final Bundle bundle = extra.bundle;
        final String bundlePayload = _base64Util.encodeString(bundle.toJson().toString());

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getSubmitVerdeIntelUrl());
        webRequest.setPostParam("bundle_type", bundle.getBundleType());
        webRequest.setPostParam("bundle", bundlePayload);

        webRequest.setPostParam("source_province", _provinceData.provinceName);
        webRequest.setPostParam("source_kingdom", _provinceData.kingdomId.toString());
        webRequest.setPostParam("source_island", _provinceData.islandId.toString());

        webRequest.setPostParam("target_province", targetProvinceName);
        webRequest.setPostParam("target_kingdom", targetKingdomId.toString());
        webRequest.setPostParam("target_island", targetIslandId.toString());

        final Integer key = 1;
        webRequest.setPostParam("key", key.toString());

        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new Response(false, "Unable to connect."));
                    }
                    return;
                }

                final Json response = request.getJsonResult();
                if (response.get("was_success", Json.Types.INTEGER) <= 0) {
                    if (callback != null) {
                        final String errorMessage = response.get("error_message", Json.Types.STRING);
                        callback.run(new Response(false, errorMessage));
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
    public Boolean isLoggedIn() { return _isLoggedIn; }

    @Override
    public void setSubdomain(final String subdomain) { }
}