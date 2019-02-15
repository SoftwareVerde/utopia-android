package com.softwareverde.utopia;

import com.softwareverde.json.Json;
import com.softwareverde.utopia.bundle.ActiveSpellsBundle;
import com.softwareverde.utopia.bundle.ArmyOffenseBundle;
import com.softwareverde.utopia.bundle.AttackBundle;
import com.softwareverde.utopia.bundle.AttackDragonInfoBundle;
import com.softwareverde.utopia.bundle.AvailableSpellsBundle;
import com.softwareverde.utopia.bundle.AvailableThieveryOperationBundle;
import com.softwareverde.utopia.bundle.AvailableThieveryOperationsBundle;
import com.softwareverde.utopia.bundle.BuildCostBundle;
import com.softwareverde.utopia.bundle.BuildingsBundle;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ChatroomBundle;
import com.softwareverde.utopia.bundle.DraftRateBundle;
import com.softwareverde.utopia.bundle.ExplorationCostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicsBundle;
import com.softwareverde.utopia.bundle.FundDragonInfoBundle;
import com.softwareverde.utopia.bundle.InfiltrateThievesBundle;
import com.softwareverde.utopia.bundle.KingdomBundle;
import com.softwareverde.utopia.bundle.KingdomIntelBundle;
import com.softwareverde.utopia.bundle.MilitaryBundle;
import com.softwareverde.utopia.bundle.MilitarySettingsBundle;
import com.softwareverde.utopia.bundle.NewsBundle;
import com.softwareverde.utopia.bundle.NewspaperBundle;
import com.softwareverde.utopia.bundle.PrivateMessageBundle;
import com.softwareverde.utopia.bundle.PrivateMessagesBundle;
import com.softwareverde.utopia.bundle.ProvinceIdBundle;
import com.softwareverde.utopia.bundle.ProvinceIdsBundle;
import com.softwareverde.utopia.bundle.ProvinceIntelBundle;
import com.softwareverde.utopia.bundle.ScienceBundle;
import com.softwareverde.utopia.bundle.SpellBundle;
import com.softwareverde.utopia.bundle.SpellResultBundle;
import com.softwareverde.utopia.bundle.StateCouncilBundle;
import com.softwareverde.utopia.bundle.ThieveryOperationBundle;
import com.softwareverde.utopia.bundle.ThroneBundle;
import com.softwareverde.utopia.bundle.TradeSettingsBundle;
import com.softwareverde.utopia.bundle.WarRoomBundle;
import com.softwareverde.utopia.database.KeyValueStore;
import com.softwareverde.utopia.database.UtopiaDatabase;
import com.softwareverde.utopia.intelsync.IntelSubmitter;
import com.softwareverde.utopia.intelsync.IntelSync;
import com.softwareverde.utopia.intelsync.IntelSyncFactory;
import com.softwareverde.utopia.intelsync.VerdeIntelUtil;
import com.softwareverde.utopia.news.NewsEvent;
import com.softwareverde.utopia.parser.UtopiaParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private static final String _LAST_CHAT_MESSAGE_PING_TIME_KEY = "LAST_CHAT_PING_MESSAGE_TIME";
    private static final String _LAST_CHAT_MESSAGE_UPLOAD_TIME_KEY = "LAST_CHAT_MESSAGE_UPLOAD_TIME";
    private static final String _HIDE_PREMIUM_ICON_KEY = "HIDE_PREMIUM_ICON";
    private static final String _VERDE_INTEL_ENABLED_KEY = "VERDE_SYNC_ENABLED";

    public class SessionResponse {
        private Boolean _wasSuccess = false;
        private String _errorMessage = null;

        public SessionResponse(UtopiaUtil.Response response) {
            _wasSuccess = response.getWasSuccess();
            _errorMessage = response.getErrorMessage();
        }
        public SessionResponse(Boolean wasSuccess, String errorMessage) {
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
        void run(SessionResponse response);
    }
    public interface KingdomCallback {
        void run(Kingdom.Identifier kingdomIdentifier);
    }
    public interface DownloadKingdomCallback {
        void run(SessionResponse response, Kingdom.Identifier kingdomIdentifier);
    }
    public interface ServerTickCallback {
        void run(Boolean serverIsTicking);
    }
    public interface OnLoggedBackInCallback {
        void onLogin();
    }
    public interface OnLoggedOutCallback {
        void run(OnLoggedBackInCallback onLoggedBackInCallback);
    }
    public interface DownloadNewsCallback {
        void run(Integer newsMonth, Integer newsYear);
    }

    public interface BadAccessCallback {
        void run(UtopiaParser.BadAccessType badAccessType);
    }

    public void addAdviewCallback(String identifier, UtopiaUtil.AdViewCallback callback) {
        _utopiaUtil.addAdViewCallback(identifier, callback);
    }
    public void removeAdViewCallback(String identifier) {
        _utopiaUtil.removeAdViewCallback(identifier);
    }

    public static String ON_PING_VIBRATE_CALLBACK_IDENTIFIER = "SessionOnPingCallbackVibrate";
    public static String ON_PING_CALLBACK_IDENTIFIER = "SessionOnPingCallbackNotification";

    public static class Dependencies {
        private KeyValueStore _keyValueStore;
        private UtopiaDatabase _utopiaDatabase;
        private IntelSyncFactory _intelSyncFactory;
        private Vibrator _vibrator;
        private NotificationMaker _notificationMaker;

        public void setKeyValueStore(final KeyValueStore keyValueStore) { _keyValueStore = keyValueStore; }
        public void setUtopiaDatabase(final UtopiaDatabase utopiaDatabase) { _utopiaDatabase = utopiaDatabase; }
        public void setIntelSyncFactory(final IntelSyncFactory intelSyncFactory) { _intelSyncFactory = intelSyncFactory; }
        public void setVibrator(final Vibrator vibrator) { _vibrator = vibrator; }
        public void setNotificationMaker(final NotificationMaker notificationMaker) { _notificationMaker = notificationMaker; }
    }

    private static Session _instance;
    private static Dependencies _dependencies;

    public static void setDependencies(final Dependencies dependencies) {
        if (_instance == null) {
            _dependencies = dependencies;
        }
        else {
            System.out.println("NOTICE: Ignoring dependencies; Session instance already created.");
        }
    }

    public static class InstanceNotAvailableException extends RuntimeException {
        public InstanceNotAvailableException() {
            super("Session instance not available. Have you called Session.setDependencies()?");
        }
    }

    public enum DownloadType {
        THRONE, NEWS, BUILDING_COUNCIL, MILITARY_COUNCIL, MILITARY_SETTINGS, AVAILABLE_SPELLS,
        PROVINCE_INTEL, ACTIVE_SPELLS, STATE_COUNCIL, PROVINCE_IDENTIFIERS,
        AVAILABLE_THIEVERY_OPERATIONS, KINGDOM, FUND_DRAGON, ATTACK_DRAGON, TRADE_SETTINGS,
        BUILD_COSTS, EXPLORATION_COSTS, PRIVATE_MESSAGES, PRIVATE_MESSAGE, SCIENCE, FORUM_TOPICS,
        FORUM_TOPIC_POSTS, KINGDOM_INTEL, CHAT_MESSAGES, WAR_ROOM_SETTINGS
    }

    public interface BeginDownloadCallback {
        void run(DownloadType begunDownloadType);
    }
    public interface FinishDownloadCallback {
        void run(DownloadType endedDownloadType);
    }

    public static Session getInstance() {
        if (_dependencies == null) {
            throw new InstanceNotAvailableException();
        }

        if (_instance == null) {
            _instance = new Session(
                _dependencies._keyValueStore,
                _dependencies._utopiaDatabase,
                _dependencies._intelSyncFactory,
                _dependencies._vibrator,
                _dependencies._notificationMaker
            );
        }

        return _instance;
    }

    private UtopiaUtil _utopiaUtil;
    private IntelSyncFactory _intelSyncFactory;
    private IntelSync _intelSync;
    private VerdeIntelUtil _verdeIntelSync;
    private IntelSubmitter _intelSubmitter;
    private List<NewsEvent> _newsArray = new ArrayList<NewsEvent>();
    private List<NewsEvent> _kingdomNewsArray = new ArrayList<NewsEvent>();
    private Boolean _isLoggedIn = false;
    private Map<String, Runnable> _loginCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _logoutCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _dateCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _throneCallbacks = new HashMap<String, Runnable>();
    private Map<String, DownloadNewsCallback> _newsCallbacks = new HashMap<String, DownloadNewsCallback>();
    private Map<String, Runnable> _spellListCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _activeSpellsCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _buildingsCouncilCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _militarySettingsCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _messageCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _stateCallbacks = new HashMap<String, Runnable>();
    private Map<String, Runnable> _privateMessageCallbacks = new HashMap<String, Runnable>();
    private Map<String, KingdomCallback> _kingdomCallbacks = new HashMap<String, KingdomCallback>();
    private Map<String, Runnable> _thieveryOpsCallbacks = new HashMap<String, Runnable>();
    private final Map<Kingdom.Identifier, Map<String, VerdeIntelUtil.AvailableIntel>> _availableVerdeIntel = new HashMap<Kingdom.Identifier, Map<String, VerdeIntelUtil.AvailableIntel>>();
    private Province _province = new Province();
    private KeyValueStore _keyValueStore;
    private UtopiaDatabase _utopiaDatabase;
    private List<Spell> _availableSpells = new ArrayList<Spell>();
    private List<DraftRate> _draftRates = new ArrayList<DraftRate>();
    private Kingdom.Identifier _kingdomIdentifier;
    private Map<Kingdom.Identifier, Kingdom> _downloadedKingdoms = new HashMap<Kingdom.Identifier, Kingdom>();
    private Chatroom _chatroom = new Chatroom();
    private List<PrivateMessage> _privateMessages = new ArrayList<PrivateMessage>();
    private Chatroom.Message _lastMessage = null;
    private volatile boolean _downloadMessages = false;
    private Thread _downloadMessagesThread = null;
    private Integer _consecutiveEmptyChatAttempts = 0;
    private Boolean _shouldVibrate = false;
    private Boolean _shouldNotify = false;
    private String _currentUtopiaDate;
    private Map<String, List<ThieveryOperation>> _thieveryOperations = new HashMap<String, List<ThieveryOperation>>();
    private Kingdom.Identifier _focusedKingdomIdentifier = null;
    private Forum _forum = new Forum();
    private ProvinceTagUtil _provinceTagUtil = new ProvinceTagUtil();
    private Vibrator _vibrator;
    private NotificationMaker _notificationMaker;
    private BeginDownloadCallback _beginDownloadCallback;
    private FinishDownloadCallback _finishDownloadCallback;
    private BadAccessCallback _badAccessCallback;

    private Map<Kingdom.Identifier, Json> _attackTimeParameters = new HashMap<Kingdom.Identifier, Json>();

    private void _onFailure(String errorMessage, Callback callback) {
        System.out.println("Session Download Failure: "+ errorMessage);

        if (callback != null) {
            callback.run(new SessionResponse(false, Util.coalesce(errorMessage)));
        }
    }
    private void _onFailure(DownloadKingdomCallback callback, String errorMessage) {
        System.out.println("Session Download Failure: "+ errorMessage);

        if (callback != null) {
            callback.run(new SessionResponse(false, Util.coalesce(errorMessage)), null);
        }
    }
    private void _onFailure(ThieveryOperationCallback callback, String errorMessage) {
        System.out.println("Session Download Failure: "+ errorMessage);

        if (callback != null) {
            callback.run(new ThieveryOperationResponse(false, Util.coalesce(errorMessage)));
        }
    }
    private void _onFailure(CastSpellCallback callback, String errorMessage) {
        System.out.println("Session Download Failure: "+ errorMessage);

        if (callback != null) {
            callback.run(new CastSpellResponse(false, Util.coalesce(errorMessage)));
        }
    }
    private void _onFailure(AttackCallback callback, String errorMessage) {
        System.out.println("Session Download Failure: "+ errorMessage);

        if (callback != null) {
            callback.run(new AttackResponse(false, Util.coalesce(errorMessage)));
        }
    }

    public void setOnServerTickCallback(final ServerTickCallback serverTickCallback) {
        if (serverTickCallback == null) {
            _utopiaUtil.setOnServerTickCallback(null);
            return;
        }

        _utopiaUtil.setOnServerTickCallback(new UtopiaUtil.OnServerTickCallback() {
            @Override
            public void run(Boolean serverIsTicking) {
                serverTickCallback.run(serverIsTicking);
            }
        });
    }

    public void setOnLoggedOutCallback(final OnLoggedOutCallback onLoggedOutCallback) {
        if (onLoggedOutCallback == null) {
            _utopiaUtil.setOnLoggedOutCallback(null);
            return;
        }

        _utopiaUtil.setOnLoggedOutCallback(new UtopiaUtil.OnLoggedOutCallback() {
            @Override
            public void run(final UtopiaUtil.UtilLoggedBackInCallback loggedInCallback) {
                onLoggedOutCallback.run(new OnLoggedBackInCallback() {
                    @Override
                    public void onLogin() {
                        loggedInCallback.onLogin();
                    }
                });
            }
        });
    }

    // NOTE: This callback is invoked if the account is not in a valid state.
    //  The BadAccessCallback will be called after the normal failure callbacks.
    public void setBadAccessCallback(final BadAccessCallback badAccessCallback) {
        _badAccessCallback = badAccessCallback;
    }

    public void loadCookies() { _loadCookies(); }
    private void _loadCookies() {
        Map<String, String> cookies = new HashMap<String, String>();

        String[] cookieKeys = _keyValueStore.getString("COOKIE_KEYS").split(",");
        for (String cookieKey : cookieKeys) {
            if (cookieKey.trim().length() == 0) {
                continue;
            }

            String cookie = _keyValueStore.getString(cookieKey);
            cookies.put(cookieKey, cookie);
        }

        _utopiaUtil.setCookies(cookies);
    }

    public void saveCookies() { _saveCookies(); }
    private void _saveCookies() {
        StringBuilder cookieKeysBuilder = new StringBuilder();
        Map<String, String> cookies = _utopiaUtil.getCookies();
        for (String cookieKey : cookies.keySet()) {
            _keyValueStore.putString(cookieKey, cookies.get(cookieKey));
            cookieKeysBuilder.append(cookieKey + ",");
        }

        String cookieKeys = cookieKeysBuilder.toString();
        _keyValueStore.putString("COOKIE_KEYS", cookieKeys);
    }
    private void _clearCookies() {
        _utopiaUtil.setCookies(new HashMap<String, String>());
    }
    private void _saveCredentials(String username, String password) {
        _keyValueStore.putString("USERNAME", username);
        _keyValueStore.putString("PASSWORD", password);
    }
    public boolean hasSavedCredentials() {
        return (_keyValueStore.hasKey("USERNAME") && _keyValueStore.hasKey("PASSWORD"));
    }
    public void clearCredentials() {
        if (_keyValueStore.hasKey("USERNAME")) {
            _keyValueStore.removeKey("USERNAME");
        }
        if (_keyValueStore.hasKey("PASSWORD")) {
            _keyValueStore.removeKey("PASSWORD");
        }
    }

    private Kingdom _getKingdom(Kingdom.Identifier identifier) {
        if (! _downloadedKingdoms.containsKey(identifier)) {
            return null;
        }

        // // TODO: Search the database for kingdom...
        // Kingdom kingdom = _persistentStore.getKingdom(identifier);
        // if (kingdom != null) {
        //     return kingdom;
        // }

        return _downloadedKingdoms.get(identifier);
    }

    public void resume(Runnable callback) {
        _utopiaUtil.resetCsrfToken(callback);
    }

    private Session(final KeyValueStore keyValueStore, final UtopiaDatabase utopiaDatabase, final IntelSyncFactory intelSyncFactory, final Vibrator vibrator, final NotificationMaker notificationMaker) {
        _utopiaUtil = UtopiaUtil.getInstance();
        _intelSubmitter = new IntelSubmitter();
        _keyValueStore = keyValueStore;
        _utopiaDatabase = utopiaDatabase;
        _intelSyncFactory = intelSyncFactory;
        _vibrator = vibrator;
        _notificationMaker = notificationMaker;

        _utopiaUtil.setIntelSubmitter(_intelSubmitter);

        if (_keyValueStore.hasKey("PROVINCE_NAME")) {
            _chatroom.setUsername(_keyValueStore.getString("PROVINCE_NAME"));
        }

        if (_hasIntelSyncEnabled() && _intelSyncFactory != null) {
            final IntelSync.IntelSyncType intelSyncType = _getIntelSyncType();
            _intelSync = _intelSyncFactory.createInstance(intelSyncType);
        }

        if (_hasVerdeIntelSyncEnabled() && _intelSyncFactory != null) {
            _initVerdeIntelSync();
        }
    }

    private void _loadNewsFromBundle(NewspaperBundle newspaperBundle) {
        _newsArray.clear();

        if (newspaperBundle.hasGroupKey(NewspaperBundle.Keys.NEWS_BUNDLE)) {
            List<Bundle> newsItems = newspaperBundle.getGroup(NewspaperBundle.Keys.NEWS_BUNDLE);
            for (Integer i = 0; i < newsItems.size(); i++) {
                NewsBundle newsBundle = (NewsBundle) newsItems.get(i);

                if (newsBundle != null && newsBundle.isValid()) {
                    String date = newsBundle.get(NewsBundle.Keys.DATE);
                    String news = newsBundle.get(NewsBundle.Keys.NEWS);
                    _newsArray.add(new NewsEvent(_province, date, news));
                }
            }
        }
    }
    private void _loadKingdomNewsFromBundle(NewspaperBundle newspaperBundle) {
        _kingdomNewsArray.clear();

        if (newspaperBundle.hasGroupKey(NewspaperBundle.Keys.NEWS_BUNDLE)) {
            List<Bundle> newsItems = newspaperBundle.getGroup(NewspaperBundle.Keys.NEWS_BUNDLE);
            for (Integer i = 0; i < newsItems.size(); i++) {
                NewsBundle newsBundle = (NewsBundle) newsItems.get(i);

                if (newsBundle != null && newsBundle.isValid()) {
                    String date = newsBundle.get(NewsBundle.Keys.DATE);
                    String news = newsBundle.get(NewsBundle.Keys.NEWS);
                    _kingdomNewsArray.add(new NewsEvent(_province, date, news));
                }
            }
        }
    }

    private void _setCurrentUtopiaDate(String date) {
        if (date == null) return;

        String previousDate = _currentUtopiaDate;
        _currentUtopiaDate = date;

        if (! _currentUtopiaDate.equals(previousDate)) {
            _executeCallbacks(_dateCallbacks);
        }
    }

    public ProvinceCredentials getProvinceCredentials() {
        final String username = _keyValueStore.getString("USERNAME");
        final String password = _keyValueStore.getString("PASSWORD");

        final String provinceName = _keyValueStore.getString("PROVINCE_NAME");
        final Integer kingdomId = Util.parseInt(_keyValueStore.getString("KINGDOM"));
        final Integer islandId = Util.parseInt(_keyValueStore.getString("ISLAND"));

        final ProvinceCredentials provinceCredentials = new ProvinceCredentials();
        provinceCredentials.setProvince(provinceName, new Kingdom.Identifier(kingdomId, islandId));
        provinceCredentials.setCredentials(username, password);
        return provinceCredentials;
    }

    public void addDateCallback(String uniqueIdentifier, Runnable callback) {
        _dateCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeDateCallback(String uniqueIdentifier) {
        _dateCallbacks.remove(uniqueIdentifier);
    }
    public void addLoginCallback(String uniqueIdentifier, Runnable callback) {
        _loginCallbacks.put(uniqueIdentifier, callback);
    }
    public void addLogoutCallback(String uniqueIdentifier, Runnable callback) {
        _logoutCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeLogoutCallback(String uniqueIdentifier) {
        _logoutCallbacks.remove(uniqueIdentifier);
    }
    public void removeLoginCallback(String uniqueIdentifier) {
        _loginCallbacks.remove(uniqueIdentifier);
    }
    public void addThroneCallback(String uniqueIdentifier, Runnable callback) {
        _throneCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeThroneCallback(String uniqueIdentifier) {
        _throneCallbacks.remove(uniqueIdentifier);
    }
    public void addNewsCallback(String uniqueIdentifier, DownloadNewsCallback callback) {
        _newsCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeNewsCallback(String uniqueIdentifier) {
        _newsCallbacks.remove(uniqueIdentifier);
    }
    public void addBuildingsCouncilCallback(String uniqueIdentifier, Runnable callback) {
        _buildingsCouncilCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeBuildingsCouncilCallback(String uniqueIdentifier) {
        _buildingsCouncilCallbacks.remove(uniqueIdentifier);
    }
    public void addSpellListCallback(String uniqueIdentifier, Runnable callback) {
        _spellListCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeSpellListCallback(String uniqueIdentifier) {
        _spellListCallbacks.remove(uniqueIdentifier);
    }
    public void addActiveSpellsCallback(String uniqueIdentifier, Runnable callback) {
        _activeSpellsCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeActiveSpellsCallback(String uniqueIdentifier) {
        _activeSpellsCallbacks.remove(uniqueIdentifier);
    }
    public void addMilitarySettingsCallback(String uniqueIdentifier, Runnable callback) {
        _militarySettingsCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeMilitarySettingsCallback(String uniqueIdentifier) {
        _militarySettingsCallbacks.remove(uniqueIdentifier);
    }
    public void addStateCouncilCallback(String uniqueIdentifier, Runnable callback) {
        _stateCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeStateCouncilCallback(String uniqueIdentifier) {
        _stateCallbacks.remove(uniqueIdentifier);
    }
    public void addKingdomCallback(String uniqueIdentifier, KingdomCallback callback) {
        _kingdomCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeKingdomCallback(String uniqueIdentifier) {
        _kingdomCallbacks.remove(uniqueIdentifier);
    }
    public void addMessagesCallback(String uniqueIdentifier, Runnable callback) {
        _messageCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeMessagesCallback(String uniqueIdentifier) {
        _messageCallbacks.remove(uniqueIdentifier);
    }
    public void addThieveryOperationsCallback(String uniqueIdentifier, Runnable callback) {
        _thieveryOpsCallbacks.put(uniqueIdentifier, callback);
    }
    public void removeThieveryOperationsCallback(String uniqueIdentifier) {
        _thieveryOpsCallbacks.remove(uniqueIdentifier);
    }
    public void addPrivateMessageCallback(String uniqueIdentifier, Runnable callback) {
        _privateMessageCallbacks.put(uniqueIdentifier, callback);
    }
    public void removePrivateMessageCallback(String uniqueIdentifier) {
        _privateMessageCallbacks.remove(uniqueIdentifier);
    }

    private void _executeBeginDownloadCallback(final DownloadType downloadType) {
        if (_beginDownloadCallback != null) {
            _beginDownloadCallback.run(downloadType);
        }
    }
    private void _executeEndDownloadCallback(final DownloadType downloadType) {
        if (_finishDownloadCallback != null) {
            _finishDownloadCallback.run(downloadType);
        }
    }

    private void _executeCallbacks(Map<String, Runnable> callbacks) {
        for (Runnable callback : callbacks.values()) {
            callback.run();
        }
    }
    private void _executeCallbacks(Kingdom.Identifier kingdomIdentifier, Map<String, KingdomCallback> callbacks) {
        for (KingdomCallback callback : callbacks.values()) {
            callback.run(kingdomIdentifier);
        }
    }
    private void _executeCallbacks(Integer month, Integer year, Map<String, DownloadNewsCallback> callbacks) {
        for (DownloadNewsCallback callback : callbacks.values()) {
            callback.run(month, year);
        }
    }

    // Login with stored credentials
    public void autoLogin(Callback callback) {
        _autoLogin(callback);
    }
    private void _autoLogin(final Callback callback) {
        String username = "";
        String password = "";

        if (_keyValueStore.hasKey("USERNAME") && _keyValueStore.hasKey("PASSWORD")) {
            username = _keyValueStore.getString("USERNAME");
            password = _keyValueStore.getString("PASSWORD");
        }

        _login(username, password, callback);
    }

    public void login(String username, String password, Callback callback) {
        _login(username, password, callback);
    }
    private void _login(final String username, final String password, final Callback callback) {
        _utopiaUtil.login(username, password, new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                if (!response.getWasSuccess()) {
                    _isLoggedIn = false;

                    if (callback != null) {
                        callback.run(new SessionResponse(false, Util.coalesce(response.getErrorMessage())));
                    }

                    return;
                }

                _isLoggedIn = true;
                _saveCredentials(username, password);
                _executeCallbacks(_loginCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    private void _logout() {
        _clearCookies();
        _isLoggedIn = false;
    }

    public void logout() {
        _logout();

        _executeCallbacks(_logoutCallbacks);
    }

    private void _downloadThrone(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.THRONE);

        _utopiaUtil.downloadThrone(new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.THRONE);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);

                    if (response.hasExtra(UtopiaUtil.BAD_ACCESS_EXTRA)) {
                        final UtopiaParser.BadAccessType badAccessType = (UtopiaParser.BadAccessType) response.getExtra(UtopiaUtil.BAD_ACCESS_EXTRA);

                        if (_badAccessCallback != null && (badAccessType != UtopiaParser.BadAccessType.NONE) ) {
                            _badAccessCallback.run(badAccessType);
                        }
                    }

                    return;
                }

                final Bundle bundle = response.getBundle();
                _setCurrentUtopiaDate(bundle.get(Bundle.Keys.DATE));

                final ThroneBundle throneBundle = (ThroneBundle) bundle;
                if (throneBundle.isValid()) {
                    _province.update(throneBundle);

                    // Store Dragon within KD
                    Kingdom kingdom = _getKingdom(_province.getKingdomIdentifier());
                    if (kingdom != null) {
                        if (throneBundle.hasKey(ThroneBundle.Keys.DRAGON_TYPE)) {
                            String dragonTypeString = throneBundle.get(ThroneBundle.Keys.DRAGON_TYPE);
                            if (kingdom != null && !kingdom.hasDragon()) {
                                kingdom.setDragonType(Dragon.getType(dragonTypeString));
                            }
                        }
                        else {
                            kingdom.setDragon(null);
                        }

                        _utopiaDatabase.storeKingdom(kingdom);
                    }
                }

                if (response.hasExtra("chatCredentials")) {
                    _chatroom.setCredentials(ChatCredentials.fromJson((String) response.getExtra("chatCredentials")));
                }
                _chatroom.setUsername(_province.getName());

                final Kingdom.Identifier kingdomIdentifier = _province.getKingdomIdentifier();

                _keyValueStore.putString("PROVINCE_NAME", _province.getName());
                _keyValueStore.putString("KINGDOM", ""+ kingdomIdentifier.getKingdomId());
                _keyValueStore.putString("ISLAND", ""+ kingdomIdentifier.getIslandId());

                if (_intelSync != null) {
                    _intelSync.setProvinceData(new IntelSync.ProvinceData(_province.getName(), kingdomIdentifier.getKingdomId(), kingdomIdentifier.getIslandId()));
                }
                if (_verdeIntelSync != null) {
                    _verdeIntelSync.setProvinceData(new IntelSync.ProvinceData(_province.getName(), kingdomIdentifier.getKingdomId(), kingdomIdentifier.getIslandId()));
                }

                _utopiaDatabase.storeProvince(_province);

                _executeCallbacks(_throneCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(response));
                }
            }
        });
    }
    public void downloadThrone(final Callback callback) {
        _downloadThrone(callback);
    }

    public void downloadNews(Callback callback) {
        _downloadNews(null, null, callback);
    }
    public void downloadNews(Integer month, Integer year, Callback callback) {
        _downloadNews(month, year, callback);
    }

    private void _downloadNews(Integer month, Integer year, final Callback callback) {
        if (year != null && month != null && _currentUtopiaDate != null) {
            Integer monthIndex = UtopiaUtil.getUtopianMonthIndex(_currentUtopiaDate);
            Integer utopianYear = UtopiaUtil.getUtopianYear(_currentUtopiaDate);

            if (monthIndex != null && utopianYear != null) {
                Integer monthDelta = month - monthIndex;
                Integer yearDelta = year - utopianYear;

                String newUtopianDate = UtopiaUtil.incrementUtopiaDate(_currentUtopiaDate, monthDelta, yearDelta);

                month = UtopiaUtil.getUtopianMonthIndex(newUtopianDate);
                year = UtopiaUtil.getUtopianYear(newUtopianDate);
            }
            else {
                year = null;
                month = null;
            }
        }
        else {
            year = null;
            month = null;
        }

        final Integer newsMonth = month;
        final Integer newsYear = year;

        _executeBeginDownloadCallback(DownloadType.NEWS);
        _utopiaUtil.downloadNews(month, year, new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.NEWS);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                NewspaperBundle bundle = (NewspaperBundle) response.getBundle();
                _setCurrentUtopiaDate(bundle.get(Bundle.Keys.DATE));

                _loadNewsFromBundle(bundle);

                if (newsMonth != null && newsYear != null) {
                    _executeCallbacks(newsMonth, newsYear, _newsCallbacks);
                }
                else {
                    _executeCallbacks(
                        UtopiaUtil.getUtopianMonthIndex(_currentUtopiaDate),
                        UtopiaUtil.getUtopianYear(_currentUtopiaDate),
                        _newsCallbacks
                    );
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });

        _executeBeginDownloadCallback(DownloadType.KINGDOM);
        _utopiaUtil.downloadKingdomNews(month, year, new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                NewspaperBundle bundle = (NewspaperBundle) response.getBundle();
                _setCurrentUtopiaDate(bundle.get(Bundle.Keys.DATE));

                _loadKingdomNewsFromBundle(bundle);

                if (newsMonth != null && newsYear != null) {
                    _executeCallbacks(newsMonth, newsYear, _newsCallbacks);
                }
                else {
                    _executeCallbacks(
                            UtopiaUtil.getUtopianMonthIndex(_currentUtopiaDate),
                            UtopiaUtil.getUtopianYear(_currentUtopiaDate),
                            _newsCallbacks
                    );
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadBuildingsCouncil(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.BUILDING_COUNCIL);
        _utopiaUtil.downloadBuildingsCouncil(new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.BUILDING_COUNCIL);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                BuildingsBundle bundle = (BuildingsBundle) response.getBundle();

                _setCurrentUtopiaDate(bundle.get(Bundle.Keys.DATE));

                _province.update(bundle);

                _executeCallbacks(_buildingsCouncilCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadMilitaryCouncil(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.MILITARY_COUNCIL);
        _utopiaUtil.downloadMilitaryCouncil(new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.MILITARY_COUNCIL);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                MilitaryBundle bundle = (MilitaryBundle) response.getBundle();
                _setCurrentUtopiaDate(bundle.get(Bundle.Keys.DATE));

                _province.update(bundle);

                _executeCallbacks(_throneCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadMilitarySettings(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.MILITARY_SETTINGS);

        _utopiaUtil.downloadMilitarySettings(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.MILITARY_SETTINGS);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                MilitarySettingsBundle militarySettingsBundle = (MilitarySettingsBundle) response.getBundle();
                _setCurrentUtopiaDate(militarySettingsBundle.get(Bundle.Keys.DATE));

                if (militarySettingsBundle.hasGroupKey(MilitarySettingsBundle.Keys.DRAFT_RATES)) {
                    _draftRates.clear();

                    List<Bundle> draftRateBundles = militarySettingsBundle.getGroup(MilitarySettingsBundle.Keys.DRAFT_RATES);
                    for (Bundle draftRateBundle : draftRateBundles) {
                        DraftRate draftRate = DraftRate.fromBundle((DraftRateBundle) draftRateBundle);
                        _draftRates.add(draftRate);
                    }
                }

                _province.update(militarySettingsBundle);

                _executeCallbacks(_militarySettingsCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    private void _downloadAvailableSpells(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.AVAILABLE_SPELLS);
            _utopiaUtil.downloadAvailableSpells(new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    _executeEndDownloadCallback(DownloadType.AVAILABLE_SPELLS);

                    if (! response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    final AvailableSpellsBundle spellListBundle = (AvailableSpellsBundle) response.getBundle();
                    if (! spellListBundle.isValid()) {
                        _onFailure("Error parsing spell list.", callback);
                        return;
                    }

                    _availableSpells.clear();
                    _setCurrentUtopiaDate(spellListBundle.get(Bundle.Keys.DATE));

                    if (spellListBundle.hasGroupKey(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE)) {
                        final List<Bundle> spellList = spellListBundle.getGroup(AvailableSpellsBundle.Keys.SPELL_LIST_BUNDLE);
                        for (final Bundle bundle : spellList) {
                            final SpellBundle spellBundle = (SpellBundle) bundle;
                            _availableSpells.add(Spell.fromBundle(spellBundle));
                        }
                    }

                    _executeCallbacks(_spellListCallbacks);

                    if (callback != null) {
                        callback.run(new SessionResponse(true, null));
                    }
                }
            });
    }
    public void downloadAvailableSpells(final Callback callback) {
        _downloadAvailableSpells(callback);
    }
    public void downloadAvailableSpells(Kingdom.Identifier kingdomIdentifier, final Callback callback) {
        if (! _utopiaUtil.isCurrentTargetKingdom(kingdomIdentifier)) {
            _utopiaUtil.setKingdom(kingdomIdentifier, new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    _downloadAvailableSpells(callback);
                }
            });
        }
        else {
            _downloadAvailableSpells(callback);
        }
    }

    public void downloadKingdomIntel(final Kingdom.Identifier kingdomIdentifier, final Callback callback) {
        _downloadKingdomIntel(kingdomIdentifier, null, callback);
    }

    public void downloadAvailableVerdeIntelCounts(final Kingdom.Identifier kingdomIdentifier, final Callback callback) {
        if (_hasVerdeIntelSyncEnabled() && _isVerdeIntelSyncLoggedIn()) {
            _verdeIntelSync.downloadIntelAvailableCount(kingdomIdentifier, new VerdeIntelUtil.DownloadAvailableIntelCountCallback() {
                @Override
                public void run(VerdeIntelUtil.DownloadIntelAvailableCountResponse downloadIntelResponse) {
                    if (! downloadIntelResponse.getWasSuccess()) {
                        if (callback != null) {
                            callback.run(new SessionResponse(false, downloadIntelResponse.getErrorMessage()));
                        }
                        return;
                    }

                    final List<VerdeIntelUtil.AvailableIntel> availableIntel = downloadIntelResponse.getAvailableIntel();
                    for (final VerdeIntelUtil.AvailableIntel intel : availableIntel) {
                        if (! _availableVerdeIntel.containsKey(intel.getKingdomIdentifier())){
                            _availableVerdeIntel.put(intel.getKingdomIdentifier(), new HashMap<String, VerdeIntelUtil.AvailableIntel>());
                        }

                        final Map<String, VerdeIntelUtil.AvailableIntel> kingdomIntel = _availableVerdeIntel.get(intel.getKingdomIdentifier());
                        kingdomIntel.put(intel.getProvinceName(), intel);
                    }

                    if (callback != null) {
                        callback.run(new SessionResponse(false, downloadIntelResponse.getErrorMessage()));
                    }
                }
            });
        }
        else {
            if (callback != null) {
                callback.run(new SessionResponse(false, "In-App sync not enabled."));
            }
        }
    }

    public void downloadProvinceIntel(final Province province, final Callback callback) {
        if (_hasVerdeIntelSyncEnabled() && _isVerdeIntelSyncLoggedIn()) {
            _verdeIntelSync.downloadProvinceIntel(new IntelSync.ProvinceData(province), new VerdeIntelUtil.DownloadIntelCallback() {
                @Override
                public void run(final VerdeIntelUtil.DownloadIntelResponse downloadIntelResponse) {
                    if (! downloadIntelResponse.getWasSuccess()) {
                        if (callback != null) {
                            callback.run(new SessionResponse(false, downloadIntelResponse.getErrorMessage()));
                        }
                        return;
                    }

                    for (final Bundle bundle : downloadIntelResponse.getBundles()) {
                        province.update(bundle);
                    }

                    _utopiaDatabase.storeProvince(province);

                    _downloadKingdomIntel(province.getKingdomIdentifier(), province, callback);
                }
            });
        }
        else {
            _downloadKingdomIntel(province.getKingdomIdentifier(), province, callback);
        }
    }
    private void _downloadKingdomIntel(final Kingdom.Identifier kingdomIdentifier, final Province province, final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.KINGDOM_INTEL);
        _utopiaUtil.downloadKingdomIntel(kingdomIdentifier, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM_INTEL);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                KingdomIntelBundle kingdomIntelBundle = (KingdomIntelBundle) response.getBundle();
                _setCurrentUtopiaDate(kingdomIntelBundle.get(Bundle.Keys.DATE));

                List<Bundle> provinceBundles = kingdomIntelBundle.getGroup(KingdomIntelBundle.Keys.PROVINCES);
                if (provinceBundles != null) {
                    for (int i = 0; i < provinceBundles.size(); ++i) {
                        ProvinceIntelBundle provinceIntelBundle = (ProvinceIntelBundle) provinceBundles.get(i);
                        String provinceName = provinceIntelBundle.get(ProvinceIntelBundle.Keys.PROVINCE_NAME);

                        // Update Cached Provinces...
                        if (_downloadedKingdoms.containsKey(kingdomIdentifier)) {
                            Kingdom kingdom = _downloadedKingdoms.get(kingdomIdentifier);
                            for (Province province : kingdom.getProvinces()) {
                                if (province.getName().equals(provinceIntelBundle.get(ProvinceIntelBundle.Keys.PROVINCE_NAME))) {
                                    province.update(provinceIntelBundle);
                                    _utopiaDatabase.storeProvince(province);
                                    break;
                                }
                            }
                        }

                        // Update parameter province...
                        if (province != null && province.getKingdomIdentifier().equals(kingdomIdentifier) && province.getName().equals(provinceName)) {
                            province.update(provinceIntelBundle);
                            break;
                        }
                    }
                }

                _executeCallbacks(_activeSpellsCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadActiveSpells(final Callback callback) {
        _downloadActiveSpells(_province, callback);
    }
    public void downloadActiveSpells(Province targetProvince, final Callback callback) {
        _downloadActiveSpells(targetProvince, callback);
    }
    public void downloadActiveSpells(Kingdom.Identifier kingdomIdentifier, Callback callback) {
        _downloadActiveSpells(kingdomIdentifier, callback);
    }
    private void _downloadActiveSpells(final Kingdom.Identifier kingdomIdentifier, final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.KINGDOM_INTEL);
        _utopiaUtil.downloadKingdomIntel(kingdomIdentifier, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM_INTEL);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                final KingdomIntelBundle kingdomIntelBundle = (KingdomIntelBundle) response.getBundle();
                _setCurrentUtopiaDate(kingdomIntelBundle.get(Bundle.Keys.DATE));

                if (kingdomIntelBundle.hasBundleKey(KingdomIntelBundle.Keys.PROVINCES)) {
                    final List<Bundle> provinceBundles = kingdomIntelBundle.getGroup(KingdomIntelBundle.Keys.PROVINCES);
                    for (int i = 0; i < provinceBundles.size(); ++i) {
                        final ProvinceIntelBundle provinceIntelBundle = (ProvinceIntelBundle) provinceBundles.get(i);

                        // Update Cached Provinces...
                        if (_downloadedKingdoms.containsKey(kingdomIdentifier)) {
                            final Kingdom kingdom = _downloadedKingdoms.get(kingdomIdentifier);
                            for (final Province province : kingdom.getProvinces()) {
                                if (province.getName().equals(provinceIntelBundle.get(ProvinceIntelBundle.Keys.PROVINCE_NAME))) {
                                    province.update(provinceIntelBundle);
                                    _utopiaDatabase.storeProvince(province);
                                    break;
                                }
                            }
                        }
                    }
                }

                _executeCallbacks(_activeSpellsCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    private void _downloadActiveSpells(final Province targetProvince, final Callback callback) {
        if (targetProvince.equals(_province)) {
            _executeBeginDownloadCallback(DownloadType.ACTIVE_SPELLS);
            _utopiaUtil.downloadActiveSelfSpells(new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    _executeEndDownloadCallback(DownloadType.ACTIVE_SPELLS);

                    if (!response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    final ActiveSpellsBundle activeSpellsBundle = (ActiveSpellsBundle) response.getBundle();
                    _setCurrentUtopiaDate(activeSpellsBundle.get(Bundle.Keys.DATE));

                    targetProvince.update(activeSpellsBundle);

                    _executeCallbacks(_activeSpellsCallbacks);

                    if (callback != null) {
                        callback.run(new SessionResponse(true, null));
                    }
                }
            });
        }
        else {
            final Kingdom.Identifier kingdomIdentifier = targetProvince.getKingdomIdentifier();
            _executeBeginDownloadCallback(DownloadType.KINGDOM_INTEL);
            _utopiaUtil.downloadKingdomIntel(kingdomIdentifier, new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    _executeEndDownloadCallback(DownloadType.KINGDOM_INTEL);

                    if (!response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    KingdomIntelBundle kingdomIntelBundle = (KingdomIntelBundle) response.getBundle();
                    _setCurrentUtopiaDate(kingdomIntelBundle.get(Bundle.Keys.DATE));

                    List<Bundle> provinceBundles = kingdomIntelBundle.getGroup(KingdomIntelBundle.Keys.PROVINCES);
                    for (int i = 0; i < provinceBundles.size(); ++i) {
                        ProvinceIntelBundle provinceIntelBundle = (ProvinceIntelBundle) provinceBundles.get(i);

                        // Update Cached Provinces...
                        if (_downloadedKingdoms.containsKey(kingdomIdentifier)) {
                            Kingdom kingdom = _downloadedKingdoms.get(kingdomIdentifier);
                            for (Province province : kingdom.getProvinces()) {
                                if (province.getName().equals(provinceIntelBundle.get(ProvinceIntelBundle.Keys.PROVINCE_NAME))) {
                                    province.update(provinceIntelBundle);
                                    _utopiaDatabase.storeProvince(province);
                                    break;
                                }
                            }
                        }

                        // Update parameter province...
                        if (targetProvince.getName().equals(provinceIntelBundle.get(ProvinceIntelBundle.Keys.PROVINCE_NAME))) {
                            targetProvince.update(provinceIntelBundle);
                            _utopiaDatabase.storeProvince(targetProvince);
                            break;
                        }
                    }

                    _executeCallbacks(_activeSpellsCallbacks);

                    if (callback != null) {
                        callback.run(new SessionResponse(true, null));
                    }
                }
            });
        }
    }

    public void downloadStateCouncil(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.STATE_COUNCIL);
        _utopiaUtil.downloadStateCouncil(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.STATE_COUNCIL);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                StateCouncilBundle stateCouncilBundle = (StateCouncilBundle) response.getBundle();
                _setCurrentUtopiaDate(stateCouncilBundle.get(Bundle.Keys.DATE));

                _province.update(stateCouncilBundle);

                _executeCallbacks(_stateCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    private void _downloadProvinceIdentifiers(final Kingdom.Identifier kingdomIdentifier, final Runnable callback) {
        _executeBeginDownloadCallback(DownloadType.PROVINCE_IDENTIFIERS);
        _utopiaUtil.downloadAvailableThieveryOperations(kingdomIdentifier, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {

                if (response.getWasSuccess()) {
                    AvailableThieveryOperationsBundle thieveryOperationsBundle = (AvailableThieveryOperationsBundle) response.getBundle();

                    Boolean thieveryOpsHadProvinceIds = false;
                    if (thieveryOperationsBundle != null && thieveryOperationsBundle.isValid()) {
                        if (thieveryOperationsBundle.hasGroupKey(AvailableThieveryOperationsBundle.Keys.PROVINCE_LIST_BUNDLE)) {
                            List<Bundle> provinceIds = thieveryOperationsBundle.getGroup(AvailableThieveryOperationsBundle.Keys.PROVINCE_LIST_BUNDLE);
                            for (Integer i = 0; i < provinceIds.size(); i++) {
                                thieveryOpsHadProvinceIds = true;
                                ProvinceIdBundle provinceIdBundle = (ProvinceIdBundle) provinceIds.get(i);
                                Province province = Province.fromProvinceIdBundle(provinceIdBundle);
                                _utopiaDatabase.storeProvince(province);
                            }
                        }
                    }

                    if (thieveryOpsHadProvinceIds) {
                        _executeEndDownloadCallback(DownloadType.PROVINCE_IDENTIFIERS);
                        if (callback != null) {
                            callback.run();
                        }
                    }
                    else {
                        // Might be out of stealth... Attempt to get utopiaProvinceId via spells instead...
                        _utopiaUtil.downloadAvailableSpells(new UtopiaUtil.Callback() {
                            @Override
                            public void run(UtopiaUtil.Response spellsResponse) {
                                if (spellsResponse.getWasSuccess()) {
                                    AvailableSpellsBundle availableSpellsBundle = (AvailableSpellsBundle) spellsResponse.getBundle();

                                    Boolean magicOpsHadProvinceIds = false;
                                    if (availableSpellsBundle != null && availableSpellsBundle.isValid()) {
                                        if (availableSpellsBundle.hasGroupKey(AvailableSpellsBundle.Keys.PROVINCE_LIST_BUNDLE)) {
                                            List<Bundle> provinceIds = availableSpellsBundle.getGroup(AvailableSpellsBundle.Keys.PROVINCE_LIST_BUNDLE);
                                            for (Integer i = 0; i < provinceIds.size(); i++) {
                                                magicOpsHadProvinceIds = true;
                                                ProvinceIdBundle provinceIdBundle = (ProvinceIdBundle) provinceIds.get(i);
                                                Province province = Province.fromProvinceIdBundle(provinceIdBundle);
                                                _utopiaDatabase.storeProvince(province);
                                            }
                                        }
                                    }

                                    if (magicOpsHadProvinceIds) {
                                        _executeEndDownloadCallback(DownloadType.PROVINCE_IDENTIFIERS);
                                        if (callback != null) {
                                            callback.run();
                                        }
                                    }
                                    else {
                                        _utopiaUtil.downloadWarRoomSettings(new UtopiaUtil.Callback() {
                                            @Override
                                            public void run(UtopiaUtil.Response warRoomResponse) {
                                                WarRoomBundle warRoomBundle = (WarRoomBundle) warRoomResponse.getBundle();

                                                Boolean warRoomHadProvinceIds = false;
                                                if (warRoomBundle != null && warRoomBundle.isValid()) {
                                                    if (warRoomBundle.hasGroupKey(WarRoomBundle.Keys.PROVINCE_LIST_BUNDLE)) {
                                                        List<Bundle> provinceIds = warRoomBundle.getGroup(WarRoomBundle.Keys.PROVINCE_LIST_BUNDLE);
                                                        for (Integer i = 0; i < provinceIds.size(); i++) {
                                                            warRoomHadProvinceIds = true;
                                                            ProvinceIdBundle provinceIdBundle = (ProvinceIdBundle) provinceIds.get(i);
                                                            Province province = Province.fromProvinceIdBundle(provinceIdBundle);
                                                            _utopiaDatabase.storeProvince(province);
                                                        }
                                                    }
                                                }

                                                if (warRoomHadProvinceIds) {
                                                    _executeEndDownloadCallback(DownloadType.PROVINCE_IDENTIFIERS);
                                                    if (callback != null) {
                                                        callback.run();
                                                    }
                                                }
                                                else {
                                                    _utopiaUtil.downloadProvinceIdsViaAid(_kingdomIdentifier, new UtopiaUtil.Callback() {
                                                        @Override
                                                        public void run(UtopiaUtil.Response aidProvinceIdsResponse) {
                                                            ProvinceIdsBundle provinceIdsBundle = (ProvinceIdsBundle) aidProvinceIdsResponse.getBundle();

                                                            Boolean aidHasProvinceIds = false;
                                                            if (provinceIdsBundle != null && provinceIdsBundle.isValid()) {
                                                                if (provinceIdsBundle.hasGroupKey(ProvinceIdsBundle.Keys.PROVINCE_LIST_BUNDLE)) {
                                                                    List<Bundle> provinceIds = provinceIdsBundle.getGroup(ProvinceIdsBundle.Keys.PROVINCE_LIST_BUNDLE);
                                                                    for (Integer i = 0; i < provinceIds.size(); i++) {
                                                                        aidHasProvinceIds = true;
                                                                        ProvinceIdBundle provinceIdBundle = (ProvinceIdBundle) provinceIds.get(i);
                                                                        Province province = Province.fromProvinceIdBundle(provinceIdBundle);
                                                                        _utopiaDatabase.storeProvince(province);
                                                                    }
                                                                }
                                                            }

                                                            _executeEndDownloadCallback(DownloadType.PROVINCE_IDENTIFIERS);
                                                            if (callback != null) {
                                                                callback.run();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void _downloadAvailableThieveryOperations(final Kingdom.Identifier identifier, final Runnable callback) {
        _executeBeginDownloadCallback(DownloadType.AVAILABLE_THIEVERY_OPERATIONS);

        _utopiaUtil.downloadAvailableThieveryOperations(identifier, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.AVAILABLE_THIEVERY_OPERATIONS);

                if (response.getWasSuccess()) {
                    List<ThieveryOperation> kingdomOps = new ArrayList<ThieveryOperation>();

                    // Store Thievery Operation List
                    AvailableThieveryOperationsBundle thieveryOpsBundle = (AvailableThieveryOperationsBundle) response.getBundle();
                    if (thieveryOpsBundle.isValid() && thieveryOpsBundle.hasGroupKey(AvailableThieveryOperationsBundle.Keys.THIEVERY_OPERATION_BUNDLE)) {
                        List<Bundle> thieveryOperations = thieveryOpsBundle.getGroup(AvailableThieveryOperationsBundle.Keys.THIEVERY_OPERATION_BUNDLE);
                        for (Integer i = 0; i < thieveryOperations.size(); i++) {
                            AvailableThieveryOperationBundle thieveryOpBundle = (AvailableThieveryOperationBundle) thieveryOperations.get(i);
                            if (thieveryOpBundle.isValid()) {
                                ThieveryOperation thieveryOperation = ThieveryOperation.fromBundle(thieveryOpBundle);
                                kingdomOps.add(thieveryOperation);
                            }
                        }
                        _thieveryOperations.put(identifier.toString(), kingdomOps);

                        _executeCallbacks(_thieveryOpsCallbacks);
                    }
                }

                if (callback != null) {
                    callback.run();
                }
            }
        });
    }
    public void downloadAvailableThieveryOperations(final Kingdom.Identifier kingdomIdentifier, final Runnable callback) {
        _downloadAvailableThieveryOperations(kingdomIdentifier, callback);
    }
    public Boolean thieveryOperationsAreDownloaded(Kingdom.Identifier kingdomIdentifier) {
        return _thieveryOperations.containsKey(kingdomIdentifier.toString());
    }
    public List<ThieveryOperation> getAvailableThieveryOperations(Kingdom.Identifier kingdomIdentifier) {
        return _thieveryOperations.get(kingdomIdentifier.toString());
    }

    public List<PrivateMessage> getPrivateMessages() {
        return _privateMessages;
    }

    private void _cacheKingdom(Kingdom kingdom) {
        if (_downloadedKingdoms.size() > 0) {
            // Remove the kingdom if it has already been downloaded...
            Boolean kingdomIsDownloaded = false;
            Integer downloadedKingdomsIndex = 0;

            if (_downloadedKingdoms.containsKey(kingdom.getIdentifier())) {
                _downloadedKingdoms.remove(kingdom.getIdentifier());
            }
        }

        _downloadedKingdoms.put(kingdom.getIdentifier(), kingdom);
    }
    private void _downloadKingdom(final Integer kingdomId, final Integer islandId, final DownloadKingdomCallback callback) {
        UtopiaUtil.Callback downloadCallback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM);

                if (! response.getWasSuccess()) {
                    _onFailure(callback, response.getErrorMessage());
                    return;
                }

                final KingdomBundle kingdomBundle = (KingdomBundle) response.getBundle();

                if (kingdomBundle == null || ! kingdomBundle.isValid()) {
                    _onFailure(callback, "Error parsing kingdom.");
                    return;
                }

                _setCurrentUtopiaDate(kingdomBundle.get(Bundle.Keys.DATE));
                final Kingdom kingdom = Kingdom.fromBundle(kingdomBundle);

                if (! kingdom.getIdentifier().isValid()) {
                    _onFailure(callback, "Error parsing Kingdom.");
                    return;
                }

                _cacheKingdom(kingdom);

                if (kingdomId == null && islandId == null) {
                    _kingdomIdentifier = kingdom.getIdentifier();
                }

                _downloadProvinceIdentifiers(kingdom.getIdentifier(), new Runnable() {
                    public void run() {
                        _executeCallbacks(kingdom.getIdentifier(), _kingdomCallbacks);

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null), kingdom.getIdentifier());
                        }
                    }
                });
            }
        };

        _executeBeginDownloadCallback(DownloadType.KINGDOM);

        if (kingdomId != null && islandId != null) {
            _utopiaUtil.downloadKingdom(kingdomId, islandId, downloadCallback);
        }
        else {
            _utopiaUtil.downloadKingdom(downloadCallback);
        }
    }
    public void downloadKingdom(DownloadKingdomCallback callback) {
        _downloadKingdom(null, null, callback);
    }
    public void downloadKingdom(Kingdom.Identifier identifier, DownloadKingdomCallback callback) {
        _downloadKingdom(identifier.getKingdomId(), identifier.getIslandId(), callback);
    }
    public void downloadNextKingdom(final Kingdom.Identifier currentIdentifier, final DownloadKingdomCallback callback) {
        _executeBeginDownloadCallback(DownloadType.KINGDOM);
        _utopiaUtil.downloadNextKingdom(currentIdentifier.getKingdomId(), currentIdentifier.getIslandId(), new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM);

                if (!response.getWasSuccess()) {
                    _onFailure(callback, response.getErrorMessage());
                    return;
                }

                KingdomBundle kingdomBundle = (KingdomBundle) response.getBundle();

                if (kingdomBundle == null || !kingdomBundle.isValid()) {
                    _onFailure(callback, "Error parsing kingdom.");
                    return;
                }

                Kingdom kingdom = Kingdom.fromBundle(kingdomBundle);
                final Kingdom.Identifier kingdomIdentifier = kingdom.getIdentifier();
                if (!kingdomIdentifier.isValid()) {
                    _onFailure(callback, "Error parsing kingdom.");
                    return;
                }

                _cacheKingdom(kingdom);

                _downloadProvinceIdentifiers(kingdom.getIdentifier(), new Runnable() {
                    public void run() {
                        _executeCallbacks(kingdomIdentifier, _kingdomCallbacks);

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null), kingdomIdentifier);
                        }
                    }
                });
            }
        });
    }
    public void downloadPreviousKingdom(Kingdom.Identifier currentIdentifier, final DownloadKingdomCallback callback) {
        _executeBeginDownloadCallback(DownloadType.KINGDOM);
        _utopiaUtil.downloadPreviousKingdom(currentIdentifier.getKingdomId(), currentIdentifier.getIslandId(), new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.KINGDOM);

                if (!response.getWasSuccess()) {
                    _onFailure(callback, response.getErrorMessage());
                    return;
                }

                KingdomBundle kingdomBundle = (KingdomBundle) response.getBundle();

                if (kingdomBundle == null || !kingdomBundle.isValid()) {
                    _onFailure(callback, "Error parsing kingdom.");
                    return;
                }

                Kingdom kingdom = Kingdom.fromBundle(kingdomBundle);
                final Kingdom.Identifier kingdomIdentifier = kingdom.getIdentifier();
                if (!kingdomIdentifier.isValid()) {
                    _onFailure(callback, "Error parsing kingdom.");
                    return;
                }

                _cacheKingdom(kingdom);

                _downloadProvinceIdentifiers(kingdomIdentifier, new Runnable() {
                    public void run() {
                        _executeCallbacks(kingdomIdentifier, _kingdomCallbacks);

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null), kingdomIdentifier);
                        }
                    }
                });
            }
        });
    }
    public Boolean hasKingdom(Kingdom.Identifier identifier) {
        return _downloadedKingdoms.containsKey(identifier);
    }

    public void trainArmy(TrainArmyData bundle, final Boolean isExpedited, final Callback callback) {
        _utopiaUtil.trainArmy(bundle, isExpedited, new UtopiaUtil.Callback() {
            public void run(UtopiaUtil.Response response) {
                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public class CastSpellResponse extends SessionResponse {
        private SpellResultBundle _spellResultBundle;
        public CastSpellResponse(UtopiaUtil.Response response) {
            super(response);
        }
        public CastSpellResponse(UtopiaUtil.Response response, SpellResultBundle spellResultBundle) {
            super(response);
            _spellResultBundle = spellResultBundle;
        }
        public CastSpellResponse(Boolean wasSuccess, String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public Boolean hasSpellResultBundle() {
            return (_spellResultBundle != null);
        }
        public SpellResultBundle getSpellResultBundle() {
            return _spellResultBundle;
        }
    }
    public interface CastSpellCallback{
        void run(CastSpellResponse response);
    }

    public void castSpell(final Spell spell, final Province targetProvince, final CastSpellCallback callback) {

        final Runnable executeOp = new Runnable() {
            @Override
            public void run() {
                Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
                _utopiaUtil.castSpell(spell, provinceUtopiaId, new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        if (! response.getWasSuccess()) {
                            _onFailure(callback, response.getErrorMessage());
                            return;
                        }

                        SpellResultBundle spellResultBundle = (SpellResultBundle) response.getBundle();
                        if (spellResultBundle == null || !spellResultBundle.isValid()) {
                            _onFailure(callback, "Invalid spell result.");
                            return;
                        }

                        if (callback != null) {
                            callback.run(new CastSpellResponse(response, spellResultBundle));
                        }
                    }
                });
            }
        };

        Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
        if (provinceUtopiaId == null || provinceUtopiaId == 0) {
            _downloadProvinceIdentifiers(targetProvince.getKingdomIdentifier(), new Runnable() {
                @Override
                public void run() {
                    Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);

                    if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                        _onFailure(callback, "Invalid target (1).");
                        return;
                    }

                    executeOp.run();
                }
            });
        }
        else {
            executeOp.run();
        }
    }
    public void castSpell(Spell spell, final CastSpellCallback callback) {
        _utopiaUtil.castDefensiveSpell(spell, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (callback != null) {
                    if (! response.getWasSuccess()) {
                        _onFailure(callback, response.getErrorMessage());
                        return;
                    }

                    SpellResultBundle spellResultBundle = (SpellResultBundle) response.getBundle();
                    if (spellResultBundle == null || !spellResultBundle.isValid()) {
                        _onFailure(callback, "Invalid spell result.");
                        return;
                    }

                    _province.setOffenseModifier(null); // Invalidate the offenseModifier.

                    if (callback != null) {
                        callback.run(new CastSpellResponse(response, spellResultBundle));
                    }
                }
            }
        });
    }

    public void buildBuilding(Building.Type type, Integer quantity, Boolean isConstructionExpedited, Boolean shouldUseBuildingCredits, final Callback callback) {
        _utopiaUtil.buildBuilding(_province.getRace(), type, quantity, isConstructionExpedited, shouldUseBuildingCredits, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public class ThieveryOperationResponse extends SessionResponse {
        private ThieveryOperationBundle _thieveryOperationBundle;
        public ThieveryOperationResponse(UtopiaUtil.Response response) {
            super(response);
        }
        public ThieveryOperationResponse(UtopiaUtil.Response response, ThieveryOperationBundle thieveryOperationBundle) {
            super(response);
            _thieveryOperationBundle = thieveryOperationBundle;
        }
        public ThieveryOperationResponse(Boolean wasSuccess, String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public Boolean hasThieveryOperationBundle() {
            return (_thieveryOperationBundle != null);
        }
        public ThieveryOperationBundle getThieveryOperationBundle() {
            return _thieveryOperationBundle;
        }
    }
    public interface ThieveryOperationCallback {
        void run(ThieveryOperationResponse response);
    }
    public void executeThieveryOperation(final ThieveryOperation thieveryOperation, final Integer thiefCount, final Province targetProvince, final Building.Type targetBuilding, final ThieveryOperationCallback callback) {

        final Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);

        if (provinceUtopiaId != null && provinceUtopiaId > 0) {
            _executeThieveryOperation(thieveryOperation, thiefCount, provinceUtopiaId, targetProvince, targetBuilding, callback);
        }
        else {
            _downloadProvinceIdentifiers(targetProvince.getKingdomIdentifier(), new Runnable() {
                @Override
                public void run() {
                    final Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);

                    if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                        _onFailure(callback, "Invalid target (2).");
                        return;
                    }

                    _executeThieveryOperation(thieveryOperation, thiefCount, provinceUtopiaId, targetProvince, targetBuilding, callback);
                }
            });
        }
    }

    private void _executeThieveryOperation(final ThieveryOperation thieveryOperation, final Integer thiefCount, final Integer provinceUtopiaId, final Province targetProvince, final Building.Type targetBuilding, final ThieveryOperationCallback callback) {
        _utopiaUtil.executeThieveryOperation(thieveryOperation, thiefCount, provinceUtopiaId, targetProvince.getKingdomIdentifier(), targetBuilding, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response utilResponse) {
                if (! utilResponse.getWasSuccess()) {
                    _onFailure(callback, utilResponse.getErrorMessage());
                    return;
                }

                final ThieveryOperationBundle operationBundle = (ThieveryOperationBundle) utilResponse.getBundle();
                if (operationBundle == null || ! operationBundle.isValid()) {
                    _onFailure(callback, "Invalid operation response.");
                    return;
                }

                if (operationBundle.hasBundleKey(ThieveryOperationBundle.Keys.TARGET_PROVINCE_BUNDLE)) {
                    ThroneBundle throneBundle = (ThroneBundle) operationBundle.getBundle(ThieveryOperationBundle.Keys.TARGET_PROVINCE_BUNDLE);

                    if (throneBundle.isValid()) {
                        targetProvince.update(throneBundle);

                        String provinceName = targetProvince.getName();
                        Kingdom.Identifier targetKingdomIdentifier = targetProvince.getKingdomIdentifier();

                        Province storedProvince = _utopiaDatabase.getProvince(provinceName, targetKingdomIdentifier.getKingdomId(), targetKingdomIdentifier.getIslandId());

                        if (storedProvince != null) {
                            storedProvince.update(throneBundle);
                            _utopiaDatabase.storeProvince(storedProvince);
                        }
                    }
                    else {
                        System.out.println("Invalid Bundle: " + throneBundle.toString());
                    }
                }

                if (operationBundle.hasBundleKey(ThieveryOperationBundle.Keys.TARGET_MILITARY_BUNDLE)) {
                    MilitaryBundle militaryBundle = (MilitaryBundle) operationBundle.getBundle(ThieveryOperationBundle.Keys.TARGET_MILITARY_BUNDLE);

                    if (militaryBundle.isValid()) {
                        targetProvince.update(militaryBundle);

                        String provinceName = targetProvince.getName();
                        Kingdom.Identifier targetKingdomIdentifier = targetProvince.getKingdomIdentifier();

                        Province storedProvince = _utopiaDatabase.getProvince(provinceName, targetKingdomIdentifier.getKingdomId(), targetKingdomIdentifier.getIslandId());

                        if (storedProvince != null) {
                            storedProvince.update(militaryBundle);
                            _utopiaDatabase.storeProvince(storedProvince);
                        }
                    }
                }

                if (operationBundle.hasBundleKey(ThieveryOperationBundle.Keys.TARGET_SURVEY_BUNDLE)) {
                    BuildingsBundle buildingsBundle = (BuildingsBundle) operationBundle.getBundle(ThieveryOperationBundle.Keys.TARGET_SURVEY_BUNDLE);

                    if (buildingsBundle.isValid()) {
                        targetProvince.update(buildingsBundle);

                        String provinceName = targetProvince.getName();
                        Kingdom.Identifier targetKingdomIdentifier = targetProvince.getKingdomIdentifier();

                        Province storedProvince = _utopiaDatabase.getProvince(provinceName, targetKingdomIdentifier.getKingdomId(), targetKingdomIdentifier.getIslandId());

                        if (storedProvince != null) {
                            storedProvince.update(buildingsBundle);
                            _utopiaDatabase.storeProvince(storedProvince);
                        }
                    }
                }

                if (operationBundle.hasBundleKey(ThieveryOperationBundle.Keys.TARGET_INFILTRATE_BUNDLE)) {
                    InfiltrateThievesBundle infiltrateThievesBundle = (InfiltrateThievesBundle) operationBundle.getBundle(ThieveryOperationBundle.Keys.TARGET_INFILTRATE_BUNDLE);

                    if (infiltrateThievesBundle.isValid()) {
                        targetProvince.update(infiltrateThievesBundle);

                        String provinceName = targetProvince.getName();
                        Kingdom.Identifier targetKingdomIdentifier = targetProvince.getKingdomIdentifier();

                        Province storedProvince = _utopiaDatabase.getProvince(provinceName, targetKingdomIdentifier.getKingdomId(), targetKingdomIdentifier.getIslandId());

                        if (storedProvince != null) {
                            storedProvince.update(infiltrateThievesBundle);
                            _utopiaDatabase.storeProvince(storedProvince);
                        }
                    }
                }

                if (callback != null) {
                    callback.run(new ThieveryOperationResponse(utilResponse, operationBundle));
                }
            }
        });
    }

    private void _downloadProvinceTags() {
        final Kingdom.Identifier kdIdentifier = _province.getKingdomIdentifier();
        if (_province == null || kdIdentifier == null || kdIdentifier.getKingdomId() == null || kdIdentifier.getIslandId() == null) {
            return;
        }

        _provinceTagUtil.getProvinceTags(_province.getName(), kdIdentifier.getKingdomId(), kdIdentifier.getIslandId(), new ProvinceTagUtil.ProvinceTagCallback() {
            @Override
            public void run(ProvinceTagUtil.ProvinceTagResponse provinceTagResponse) {
                if (provinceTagResponse.getWasSuccess()) {
                    final List<ProvinceTagUtil.ProvinceTag> provinceTags = provinceTagResponse.getProvinceTags();
                    for (ProvinceTagUtil.ProvinceTag provinceTag : provinceTags) {

                        final Chatroom.Message message = new Chatroom.Message();
                        message.setId(provinceTag.reverbimId);
                        message.setDisplayName(provinceTag.fromProvince);
                        message.setTimestamp(provinceTag.sentTime);
                        message.setMessage(provinceTag.message);

                        final Boolean messageTagsUser = message.containsUserPing(_province.getName());
                        if (messageTagsUser) {
                            _chatroomPingCallback.run(message);
                        }
                    }
                }
            }
        });
    }

    private synchronized void _downloadChatMessages(final Callback callback) {
        final Boolean downloadThroneUponFailure = false;

        final String lastMessageId;
        if (_chatroom.hasMessages()) {
            lastMessageId = _chatroom.getLastMessage().getId();
        }
        else {
            lastMessageId = null;
        }

        final UtopiaUtil.Callback onSuccessCallback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                final ChatroomBundle chatroomBundle = (ChatroomBundle) response.getBundle();
                if (!chatroomBundle.isValid()) {
                    _onFailure("Error parsing chat messages.", callback);
                    return;
                }

                _chatroom.addMessages(chatroomBundle);

                final Long lastUploadedMessageTime = Util.parseLong(_keyValueStore.getString(_LAST_CHAT_MESSAGE_UPLOAD_TIME_KEY));
                Long newLastUploadedMessageTime = lastUploadedMessageTime;

                final List<Chatroom.Message> newMessages = _chatroom.getMessagesAfter(lastMessageId); // In reverse order...
                for (Integer i=0; i<newMessages.size(); ++i) {
                    final Chatroom.Message message = newMessages.get(i);
                    final Kingdom kingdom = _getKingdom(_province.getKingdomIdentifier());
                    if (kingdom == null) { break; }

                    for (Province province : kingdom.getProvinces()) {
                        if (message.containsUserPing(province.getName())) {

                            final ProvinceTagUtil.ProvinceTag provinceTag = new ProvinceTagUtil.ProvinceTag();
                            provinceTag.message = message.getMessage();
                            provinceTag.kingdom = province.getKingdomIdentifier().getKingdomId();
                            provinceTag.island = province.getKingdomIdentifier().getIslandId();
                            provinceTag.toProvince = province.getName();
                            provinceTag.fromProvince = message.getDisplayName();
                            provinceTag.sentTime = message.getTimestamp();
                            provinceTag.reverbimId = message.getId();

                            if (message.getTimestamp() > lastUploadedMessageTime) {
                                _provinceTagUtil.sendProvinceTag(provinceTag, null);
                                if (message.getTimestamp() > newLastUploadedMessageTime) {
                                    newLastUploadedMessageTime = message.getTimestamp();
                                }
                            }
                        }
                    }
                }

                _keyValueStore.putString(_LAST_CHAT_MESSAGE_UPLOAD_TIME_KEY, newLastUploadedMessageTime.toString());


                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        };

        _executeBeginDownloadCallback(DownloadType.CHAT_MESSAGES);
        _utopiaUtil.downloadChatMessages(_chatroom.getCredentials(), lastMessageId, new UtopiaUtil.Callback() {
            @Override
            public void run(final UtopiaUtil.Response downloadChatMessagesResponse) {
                _executeEndDownloadCallback(DownloadType.CHAT_MESSAGES);

                _downloadProvinceTags(); // Download province-tags regardless of chat-message's success.

                if ((! downloadChatMessagesResponse.getWasSuccess()) && downloadThroneUponFailure) {
                    _downloadThrone(new Callback() {
                        @Override
                        public void run(final SessionResponse downloadThroneResponse) {
                            if (! downloadThroneResponse.getWasSuccess()) {
                                _onFailure(downloadChatMessagesResponse.getErrorMessage(), callback);
                                return;
                            }

                            _executeBeginDownloadCallback(DownloadType.CHAT_MESSAGES);
                            _utopiaUtil.downloadChatMessages(_chatroom.getCredentials(), lastMessageId, new UtopiaUtil.Callback() {
                                @Override
                                public void run(final UtopiaUtil.Response secondDownloadChatMessagesResponse) {
                                    _executeEndDownloadCallback(DownloadType.CHAT_MESSAGES);

                                    if (! secondDownloadChatMessagesResponse.getWasSuccess()) {
                                        _onFailure(downloadChatMessagesResponse.getErrorMessage(), callback);
                                        return;
                                    }

                                    onSuccessCallback.run(downloadChatMessagesResponse);
                                }
                            });
                        }
                    });

                    return;
                }
                else if (! downloadChatMessagesResponse.getWasSuccess()) {
                    _onFailure(downloadChatMessagesResponse.getErrorMessage(), callback);
                    return;
                }

                onSuccessCallback.run(downloadChatMessagesResponse);
            }
        });
    }

    public synchronized void downloadChatMessages(final Callback callback) {
        _downloadChatMessages(callback);
    }
    public void sendChatMessage(final String message, final Callback callback) {
        final Runnable execSendChatMessage = new Runnable() {
            @Override
            public void run() {
                _utopiaUtil.sendChatMessage(message, _chatroom.getCredentials(), new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        _downloadChatMessages(new Callback() {
                            @Override
                            public void run(SessionResponse response) {
                                if (! response.getWasSuccess()) {
                                    _onFailure(response.getErrorMessage(), callback);
                                    return;
                                }

                                if (callback != null) {
                                    callback.run(new SessionResponse(true, null));
                                }
                            }
                        });
                    }
                });
            }
        };

        if (_chatroom.getCredentials() == null) {
            _downloadThrone(new Callback() {
                @Override
                public void run(SessionResponse response) {
                    execSendChatMessage.run();
                }
            });
        }
        else {
            execSendChatMessage.run();
        }

    }

    public Boolean isLoggedIn() {
        return _isLoggedIn;
    }
    public Province getProvince() { return _province; }
    public Province loadProvinceFromStore(Province province) {
        Kingdom.Identifier kingdomIdentifier = province.getKingdomIdentifier();
        _utopiaDatabase.storeProvince(province);
        return _utopiaDatabase.getProvince(province.getName(), kingdomIdentifier.getKingdomId(), kingdomIdentifier.getIslandId());
    }
    public List<NewsEvent> getNews() { return _newsArray; }
    public List<NewsEvent> getKingdomNews() { return _kingdomNewsArray; }
    public ChatCredentials getChatCredentials() { return _chatroom.getCredentials(); }

    public List<Spell> getAvailableSpells() { return _availableSpells; }
    public List<Spell> getAvailableSpells(final Spell.SpellType spellType) {
        final List <Spell> spellList = new ArrayList<Spell>();
        for (final Spell spell : _availableSpells) {
            if (spell.getType() == spellType) {
                spellList.add(spell);
            }
        }
        return spellList;
    }

    public List<DraftRate> getDraftRates() {
        return _draftRates;
    }

    public Kingdom getKingdom(Kingdom.Identifier identifier) {
        return _getKingdom(identifier);
    }
    public Kingdom getKingdom() {
        if (_kingdomIdentifier == null) {
            return null;
        }

        return _getKingdom(_kingdomIdentifier);
    }

    public Chatroom getChatroom() { return _chatroom; }

    private Integer _waitingForCredentialsCount = 0;
    private void _startDownloadMessagesThread() {
        _downloadMessages = true;
        (new Thread() {
            @Override
            public void run() {
                while (_downloadMessages && _chatroom.getCredentials() == null) {
                    try {
                        if (_waitingForCredentialsCount % 5 == 4) {
                            _downloadThrone(null);
                        }

                        System.out.println("Waiting to start chatroom thread..."+ (!_downloadMessages ? " What?" : "") + (_chatroom.getCredentials() == null ? " Missing credentials." : ""));
                        Thread.sleep(1000);

                        _waitingForCredentialsCount += 1;
                    } catch (Exception e) { }
                }

                if (_downloadMessages) {
                    System.out.println("Starting chatroom thread.");
                    _execDownloadMessagesThread();
                }
            }
        }).start();
    }
    private void _stopDownloadMessagesThread() {
        _downloadMessages = false;
    }

    private void _execDownloadMessagesThread() {
        if (! _downloadMessages) return;

        if (_downloadMessagesThread != null) {
            try {
                _downloadMessagesThread.join();
            } catch (Exception e) { }
        }

        _downloadMessagesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                _downloadChatMessages(new Callback() {
                    @Override
                    public void run(SessionResponse response) {
                        Chatroom.Message previousLastMessage = _lastMessage;
                        _lastMessage = _chatroom.getLastMessage();
                        if (previousLastMessage != _lastMessage) {
                            _executeCallbacks(_messageCallbacks);
                            _consecutiveEmptyChatAttempts = 0;
                        }
                        else {
                            _consecutiveEmptyChatAttempts++;
                        }

                        try {
                            Float MAX = 60.0f;
                            Double rate = 0.0d;
                            Float i = (float) _consecutiveEmptyChatAttempts;
                            if (i < 5) i += MAX/48.0f; // 1.25f;
                            rate = (i * i * i * 2.0) / (MAX * MAX);
                            if (rate > 1.0) rate = 1.0d;
                            rate = Math.ceil(rate * MAX);

                            Integer time = (int) (1000.0d * rate);
                            System.out.println("Sleeping: "+ time);
                            Thread.sleep(time);
                        } catch (Exception e) { }

                        _downloadMessagesThread = null;
                        _execDownloadMessagesThread();
                    }
                });
            }
        });

        _downloadMessagesThread.start();
    }
    public Boolean downloadMessagesThreadIsRunning() {
        return _downloadMessages;
    }
    public void startDownloadMessagesThread() {
        _startDownloadMessagesThread();
    }
    public void stopDownloadMessagesThread() {
        _stopDownloadMessagesThread();
    }

    private Chatroom.PingCallback _chatroomPingCallback = new Chatroom.PingCallback() {
        @Override
        public void run(Chatroom.Message message) {
            final Long previousChatPingMessageTime = Util.parseLong(_keyValueStore.getString(_LAST_CHAT_MESSAGE_PING_TIME_KEY));
            final Long thisChatPingMessageTime = message.getTimestamp();

            if (thisChatPingMessageTime > previousChatPingMessageTime) {
                _keyValueStore.putString(_LAST_CHAT_MESSAGE_PING_TIME_KEY, thisChatPingMessageTime.toString());

                if (_shouldNotify && _notificationMaker != null) {
                    _notificationMaker.showNotification(message);
                }
                if (_shouldVibrate && _vibrator != null) {
                    _vibrator.vibrate();
                }
            }
        }
    };

    public void enableVibrateOnPing() {
        _shouldVibrate = true;

        _chatroom.removeCallback(Session.ON_PING_CALLBACK_IDENTIFIER);
        _chatroom.addPingCallback(Session.ON_PING_CALLBACK_IDENTIFIER, _chatroomPingCallback);
    }

    public void enableNotificationOnPing() {
        _shouldNotify = true;

        _chatroom.removeCallback(Session.ON_PING_CALLBACK_IDENTIFIER);
        _chatroom.addPingCallback(Session.ON_PING_CALLBACK_IDENTIFIER, _chatroomPingCallback);
    }
    public void disableVibrateOnPing() {
        _shouldVibrate = false;
    }
    public void disableNotificationOnPing() {
        _shouldNotify = false;
    }

    public String getCurrentUtopiaDate() {
        return _currentUtopiaDate;
    }

    public Integer getCurrentUtopiaYear() {
        return UtopiaUtil.getUtopianYear(_currentUtopiaDate);
    }
    public Integer getCurrentUtopiaMonth() {
        return UtopiaUtil.getUtopianMonthIndex(_currentUtopiaDate);
    }

    public void calculateOffense(final UtopiaUtil.Attack attack, final Callback callback) {
        final UtopiaUtil.Callback calculateOffenseCallback = new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                Bundle bundle = response.getBundle();

                attack.setCalculatedOffense(Util.parseInt(bundle.get(ArmyOffenseBundle.Keys.TOTAL_OFFENSE)));

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        };

        final Province sourceProvince = attack.getProvince(); // This should always just be Session._province, but...
        if ((sourceProvince.getOffenseModifier() == null) || (! _attackTimeParameters.containsKey(attack.getTargetProvince().getKingdomIdentifier()))) {
            _executeBeginDownloadCallback(DownloadType.WAR_ROOM_SETTINGS);

            _utopiaUtil.downloadWarRoomSettings(new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    _executeEndDownloadCallback(DownloadType.WAR_ROOM_SETTINGS);

                    if (! response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    WarRoomBundle warRoomBundle = (WarRoomBundle) response.getBundle();

                    sourceProvince.setOffenseModifier(Util.parseFloat(warRoomBundle.get(WarRoomBundle.Keys.OFFENSE_MODIFIER)));
                    sourceProvince.setMercenaryCost(Util.parseInt(warRoomBundle.get(WarRoomBundle.Keys.MERCENARY_COST)));
                    sourceProvince.setMercenaryRate(Util.parseFloat(warRoomBundle.get(WarRoomBundle.Keys.MERCENARY_RATE)));
                    sourceProvince.setMinConquestNetworth(Util.parseInt(warRoomBundle.get(WarRoomBundle.Keys.MIN_CONQUEST_NW)));
                    sourceProvince.setMaxConquestNetworth(Util.parseInt(warRoomBundle.get(WarRoomBundle.Keys.MAX_CONQUEST_NW)));

                    final Json attackTimeJson = Json.parse(warRoomBundle.get(WarRoomBundle.Keys.ATTACK_TIME_PARAMS));
                    _attackTimeParameters.put(attack.getTargetProvince().getKingdomIdentifier(), attackTimeJson);

                    _utopiaUtil.calculateOffense(attack, calculateOffenseCallback);
                }
            });
        }
        else {
            _utopiaUtil.calculateOffense(attack, calculateOffenseCallback);
        }
    }

    private void _downloadAttackTimes(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.WAR_ROOM_SETTINGS);
        _downloadProvinceIdentifiers(_focusedKingdomIdentifier, new Runnable() {
            @Override
            public void run() {
                _utopiaUtil.downloadWarRoomSettings(new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        _executeEndDownloadCallback(DownloadType.WAR_ROOM_SETTINGS);

                        if (! response.getWasSuccess()) {
                            _onFailure(response.getErrorMessage(), callback);
                            return;
                        }

                        WarRoomBundle warRoomBundle = (WarRoomBundle) response.getBundle();

                        final Json attackTimeJson = Json.parse(warRoomBundle.get(WarRoomBundle.Keys.ATTACK_TIME_PARAMS));
                        _attackTimeParameters.put(_focusedKingdomIdentifier, attackTimeJson);

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null));
                        }
                    }
                });
            }
        });
    }

    public interface CalculateAttackTimeCallback {
        void run(Float attackTime);
    }

    public void calculateAttackTime(final UtopiaUtil.Attack attack, final CalculateAttackTimeCallback calculateAttackTimeCallback) {
        final Runnable calculateAttackTimeRunnable = new Runnable() {
            @Override
            public void run() {
                final Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(attack.getTargetProvince());
                final String targetProvinceIdString = Util.coalesce(provinceUtopiaId).toString();
                final String attackTypeString = UtopiaUtil.Attack.getStringForAttackType(attack.getType());
                final String attackTimeModifierString = UtopiaUtil.Attack.getStringForAttackTime(attack.getTime());

                final Json attackTimeParameters = _attackTimeParameters.get(attack.getTargetProvince().getKingdomIdentifier());

                if (attackTimeParameters == null) {
                    if (calculateAttackTimeCallback != null) {
                        calculateAttackTimeCallback.run(0.0F);
                    }
                    return;
                }

                final Json networthAttackTimes = attackTimeParameters.get("networthAttackTimes");
                final Json noNetworthAttackTimes = attackTimeParameters.get("noNetworthAttackTimes");
                final Json attackTypeReductions = attackTimeParameters.get("attackTypeReductions");
                final Json attackTimeModifications = attackTimeParameters.get("attackTimeModifications");

                final Float baseAttackTime;
                {
                    final UtopiaUtil.Attack.Type attackType = attack.getType();
                    if ((attackType == UtopiaUtil.Attack.Type.TRADITIONAL_MARCH) || (attackType == UtopiaUtil.Attack.Type.PLUNDER) || (attackType == UtopiaUtil.Attack.Type.ABDUCT) || (attackType == UtopiaUtil.Attack.Type.MASSACRE)) {
                        baseAttackTime = Util.parseFloat(networthAttackTimes.get(targetProvinceIdString, Json.Types.STRING));
                    }
                    else {
                        baseAttackTime = Util.parseFloat(noNetworthAttackTimes.get(targetProvinceIdString, Json.Types.STRING));
                    }
                }

                final Float attackTimeReduction = Util.parseFloat(attackTypeReductions.get(attackTypeString, Json.Types.STRING));

                final Float attackTimeModification;
                {
                    final Float originalAttackTimeModification = Util.parseFloat(attackTimeModifications.get(attackTimeModifierString).get(0, Json.Types.STRING));
                    if (attack.getType() == UtopiaUtil.Attack.Type.AMBUSH) {
                        attackTimeModification = Math.max(0.0F, originalAttackTimeModification);
                    }
                    else {
                        attackTimeModification = originalAttackTimeModification;
                    }
                }

                final Float attackTime = baseAttackTime * (1.0F + attackTimeReduction) + attackTimeModification;
                if (calculateAttackTimeCallback != null) {
                    calculateAttackTimeCallback.run(attackTime);
                }
            }
        };

        _downloadProvinceIdentifiers(attack.getTargetProvince().getKingdomIdentifier(), new Runnable() {
            @Override
            public void run() {
                _downloadAttackTimes(new Callback() {
                    @Override
                    public void run(final SessionResponse response) {
                        calculateAttackTimeRunnable.run();
                    }
                });
            }
        });
    }

    public class AttackResponse extends SessionResponse {
        private AttackBundle _attackBundle;
        public AttackResponse(UtopiaUtil.Response response) {
            super(response);
        }
        public AttackResponse(UtopiaUtil.Response response, AttackBundle attackBundle) {
            super(response);
            _attackBundle = attackBundle;
        }
        public AttackResponse(Boolean wasSuccess, String errorMessage) {
            super(wasSuccess, errorMessage);
        }

        public Boolean hasAttackBundle() {
            return (_attackBundle != null);
        }
        public AttackBundle getAttackBundle() {
            return _attackBundle;
        }
    }
    public interface AttackCallback {
        void run(AttackResponse response);
    }
    public void executeAttack(final UtopiaUtil.Attack attack, final AttackCallback callback) {

        final Runnable executeAttack = new Runnable() {
            @Override
            public void run() {
                Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(attack.getTargetProvince());
                _utopiaUtil.executeAttack(attack, provinceUtopiaId, new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        if (! response.getWasSuccess()) {
                            _onFailure(callback, response.getErrorMessage());
                            return;
                        }

                        AttackBundle bundle = (AttackBundle) response.getBundle();

                        if (callback != null) {
                            callback.run(new AttackResponse(response, bundle));
                        }
                    }
                });
            }
        };

        final Runnable checkProvinceIdentifierAndContinue = new Runnable() {
            @Override
            public void run() {
                final Province targetProvince = attack.getTargetProvince();
                Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
                if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                    _downloadProvinceIdentifiers(targetProvince.getKingdomIdentifier(), new Runnable() {
                        @Override
                        public void run() {
                            Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);

                            if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                                _onFailure(callback, "Invalid target.");
                                return;
                            }

                            executeAttack.run();
                        }
                    });
                }
                else {
                    executeAttack.run();
                }
            }
        };

        final Kingdom.Identifier kingdomIdentifier = attack.getTargetProvince().getKingdomIdentifier();
        if (! _utopiaUtil.isCurrentTargetKingdom(kingdomIdentifier)) {
            _utopiaUtil.setKingdom(kingdomIdentifier, new UtopiaUtil.Callback() {
                @Override
                public void run(UtopiaUtil.Response response) {
                    checkProvinceIdentifierAndContinue.run();
                }
            });
        }
        else {
            checkProvinceIdentifierAndContinue.run();
        }
    }

    public void downloadFundDragonInfo(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.FUND_DRAGON);
        _utopiaUtil.downloadFundDragonInfo(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.FUND_DRAGON);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                FundDragonInfoBundle fundDragonInfoBundle = (FundDragonInfoBundle) response.getBundle();

                if (!fundDragonInfoBundle.isValid()) {
                    _onFailure("Error parsing Fund-Dragon bundle.", callback);
                    return;
                }

                Kingdom kingdom = _getKingdom(_province.getKingdomIdentifier());

                if (kingdom == null) {
                    _onFailure("Could not update kingdom.", callback);
                    return;
                }

                kingdom.update(fundDragonInfoBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }
    public void downloadAttackDragonInfo(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.ATTACK_DRAGON);
        _utopiaUtil.downloadAttackDragonInfo(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.ATTACK_DRAGON);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                Kingdom kingdom = _getKingdom(_province.getKingdomIdentifier());
                Dragon dragon;
                if (kingdom == null || (dragon = kingdom.getDragon()) == null) {
                    System.out.println("ERROR: kingdom/dragon not found for " + _province.getKingdomIdentifier());
                    _onFailure("Dragon not found.", callback);
                    return;
                }

                AttackDragonInfoBundle attackDragonInfoBundle = (AttackDragonInfoBundle) response.getBundle();
                if (!attackDragonInfoBundle.isValid()) {
                    _onFailure("Error parsing dragon info bundle.", callback);
                    return;
                }

                dragon.setHealth(Util.parseInt(attackDragonInfoBundle.get(AttackDragonInfoBundle.Keys.HEALTH_REMAINING)));

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void fundDragon(Integer amount, final Callback callback) {
        _utopiaUtil.fundDragon(amount, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void attackDragon(UtopiaUtil.AttackDragonArmy attackDragonArmy, final Callback callback) {
        _utopiaUtil.attackDragon(attackDragonArmy, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void sendAid(final UtopiaUtil.AidShipment aidShipment, final Province targetProvince, final Callback callback) {
        final Runnable executeAidRequest = new Runnable() {
            @Override
            public void run() {
                Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
                _utopiaUtil.sendAid(aidShipment, _province.allowsIncomingAid(), provinceUtopiaId, new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        if (! response.getWasSuccess()) {
                            _onFailure(response.getErrorMessage(), callback);
                            return;
                        }

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null));
                        }
                    }
                });
            }
        };

        final Runnable checkProvinceIdentifierAndContinue = new Runnable() {
            @Override
            public void run() {
                Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
                if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                    _downloadProvinceIdentifiers(targetProvince.getKingdomIdentifier(), new Runnable() {
                        @Override
                        public void run() {
                            Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(targetProvince);
                            if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                                _onFailure("Unable to determine Province ID.", callback);
                                return;
                            }

                            executeAidRequest.run();
                        }
                    });
                }
                else {
                    executeAidRequest.run();
                }
            }
        };

        if (_province.allowsIncomingAid() == null) {
            _downloadTradeSettings(new Callback() {
                @Override
                public void run(SessionResponse response) {
                    if (! response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    checkProvinceIdentifierAndContinue.run();
                }
            });
        }
        else {
            checkProvinceIdentifierAndContinue.run();
        }
    }

    private void _downloadTradeSettings(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.TRADE_SETTINGS);
        _utopiaUtil.downloadTradeSettings(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.TRADE_SETTINGS);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                TradeSettingsBundle tradeSettingsBundle = (TradeSettingsBundle) response.getBundle();

                _province.update(tradeSettingsBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }
    public void downloadTradeSettings(final Callback callback) {
        _downloadTradeSettings(callback);
    }

    public void downloadBuildCosts(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.BUILD_COSTS);
        _utopiaUtil.downloadBuildingCost(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.BUILD_COSTS);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                BuildCostBundle buildCostBundle = (BuildCostBundle) response.getBundle();
                _province.update(buildCostBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadExplorationCosts(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.EXPLORATION_COSTS);
        _utopiaUtil.downloadExplorationCost(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.EXPLORATION_COSTS);

                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                ExplorationCostsBundle explorationCostsBundle = (ExplorationCostsBundle) response.getBundle();
                _province.update(explorationCostsBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void exploreAcres(Integer acres, final Callback callback) {
        _utopiaUtil.exploreAcres(acres, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (!response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void setBeginDownloadCallback(final BeginDownloadCallback beginDownloadCallback) { _beginDownloadCallback = beginDownloadCallback; }
    public void setFinishDownloadCallback(final FinishDownloadCallback finishDownloadCallback) { _finishDownloadCallback = finishDownloadCallback; }

    public void setIntelSubmitBeginCallback(Runnable callback) { _intelSubmitter.setIntelSubmitBeginCallback(callback); }
    public void setIntelSubmitEndCallback(Runnable callback) { _intelSubmitter.setIntelSubmitEndCallback(callback); }

    public void intelSyncLogin(final Callback callback) {
        final String domain = _keyValueStore.getString("INTEL_SYNC_DOMAIN");
        final String username = _keyValueStore.getString("INTEL_SYNC_USERNAME");
        final String password = _keyValueStore.getString("INTEL_SYNC_PASSWORD");
        _authenticateAndStoreIntelSyncCredentials(domain, username, password, new Callback() {
            @Override
            public void run(SessionResponse response) {

                final String provinceName = _keyValueStore.getString("PROVINCE_NAME");
                final Integer kingdom = Util.parseInt(_keyValueStore.getString("KINGDOM"));
                final Integer island = Util.parseInt(_keyValueStore.getString("ISLAND"));
                _intelSync.setProvinceData(new IntelSync.ProvinceData(provinceName, kingdom, island));
                _intelSubmitter.addIntelSync(_intelSync);

                if (callback != null) {
                    callback.run(response);
                }
            }
        });
    }

    public void setVerdeIntelSyncEnabled(final Boolean isEnabled) {
        final Boolean wasEnabled = _hasVerdeIntelSyncEnabled();

        if (isEnabled != wasEnabled) {
            if (isEnabled) {
                _keyValueStore.putString(_VERDE_INTEL_ENABLED_KEY, "1");
                _initVerdeIntelSync();
            }
            else {
                _keyValueStore.putString(_VERDE_INTEL_ENABLED_KEY, (isEnabled ? "1" : "0"));
                _intelSubmitter.removeIntelSync(_verdeIntelSync);
            }
        }
    }

    public Boolean hasVerdeIntelSyncEnabled() {
        return _hasVerdeIntelSyncEnabled();
    }

    public Boolean isVerdeIntelSyncLoggedIn() {
        return _isVerdeIntelSyncLoggedIn();
    }

    private void _initVerdeIntelSync() {
        final String provinceName = _keyValueStore.getString("PROVINCE_NAME");
        final Integer kingdomId = Util.parseInt(_keyValueStore.getString("KINGDOM"));
        final Integer islandId = Util.parseInt(_keyValueStore.getString("ISLAND"));

        if (_verdeIntelSync != null) {
            _intelSubmitter.removeIntelSync(_verdeIntelSync);
        }

        _verdeIntelSync = (VerdeIntelUtil) _intelSyncFactory.createInstance(IntelSync.IntelSyncType.VERDE);
        if (provinceName != null) {
            _verdeIntelSync.setProvinceData(new IntelSync.ProvinceData(provinceName, kingdomId, islandId));
        }
        _intelSubmitter.addIntelSync(_verdeIntelSync);
    }

    public void verdeIntelSyncLogin() {
        if (_hasVerdeIntelSyncEnabled()) {
            if (_verdeIntelSync == null && _intelSyncFactory != null) {
                _initVerdeIntelSync();
            }

            if (_verdeIntelSync != null) {
                _verdeIntelSync.login("", "", null);
            }
        }
    }

    public void intelSyncAuthenticate(final IntelSync.IntelSyncType intelSyncType, final String domain, final String username, final String password, final Callback callback) {
        _authenticateAndStoreIntelSyncCredentials(intelSyncType, domain, username, password, callback);
    }

    private void _authenticateAndStoreIntelSyncCredentials(final String domain, final String username, final String password, final Callback callback) {
        final IntelSync.IntelSyncType intelSyncType = _getIntelSyncType();
        if (intelSyncType != null) {
            _authenticateAndStoreIntelSyncCredentials(intelSyncType, domain, username, password, callback);
        }
        else {
            _onFailure("No intel sync type set.", callback);
        }
    }

    private void _authenticateAndStoreIntelSyncCredentials(final IntelSync.IntelSyncType intelSyncType, final String domain, final String username, final String password, final Callback callback) {
        _intelSync = _intelSyncFactory.createInstance(intelSyncType);

        if (_province != null) {
            final String provinceName = _keyValueStore.getString("PROVINCE_NAME");
            final Integer kingdomId = Util.parseInt(_keyValueStore.getString("KINGDOM"));
            final Integer islandId = Util.parseInt(_keyValueStore.getString("ISLAND"));

            if (provinceName != null) {
                _intelSync.setProvinceData(new IntelSync.ProvinceData(provinceName, kingdomId, islandId));
            }
        }

        if (_intelSync != null) {
            _intelSync.setSubdomain(domain);
            _intelSync.login(username, password, new IntelSync.Callback() {
                @Override
                public void run(IntelSync.Response response) {
                    if (! response.getWasSuccess()) {
                        _onFailure(response.getErrorMessage(), callback);
                        return;
                    }

                    switch (intelSyncType) {
                        case UMUNK:
                            _keyValueStore.putString("INTEL_SYNC_TYPE", "UMUNK");
                            break;
                        case STINGER:
                            _keyValueStore.putString("INTEL_SYNC_TYPE", "STINGER");
                            break;
                        case UPOOPU:
                            _keyValueStore.putString("INTEL_SYNC_TYPE", "UPOOPU");
                            break;
                        default:
                            _keyValueStore.putString("INTEL_SYNC_TYPE", "");
                            break;
                    }

                    _keyValueStore.putString("INTEL_SYNC_DOMAIN", domain);
                    _keyValueStore.putString("INTEL_SYNC_USERNAME", username);
                    _keyValueStore.putString("INTEL_SYNC_PASSWORD", password);

                    if (callback != null) {
                        callback.run(new SessionResponse(true, null));
                    }
                }
            });
        }
    }

    public void clearIntelSyncCredentials() {
        _keyValueStore.putString("INTEL_SYNC_TYPE", "");
        _keyValueStore.putString("INTEL_SYNC_DOMAIN", "");
        _keyValueStore.putString("INTEL_SYNC_USERNAME", "");
        _keyValueStore.putString("INTEL_SYNC_PASSWORD", "");

        _intelSubmitter.clearIntelSyncs();
    }

    private Boolean _hasIntelSyncEnabled() {
        return (_getIntelSyncType() != null && _keyValueStore.getString("INTEL_SYNC_TYPE").length() > 0 && _keyValueStore.getString("INTEL_SYNC_USERNAME").length() > 0 && _keyValueStore.getString("INTEL_SYNC_PASSWORD").length() > 0);
    }

    private Boolean _hasVerdeIntelSyncEnabled() {
        return ((! _keyValueStore.hasKey(_VERDE_INTEL_ENABLED_KEY)) || Util.parseInt(_keyValueStore.getString(_VERDE_INTEL_ENABLED_KEY)) > 0);
    }

    private Boolean _isVerdeIntelSyncLoggedIn() {
        return (_verdeIntelSync != null && _verdeIntelSync.isLoggedIn());
    }

    public Boolean hasIntelSyncEnabled() {
        return _hasIntelSyncEnabled();
    }

    public String getIntelSyncDomain() {
        return _keyValueStore.getString("INTEL_SYNC_DOMAIN");
    }

    private IntelSync.IntelSyncType _getIntelSyncType() {
        String syncType = _keyValueStore.getString("INTEL_SYNC_TYPE");
        switch(syncType.toUpperCase()) {
            case "UMUNK":
                return IntelSync.IntelSyncType.UMUNK;
            case "STINGER":
                return IntelSync.IntelSyncType.STINGER;
            case "UPOOPU":
                return IntelSync.IntelSyncType.UPOOPU;
            default:
                return null;
        }
    }
    public IntelSync.IntelSyncType getIntelSyncType() {
        return _getIntelSyncType();
    }

    public Boolean isIntelSyncLoggedIn() {
        if (_intelSync == null) {
            return false;
        }

        return _intelSync.isLoggedIn();
    }

    public Boolean getShouldHidePremiumIconPreference() {
        return (_keyValueStore.hasKey(_HIDE_PREMIUM_ICON_KEY) && Util.parseInt(_keyValueStore.getString(_HIDE_PREMIUM_ICON_KEY)) > 0);
    }

    public void setShouldHidePremiumIconPreference(Boolean shouldHideIcon) {
        _keyValueStore.putString(_HIDE_PREMIUM_ICON_KEY, (shouldHideIcon ? "1" : "0"));
    }

    public void setFocusedKingdom(Kingdom.Identifier kingdomIdentifier) {
        _focusedKingdomIdentifier = kingdomIdentifier;
    }

    public Kingdom.Identifier getFocusedKingdomIdentifier() {
        // return _utopiaUtil.getTargetKingdomIdentifier();
        return _focusedKingdomIdentifier;
    }

    public void downloadPrivateMessages(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.PRIVATE_MESSAGES);
        _utopiaUtil.downloadPrivateMessages(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.PRIVATE_MESSAGES);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                _privateMessages.clear();

                List<Bundle> privateMessageBundles = response.getBundle().getGroup(PrivateMessagesBundle.Keys.PRIVATE_MESSAGES);
                if (privateMessageBundles == null) {
                    _onFailure("Error parsing private messages.", callback);
                    return;
                }

                for (Bundle privateMessageBundle : privateMessageBundles) {
                    PrivateMessage privateMessage = PrivateMessage.fromBundle((PrivateMessageBundle) privateMessageBundle, _currentUtopiaDate);
                    _privateMessages.add(privateMessage);
                }

                _executeCallbacks(_privateMessageCallbacks);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadPrivateMessage(final PrivateMessage privateMessage, final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.PRIVATE_MESSAGE);
        _utopiaUtil.downloadPrivateMessage(privateMessage.getMessageId(), new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.PRIVATE_MESSAGE);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                privateMessage.update((PrivateMessageBundle) response.getBundle(), _currentUtopiaDate);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void sendPrivateMessage(final PrivateMessage privateMessage, final Callback callback) {
        if (privateMessage == null || ! privateMessage.isValid()) {
            if (callback != null) {
                callback.run(new SessionResponse(false, "Invalid message."));
                return;
            }
        }

        if (privateMessage.getIsReply()) {
            _utopiaUtil.sendPrivateMessageReply(
                privateMessage.getMessageId(),
                privateMessage.getTitle(),
                privateMessage.getContent(),
                new UtopiaUtil.Callback() {
                    @Override
                    public void run(UtopiaUtil.Response response) {
                        if (! response.getWasSuccess()) {
                            _onFailure(response.getErrorMessage(), callback);
                            return;
                        }

                        if (callback != null) {
                            callback.run(new SessionResponse(true, null));
                        }
                    }
                }
            );
        }
        else {
            final Province recipientProvince = privateMessage.getReceivingProvince();

            final Runnable executeSendPrivateMessage = new Runnable() {
                @Override
                public void run() {
                    Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(recipientProvince);
                    _utopiaUtil.sendPrivateMessage(
                            provinceUtopiaId,
                            privateMessage.getTitle(),
                            privateMessage.getContent(),
                            new UtopiaUtil.Callback() {
                                @Override
                                public void run(UtopiaUtil.Response response) {
                                    if (! response.getWasSuccess()) {
                                        _onFailure(response.getErrorMessage(), callback);
                                        return;
                                    }

                                    if (callback != null) {
                                        callback.run(new SessionResponse(true, null));
                                    }
                                }
                            }
                    );
                }
            };

            if (recipientProvince.getUtopiaId() == null || recipientProvince.getUtopiaId() == 0) {
                _downloadProvinceIdentifiers(recipientProvince.getKingdomIdentifier(), new Runnable() {
                    @Override
                    public void run() {
                        Integer provinceUtopiaId = _utopiaDatabase.getProvinceUtopiaId(recipientProvince);

                        if (provinceUtopiaId == null || provinceUtopiaId == 0) {
                            _onFailure("Invalid target.", callback);
                            return;
                        }

                        executeSendPrivateMessage.run();
                    }
                });
            }
            else {
                executeSendPrivateMessage.run();
            }
        }
    }

    public void allocateScientists(final List<Scientist> scientists, final Callback callback) {
        _utopiaUtil.allocateScientists(scientists, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void downloadScience(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.SCIENCE);
        _utopiaUtil.downloadScience(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.SCIENCE);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (response.hasBundle()) {
                    ScienceBundle scienceBundle = (ScienceBundle) response.getBundle();
                    _province.update(scienceBundle);
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });

        // _utopiaUtil.downloadScienceCouncil(null); // Download Science Council for intel submission.
    }

    public void clearAllData() {
        _utopiaDatabase.clear();
        _keyValueStore.clearAllStores();
    }

    public void downloadForumTopics(final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.FORUM_TOPICS);
        _utopiaUtil.downloadForumTopics(new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.FORUM_TOPICS);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                final ForumTopicsBundle forumTopicsBundle = (ForumTopicsBundle) response.getBundle();
                _forum.clearTopics();
                _forum.loadTopics(forumTopicsBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public List<Forum.ForumTopic> getForumTopics() {
        return _forum.getForumTopics();
    }

    public Forum.ForumTopic getForumTopic(final Integer topicId) {
        for (Forum.ForumTopic forumTopic : _forum.getForumTopics()) {
            if (forumTopic.topicId.equals(topicId)) {
                return forumTopic;
            }
        }

        return null;
    }

    public void downloadForumTopicPosts(final Forum.ForumTopic forumTopic, final Integer pageNumber, final Callback callback) {
        _executeBeginDownloadCallback(DownloadType.FORUM_TOPIC_POSTS);
        _utopiaUtil.downloadForumTopicPosts(forumTopic.topicId, pageNumber + 1, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                _executeEndDownloadCallback(DownloadType.FORUM_TOPIC_POSTS);

                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                final ForumTopicPostsBundle forumTopicPostsBundle = (ForumTopicPostsBundle) response.getBundle();
                _forum.loadForumTopicPosts(forumTopicPostsBundle);

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void submitForumTopicPost(final Forum.ForumTopicPost forumTopicPost, final Callback callback) {
        _utopiaUtil.submitForumTopicPost(forumTopicPost.topicId, forumTopicPost.content, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public void submitForumTopic(final Forum.ForumTopic forumTopic, final Callback callback) {
        _utopiaUtil.submitForumTopic(forumTopic.title, forumTopic.content, new UtopiaUtil.Callback() {
            @Override
            public void run(UtopiaUtil.Response response) {
                if (! response.getWasSuccess()) {
                    _onFailure(response.getErrorMessage(), callback);
                    return;
                }

                if (callback != null) {
                    callback.run(new SessionResponse(true, null));
                }
            }
        });
    }

    public VerdeIntelUtil.AvailableIntel getVerdeIntelCountsForProvince(final String provinceName, final Kingdom.Identifier kingdomIdentifier) {
        if (! _availableVerdeIntel.containsKey(kingdomIdentifier)) {
            return null;
        }

        final Map<String, VerdeIntelUtil.AvailableIntel> kingdomIntel = _availableVerdeIntel.get(kingdomIdentifier);
        if (kingdomIntel.containsKey(provinceName)) {
            return kingdomIntel.get(provinceName);
        }

        return null;
    }
}
