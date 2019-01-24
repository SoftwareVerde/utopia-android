package com.softwareverde.utopia;

import com.softwareverde.util.Json;
import com.softwareverde.util.Util;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.bundle.ActiveSpellsBundle;
import com.softwareverde.utopia.bundle.ArmyOffenseBundle;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.AttackDragonInfoBundle;
import com.softwareverde.utopia.bundle.AvailableSpellsBundle;
import com.softwareverde.utopia.bundle.AvailableThieveryOperationsBundle;
import com.softwareverde.utopia.bundle.BuildCostBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ChatroomBundle;
import com.softwareverde.utopia.bundle.ExplorationCostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicsBundle;
import com.softwareverde.utopia.bundle.FundDragonInfoBundle;
import com.softwareverde.utopia.bundle.InfiltrateThievesBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomIntelBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.MilitarySettingsBundle;
import com.softwareverde.utopia.bundle.NewspaperBundle;
import com.softwareverde.utopia.bundle.PrivateMessageBundle;
import com.softwareverde.utopia.bundle.PrivateMessagesBundle;
import com.softwareverde.utopia.bundle.ProvinceIdsBundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.ScienceResultBundle;
import com.softwareverde.utopia.bundle.SendAidBundle;
import com.softwareverde.utopia.bundle.SpellBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.StateCouncilBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;
import com.softwareverde.utopia.bundle.TradeSettingsBundle;
import com.softwareverde.utopia.bundle.WarRoomBundle;
import com.softwareverde.utopia.intelsync.IntelSubmitter;
import com.softwareverde.utopia.parser.HtmlParser;
import com.softwareverde.utopia.parser.UtopiaParser;
import com.softwareverde.utopia.util.BuildVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtopiaUtil {
    private static final Json _monthIndexTranslations = Json.fromString("{JAN: 0, FEB: 1, MAR: 2, APR: 3, MAY: 4, JUN: 5, JUL: 6}");
    private static final Json _monthTickTranslations = Json.fromString("{JAN: 0, FEB: 24, MAR: 48, APR: 72, MAY: 96, JUN: 120, JUL: 144}");

    private static class ExecuteWebRequestSettings {
        Boolean executeAsynchronously = true;
        Boolean autoSetCookies = true;
        Boolean detectServerTick = true;
        Boolean detectLogout = true;
        Boolean detectServerError = true;
        Boolean ignoreCache = false;
    }

    public static final String BAD_ACCESS_EXTRA = "UtopiaUtil.BAD_ACCESS_EXTRA";

    public static class Response {
        private Boolean _wasSuccess = false;
        private String _errorMessage = null;
        private Bundle _bundle = null;
        private Map<String, Object> _extras = new HashMap<String, Object>();

        public Response() { }
        public Response(Boolean wasSuccess, String errorMessage, Bundle bundle) {
            _wasSuccess = wasSuccess;
            _errorMessage = errorMessage;
            _bundle = bundle;
        }

        public Boolean getWasSuccess() { return _wasSuccess; }
        public Bundle getBundle() { return _bundle; }
        public Boolean hasBundle() { return (_bundle != null); }
        public Boolean hasExtra(String key) { return _extras.containsKey(key); }
        public void putExtra(String key, Object value) { _extras.put(key, value); }
        public Object getExtra(String key) { return _extras.get(key); }

        public String getErrorMessage() {
            if (_wasSuccess) return null;
            return _errorMessage;
        }
    }

    public interface OnLoggedOutCallback {
        void run(UtilLoggedBackInCallback loggedInCallback);
    }

    public interface UtilLoggedBackInCallback {
        void onLogin();
    }

    public interface Callback {
        void run(Response response);
    }

    public interface AdViewCallback {
        void run(String html);
    }

    public interface OnServerTickCallback {
        void run(Boolean serverIsTicking);
    }

    public static class Dependencies {
        private BuildVersion _buildVersion = null;
        private HtmlParser _htmlParser = null;
        private UtopiaParser _utopiaParser = null;

        public void setBuildVersion(final BuildVersion buildVersion) { _buildVersion = buildVersion; }
        public void setHtmlParser(final HtmlParser htmlParser) { _htmlParser = htmlParser; }
        public void setUtopiaParser(final UtopiaParser utopiaParser) { _utopiaParser = utopiaParser; }
    }

    public static class InstanceNotAvailableException extends RuntimeException {
        public InstanceNotAvailableException() {
            super("UtopiaUtil instance not available. Have you called UtopiaUtil.setDependencies()?");
        }
    }

    private static Dependencies _dependencies;
    public static void setDependencies(final Dependencies dependencies) {
        if (_instance == null) {
            _dependencies = dependencies;
        }
        else {
            System.out.println("NOTICE: Ignoring dependencies; UtopiaUtil instance already created.");
        }
    }

    private static UtopiaUtil _instance;
    public static UtopiaUtil getInstance() {
        if (_dependencies == null) {
            throw new InstanceNotAvailableException();
        }

        if (_instance == null) {
            _instance = new UtopiaUtil(_dependencies);
        }

        return _instance;
    }

    public void addAdViewCallback(String identifier, AdViewCallback callback) {
        _adViewCallbacks.put(identifier, callback);
    }

    public void removeAdViewCallback(String identifier) {
        if (_adViewCallbacks.containsKey(identifier)) {
            _adViewCallbacks.remove(identifier);
        }
    }

    private BuildVersion _buildVersion;
    private HtmlParser _htmlParser;
    private UtopiaParser _utopiaParser;
    private IntelSubmitter _intelSubmitter;

    private Map<String, AdViewCallback> _adViewCallbacks = new HashMap<String, AdViewCallback>();
    private Map<String, String> _receivedCookies = new HashMap<String, String>();
    private Kingdom.Identifier _currentKingdom;
    private Boolean _serverIsCurrentlyTicking = false;
    private OnServerTickCallback _onServerTickCallback;
    private OnLoggedOutCallback _onLoggedOutCallback = null;

    private final UtopiaCache _utopiaCache = new UtopiaCache();

    private Boolean _hasErrors(String html) {
        HtmlParser.Document dom = _htmlParser.parse(html);
        HtmlParser.Elements errorElements = dom.select(".error, .errorlist");
        return (errorElements.getCount() > 0);
    }

    private String _getErrors(String html) {
        HtmlParser.Document dom = _htmlParser.parse(html);
        HtmlParser.Elements elements = dom.select(".error, .errorlist");
        String errorText = "";
        for (HtmlParser.Element element : elements) {
            errorText += element.getText() +"\n";
        }
        return errorText;
    }

    public static Integer getUtopianYear(String utopiaDate) {
        return _getUtopianYear(utopiaDate);
    }

    private static Integer _getUtopianYear(String utopiaDate) {
        if (utopiaDate == null || utopiaDate.length() == 0) {
            return null;
        }

        final String identifier = "YR";
        if (! utopiaDate.contains(identifier)) {
            return null;
        }
        return Util.parseInt(utopiaDate.substring(utopiaDate.indexOf(identifier) + identifier.length()));
    }

    public static Integer getUtopianMonthIndex(String utopiaDate) {
        return _getUtopianMonthIndex(utopiaDate);
    }

    private static Integer _getUtopianMonthIndex(String utopiaDate) {
        if (utopiaDate == null || utopiaDate.length() == 0) {
            return null;
        }

        if (utopiaDate.length() < 3) {
            return null;
        }
        String monthName = utopiaDate.trim().substring(0, 3); // utopiaDate.indexOf(identifier));
        monthName = monthName.toUpperCase().trim();

        String monthIndex = UtopiaUtil._monthIndexTranslations.get(monthName, Json.Types.STRING);
        if (monthIndex.length() == 0) {
            return null;
        }

        return Util.parseInt(monthIndex);
    }

    public static String getUtopianMonthFromIndex(Integer index) {
        return _getUtopianMonthFromIndex(index);
    }

    private static String _getUtopianMonthFromIndex(Integer index) {
        String monthString = "";

        for (String monthIndexKey : UtopiaUtil._monthIndexTranslations.getKeys()) {
            Integer monthIndex = UtopiaUtil._monthIndexTranslations.get(monthIndexKey, Json.Types.INTEGER);
            if (monthIndex.equals(index)) {
                monthString = monthIndexKey;
                break;
            }
        }

        String capitalizedMonth = monthString.trim();
        if (capitalizedMonth.length() > 1) {
            capitalizedMonth = Character.toUpperCase(monthString.charAt(0)) + monthString.substring(1).toLowerCase();
        }

        return capitalizedMonth;
    }

    public static String incrementUtopiaDate(String utopiaDate, Integer months, Integer years) {
        final Integer maxMonths = UtopiaUtil._monthIndexTranslations.length();

        if (months == null) {
            months = 0;
        }
        if (years == null) {
            years = 0;
        }

        Integer baseDays = 0;
        if (utopiaDate.contains(" ") && utopiaDate.contains(",")) {
            baseDays = Util.parseInt(utopiaDate.substring(utopiaDate.indexOf(" ") + 1, utopiaDate.indexOf(",")).trim());
        }

        Integer baseMonth = _getUtopianMonthIndex(utopiaDate);
        Integer baseYear = _getUtopianYear(utopiaDate);

        Integer baseMonths = baseYear * maxMonths + baseMonth;
        Integer deltaMonths = years * maxMonths + months;

        Integer newYear = (baseMonths + deltaMonths) / maxMonths;

        String capitalizedMonth = UtopiaUtil._getUtopianMonthFromIndex((baseMonths + deltaMonths) % maxMonths);

        return capitalizedMonth +" "+ baseDays.toString() +", YR"+ newYear.toString();
    }

    public static Integer countTicksByDate(String utopianDate) {
        Integer yearTickTranslation = 24 * 7;

        utopianDate = utopianDate.trim().toUpperCase();
        Integer ticks = 0;

        for (String key : UtopiaUtil._monthTickTranslations.getKeys()) {
            if (utopianDate.contains(key)) {
                ticks += Integer.parseInt(UtopiaUtil._monthTickTranslations.get(key, Json.Types.STRING));
                break;
            }
        }

        Integer begin = utopianDate.indexOf(" ");
        Integer end = utopianDate.indexOf(",");
        Integer yrIndex = utopianDate.indexOf("YR");
        Boolean containsSecondSpace = (begin != utopianDate.lastIndexOf(" "));
        if (begin >= 0 && end >= 0 && yrIndex >= 0 && ! begin.equals(end) && containsSecondSpace) {
            String tickString = utopianDate.substring(begin + 1, end);
            ticks += Integer.parseInt(tickString);
            String yearString = utopianDate.substring(utopianDate.indexOf("YR")+2);
            ticks += Integer.parseInt(yearString) * yearTickTranslation;
        }
        else if (begin >= 0 && yrIndex >= 0 && ! begin.equals(end) && containsSecondSpace) {
            end = utopianDate.indexOf(" ", begin + 1);
            if (end >= 0) {
                String tickString = utopianDate.substring(begin + 1, end);
                ticks += Integer.parseInt(tickString);
                String yearString = utopianDate.substring(utopianDate.indexOf("YR") + 2);
                ticks += Integer.parseInt(yearString) * yearTickTranslation;
            }
        }

        return ticks;
    }

    public static Long utopianTicksToTimestamp(final Integer ticks) {
        final Integer secondsFromNow = (int) (ticks * 60.0f * 60.0f);
        return (System.currentTimeMillis() / 1000L) + secondsFromNow;
    }

    public void setIntelSubmitter(final IntelSubmitter intelSubmitter) {
        _intelSubmitter = intelSubmitter;
    }

    public void setOnServerTickCallback(OnServerTickCallback callback) {
        _onServerTickCallback = callback;
    }

    public void setOnLoggedOutCallback(OnLoggedOutCallback onLoggedOutCallback) {
        _onLoggedOutCallback = onLoggedOutCallback;
    }

    private UtopiaUtil(final Dependencies dependencies) {
        _buildVersion = dependencies._buildVersion;
        _htmlParser = dependencies._htmlParser;
        _utopiaParser = dependencies._utopiaParser;
    }

    private Boolean _isServerCurrentlyTicking(WebRequest request) {
        final String urlTickingToken = "/shared/lobby";

        Map<String, List<String>> headers = request.getHeaders();
        if (headers != null && headers.containsKey("Location")) {
            List<String> locationHeaders = headers.get("Location"); // Should only be one, but...
            for (String location : locationHeaders) {
                if (location.contains(urlTickingToken)) {
                    return true;
                }
            }
        }
        else if (request.hasResult()) {
            String html = request.getRawResult();
            return (_htmlParser.parse(html).select(".lobby-panel").getText().contains("The game is currently ticking."));
        }

        return false;
    }

    private Boolean _isLoggedOut(WebRequest request) {
        final String urlLoginToken = "/?next=";

        Map<String, List<String>> headers = request.getHeaders();
        if (headers != null && headers.containsKey("Location")) {
            List<String> locationHeaders = headers.get("Location"); // Should only be one, but...
            for (String location : locationHeaders) {
                if (location.contains(urlLoginToken)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void _waitForServerTick() {
        try {
            _serverIsCurrentlyTicking = true;
            if (_onServerTickCallback != null) {
                _onServerTickCallback.run(true);
            }

            Thread.sleep(5000);
        } catch (InterruptedException e) { }
    }

    private void _notWaitingForServerTick() {
        if (_serverIsCurrentlyTicking && _onServerTickCallback != null) {
            _onServerTickCallback.run(false);
        }
        _serverIsCurrentlyTicking = false;
    }

    private void _executeRequest(WebRequest request, WebRequest.Callback callback) {
        _executeRequest(request, new ExecuteWebRequestSettings(), callback);
    }

    private void _executeRequest(final WebRequest request, ExecuteWebRequestSettings executeWebRequestSettingsParam, final WebRequest.Callback callback) {
        if (executeWebRequestSettingsParam == null) {
            executeWebRequestSettingsParam = new ExecuteWebRequestSettings();
        }
        final ExecuteWebRequestSettings settings = executeWebRequestSettingsParam;

        synchronized (_utopiaCache) {
            if ((! executeWebRequestSettingsParam.ignoreCache) && _utopiaCache.contains(request)) {
                final WebRequest cachedWebRequest = _utopiaCache.get(request);
                if ( (request.hasResult()) && (! Util.coalesce(request.getRawResult()).isEmpty()) ) {
                    System.out.println("UtopiaUtil - Using Cached Web Request: " + cachedWebRequest.getUrl());
                    if (callback != null) {
                        (new Thread(new Runnable() {
                            @Override
                            public void run() {
                                callback.run(cachedWebRequest);
                            }
                        })).start();
                    }
                    return;
                }
            }
        }

        if (settings.autoSetCookies) {
            _setCookies(request);
        }

        System.out.println("UtopiaUtil - Executing: "+ request.getUrl() +" ["+ (request.getType() == WebRequest.RequestType.POST ? "P" : "G") +"]");

        request.execute(settings.executeAsynchronously, new WebRequest.Callback() {
            public void run(WebRequest responseRequest) {
                synchronized (_utopiaCache) {
                    _utopiaCache.put(responseRequest);
                }

                _storeCookies(responseRequest);

                if (settings.detectServerError) {
                    if (responseRequest.hasResult()) {
                        final String result = responseRequest.getRawResult();
                        if (result.contains("Oops, something went wrong.")) {
                            _executeRequest(request, settings, callback);
                            return;
                        }
                    }
                }

                if (settings.detectServerTick) {
                    if (_isServerCurrentlyTicking(request)) {
                        synchronized (_utopiaCache) {
                            _utopiaCache.clear();
                        }

                        _waitForServerTick();

                        settings.autoSetCookies = false;
                        _executeRequest(request, settings, callback);
                        return;
                    }
                    _notWaitingForServerTick();
                }

                if (settings.detectLogout) {
                    if (_isLoggedOut(responseRequest) && _onLoggedOutCallback != null) {
                        synchronized (_utopiaCache) {
                            _utopiaCache.clear();
                        }

                        _onLoggedOutCallback.run(new UtilLoggedBackInCallback() {
                            @Override
                            public void onLogin() {
                                settings.autoSetCookies = true;
                                settings.detectLogout = false;
                                _executeRequest(request, settings, callback);
                            }
                        });
                        return;
                    }
                }

                if (responseRequest.hasResult()) {
                    final String result = responseRequest.getRawResult();
                    if (result.length() > 0 && result.contains("div-gpt-ad")) {
                        for (final AdViewCallback adviewCallback : _adViewCallbacks.values()) {
                            (new Thread() {
                                @Override
                                public void run() {
                                    adviewCallback.run(_utopiaParser.parseAds(result));
                                }
                            }).start();
                        }
                    }
                }

                if (callback != null) {
                    callback.run(responseRequest);
                }
            }
        });
    }

    private Boolean _storeCookies(WebRequest request) {
        List<String> cookies = request.getCookies();

        if (cookies != null) {
            for (String cookie : cookies) {
                String key = cookie.substring(0, cookie.indexOf("="));
                String value = cookie.substring(cookie.indexOf("=") + 1);
                _receivedCookies.put(key, value);
            }
            return true;
        }
        return false;
    }

    private void _setCookies(WebRequest request) {
        String clientId = null;
        String csrfToken = null;
        String sessionId = null;
        for (String key : _receivedCookies.keySet()) {
            String value = _receivedCookies.get(key);
            switch (key) {
                case "clientid":
                    clientId = value;
                    break;
                case "csrftoken":
                    csrfToken = value;
                    break;
                case "sessionid":
                    sessionId = value;
                    break;
            }

            request.setCookie(key +"="+ value +";");
        }

        request.setCookie("bm_monthly_unique=true;");
        request.setCookie("bm_daily_unique=true;");
        request.setCookie("bm_sample_frequency=1;");
        request.setCookie("_ga=GA1.2.465956296.1426620990;");
        request.setCookie("_gat=1;");
        request.setCookie("__utmt=1;");
        request.setCookie("__utma=238191684.465956296.1426620990.1426697397.1426701602.3;");
        request.setCookie("__utmb=238191684.1.10.1426701602;");
        request.setCookie("__utmc=238191684;");
        request.setCookie("__utmz=238191684.1426620990.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none);");
        request.setCookie("bm_last_load_status=NOT_BLOCKING;");
        request.setCookie("ads_bm_last_load_status=NOT_BLOCKING;");
        request.setCookie("eclientid="+ clientId +";");

        request.setCookie("utopia_app="+ _buildVersion.getVersionNumber() +";");
        request.setHeader("User-Agent", "Utopia Android App v"+ _buildVersion.getVersionName());

        request.setHeader("X-CSRFToken", csrfToken);
    }

    private void _csrfRequest(final Runnable callback) {
        final WebRequest csrfTokenRequest = new WebRequest();
        csrfTokenRequest.setUrl(Settings.getLoginUrl());
        csrfTokenRequest.setType(WebRequest.RequestType.GET);

        WebRequest.Callback requestCallback = null;
        if (callback != null) {
            requestCallback = new WebRequest.Callback() {
                public void run(WebRequest request) {
                    if (! request.hasResult()) {
                        System.out.println("CSRF ERRORS: <No Response>");
                    }
                    else {
                        String response = request.getRawResult();
                        if (_hasErrors(response)) {
                            System.out.println("CSRF ERRORS: " + _getErrors(response));
                        }
                    }

                    if (callback != null) {
                        callback.run();
                    }
                }
            };
        }

        final ExecuteWebRequestSettings params = new ExecuteWebRequestSettings();
        params.ignoreCache = true;

        _executeRequest(csrfTokenRequest, params, requestCallback);
    }

    public void resetCsrfToken(Runnable callback) {
        _csrfRequest(callback);
    }

    public Map<String, String> getCookies() {
        return new HashMap<String, String>(_receivedCookies);
    }

    public void setCookies(Map<String, String> cookies) {
        _receivedCookies = cookies;
    }

    public void login(String username, String password, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest loginRequest = new WebRequest();
        loginRequest.setUrl(Settings.getLoginUrl());
        loginRequest.setType(WebRequest.RequestType.POST);
        loginRequest.setPostParam("username", username);
        loginRequest.setPostParam("password", password);

        final ExecuteWebRequestSettings settings = new ExecuteWebRequestSettings();
        settings.detectLogout = false;
        settings.detectServerTick = false;

        Runnable loginRunnable = new Runnable() {
            @Override
            public void run() {
                _executeRequest(
                    loginRequest,
                    settings,
                    new WebRequest.Callback() {
                        @Override
                        public void run(WebRequest request) {
                            if (! request.hasResult()) {
                                _onFailure("Failed to connect.", callback);
                                return;
                            }

                            String html = request.getRawResult();
                            if (_hasErrors(html)) {
                                String error = _getErrors(html);
                                _onFailure(error, callback);
                                return;
                            }

                            callback.run(new Response(true, null, null));
                        }
                    }
                );
            }
        };

        if (_receivedCookies.size() == 0) {
            _csrfRequest(loginRunnable);
        }
        else {
            loginRunnable.run();
        }
    }

    private void _onFailure(String message, Callback callback) {
        if (callback != null) {
            callback.run(new Response(false, message, null));
        }
    }

    // NOTE: Detects if the account has been setup...
    private void _detectBadAccess(final String html, final Callback callback) {
        final UtopiaParser.BadAccessType badAccessType = _utopiaParser.parseBadAccessType(html);
        switch (badAccessType) {
            case UNVERIFIED_EMAIL: {
                if (callback != null) {
                    final Response errorResponse = new Response(false, "Your email address must be verified before you can continue. Please check your email for the verification link.", null);
                    errorResponse.putExtra(UtopiaUtil.BAD_ACCESS_EXTRA, badAccessType);
                    callback.run(errorResponse);
                }
            } break;

            case NO_PROVINCE: {
                if (callback != null) {
                    final Response errorResponse = new Response(false, "You must create your province before you can continue. Please use the website to create your province.", null);
                    errorResponse.putExtra(UtopiaUtil.BAD_ACCESS_EXTRA, badAccessType);
                    callback.run(errorResponse);
                }
            } break;

            case DEAD_PROVINCE: {
                if (callback != null) {
                    final Response errorResponse = new Response(false, "Your province has collapsed. Please use the website to create a new province.", null);
                    errorResponse.putExtra(UtopiaUtil.BAD_ACCESS_EXTRA, badAccessType);
                    callback.run(errorResponse);
                }
            } break;

            case NONE:
            default: {
                _onFailure("Error parsing throne.", callback);
            } break;
        }
    }

    public void downloadThrone(final Callback callback) {
        final WebRequest throneRequest = new WebRequest();
        throneRequest.setType(WebRequest.RequestType.GET);
        throneRequest.setUrl(Settings.getThroneUrl());
        throneRequest.setFollowsRedirects(true); // Necessary for checking BadAccess.
        _executeRequest(throneRequest, new WebRequest.Callback() {
            public void run(final WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ThroneBundle throneBundle = _utopiaParser.parseThrone(html);

                if (throneBundle == null || ! throneBundle.isValid()) {
                    _detectBadAccess(html, callback);
                    return;
                }

                final Response response = new Response(true, null, throneBundle);

                final String chatCredentials = ChatCredentials.extractCredentials(html);
                if (chatCredentials.length() > 0) {
                    response.putExtra("chatCredentials", chatCredentials);
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitThroneIntel(request, throneBundle);
                }

                if (callback != null) {
                    callback.run(response);
                }
            }
        });
    }

    public void downloadMilitarySettings(final Callback callback) {
        WebRequest militaryRequest = new WebRequest();
        militaryRequest.setUrl(Settings.getMilitarySettingsUrl());
        militaryRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(militaryRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                String html = request.getRawResult();

                MilitarySettingsBundle militarySettingsBundle = _utopiaParser.parseMilitarySettings(html);

                if (militarySettingsBundle == null || ! militarySettingsBundle.isValid()) {
                    _onFailure("Error military settings.", callback);
                    return;
                }

                // _submitIntel(html, null);

                if (callback != null) {
                    callback.run(new Response(true, null, militarySettingsBundle));
                }
            }
        });
    }

    public void downloadNews(Integer month, Integer year, Callback callback) {
        _downloadNews(month, year, callback);
    }
    public void downloadNews(Callback callback) {
        _downloadNews(null, null, callback);
    }
    private void _downloadNews(Integer month, Integer year, final Callback callback) {
        final WebRequest webRequest = new WebRequest();

        final String url;
        if (month != null && year != null) {
            url = Settings.getNewsUrl(month, year);
        }
        else {
            url = Settings.getNewsUrl();
        }
        webRequest.setUrl(url);

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final NewspaperBundle newspaperBundle = _utopiaParser.parseNews(html);
                if (newspaperBundle == null || ! newspaperBundle.isValid()) {
                    _onFailure("Error parsing news.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitNewsIntel(request, newspaperBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, newspaperBundle));
                }
            }
        });
    }

    public void downloadKingdomNews(Callback callback) {
        _downloadKingdomNews(null, null, callback);
    }

    public void downloadKingdomNews(Integer month, Integer year, Callback callback) {
        _downloadKingdomNews(month, year, callback);
    }

    private void _downloadKingdomNews(Integer month, Integer year, final Callback callback) {
        final WebRequest kingdomNewsRequest = new WebRequest();
        if (month != null && year != null) {
            kingdomNewsRequest.setUrl(Settings.getKingdomNewsUrl(month, year));
        }
        else {
            kingdomNewsRequest.setUrl(Settings.getKingdomNewsUrl());
        }
        _executeRequest(kingdomNewsRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final NewspaperBundle newspaperBundle = _utopiaParser.parseNews(html);
                if (newspaperBundle == null || ! newspaperBundle.isValid()) {
                    _onFailure("Error parsing news.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitNewsIntel(request, newspaperBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, newspaperBundle));
                }
            }
        });
    }

    public void downloadBuildingsCouncil(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getBuildingsUrl());
        webRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final BuildingsBundle buildingsBundle = _utopiaParser.parseBuildingCouncil(html);

                if (buildingsBundle == null || ! buildingsBundle.isValid()) {
                    _onFailure("Error parsing building council.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitBuildingsIntel(request, buildingsBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, buildingsBundle));
                }
            }
        });
    }

    public void downloadMilitaryCouncil(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getMilitaryUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final MilitaryBundle militaryBundle = _utopiaParser.parseMilitaryCouncil(html);

                if (militaryBundle == null || ! militaryBundle.isValid()) {
                    _onFailure("Error parsing military council.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitMilitaryIntel(request, militaryBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, militaryBundle));
                }
            }
        });
    }

    public void trainArmy(TrainArmyData armyBundle, final Boolean isExpedited, final Callback callback) {
        final TrainArmyData releaseArmyBundle = new TrainArmyData(armyBundle.getRace());
        final TrainArmyData trainArmyData = new TrainArmyData(armyBundle.getRace());

        { // Copy the Military Settings. It's unnecessary for the releaseArmy, but is a good precaution.
            trainArmyData.setDraftRate(armyBundle.getDraftRate());
            trainArmyData.setDraftTarget(armyBundle.getDraftTarget());
            trainArmyData.setWageRate(armyBundle.getWageRate());

            releaseArmyBundle.setDraftRate(armyBundle.getDraftRate());
            releaseArmyBundle.setDraftTarget(armyBundle.getDraftTarget());
            releaseArmyBundle.setWageRate(armyBundle.getWageRate());
        }

        Boolean shouldReleaseArmy = false;
        Boolean shouldTrainArmy = false;

        Integer unitCount;

        { // Soldiers
            unitCount = armyBundle.getSoldierCount();
            if (unitCount < 0) {
                releaseArmyBundle.setSoldierCount(Math.abs(unitCount));
                shouldReleaseArmy = true;
            }
            else if (unitCount > 0) {
                trainArmyData.setSoldierCount(unitCount);
                shouldTrainArmy = true;
            }
        }

        { // Defensive Units
            unitCount = armyBundle.getDefensiveUnitCount();
            if (unitCount < 0) {
                releaseArmyBundle.setDefensiveUnitCount(Math.abs(unitCount));
                shouldReleaseArmy = true;
            }
            else if (unitCount > 0) {
                trainArmyData.setDefensiveUnitCount(unitCount);
                shouldTrainArmy = true;
            }
        }

        { // Offensive Units
            unitCount = armyBundle.getOffensiveUnitCount();
            if (unitCount < 0) {
                releaseArmyBundle.setOffensiveUnitCount(Math.abs(unitCount));
                shouldReleaseArmy = true;
            }
            else if (unitCount > 0) {
                trainArmyData.setOffensiveUnitCount(unitCount);
                shouldTrainArmy = true;
            }
        }

        { // Elites
            unitCount = armyBundle.getEliteCount();
            if (unitCount < 0) {
                releaseArmyBundle.setEliteCount(Math.abs(unitCount));
                shouldReleaseArmy = true;
            }
            else if (unitCount > 0) {
                trainArmyData.setEliteCount(unitCount);
                shouldTrainArmy = true;
            }
        }

        { // Thieves
            unitCount = armyBundle.getThiefCount();
            if (unitCount < 0) {
                releaseArmyBundle.setThiefCount(Math.abs(unitCount));
                shouldReleaseArmy = true;
            }
            else if (unitCount > 0) {
                trainArmyData.setThiefCount(unitCount);
                shouldTrainArmy = true;
            }
        }

        // Note: Since we do not detect changes to MilitarySettings, we must always train the army.
        //  Release Army does not set MilitarySettings.
        if (shouldReleaseArmy) {
            _releaseArmy(releaseArmyBundle, new Callback() {
                @Override
                public void run(Response response) {
                    _trainArmy(trainArmyData, isExpedited, callback);
                }
            });
        }
        else {
            _trainArmy(trainArmyData, isExpedited, callback);
        }
    }
    private void _trainArmy(TrainArmyData trainArmyData, Boolean isExpedited, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        WebRequest trainArmy = new WebRequest();
        trainArmy.setUrl(Settings.getTrainArmyUrl());
        trainArmy.setType(WebRequest.RequestType.POST);

        trainArmy.setPostParam("csrfmiddlewaretoken",   _receivedCookies.get("csrftoken"));
        trainArmy.setPostParam("draft_rate",        trainArmyData.getDraftRate().getIdentifier());
        trainArmy.setPostParam("draft_target",      trainArmyData.getDraftTarget().toString());
        trainArmy.setPostParam("wage_rate",         trainArmyData.getWageRate().toString());
        trainArmy.setPostParam("unit-quantity_0",   trainArmyData.getOffensiveUnitCount().toString());  // Offensive Units
        trainArmy.setPostParam("unit-quantity_1",   trainArmyData.getDefensiveUnitCount().toString());  // Defensive Units

        if (trainArmyData.getRace() != Province.Race.UNDEAD) {
            trainArmy.setPostParam("unit-quantity_2", trainArmyData.getEliteCount().toString());        // Elites
            trainArmy.setPostParam("unit-quantity_3", trainArmyData.getThiefCount().toString());        // Thieves
        }
        else {
            trainArmy.setPostParam("unit-quantity_2", trainArmyData.getThiefCount().toString());        // Undead Thieves
        }

        if (isExpedited != null && isExpedited) {
            trainArmy.setPostParam("unit-accelerate", "on");
        }

        trainArmy.setPostParam("train", "Train troops");

        _executeRequest(trainArmy, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }
    private void _releaseArmy(TrainArmyData trainArmyData, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        WebRequest trainArmy = new WebRequest();
        trainArmy.setUrl(Settings.getReleaseArmyUrl());
        trainArmy.setType(WebRequest.RequestType.POST);

        trainArmy.setPostParam("csrfmiddlewaretoken",   _receivedCookies.get("csrftoken"));
        trainArmy.setPostParam("draft_rate",        trainArmyData.getDraftRate().getIdentifier());
        trainArmy.setPostParam("draft_target",      trainArmyData.getDraftTarget().toString());
        trainArmy.setPostParam("wage_rate",         trainArmyData.getWageRate().toString());
        trainArmy.setPostParam("unit-quantity_0",   trainArmyData.getSoldierCount().toString());        // Soldiers
        trainArmy.setPostParam("unit-quantity_1",   trainArmyData.getOffensiveUnitCount().toString());  // Offensive Units
        trainArmy.setPostParam("unit-quantity_2",   trainArmyData.getDefensiveUnitCount().toString());  // Defensive Units
        trainArmy.setPostParam("unit-quantity_3", trainArmyData.getEliteCount().toString());            // Elites
        trainArmy.setPostParam("unit-quantity_4", trainArmyData.getThiefCount().toString());            // Thieves
        trainArmy.setPostParam("train", "Train troops");

        _executeRequest(trainArmy, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    private void _downloadAvailableSpells(final Spell.SpellType spellType, final Callback callback) {
        final WebRequest spellsRequest = new WebRequest();
        spellsRequest.setType(WebRequest.RequestType.GET);

        if (spellType == Spell.SpellType.OFFENSIVE) {
            spellsRequest.setUrl(Settings.getOffensiveSpellsUrl());
        }
        else {
            spellsRequest.setUrl(Settings.getDefensiveSpellsUrl());
        }

        _executeRequest(spellsRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (!request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final AvailableSpellsBundle availableSpellsBundle;
                if (spellType == Spell.SpellType.OFFENSIVE) {
                    availableSpellsBundle = _utopiaParser.parseAvailableOffensiveSpells(html);
                }
                else {
                    availableSpellsBundle = _utopiaParser.parseAvailableDefensiveSpells(html);
                }

                if (availableSpellsBundle == null || ! availableSpellsBundle.isValid()) {
                    _onFailure("Error parsing spell list.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, availableSpellsBundle));
                }
            }
        });
    }
    public void downloadAvailableSpells(final Callback callback) {
        _downloadAvailableSpells(Spell.SpellType.DEFENSIVE, new Callback() {
            @Override
            public void run(final Response defensiveSpellsResultBundle) {
                _downloadAvailableSpells(Spell.SpellType.OFFENSIVE, new Callback() {
                    @Override
                    public void run(Response offensiveSpellsResultBundle) {
                        final AvailableSpellsBundle defensiveSpellsBundle = (AvailableSpellsBundle) defensiveSpellsResultBundle.getBundle();
                        final AvailableSpellsBundle offensiveSpellsBundle = (AvailableSpellsBundle) offensiveSpellsResultBundle.getBundle();

                        if (!offensiveSpellsResultBundle.hasBundle() || !defensiveSpellsResultBundle.hasBundle()) {
                            _onFailure("Error parsing spell list.", callback);
                            return;
                        }
                        // Combine the two lists together...
                        // NOTE: Use the offensiveSpellsBundle as the base because it may contain utopiaProvinceIds.
                        final AvailableSpellsBundle combinedSpellListBundle = offensiveSpellsBundle;
                        if (defensiveSpellsBundle.hasGroupKey(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE)) { // Bundle may not have group when out of mana...
                            for (final Bundle bundle : defensiveSpellsBundle.getGroup(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE)) {
                                final SpellBundle spellBundle = (SpellBundle) bundle;
                                combinedSpellListBundle.addToGroup(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE, spellBundle);
                            }
                        }

                        if (combinedSpellListBundle == null || ! combinedSpellListBundle.isValid()) {
                            _onFailure("Error parsing spell list.", callback);
                            return;
                        }

                        if (callback != null) {
                            callback.run(new Response(true, null, combinedSpellListBundle));
                        }
                    }
                });
            }
        });
    }
    public void downloadActiveSelfSpells(final Callback callback) {
        final WebRequest activeSpellsRequest = new WebRequest();
        activeSpellsRequest.setType(WebRequest.RequestType.GET);
        activeSpellsRequest.setUrl(Settings.getActiveSpellsUrl());

        _executeRequest(activeSpellsRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (!request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final ActiveSpellsBundle activeSpellsBundle = _utopiaParser.parseActiveSpells(html);

                if (activeSpellsBundle == null || ! activeSpellsBundle.isValid()) {
                    _onFailure("Error parsing active spells.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitActiveSpellsIntel(request, activeSpellsBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, activeSpellsBundle));
                }
            }
        });
    }

    public void downloadKingdomIntel(final Kingdom.Identifier kingdomIdentifier, final Callback callback) {
        final WebRequest kingdomIntelRequest = new WebRequest();
        kingdomIntelRequest.setType(WebRequest.RequestType.GET);
        kingdomIntelRequest.setUrl(Settings.getKingdomIntelUrl(kingdomIdentifier));

        _executeRequest(kingdomIntelRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final KingdomIntelBundle kingdomIntelBundle = _utopiaParser.parseKingdomIntel(html);

                if (kingdomIntelBundle == null || ! kingdomIntelBundle.isValid()) {
                    _onFailure("Error parsing kingdom intel.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitKingdomIntel(request, kingdomIdentifier, kingdomIntelBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, kingdomIntelBundle));
                }
            }
        });
    }

    public void castDefensiveSpell(final Spell spell, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getDefensiveSpellsUrl());
        webRequest.setType(WebRequest.RequestType.POST);

        webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
        webRequest.setPostParam("spell", spell.getIdentifier());

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest castRequest) {
                if (! castRequest.getHeaders().containsKey("Location")) {
                    String errorMessage = "";

                    if (castRequest.hasResult()) {
                        final String html = castRequest.getRawResult();
                        final SpellResultBundle spellResultBundle = _utopiaParser.parseSpellResult(html);
                        errorMessage = spellResultBundle.get(SpellResultBundle.Keys.RESULT_TEXT);
                    }
                    else {
                        errorMessage = "Failed to connect.";
                    }

                    _onFailure(errorMessage, callback);
                    return;
                }

                final String location = castRequest.getHeaders().get("Location").get(0);
                final WebRequest resultRequest = new WebRequest();
                resultRequest.setUrl(location);
                resultRequest.setType(WebRequest.RequestType.GET);
                resultRequest.setFollowsRedirects(false);
                resultRequest.setHeader("Referer", Settings.getOffensiveSpellsUrl());
                resultRequest.setHeader("Host", Settings.getHost());

                _executeRequest(resultRequest, new WebRequest.Callback() {
                    @Override
                    public void run(WebRequest responseRequest) {
                        if (! responseRequest.hasResult()) {
                            _onFailure("Failed to connect.", callback);
                            return;
                        }

                        final String html = responseRequest.getRawResult();
                        final SpellResultBundle spellResultBundle = _utopiaParser.parseSpellResult(html);

                        if ( (! spellResultBundle.hasKey(SpellResultBundle.Keys.SPELL_IDENTIFIER)) || (spellResultBundle.get(SpellResultBundle.Keys.SPELL_IDENTIFIER).isEmpty()) ) {
                            spellResultBundle.put(SpellResultBundle.Keys.SPELL_IDENTIFIER, spell.getIdentifier());
                        }

                        if (! spellResultBundle.isValid()) {
                            _onFailure("Error parsing spell result.", callback);
                            return;
                        }

                        spellResultBundle.put(SpellResultBundle.Keys.IS_DEFENSIVE_SPELL, "1");

                        if (_intelSubmitter != null) {
                            _intelSubmitter.submitCastSpellIntel(responseRequest, spellResultBundle);
                        }

                        if (callback != null) {
                            callback.run(new Response(true, null, spellResultBundle));
                        }
                    }
                });
            }
        });
    }

    public void castSpell(final Spell spell, final Integer targetProvinceId, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getOffensiveSpellsUrl());
        webRequest.setType(WebRequest.RequestType.POST);

        webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
        webRequest.setPostParam("target_province", targetProvinceId.toString());
        webRequest.setPostParam("spell", spell.getIdentifier());
        webRequest.setFollowsRedirects(false);

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest castRequest) {
                if (! castRequest.getHeaders().containsKey("Location")) {
                    String errorMessage = "";

                    if (castRequest.hasResult()) {
                        final String html = castRequest.getRawResult();
                        final SpellResultBundle spellResultBundle = _utopiaParser.parseSpellResult(html);
                        errorMessage = spellResultBundle.get(SpellResultBundle.Keys.RESULT_TEXT);
                    }
                    else {
                        errorMessage = "Failed to connect.";
                    }

                    _onFailure(errorMessage, callback);
                    return;
                }

                final String location = castRequest.getHeaders().get("Location").get(0);
                final WebRequest resultRequest = new WebRequest();
                resultRequest.setUrl(location);
                resultRequest.setType(WebRequest.RequestType.GET);
                resultRequest.setFollowsRedirects(false);
                resultRequest.setHeader("Referer", Settings.getOffensiveSpellsUrl());
                resultRequest.setHeader("Host", Settings.getHost());

                final ExecuteWebRequestSettings requestSettings = new ExecuteWebRequestSettings();
                requestSettings.executeAsynchronously = false;

                _executeRequest(
                    resultRequest,
                    requestSettings,
                    new WebRequest.Callback() {
                        @Override
                        public void run(WebRequest responseRequest) {
                            if (!responseRequest.hasResult()) {
                                _onFailure("Failed to connect.", callback);
                                return;
                            }

                            final String html = responseRequest.getRawResult();
                            final SpellResultBundle spellResultBundle = _utopiaParser.parseSpellResult(html);

                            if ( (! spellResultBundle.hasKey(SpellResultBundle.Keys.SPELL_IDENTIFIER)) || (spellResultBundle.get(SpellResultBundle.Keys.SPELL_IDENTIFIER).isEmpty()) ) {
                                spellResultBundle.put(SpellResultBundle.Keys.SPELL_IDENTIFIER, spell.getIdentifier());
                            }

                            if (! spellResultBundle.isValid()) {
                                _onFailure("Error parsing spell result.", callback);
                                return;
                            }

                            spellResultBundle.put(SpellResultBundle.Keys.IS_DEFENSIVE_SPELL, "0");

                            if (_intelSubmitter != null) {
                                _intelSubmitter.submitCastSpellIntel(responseRequest, spellResultBundle);
                            }

                            if (callback != null) {
                                callback.run(new Response(true, null, spellResultBundle));
                            }
                        }
                    }
                );
            }
        });
    }

    public void downloadStateCouncil(final Callback callback) {
        final WebRequest stateRequest = new WebRequest();
        stateRequest.setUrl(Settings.getStateUrl());
        stateRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(stateRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (!request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final StateCouncilBundle stateCouncilBundle = _utopiaParser.parseStateCouncil(html);

                if (stateCouncilBundle == null || !stateCouncilBundle.isValid()) {
                    _onFailure("Error parsing state council.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitStateCouncilIntel(request, stateCouncilBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, stateCouncilBundle));
                }
            }
        });
    }

    private void _executeKingdomRequest(WebRequest kingdomRequest, final Callback callback) {
        _executeRequest(kingdomRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (!request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final KingdomBundle kingdomBundle = _utopiaParser.parseKingdom(html);

                if (kingdomBundle == null || !kingdomBundle.isValid()) {
                    _onFailure("Error parsing kingdom.", callback);
                    return;
                }

                final Kingdom.Identifier kingdomIdentifier = new Kingdom.Identifier(
                    Util.parseInt(kingdomBundle.get(KingdomBundle.Keys.KINGDOM_ID)),
                    Util.parseInt(kingdomBundle.get(KingdomBundle.Keys.ISLAND_ID))
                );

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitKingdomIntel(request, kingdomIdentifier, kingdomBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, kingdomBundle));
                }
            }
        });
    }
    public void downloadKingdom(Callback callback) {
        final String url = Settings.getKingdomUrl();

        final WebRequest kingdomRequest = new WebRequest();
        kingdomRequest.setType(WebRequest.RequestType.GET);
        kingdomRequest.setUrl(url);
        _executeKingdomRequest(kingdomRequest, callback);
    }
    public void downloadKingdom(Integer kingdomId, Integer islandId, Callback callback) {
        final String url = Settings.getKingdomUrl() + kingdomId.toString() +"/"+ islandId.toString();

        final WebRequest kingdomRequest = new WebRequest();
        kingdomRequest.setType(WebRequest.RequestType.GET);
        kingdomRequest.setUrl(url);
        _executeKingdomRequest(kingdomRequest, callback);
    }
    public void downloadNextKingdom(Integer kingdomId, Integer islandId, Callback callback) {
        final String url = Settings.getKingdomUrl() + kingdomId.toString() +"/"+ islandId.toString() +"?next";

        final WebRequest kingdomRequest = new WebRequest();
        kingdomRequest.setType(WebRequest.RequestType.GET);
        kingdomRequest.setUrl(url);
        kingdomRequest.setFollowsRedirects(true);
        _executeKingdomRequest(kingdomRequest, callback);
    }
    public void downloadPreviousKingdom(Integer kingdomId, Integer islandId, Callback callback) {
        final String url = Settings.getKingdomUrl() + kingdomId.toString() +"/"+ islandId.toString() +"?previous";

        final WebRequest kingdomRequest = new WebRequest();
        kingdomRequest.setType(WebRequest.RequestType.GET);
        kingdomRequest.setUrl(url);
        kingdomRequest.setFollowsRedirects(true);
        _executeKingdomRequest(kingdomRequest, callback);
    }

    public void downloadChatMessages(final ChatCredentials chatCredentials, String lastMessageId, final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setType(WebRequest.RequestType.GET);

        if (lastMessageId == null) {
            webRequest.setUrl(chatCredentials.getUrl());
        }
        else {
            webRequest.setUrl(chatCredentials.getMessagesUrl(lastMessageId));
            webRequest.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            webRequest.setHeader("Referrer", chatCredentials.getUrl());
        }

        WebRequest.Callback webRequestCallback = new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                if (request.getResponseCode() != 200) {
                    _onFailure("Invalid chatroom ticket.", callback);
                    return;
                }

                final String html = request.getRawResult();

                final ChatroomBundle chatroomBundle = _utopiaParser.parseChatroom(html);
                if (chatroomBundle == null || ! chatroomBundle.isValid()) {
                    _onFailure("Error parsing bundle.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, chatroomBundle));
                }
            }
        };

        webRequest.execute(true, webRequestCallback);
    }

    public void sendChatMessage(String message, ChatCredentials credentials, final Callback callback) {
        if (credentials == null) {
            _onFailure("Missing chat credentials.", callback);
            return;
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(credentials.getSendMessageUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("ticket", credentials.getTicket());
        webRequest.setPostParam("message", message);
        webRequest.execute(true, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    private final Building.Type[] _buildingTypeFieldOrder = new Building.Type[] {
            Building.Type.HOMES,            Building.Type.FARMS,
            Building.Type.MILLS,            Building.Type.BANKS,
            Building.Type.TRAINING_GROUNDS, Building.Type.ARMORIES,
            Building.Type.BARRACKS,         Building.Type.FORTS,
            Building.Type.GUARD_STATIONS,   Building.Type.HOSPITALS,
            Building.Type.GUILDS,           Building.Type.TOWERS,
            Building.Type.THIEVES_DENS,     Building.Type.WATCHTOWERS,
            Building.Type.LABORATORIES,     Building.Type.UNIVERSITIES,
            Building.Type.STABLES,          Building.Type.DUNGEONS
    };
    /*
        private final Building.Type[] _avianBuildingTypeFieldOrder = new Building.Type[] {
                Building.Type.HOMES,            Building.Type.FARMS,
                Building.Type.MILLS,            Building.Type.BANKS,
                Building.Type.TRAINING_GROUNDS, Building.Type.ARMORIES,
                Building.Type.BARRACKS,         Building.Type.FORTS,
                Building.Type.GUARD_STATIONS,   Building.Type.HOSPITALS,
                Building.Type.GUILDS,           Building.Type.TOWERS,
                Building.Type.THIEVES_DENS,     Building.Type.WATCHTOWERS,
                Building.Type.LABORATORIES,     Building.Type.UNIVERSITIES,
                Building.Type.DUNGEONS          // Cannot Use Stables...
        };
    */
    private Integer _getBuildingFieldOrderByType(Province.Race race, Building.Type searchType) {
        // final Building.Type[] buildingTypeFieldOrder = (Province.Race.AVIAN.equals(race) ? _avianBuildingTypeFieldOrder : _buildingTypeFieldOrder);
        final Building.Type[] buildingTypeFieldOrder = _buildingTypeFieldOrder;

        Integer i = 0;
        for (Building.Type type : buildingTypeFieldOrder) {
            if (searchType == type) {
                return i;
            }

            i++;
        }

        return null;
    }

    public void buildBuilding(Province.Race race, Building.Type buildingType, Integer quantity, Boolean isConstructionExpedited, Boolean shouldUseBuildingCredits, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(quantity < 0 ? Settings.getRazeBuildingUrl() : Settings.getConstructBuildingUrl());
        webRequest.setType(WebRequest.RequestType.POST);

        // webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
        webRequest.setPostParam("quantity_"+ _getBuildingFieldOrderByType(race, buildingType), ((Integer) Math.abs(quantity)).toString());

        if (isConstructionExpedited) {
            webRequest.setPostParam("accelerate", "on");
        }

        if (shouldUseBuildingCredits) {
            webRequest.setPostParam("use_credits", "on");
        }

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new Response(false, "Failed to connect.", null));
                    }

                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    private void _setKingdom(final Kingdom.Identifier identifier, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getSetKingdomUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setFollowsRedirects(false);

        webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
        webRequest.setPostParam("kingdom", identifier.getKingdomId().toString());
        webRequest.setPostParam("island", identifier.getIslandId().toString());
        webRequest.setPostParam("next_url", "/wol/game/thievery");
        webRequest.setPostParam("change_kingdom", "Go");

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    if (callback != null) {
                        callback.run(new Response(false, "Failed to connect", null));
                    }

                    return;
                }

                _currentKingdom = identifier;

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }
    public void setKingdom(Kingdom.Identifier identifier, final Callback callback) {
        _setKingdom(identifier, callback);
    }
    public Boolean isCurrentTargetKingdom(Kingdom.Identifier kingdomIdentifier) {
        return (kingdomIdentifier != null && _currentKingdom != null && _currentKingdom.equals(kingdomIdentifier));
    }
    public Kingdom.Identifier getTargetKingdomIdentifier() {
        return new Kingdom.Identifier(_currentKingdom.getKingdomId(), _currentKingdom.getIslandId());
    }

    public void downloadAvailableThieveryOperations(final Kingdom.Identifier identifier, final Callback callback) {
        final Callback downloadOps = new Callback() {
            @Override
            public void run(Response bundle) {
                final WebRequest webRequest = new WebRequest();
                webRequest.setUrl(Settings.getThieveryUrl());
                webRequest.setType(WebRequest.RequestType.GET);

                webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));

                _executeRequest(webRequest, new WebRequest.Callback() {
                    public void run(WebRequest request) {
                        if (! request.hasResult()) {
                            _onFailure("Failed to connect.", callback);
                            return;
                        }

                        final String html = request.getRawResult();

                        final AvailableThieveryOperationsBundle availableThieveryOperationsBundle = _utopiaParser.parseAvailableThieveryOperations(html);

                        if (availableThieveryOperationsBundle == null || ! availableThieveryOperationsBundle.isValid()) {
                            _onFailure("Error parsing thievery operations.", callback);
                            return;
                        }

                        if (callback != null) {
                            callback.run(new Response(true, null, availableThieveryOperationsBundle));
                        }
                    }
                });
            }
        };

        if (_currentKingdom == null || !_currentKingdom.getKingdomId().equals(identifier.getKingdomId()) || !_currentKingdom.getIslandId().equals(identifier.getIslandId())) {
            _setKingdom(identifier, downloadOps);
        }
        else {
            downloadOps.run(null);
        }
    }

    public void executeThieveryOperation(final ThieveryOperation thieveryOperation, final Integer thiefCount, final Integer provinceUtopiaId, final Kingdom.Identifier kingdomIdentifier, final Building.Type targetBuilding, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final String targetBuildingString;
        {
            if (targetBuilding != null) {
                targetBuildingString = Building.getBuildingIdentifier(targetBuilding);
            }
            else {
                targetBuildingString = null;
            }
        }

        final Callback executeOperation = new Callback() {
            @Override
            public void run(Response bundle) {
                final WebRequest webRequest = new WebRequest();
                webRequest.setUrl(Settings.getThieveryUrl());
                webRequest.setType(WebRequest.RequestType.POST);
                webRequest.setFollowsRedirects(false);

                webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
                webRequest.setPostParam("target_province", provinceUtopiaId.toString());
                webRequest.setPostParam("operation", thieveryOperation.getIdentifier());

                if (targetBuildingString != null) {
                    webRequest.setPostParam("land_type", targetBuildingString);
                }

                webRequest.setPostParam("quantity", thiefCount.toString());
                webRequest.setPostParam("do_thief_operation", "Run operation");

                _executeRequest(webRequest, new WebRequest.Callback() {
                    public void run(WebRequest request) {
                        if (! request.getHeaders().containsKey("Location")) {
                            String errorMessage = "";

                            if (request.hasResult()) {
                                String html = request.getRawResult();
                                ThieveryOperationBundle thieveryOperationBundle = _utopiaParser.parseThieveryOperation(html);
                                errorMessage = thieveryOperationBundle.get(ThieveryOperationBundle.Keys.RESULT_TEXT);
                            }
                            else {
                                errorMessage = "Failed to connect.";
                            }

                            if (callback != null) {
                                callback.run(new Response(false, errorMessage, null));
                            }
                            return;
                        }

                        final String location = webRequest.getHeaders().get("Location").get(0);
                        final WebRequest resultRequest = new WebRequest();
                        resultRequest.setUrl(location);
                        resultRequest.setType(WebRequest.RequestType.GET);
                        resultRequest.setFollowsRedirects(false);
                        resultRequest.setHeader("Referer", Settings.getThieveryUrl());
                        resultRequest.setHeader("Host", Settings.getHost());

                        final ExecuteWebRequestSettings requestSettings = new ExecuteWebRequestSettings();
                        requestSettings.executeAsynchronously = false;

                        _executeRequest(
                            resultRequest,
                            requestSettings,
                            new WebRequest.Callback() {
                                @Override
                                public void run(WebRequest request) {
                                    if (! request.hasResult()) {
                                        _onFailure("Failed to connect.", callback);
                                        return;
                                    }

                                    final String html = request.getRawResult();
                                    final ThieveryOperationBundle thieveryOperationBundle = _utopiaParser.parseThieveryOperation(html);

                                    if (! thieveryOperationBundle.isValid()) {
                                        _onFailure("Error parsing thievery operation.", callback);
                                        return;
                                    }

                                    final Boolean wasSuccess = (Util.parseInt(thieveryOperationBundle.get(ThieveryOperationBundle.Keys.WAS_SUCCESS)) > 0);

                                    if (wasSuccess && thieveryOperation.getIdentifier().equals(ThieveryOperation.Identifiers.SPY_ON_THRONE)) {
                                        ThroneBundle throneBundle = _utopiaParser.parseThrone(html);
                                        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_PROVINCE_BUNDLE, throneBundle);
                                    }
                                    if (wasSuccess && thieveryOperation.getIdentifier().equals(ThieveryOperation.Identifiers.SPY_ON_MILITARY)) {
                                        MilitaryBundle militaryBundle = _utopiaParser.parseMilitaryCouncil(html);
                                        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_MILITARY_BUNDLE, militaryBundle);
                                    }
                                    if (wasSuccess && thieveryOperation.getIdentifier().equals(ThieveryOperation.Identifiers.SURVEY)) {
                                        BuildingsBundle buildingsBundle = _utopiaParser.parseBuildingCouncil(html);
                                        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_SURVEY_BUNDLE, buildingsBundle);
                                    }
                                    if (wasSuccess && thieveryOperation.getIdentifier().equals(ThieveryOperation.Identifiers.INFILTRATE)) {
                                        InfiltrateThievesBundle infiltrateThievesBundle = _utopiaParser.parseInfiltrateThieves(html);
                                        thieveryOperationBundle.put(ThieveryOperationBundle.Keys.TARGET_INFILTRATE_BUNDLE, infiltrateThievesBundle);
                                    }

                                    if (_intelSubmitter != null) {
                                        _intelSubmitter.submitThieveryOperationIntel(request, thieveryOperationBundle);
                                    }

                                    if (callback != null) {
                                        callback.run(new Response(true, null, thieveryOperationBundle));
                                    }
                                }
                            }
                        );
                    }
                });
            }
        };

        if (_currentKingdom == null || ! _currentKingdom.equals(kingdomIdentifier)) {
            _setKingdom(kingdomIdentifier, executeOperation);
        }
        else {
            executeOperation.run(null);
        }
    }

    public static class Attack {
        public enum Type {
            TRADITIONAL_MARCH, RAZE, PLUNDER, ABDUCT, MASSACRE, CONQUEST, AMBUSH
        }
        public static String getStringForAttackType(Type attackType) {
            switch (attackType) {
                case TRADITIONAL_MARCH: { return "TRADITIONAL_MARCH"; }
                case RAZE:              { return "RAZE"; }
                case PLUNDER:           { return "PLUNDER"; }
                case ABDUCT:            { return "ABDUCT"; }
                case MASSACRE:          { return "MASSACRE"; }
                case CONQUEST:          { return "CONQUEST"; }
                case AMBUSH:            { return "AMBUSH"; }
                default:                { return null; }
            }
        }
        public static String getDisplayNameForAttackType(Type attackType) {
            switch (attackType) {
                case TRADITIONAL_MARCH: { return "TRADITIONAL MARCH"; }
                case RAZE:              { return "RAZE"; }
                case PLUNDER:           { return "PLUNDER"; }
                case ABDUCT:            { return "ABDUCT"; }
                case MASSACRE:          { return "MASSACRE"; }
                case CONQUEST:          { return "CONQUEST"; }
                case AMBUSH:            { return "AMBUSH"; }
                default:                { return null; }
            }
        }
        public static Type getAttackTypeForString(String attackType) {
            switch (attackType.toUpperCase()) {
                case "TRADITIONAL_MARCH":   { return Type.TRADITIONAL_MARCH; }
                case "RAZE":                { return Type.RAZE; }
                case "PLUNDER":             { return Type.PLUNDER; }
                case "ABDUCT":              { return Type.ABDUCT; }
                case "MASSACRE":            { return Type.MASSACRE; }
                case "CONQUEST":            { return Type.CONQUEST; }
                case "AMBUSH":              { return Type.AMBUSH; }
                default:                    { return null; }
            }
        }

        public enum Time {
            MINUS_2, MINUS_1, DEFAULT, PLUS_1, PLUS_2, PLUS_3, PLUS_4
        }
        public static String getStringForAttackTime(Time attackTime) {
            switch (attackTime) {
                case DEFAULT:           { return "DEFAULT"; }
                case MINUS_1:           { return "MINUS_1"; }
                case MINUS_2:           { return "MINUS_2"; }
                case PLUS_1:            { return "PLUS_1"; }
                case PLUS_2:            { return "PLUS_2"; }
                case PLUS_3:            { return "PLUS_3"; }
                case PLUS_4:            { return "PLUS_4"; }
                default:                { return null; }
            }
        }
        public static String getDisplayNameForAttackTime(Time attackTime) {
            switch (attackTime) {
                case DEFAULT:           { return "0 HOURS"; }
                case MINUS_1:           { return "-1 HOURS"; }
                case MINUS_2:           { return "-2 HOURS"; }
                case PLUS_1:            { return "+1 HOURS"; }
                case PLUS_2:            { return "+2 HOURS"; }
                case PLUS_3:            { return "+3 HOURS"; }
                case PLUS_4:            { return "+4 HOURS"; }
                default:                { return null; }
            }
        }
        public static Time  getAttackTimeForString(String attackTime) {
            switch (attackTime.toUpperCase()) {
                case "DEFAULT":         { return Time.DEFAULT; }
                case "MINUS_1":         { return Time.MINUS_1; }
                case "MINUS_2":         { return Time.MINUS_2; }
                case "PLUS_1":          { return Time.PLUS_1; }
                case "PLUS_2":          { return Time.PLUS_2; }
                case "PLUS_3":          { return Time.PLUS_3; }
                case "PLUS_4":          { return Time.PLUS_4; }
                default:                { return null; }
            }
        }

        private Army _army;
        private Province _province;
        private Province _targetProvince;
        private Type _type;
        private Time _time;
        private Integer _calculatedOffense;
        private Integer _armyAddCallbackIdentifier;

        private final Runnable _onArmyChangedCallback = new Runnable() {
            @Override
            public void run() {
                _calculatedOffense = null;
            }
        };

        public Attack(Province province) {
            _province = province;
            _time = Time.DEFAULT;
        }

        public void setArmy(Army army) {
            if (_army != null) {
                _army.removeOnChangeCallback(_armyAddCallbackIdentifier);
            }

            _army = army;
            _armyAddCallbackIdentifier = _army.addOnChangeCallback(_onArmyChangedCallback);
            _calculatedOffense = null;
        }
        public Army getArmy() { return _army; }

        public Province getProvince() { return _province; }

        public void setTargetProvince(Province province) {
            _targetProvince = province;
            _calculatedOffense = null;
        }
        public Province getTargetProvince() { return _targetProvince; }

        public void setType(Type type) {
            _type = type;
            _calculatedOffense = null;
        }
        public Type getType() { return _type; }

        public void setTime(Time time) { _time = time; }
        public Time getTime() { return _time; }

        public Boolean isOffenseCalculated() { return (_calculatedOffense != null); }
        public void setCalculatedOffense(Integer calculatedOffense) { _calculatedOffense = calculatedOffense; }
        public Integer getCalculatedOffense() { return _calculatedOffense; }

        public Boolean isValid() {
            return (_province != null && _targetProvince != null && _type != null && _army != null);
        }
    }
    public static class Army {
        private Integer _generals;
        private Integer _soldiers;
        private Integer _offensive_units;
        private Integer _elites;
        private Integer _horses;
        private Integer _prisoners;
        private Integer _mercenaries;
        private ArrayList<Runnable> _onChangeCallbacks = new ArrayList<Runnable>();

        private void _executeCallbacks() {
            for (Runnable runnable : _onChangeCallbacks) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        }

        public Integer getGenerals() { return _generals; }
        public Integer getSoldiers() { return _soldiers; }
        public Integer getOffensiveUnits() { return _offensive_units; }
        public Integer getElites() { return _elites; }
        public Integer getHorses() { return _horses; }
        public Integer getPrisoners() { return _prisoners; }
        public Integer getMercenaries() { return _mercenaries; }

        public void setGenerals(Integer generals) {
            _generals = generals;
            _executeCallbacks();
        }
        public void setSoldiers(Integer soldiers) {
            _soldiers = soldiers;
            _executeCallbacks();
        }
        public void setOffensiveUnits(Integer offensiveUnits) {
            _offensive_units = offensiveUnits;
            _executeCallbacks();
        }
        public void setElites(Integer elites) {
            _elites = elites;
            _executeCallbacks();
        }
        public void setHorses(Integer horses) {
            _horses = horses;
            _executeCallbacks();
        }
        public void setPrisoners(Integer prisoners) {
            _prisoners = prisoners;
            _executeCallbacks();
        }
        public void setMercenaries(Integer mercenaries) {
            _mercenaries = mercenaries;
            _executeCallbacks();
        }

        public Integer addOnChangeCallback(Runnable callback) {
            _onChangeCallbacks.add(callback);
            return _onChangeCallbacks.size() - 1;
        }
        public void removeOnChangeCallback(Integer addOnChangeCallbackIdentifier) {
            if (addOnChangeCallbackIdentifier != null && addOnChangeCallbackIdentifier < _onChangeCallbacks.size()) {
                _onChangeCallbacks.set(addOnChangeCallbackIdentifier, null);
            }
        }
    }

    public void calculateOffense(Attack attack, Callback callback) {
        _calculateOffense(attack, callback);
    }
    public void _calculateOffense(Attack attack, final Callback callback) {
        if (attack.getProvince().getOffenseModifier() == null) {
            _onFailure("Offense-Modifier not downloaded.", callback);
            return;
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getCalculateOffenseUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        webRequest.setFollowsRedirects(true);

        Boolean hasAggressionSpell = (attack.getProvince().getSpellDuration(Spell.SpellNames.AGGRESSION) > 0);

        if (! attack.isValid()) {
            callback.run(new Response(false, "Invalid attack object.", null));
            return;
        }

        webRequest.setGetParam("race", Province.getStringForRace(attack.getProvince().getRace()).toUpperCase());
        webRequest.setGetParam("under_spell_aggression", (hasAggressionSpell ? "True" : "False"));
        webRequest.setGetParam("precalculated_modifier", attack.getProvince().getOffenseModifier().toString());
        webRequest.setGetParam("attack_type", Attack.getStringForAttackType(attack.getType()));
        webRequest.setGetParam("general_count", attack.getArmy().getGenerals().toString());
        webRequest.setGetParam("infantry_count", attack.getArmy().getSoldiers().toString());
        webRequest.setGetParam("offensive_specialist_count", attack.getArmy().getOffensiveUnits().toString());
        webRequest.setGetParam("elite_count", attack.getArmy().getElites().toString());
        webRequest.setGetParam("horse_count", attack.getArmy().getHorses().toString());
        webRequest.setGetParam("prisoner_count", attack.getArmy().getPrisoners().toString());
        webRequest.setGetParam("mercenary_count", attack.getArmy().getMercenaries().toString());

        final ExecuteWebRequestSettings webRequestSettings = new ExecuteWebRequestSettings();
        webRequestSettings.ignoreCache = true;

        _executeRequest(webRequest, webRequestSettings, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("No response.", callback);
                    return;
                }

                ArmyOffenseBundle armyOffenseBundle = _utopiaParser.parseArmyOffense(request.getRawResult());

                if (! armyOffenseBundle.isValid()) {
                    _onFailure("Unable to parse result.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, armyOffenseBundle));
                }
            }
        });
    }

    public void downloadWarRoomSettings(final Callback callback) {
        WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getAttackUrl());
        webRequest.setType(WebRequest.RequestType.GET);

        webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));

        _executeRequest(webRequest, new WebRequest.Callback() {
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                String html = request.getRawResult();
                WarRoomBundle warRoomBundle = _utopiaParser.parseWarRoomBundle(html);

                if (! warRoomBundle.isValid()) {
                    _onFailure("Error parsing province war-room.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, warRoomBundle));
                }
            }
        });
    }

    public void executeAttack(final Attack attack, final Integer provinceUtopiaId, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        Kingdom.Identifier kingdomIdentifier = attack.getTargetProvince().getKingdomIdentifier();

        final Callback executeOperation = new Callback() {
            @Override
            public void run(Response bundle) {
                final WebRequest webRequest = new WebRequest();
                webRequest.setUrl(Settings.getAttackUrl());
                webRequest.setType(WebRequest.RequestType.POST);
                webRequest.setFollowsRedirects(false);

                Army army = attack.getArmy();
                webRequest.setPostParam("csrfmiddlewaretoken", _receivedCookies.get("csrftoken"));
                webRequest.setPostParam("target_province", provinceUtopiaId.toString());
                webRequest.setPostParam("attack_type", Attack.getStringForAttackType(attack.getType()));
                webRequest.setPostParam("modify_attack_time", Attack.getStringForAttackTime(attack.getTime()));
                webRequest.setPostParam("general", Util.coalesce(army.getGenerals()).toString());
                webRequest.setPostParam("infantry", Util.coalesce(army.getSoldiers()).toString());
                webRequest.setPostParam("offensive_specialist", Util.coalesce(army.getOffensiveUnits()).toString());
                webRequest.setPostParam("elite", Util.coalesce(army.getElites()).toString());
                webRequest.setPostParam("horse", Util.coalesce(army.getHorses()).toString());
                webRequest.setPostParam("mercenary", Util.coalesce(army.getMercenaries()).toString());
                webRequest.setPostParam("prisoner", Util.coalesce(army.getPrisoners()).toString());

                _executeRequest(webRequest, new WebRequest.Callback() {
                    public void run(WebRequest request) {
                        if (! request.getHeaders().containsKey("Location")) {
                            String errorMessage = "";

                            if (request.hasResult()) {
                                String html = request.getRawResult();
                                AttackBundle attackBundle = _utopiaParser.parseAttack(html);
                                errorMessage = attackBundle.get(AttackBundle.Keys.RESULT_TEXT);
                            }
                            else {
                                errorMessage = "Failed to connect.";
                            }

                            if (callback != null) {
                                callback.run(new Response(false, errorMessage, null));
                            }
                            return;
                        }

                        String location = webRequest.getHeaders().get("Location").get(0);
                        final WebRequest resultRequest = new WebRequest();
                        resultRequest.setUrl(location);
                        resultRequest.setType(WebRequest.RequestType.GET);
                        resultRequest.setFollowsRedirects(false);
                        resultRequest.setHeader("Referer", Settings.getAttackUrl());
                        resultRequest.setHeader("Host", Settings.getHost());

                        ExecuteWebRequestSettings requestSettings = new ExecuteWebRequestSettings();
                        requestSettings.executeAsynchronously = false;

                        _executeRequest(
                                resultRequest,
                                requestSettings,
                                new WebRequest.Callback() {
                                    @Override
                                    public void run(WebRequest request) {
                                        if (! request.hasResult()) {
                                            _onFailure("Failed to connect.", callback);
                                            return;
                                        }

                                        String html = request.getRawResult();
                                        AttackBundle attackBundle = _utopiaParser.parseAttack(html);

                                        if (! attackBundle.isValid()) {
                                            _onFailure("Error parsing attack result.", callback);
                                            return;
                                        }

                                        if (_intelSubmitter != null) {
                                            _intelSubmitter.submitAttackIntel(request, attack, attackBundle);
                                        }

                                        if (callback != null) {
                                            callback.run(new Response(true, null, attackBundle));
                                        }
                                    }
                                }
                        );
                    }
                });
            }
        };

        if (_currentKingdom == null || ! _currentKingdom.equals(kingdomIdentifier)) {
            _setKingdom(kingdomIdentifier, executeOperation);
        }
        else {
            executeOperation.run(null);
        }
    }

    public void downloadFundDragonInfo(final Callback callback) {
        WebRequest fundDragonInfoRequest = new WebRequest();
        fundDragonInfoRequest.setUrl(Settings.getFundDragonUrl());
        fundDragonInfoRequest.setType(WebRequest.RequestType.GET);
        fundDragonInfoRequest.setFollowsRedirects(true);

        _executeRequest(fundDragonInfoRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                FundDragonInfoBundle fundDragonInfoBundle = _utopiaParser.parseFundDragon(request.getRawResult());

                if (! fundDragonInfoBundle.isValid()) {
                    _onFailure("Error parsing fund dragon info.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, fundDragonInfoBundle));
                }
            }
        });
    }

    public void downloadAttackDragonInfo(final Callback callback) {
        WebRequest attackDragonInfoRequest = new WebRequest();
        attackDragonInfoRequest.setUrl(Settings.getAttackDragonUrl());
        attackDragonInfoRequest.setType(WebRequest.RequestType.GET);
        attackDragonInfoRequest.setFollowsRedirects(true);

        _executeRequest(attackDragonInfoRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                String html = request.getRawResult();
                AttackDragonInfoBundle attackDragonInfoBundle = _utopiaParser.parseAttackDragon(html);

                if (! attackDragonInfoBundle.isValid()) {
                    _onFailure("Error parsing attack dragon info.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, attackDragonInfoBundle));
                }
            }
        });
    }

    public void fundDragon(Integer amount, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest fundDragonRequest = new WebRequest();
        fundDragonRequest.setUrl(Settings.getFundDragonUrl());
        fundDragonRequest.setType(WebRequest.RequestType.POST);
        fundDragonRequest.setFollowsRedirects(false);

        fundDragonRequest.setPostParam("amount", amount.toString());

        _executeRequest(fundDragonRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public static class AttackDragonArmy {
        public Integer soldiers = 0;
        public Integer offensiveUnits = 0;
        public Integer defensiveUnits = 0;
        public Integer elites = 0;
    }
    public void attackDragon(AttackDragonArmy attackDragonArmy, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest attackDragonRequest = new WebRequest();
        attackDragonRequest.setUrl(Settings.getAttackDragonUrl());
        attackDragonRequest.setType(WebRequest.RequestType.POST);
        attackDragonRequest.setFollowsRedirects(false);

        attackDragonRequest.setPostParam("quantity_0", attackDragonArmy.soldiers.toString());
        attackDragonRequest.setPostParam("quantity_1", attackDragonArmy.offensiveUnits.toString());
        attackDragonRequest.setPostParam("quantity_2", attackDragonArmy.defensiveUnits.toString());
        attackDragonRequest.setPostParam("quantity_3", attackDragonArmy.elites.toString());

        _executeRequest(attackDragonRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final AttackDragonInfoBundle attackDragonInfoBundle = _utopiaParser.parseAttackDragon(html); // TODO: Should update bundle to include army sent...

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitAttackDragonIntel(request, attackDragonInfoBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void downloadTradeSettings(Callback callback) {
        _downloadTradeSettings(callback);
    }
    private void _downloadTradeSettings(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getAidUrl());
        webRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Unable to connect.", callback);
                    return;
                }

                final TradeSettingsBundle tradeSettingsBundle = _utopiaParser.parseTradeBalance(request.getRawResult());
                if (! tradeSettingsBundle.isValid()) {
                    _onFailure("Unable to parse trade settings.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, tradeSettingsBundle));
                }
            }
        });
    }

    public static class AidShipment {
        public Integer food = 0;
        public Integer gold = 0;
        public Integer runes = 0;
        public Integer soldiers = 0;
    }
    public void sendAid(AidShipment aidShipment, Boolean provinceAllowsAid, Integer targetProvinceId, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getAidUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setFollowsRedirects(false);

        webRequest.setPostParam("target_province", targetProvinceId.toString());
        webRequest.setPostParam("food", aidShipment.food.toString());
        webRequest.setPostParam("money", aidShipment.gold.toString());
        webRequest.setPostParam("runes", aidShipment.runes.toString());
        webRequest.setPostParam("infantry_count", aidShipment.soldiers.toString());
        webRequest.setPostParam("block_option", provinceAllowsAid ? TradeSettingsBundle.PERMIT_AID_VALUE : TradeSettingsBundle.BLOCKED_AID_VALUE);

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(final WebRequest request) {
                if (! webRequest.getHeaders().containsKey("Location")) {
                    String errorMessage = "";

                    if (webRequest.hasResult()) {
                        final String html = webRequest.getRawResult();
                        final SendAidBundle sendAidBundle = _utopiaParser.parseSendAid(html);
                        errorMessage = sendAidBundle.get(SendAidBundle.Keys.RESULT_TEXT);
                    }
                    else {
                        errorMessage = "Failed to connect.";
                    }

                    _onFailure(errorMessage, callback);
                    return;
                }

                final String location = webRequest.getHeaders().get("Location").get(0);
                final WebRequest resultRequest = new WebRequest();
                resultRequest.setUrl(location);
                resultRequest.setType(WebRequest.RequestType.GET);
                resultRequest.setFollowsRedirects(false);
                resultRequest.setHeader("Referer", Settings.getOffensiveSpellsUrl());
                resultRequest.setHeader("Host", Settings.getHost());

                _executeRequest(resultRequest, new WebRequest.Callback() {
                    @Override
                    public void run(WebRequest responseRequest) {
                        if (!responseRequest.hasResult()) {
                            _onFailure("Failed to connect.", callback);
                            return;
                        }

                        String html = resultRequest.getRawResult();
                        SendAidBundle sendAidBundle = _utopiaParser.parseSendAid(html);

                        if (! sendAidBundle.isValid()) {
                            _onFailure("Aid sent, but unable to parse result.", callback);
                            return;
                        }

                        if (_intelSubmitter != null) {
                            _intelSubmitter.submitSendAidIntel(request, sendAidBundle);
                        }

                        if (callback != null) {
                            callback.run(new Response(true, null, null));
                        }
                    }
                });
            }
        });
    }

    public void downloadBuildingCost(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getConstructBuildingUrl());
        webRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest responseRequest) {
                if (! responseRequest.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                BuildCostBundle buildCostBundle = _utopiaParser.parseBuildCosts(responseRequest.getRawResult());
                if (! buildCostBundle.isValid()) {
                    _onFailure("Error parsing build costs.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, buildCostBundle));
                }
            }
        });
    }

    public void downloadExplorationCost(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getExplorationUrl());
        webRequest.setType(WebRequest.RequestType.GET);

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest responseRequest) {
                if (! responseRequest.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                ExplorationCostsBundle explorationCostsBundle = _utopiaParser.parseExplorationCosts(responseRequest.getRawResult());
                if (! explorationCostsBundle.isValid()) {
                    _onFailure("Error parsing exploration costs.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, explorationCostsBundle));
                }
            }
        });
    }

    public void exploreAcres(Integer acres, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getExplorationUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("num_acres", acres.toString());
        webRequest.setFollowsRedirects(false);

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest responseRequest) {
                if (! responseRequest.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void downloadPrivateMessages(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getPrivateMessagesUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final PrivateMessagesBundle privateMessagesBundle = _utopiaParser.parsePrivateMessages(request.getRawResult());
                if (! privateMessagesBundle.isValid()) {
                    _onFailure("Error parsing private messages.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, privateMessagesBundle));
                }
            }
        });
    }

    public void downloadPrivateMessage(Integer messageId, final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getReadPrivateMessageUrl(messageId));
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final PrivateMessageBundle privateMessageBundle = _utopiaParser.parsePrivateMessage(request.getRawResult());
                if (! privateMessageBundle.isValid()) {
                    _onFailure("Error parsing private message.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, privateMessageBundle));
                }
            }
        });
    }

    public void sendPrivateMessageReply(Integer messageId, String title, String content, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getReplyPrivateMessageUrl(messageId));
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("subject", title);
        webRequest.setPostParam("body", content);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void sendPrivateMessage(Integer provinceUtopiaId, String title, String content, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getComposePrivateMessageUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("recipient", provinceUtopiaId.toString());
        webRequest.setPostParam("subject", title);
        webRequest.setPostParam("body", content);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void allocateScientists(final List<Scientist> scientists, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getScienceUrl());
        webRequest.setType(WebRequest.RequestType.POST);

        webRequest.setPostParam("reassign", "Reassign Scientists");

        for (final Scientist scientist : scientists) {
            webRequest.setPostParam(scientist.getFormName(), Science.getStringForType(scientist.getAssignment()));
        }

        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! webRequest.getHeaders().containsKey("Location")) {
                    final String errorMessage;
                    {
                        if (webRequest.hasResult()) {
                            final String html = webRequest.getRawResult();
                            final ScienceResultBundle scienceResultBundle = _utopiaParser.parseScienceResult(html);
                            if (scienceResultBundle.isValid()) {
                                final String resultText = scienceResultBundle.get(ScienceResultBundle.Keys.RESULT_TEXT).trim();
                                if (resultText.isEmpty()) {
                                    errorMessage = "Nothing to do.";
                                }
                                else {
                                    errorMessage = resultText;
                                }
                            }
                            else {
                                errorMessage = "Error parsing result.";
                            }
                        }
                        else {
                            errorMessage = "Failed to connect.";
                        }
                    }

                    _onFailure(errorMessage, callback);
                    return;
                }

                final String location = webRequest.getHeaders().get("Location").get(0);
                final WebRequest resultRequest = new WebRequest();
                resultRequest.setUrl(location);
                resultRequest.setType(WebRequest.RequestType.GET);
                resultRequest.setFollowsRedirects(false);
                resultRequest.setHeader("Referer", Settings.getScienceUrl());
                resultRequest.setHeader("Host", Settings.getHost());

                _executeRequest(resultRequest, new WebRequest.Callback() {
                    @Override
                    public void run(WebRequest responseRequest) {
                        if (! responseRequest.hasResult()) {
                            _onFailure("Failed to connect.", callback);
                            return;
                        }

                        final ScienceResultBundle scienceResultBundle = _utopiaParser.parseScienceResult(responseRequest.getRawResult());

                        if (scienceResultBundle == null || ! scienceResultBundle.isValid()) {
                            _onFailure("Error parsing science result.", callback);
                            return;
                        }

                        if (Util.parseInt(scienceResultBundle.get(ScienceResultBundle.Keys.WAS_SUCCESS)) == 0) {
                            final String errorMessage;
                            {
                                final String resultText = scienceResultBundle.get(ScienceResultBundle.Keys.RESULT_TEXT).trim();
                                if (resultText.isEmpty()) {
                                    errorMessage = "Nothing to do.";
                                }
                                else {
                                    errorMessage = resultText;
                                }
                            }

                            _onFailure(errorMessage, callback);
                            return;
                        }

                        if (callback != null) {
                            callback.run(new Response(true, null, scienceResultBundle));
                        }
                    }
                });
            }
        });
    }

    public void downloadScience(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getScienceUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ScienceBundle scienceBundle = _utopiaParser.parseScience(html);

                if (! scienceBundle.isValid()) {
                    _onFailure("Failed to parse science.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitScienceIntel(request, scienceBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, scienceBundle));
                }
            }
        });
    }

    public void downloadScienceCouncil(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getScienceCouncilUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ScienceBundle scienceBundle = _utopiaParser.parseScience(html);

                if (! scienceBundle.isValid()) {
                    _onFailure("Failed to parse science.", callback);
                    return;
                }

                if (_intelSubmitter != null) {
                    _intelSubmitter.submitScienceIntel(request, scienceBundle);
                }

                if (callback != null) {
                    callback.run(new Response(true, null, scienceBundle));
                }
            }
        });
    }

    public void downloadForumTopics(final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getForumUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ForumTopicsBundle forumTopicsBundle = _utopiaParser.parseForumTopics(html);

                if (! forumTopicsBundle.isValid()) {
                    _onFailure("Failed to parse forum topics.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, forumTopicsBundle));
                }
            }
        });
    }

    public void downloadForumTopicPosts(final Integer forumTopicId, final Integer pageNumber, final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getForumPostUrl(forumTopicId, pageNumber));
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ForumTopicPostsBundle forumTopicPostsBundle = _utopiaParser.parseForumTopicPosts(html, forumTopicId.toString());

                if (! forumTopicPostsBundle.isValid()) {
                    _onFailure("Failed to parse forum topic posts.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, forumTopicPostsBundle));
                }
            }
        });
    }

    public void submitForumTopicPost(final Integer forumTopicId, final String content, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getForumPostUrl(forumTopicId, 1));
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("body", content);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void submitForumTopic(final String title, final String content, final Callback callback) {
        synchronized (_utopiaCache) {
            _utopiaCache.clear();
        }

        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getForumUrl());
        webRequest.setType(WebRequest.RequestType.POST);
        webRequest.setPostParam("subject", title);
        webRequest.setPostParam("body", content);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, null));
                }
            }
        });
    }

    public void downloadProvinceIdsViaAid(final Kingdom.Identifier provinceKingdomId, final Callback callback) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getAidUrl());
        webRequest.setType(WebRequest.RequestType.GET);
        _executeRequest(webRequest, new WebRequest.Callback() {
            @Override
            public void run(WebRequest request) {
                if (! request.hasResult()) {
                    _onFailure("Failed to connect.", callback);
                    return;
                }

                final String html = request.getRawResult();
                final ProvinceIdsBundle provinceIdsBundle = _utopiaParser.parseAidProvinceIds(html, provinceKingdomId.getKingdomId(), provinceKingdomId.getIslandId());

                if (! provinceIdsBundle.isValid()) {
                    _onFailure("Failed to parse province ids.", callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new Response(true, null, provinceIdsBundle));
                }
            }
        });
    }
}
