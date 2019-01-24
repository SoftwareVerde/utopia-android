package com.softwareverde.utopia;

import com.softwareverde.util.Json;
import com.softwareverde.util.Util;
import com.softwareverde.util.WebRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProvinceTagUtil {
    private static final Integer _sharedPrime = 92107;

    public static class Response {
        private Boolean _wasSuccess = false;
        private String _errorMessage = null;

        public Response() { }
        public Response(Boolean wasSuccess, String errorMessage) {
            _wasSuccess = wasSuccess;
            _errorMessage = errorMessage;
        }

        public Boolean getWasSuccess() { return _wasSuccess; }

        public String getErrorMessage() {
            if (_wasSuccess) return null;
            return _errorMessage;
        }
    }

    public interface Callback {
        void run(Response response);
    }

    public static class ProvinceTagResponse extends Response {
        private List<ProvinceTag> _provinceTags = new ArrayList<ProvinceTag>();

        public ProvinceTagResponse() { }
        public ProvinceTagResponse(Boolean wasSuccess, String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public void addProvinceTag(final ProvinceTag provinceTag) {
            _provinceTags.add(provinceTag);
        }

        public List<ProvinceTag> getProvinceTags() {
            return _provinceTags;
        }
    }
    public interface ProvinceTagCallback {
        void run (ProvinceTagResponse response);
    }

    public static class ProvinceTag {
        public static ProvinceTag fromJson(final Json json) {
            final ProvinceTag provinceTag = new ProvinceTag();

            provinceTag.id = Util.parseInt(json.get("id", Json.Types.STRING));

            provinceTag.kingdom = Util.parseInt(json.get("kingdom", Json.Types.STRING));
            provinceTag.island = Util.parseInt(json.get("island", Json.Types.STRING));

            provinceTag.sentTime = Util.datetimeToTimestamp(json.get("sent_date", Json.Types.STRING)) * 1000L;

            provinceTag.toProvince = json.get("to_province", Json.Types.STRING);
            provinceTag.fromProvince = json.get("from_province", Json.Types.STRING);

            provinceTag.message = json.get("message", Json.Types.STRING);

            provinceTag.reverbimId = json.get("reverbim_id", Json.Types.STRING);
            if (provinceTag.reverbimId.length() == 0) {
                provinceTag.reverbimId = null;
            }

            return provinceTag;
        }

        Integer id = 0;

        Integer kingdom = 0;
        Integer island = 0;

        String toProvince = "";
        String fromProvince = "";

        Long sentTime = 0L;

        String message = "";

        String reverbimId = null;

        public String concatenate() {
            return this.island.toString() + this.kingdom.toString() + this.toProvince + this.fromProvince + this.message;
        }
    }

    public static List<String> calculateProvinceTagSuggestions(final String inputText, final List<Province> provinces) {
        final String lowercaseInputText = inputText.toLowerCase();

        final Integer startIndex = inputText.indexOf("@") + 1;

        Integer minMatchLength = 0;
        for (Integer i=startIndex; i<inputText.length(); ++i) {
            if (inputText.charAt(i) == ' ') { break; }
            minMatchLength += 1;
        }

        final Map<String, Integer> provinceMatchMap = new HashMap<String, Integer>();

        Integer mostMatchLength = 0;

        for (final Province province : provinces) {
            final String provinceName = province.getName();
            final String lowercaseProvinceName = provinceName.toLowerCase();

            provinceMatchMap.put(provinceName, 0);

            if (startIndex + 1 > inputText.length()) { continue; }

            for (Integer i=1; i <= provinceName.length(); ++i) {

                if (lowercaseInputText.length() < startIndex + i) { break; }

                final String inputMatch = lowercaseInputText.substring(startIndex, startIndex + i);
                final String provinceMatch = lowercaseProvinceName.substring(0, i);

                if (! inputMatch.equals(provinceMatch)) {
                    break;
                }

                provinceMatchMap.put(provinceName, i);

                if (i > mostMatchLength) {
                    mostMatchLength = i;
                }
            }
        }

        final List<String> provinceTagSuggestions = new ArrayList<String>();
        for (final String provinceName : provinceMatchMap.keySet()) {
            final Integer provinceMatchLength = provinceMatchMap.get(provinceName);

            if (provinceMatchLength < minMatchLength) { continue; }

            if (provinceName.length() == provinceMatchLength) { continue; } // Don't suggest a fully-matched province...

            if (provinceMatchLength.equals(mostMatchLength)) {
                provinceTagSuggestions.add(provinceName);
            }
        }

        return provinceTagSuggestions;
    }

    public static String applyProvinceTagSuggestion(final String inputText, final String provinceName) {
        final Integer startIndex = inputText.indexOf("@");
        Integer endIndex = startIndex+1;

        while (endIndex < inputText.length()) {
            final String matchString = inputText.substring(startIndex+1, endIndex+1);
            if (matchString.length() > provinceName.length()) {
                break;
            }

            if (! matchString.equalsIgnoreCase(provinceName.substring(0, matchString.length()))) {
                break;
            }

            endIndex += 1;
        }

        if (endIndex >= inputText.length()) {
            endIndex = inputText.length() - 1;
        }

        String newText = inputText.substring(0, startIndex);
        newText += "@"+ provinceName +" ";
        if (endIndex < inputText.length()+1) {
            newText += inputText.substring(endIndex + 1);
        }

        return newText;
    }

    private void _onFailure(String message, Callback callback) {
        if (callback != null) {
            callback.run(new Response(false, message));
        }
    }

    private void _onFailure(String message, ProvinceTagCallback callback) {
        if (callback != null) {
            callback.run(new ProvinceTagResponse(false, message));
        }
    }

    private Integer _stringToInt(final String string) {
        Integer sum = 0;
        for (Integer i=0; i<string.length(); ++i) {
            sum += string.charAt(i);
        }
        return sum;
    }

    public void sendProvinceTag(final ProvinceTag provinceTag, final Callback callback) {
        final Integer key = _stringToInt(Util.md5(provinceTag.concatenate())) * _sharedPrime;

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getSendProvinceTagUrl());
        webRequest.setPostParam("kingdom", provinceTag.kingdom.toString());
        webRequest.setPostParam("island", provinceTag.island.toString());
        webRequest.setPostParam("to_province", provinceTag.toProvince);
        webRequest.setPostParam("from_province", provinceTag.fromProvince);
        webRequest.setPostParam("message", provinceTag.message);

        if (provinceTag.sentTime != null) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            final String sentDate = dateFormat.format(new Date(provinceTag.sentTime));
            webRequest.setPostParam("sent_date", sentDate);
        }

        if (provinceTag.reverbimId != null) {
            webRequest.setPostParam("reverbim_id", provinceTag.reverbimId);
        }

        webRequest.setPostParam("key", key.toString());
        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final Json response = request.getJsonResult();
                if (response.get("was_success", Json.Types.INTEGER) == 0) {
                    _onFailure(response.get("error_message", Json.Types.STRING), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
            }
        });
    }

    public void getProvinceTags(final String province, final Integer kingdom, final Integer island, final ProvinceTagCallback callback) {
        final Integer payloadInt = _stringToInt(Util.md5(island.toString() + kingdom.toString() + province));
        final Integer key = payloadInt * _sharedPrime;

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getProvinceTagsUrl());
        webRequest.setPostParam("kingdom", kingdom.toString());
        webRequest.setPostParam("island", island.toString());
        webRequest.setPostParam("province", province);
        webRequest.setPostParam("key", key.toString());
        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final Json jsonResponse = request.getJsonResult();
                if (jsonResponse.get("was_success", Json.Types.INTEGER) == 0) {
                    _onFailure(jsonResponse.get("error_message", Json.Types.STRING), callback);
                    return;
                }

                final ProvinceTagResponse response = new ProvinceTagResponse(true, null);

                final Json jsonProvinceTags = jsonResponse.get("province_tags");
                for (Integer i=0; i<jsonProvinceTags.length(); i++) {
                    final Json jsonProvinceTag = jsonProvinceTags.get(i);
                    response.addProvinceTag(ProvinceTag.fromJson(jsonProvinceTag));
                }

                if (callback != null) {
                    callback.run(response);
                }
            }
        });
    }

    public void registerApnsToken(final String apnsToken, final String provinceName, final Integer kingdom, final Integer island, final Callback callback) {
        final Integer payloadInt = _stringToInt(Util.md5(apnsToken + island.toString() + kingdom.toString() + provinceName));
        final Integer key = payloadInt * _sharedPrime;

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getApnsRegistrationUrl());
        webRequest.setPostParam("apns_token", apnsToken);
        webRequest.setPostParam("kingdom", kingdom.toString());
        webRequest.setPostParam("island", island.toString());
        webRequest.setPostParam("province", provinceName);
        webRequest.setPostParam("key", key.toString());
        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final Json jsonResponse = request.getJsonResult();
                if (jsonResponse.get("was_success", Json.Types.INTEGER) == 0) {
                    _onFailure(jsonResponse.get("error_message", Json.Types.STRING), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null));
                }
            }
        });
    }
}
