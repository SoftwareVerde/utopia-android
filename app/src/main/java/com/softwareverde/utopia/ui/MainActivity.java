package com.softwareverde.utopia.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareverde.json.Json;
import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.Util;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.InAppPurchases;
import com.softwareverde.utopia.KingdomChatService;
import com.softwareverde.utopia.MainApplication;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Settings;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.config.AppBuildConfiguration;
import com.softwareverde.utopia.config.BuildConfiguration;
import com.softwareverde.utopia.database.AndroidKeyValueStore;
import com.softwareverde.utopia.database.KeyValueStore;
import com.softwareverde.utopia.parser.UtopiaParser;
import com.softwareverde.utopia.ui.fragment.CommunicationFragment;
import com.softwareverde.utopia.ui.fragment.DragonFragment;
import com.softwareverde.utopia.ui.fragment.ExplorationFragment;
import com.softwareverde.utopia.ui.fragment.ForumFragment;
import com.softwareverde.utopia.ui.fragment.IntelFragment;
import com.softwareverde.utopia.ui.fragment.InteractiveBuildingsCouncilFragment;
import com.softwareverde.utopia.ui.fragment.Kingdom2Fragment;
import com.softwareverde.utopia.ui.fragment.LoginFragment;
import com.softwareverde.utopia.ui.fragment.MainThroneFragment;
import com.softwareverde.utopia.ui.fragment.NavigationDrawerFragment;
import com.softwareverde.utopia.ui.fragment.NewsFragment;
import com.softwareverde.utopia.ui.fragment.ScienceFragment;
import com.softwareverde.utopia.ui.fragment.SpellListFragment;
import com.softwareverde.utopia.util.BuildVersion;
import com.softwareverde.utopia.util.android.AndroidBuildVersion;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String LOGIN_CALLBACK_IDENTIFIER = "MainActivityLoginCallback";
    public static final String DATE_CALLBACK_IDENTIFIER = "MainActivityDateCallback";

    public enum PermissionType {
        READ_PHONE_STATE
    }

    public interface PermissionCallback {
        void run(Boolean wasGranted);
    }

    private static final HashMap<PermissionType, List<PermissionCallback>> _permissionCallbacks = new HashMap<PermissionType, List<PermissionCallback>>();
    public static void addPermissionCallback(PermissionType permissionType, PermissionCallback callback) {
        synchronized (MainActivity._permissionCallbacks) {
            if (! MainActivity._permissionCallbacks.containsKey(permissionType)) {
                MainActivity._permissionCallbacks.put(permissionType, new LinkedList<PermissionCallback>());
            }

            List<PermissionCallback> permissionCallbacks = MainActivity._permissionCallbacks.get(permissionType);
            permissionCallbacks.add(callback);
        }
    }

    private BuildConfiguration _buildConfig = new AppBuildConfiguration();
    private NavigationDrawerFragment _navigationDrawerFragment;
    private CharSequence _title;
    private Session _session;
    final private Boolean _displayAds = (! MainApplication.isDev());
    final private String _authenticateUrl = (MainApplication.isDev() ? "https://utopia.softwareverde.com/auth-dev/" : "https://utopia.softwareverde.com/auth/");
    private Long _lastAdviewUpdate = 0L;
    private BuildVersion _buildVersion;

    private Boolean _hasCustomActionBarView = false;
    private Menu _menu;
    private Runnable _onBackCallback;
    private KeyValueStore _keyValueStore;
    private InAppPurchases _inAppPurchases;
    private LinearLayout _loadingDescriptionsContainer;
    private LinearLayout _loadingDescriptionItemsContainer;

    public void showIntelSyncIcon() {
        final ImageView imageView = (ImageView) this.findViewById(R.id.intel_sync_icon);

        switch (_session.getIntelSyncType()) {
            case UMUNK:
                imageView.setImageResource(R.drawable.umunk);
                break;
            case STINGER:
                imageView.setImageResource(R.drawable.stinger);
                break;
            case UPOOPU:
                imageView.setImageResource(R.drawable.upoopu);
                break;
            default:
                break;
        }

        imageView.setVisibility(View.VISIBLE);
    }

    private void _setActionBarView(final View view) {
        final ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(view == null);
        actionBar.setDisplayShowTitleEnabled(view == null);

        actionBar.setDisplayHomeAsUpEnabled(view != null);
        actionBar.setHomeButtonEnabled(view != null);

        actionBar.setDisplayShowCustomEnabled(view != null);
        actionBar.setCustomView(view);

        if (view != null) {
            ((Toolbar) view.getParent()).setContentInsetsAbsolute(0, 0);
        }

        actionBar.setDisplayOptions(view != null ? ActionBar.DISPLAY_SHOW_CUSTOM : ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
    }

    public void hideIntelSyncIcon() {
        this.findViewById(R.id.intel_sync_icon).setVisibility(View.GONE);
    }

    private void _changePage(final Fragment fragment) {
        final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void _setOptionMenuItemsVisible(final Boolean isVisible) {
        if (_menu == null) { return; }

        for (Integer i=0; i<_menu.size(); ++i) {
            _menu.getItem(i).setVisible(isVisible);
        }
    }

    private void _setDownloadDescriptionCallbacks() {
        _session.setBeginDownloadCallback(new Session.BeginDownloadCallback() {
            @Override
            public void run(final Session.DownloadType begunDownloadType) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (_loadingDescriptionLock) {
                            final TextView loadingDescriptionItemView = (TextView) MainActivity.this.getLayoutInflater().inflate(R.layout.loading_description_item, _loadingDescriptionItemsContainer, false);
                            loadingDescriptionItemView.setText(_downloadIdentifierToString(begunDownloadType));
                            _loadingDescriptionsContainer.setVisibility(View.VISIBLE);
                            _loadingDescriptionItems.add(loadingDescriptionItemView);
                            _loadingDescriptionItemsContainer.addView(loadingDescriptionItemView);
                        }
                    }
                });
            }
        });

        _session.setFinishDownloadCallback(new Session.FinishDownloadCallback() {
            @Override
            public void run(final Session.DownloadType endedDownloadType) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (_loadingDescriptionLock) {
                            for (final TextView loadingDescriptionItemView : _loadingDescriptionItems) {
                                if (loadingDescriptionItemView.getText().equals(_downloadIdentifierToString(endedDownloadType))) {
                                    final Animation fadeOutAnimation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out);
                                    fadeOutAnimation.setDuration(300);
                                    fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) { }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            _loadingDescriptionItemsContainer.removeView(loadingDescriptionItemView);

                                            if (_loadingDescriptionItemsContainer.getChildCount() == 0) {
                                                _loadingDescriptionsContainer.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) { }
                                    });
                                    loadingDescriptionItemView.setAnimation(fadeOutAnimation);
                                    loadingDescriptionItemView.setVisibility(View.INVISIBLE);

                                    _loadingDescriptionItems.remove(loadingDescriptionItemView);
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    private String _downloadIdentifierToString(final Session.DownloadType downloadIdentifier) {
        final String downloadIdentifierString = downloadIdentifier.name().replaceAll("_", " ").toLowerCase();
        final Integer stringLength = downloadIdentifierString.length();

        final Character spaceCharcter = ' ';

        final StringBuffer stringBuffer = new StringBuffer();
        for (Integer i=0; i<stringLength; ++i) {
            final Character previous = (i > 0 ? downloadIdentifierString.charAt(i-1) : null);
            final Character current = downloadIdentifierString.charAt(i);
            // final Character next = (i+1 < stringLength - 1 ? downloadIdentifierString.charAt(i+1) : null);

            if (previous == null || previous.equals(spaceCharcter)) {
                stringBuffer.append(Character.toUpperCase(current));
            }
            else {
                stringBuffer.append(current);
            }
        }

        return stringBuffer.toString();
    }

    public void setActionBarView(final View view) {
        _setActionBarView(view);
        _hasCustomActionBarView = true;

        _setOptionMenuItemsVisible(false);
    }

    public void resetActionBarView() {
        _setActionBarView(null);
        _hasCustomActionBarView = false;

        _setOptionMenuItemsVisible(true);
    }

    private final LinkedBlockingQueue<TextView> _loadingDescriptionItems = new LinkedBlockingQueue<TextView>();
    private final Object _loadingDescriptionLock = new Object();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        _buildConfig.configureDependencies(this);
        super.onCreate(savedInstanceState); // NOTE: Must get called after session is prepared.

        _buildVersion = new AndroidBuildVersion(this);
        _keyValueStore = new AndroidKeyValueStore(this, AndroidKeyValueStore.Stores.MAIN_ACTIVITY);

        _session = Session.getInstance();

        this.setContentView(R.layout.activity_main);

        _session.setBadAccessCallback(new Session.BadAccessCallback() {
            @Override
            public void run(final UtopiaParser.BadAccessType badAccessType) {
                if (badAccessType != UtopiaParser.BadAccessType.NONE) {
                    Dialog.setActivity(MainActivity.this);

                    switch (badAccessType) {
                        case UNVERIFIED_EMAIL: {
                            Dialog.alert("Invalid Account", "Your email address must be verified before you can continue. Please check your email for the verification link.", new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity.this.finishAffinity();
                                    System.exit(0);
                                }
                            });
                        } break;

                        case NO_PROVINCE: {
                            Dialog.alert("Invalid Province", "Your province has not been created yet. Please create your province through the website before continuing.", new Runnable() {
                                @Override
                                public void run() {
                                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(Settings.getCreateProvinceUrl()));
                                    MainActivity.this.startActivity(intent);

                                    MainActivity.this.finishAffinity();
                                    System.exit(0);
                                }
                            });
                        } break;

                        case DEAD_PROVINCE: {
                            Dialog.alert("Dead Province", "Your province has been destroyed. You'll need to recreate your province through the website before continuing.", new Runnable() {
                                @Override
                                public void run() {
                                    final Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(Settings.getCreateProvinceUrl()));
                                    MainActivity.this.startActivity(intent);

                                    MainActivity.this.finishAffinity();
                                    System.exit(0);
                                }
                            });
                        } break;
                    }
                }
            }
        });

        _session.loadCookies();

        _navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        _title = getTitle();

        _loadingDescriptionsContainer = (LinearLayout) MainActivity.this.findViewById(R.id.loading_descriptions_container);
        _loadingDescriptionItemsContainer = (LinearLayout) MainActivity.this.findViewById(R.id.loading_description_items_container);

        _loadingDescriptionsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _loadingDescriptionItems.clear();
                _loadingDescriptionItemsContainer.removeAllViews();
                _loadingDescriptionsContainer.setVisibility(View.GONE);
            }
        });

        _navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        _session.setIntelSubmitBeginCallback(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (_session.hasIntelSyncEnabled()) {
                            MainActivity.this.showIntelSyncIcon();
                        }
                    }
                });
            }
        });
        _session.setIntelSubmitEndCallback(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.hideIntelSyncIcon();
                    }
                });
            }
        });

        _session.addLoginCallback(MainActivity.LOGIN_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, new MainThroneFragment());
                        transaction.commit();
                    }
                });

                if (Settings.isChatServiceEnabled()) {
                    System.out.println("NOTICE: Chat service disabled.");
                    KingdomChatService.startService(MainActivity.this, _session.getProvinceCredentials());
                }
            }
        });

        _session.addDateCallback(DATE_CALLBACK_IDENTIFIER, new Runnable() {
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String currentUtopiaDate = _session.getCurrentUtopiaDate();
                        final ActionBar actionBar = MainActivity.this.getSupportActionBar();
                        if (currentUtopiaDate != null && actionBar != null) {
                            actionBar.setSubtitle(currentUtopiaDate);
                        }
                    }
                });
            }
        });

        if (_displayAds) {
            this.findViewById(R.id.adview).setVisibility(View.GONE);

            _session.addAdviewCallback("THRONE_ADVIEW", new UtopiaUtil.AdViewCallback() {
                @Override
                public void run(final String html) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.this.findViewById(R.id.adview).setVisibility(View.VISIBLE);
                            _loadAdview(html);
                        }
                    });
                }
            });
        }
        else {
            this.findViewById(R.id.adview).setVisibility(View.GONE);
            FrameLayout mainContainer = (FrameLayout) this.findViewById(R.id.container);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainContainer.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
            mainContainer.setLayoutParams(layoutParams);
        }

        _session.setOnServerTickCallback(new Session.ServerTickCallback() {
            @Override
            public void run(Boolean serverIsTicking) {
                final String title = "Server Ticking";
                if (serverIsTicking) {
                    if (!Dialog.isShowingProgress(title)) {
                        Dialog.setActivity(MainActivity.this);
                        Dialog.showProgress(title, "Please wait... the server is ticking.");
                    }
                }
                else {
                    Dialog.setActivity(MainActivity.this);
                    Dialog.hideProgress(title);
                }
            }
        });

        _session.setOnLoggedOutCallback(new Session.OnLoggedOutCallback() {
            @Override
            public void run(final Session.OnLoggedBackInCallback onLoggedBackInCallback) {
                if (_session.hasSavedCredentials()) {
                    final String title = "Authenticating";
                    Dialog.setActivity(MainActivity.this);
                    Dialog.showProgress(title, "Please wait. Logging back in...");

                    _session.autoLogin(new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            Dialog.setActivity(MainActivity.this);
                            Dialog.hideProgress(title);

                            onLoggedBackInCallback.onLogin();
                        }
                    });
                }
                else {
                    _changePage(new LoginFragment());
                }
            }
        });

        if (_session.hasIntelSyncEnabled() && ! _session.isIntelSyncLoggedIn()) {
            _session.intelSyncLogin(null);
        }

        if (_session.hasVerdeIntelSyncEnabled() && ! _session.isVerdeIntelSyncLoggedIn()) {
            _session.verdeIntelSyncLogin();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int id) {
        final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();

        switch (id) {
            case NavigationDrawerFragment.LOGIN_TAB_ID: {
                transaction.replace(R.id.container, new LoginFragment());
            }
            break;
            case NavigationDrawerFragment.THRONE_TAB_ID: {
                transaction.replace(R.id.container, new MainThroneFragment());
            }
            break;
            case NavigationDrawerFragment.NEWS_TAB_ID: {
                transaction.replace(R.id.container, new NewsFragment());
            }
            break;
            case NavigationDrawerFragment.BUILDINGS_TAB_ID: {
                transaction.replace(R.id.container, new InteractiveBuildingsCouncilFragment());
            }
            break;
            case NavigationDrawerFragment.CHAT_TAB_ID: {
                transaction.replace(R.id.container, new CommunicationFragment());
            }
            break;
            case NavigationDrawerFragment.SPELLS_TAB_ID: {
                transaction.replace(R.id.container, new SpellListFragment());
            }
            break;
            case NavigationDrawerFragment.KINGDOM_TAB_ID: {
                transaction.replace(R.id.container, new Kingdom2Fragment());
            }
            break;
            case NavigationDrawerFragment.INTEL_SETTINGS_TAB_ID: {
                transaction.replace(R.id.container, new IntelFragment());
            }
            break;
            case NavigationDrawerFragment.DRAGON_TAB_ID: {
                transaction.replace(R.id.container, new DragonFragment());
            }
            break;
            case NavigationDrawerFragment.EXPLORE_TAB_ID: {
                transaction.replace(R.id.container, new ExplorationFragment());
            }
            break;
            case NavigationDrawerFragment.SCIENCE_TAB_ID: {
                transaction.replace(R.id.container, new ScienceFragment());
            }
            break;
            case NavigationDrawerFragment.FORUM_TAB_ID: {
                transaction.replace(R.id.container, new ForumFragment());
            }
            break;
            default: {
                System.out.println("Unknown Button Clicked: " + id);
            }
            break;
        }

        AndroidUtil.closeKeyboard(this);

        transaction.commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(_title);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer); // Use the Hamburger-Icon for Drawer
    }

    public void setOnBackCallback(final Runnable onBackCallback) {
        _onBackCallback = onBackCallback;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        _menu = menu;

        final Boolean result;
        if (! _navigationDrawerFragment.isDrawerOpen()) {
            this.getMenuInflater().inflate(R.menu.main, menu);
            result = true;
        }
        else {
            result = super.onCreateOptionsMenu(menu);
        }

        _setOptionMenuItemsVisible(! _hasCustomActionBarView);
        this.restoreActionBar();

        return result;
    }

    @Override
    public void onDestroy() {
        _session.saveCookies();
        _session.stopDownloadMessagesThread();

        _session.removeLoginCallback(MainActivity.LOGIN_CALLBACK_IDENTIFIER);

        if (_inAppPurchases != null) {
            _inAppPurchases.destroy();
        }
        _inAppPurchases = null;

        super.onDestroy();
    }

    private void _loadAdview(final String adScripts) {
        Long now = System.currentTimeMillis();
        if (now - _lastAdviewUpdate < 10 * 1000) {
            return;
        }

        System.out.println("Updating Adview...");

        final WebView adview = ((WebView) this.findViewById(R.id.adview));
        adview.getSettings().setJavaScriptEnabled(true);
        adview.getSettings().setLoadWithOverviewMode(true);
        adview.getSettings().setUseWideViewPort(true);


        final String html = "<!DOCTYPE HTML><html><head><style>html, body { margin: 0px; padding: 0px; background-color: #000000; }</style></head><body><center>"+ adScripts +"</center></body></html>";

        adview.loadDataWithBaseURL("https://utopia-game.com/", html, "text/html", "utf-8", null);
    }

    public void authenticateApp(final Runnable callback) {
        final String imei = ""; // Unsupported

        AndroidUtil.getAndroidId(MainActivity.this, new AndroidUtil.AndroidIdCallback() {
            @Override
            public void run(final String androidId) {
                final String versionNumber = _buildVersion.getVersionNumber().toString();

                final WebRequest webRequest = new WebRequest();
                webRequest.setUrl(_authenticateUrl);
                webRequest.setType(WebRequest.RequestType.POST);
                webRequest.setPostParam("imei", imei);
                webRequest.setPostParam("android_id", androidId);
                webRequest.setPostParam("version", versionNumber);
                webRequest.execute(true, new WebRequest.Callback() {
                    @Override
                    public void run(WebRequest request) {
                        if (request.hasResult()) {
                            Json response = request.getJsonResult();
                            Boolean isAuthenticated = (response.get("authenticated", Json.Types.INTEGER) > 0);

                            if (isAuthenticated) {
                                _keyValueStore.putString("authenticated", Long.valueOf(System.currentTimeMillis()).toString());
                            }
                            else {
                                _keyValueStore.putString("authenticated", "0");
                            }
                        }

                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
            }
        });
    }

    public Boolean isAppAuthenticated() {
        String authenticated = _keyValueStore.getString("authenticated");
        return (Util.parseLong(authenticated) > 0L);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Boolean resultWasHandledByInAppPurchases = false;

        System.out.println("onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (_inAppPurchases != null) {
            resultWasHandledByInAppPurchases = _inAppPurchases.handleActivityResult(requestCode, resultCode, data);
        }

        if (! resultWasHandledByInAppPurchases) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        _buildConfig.configureDependencies(this);

        _setDownloadDescriptionCallbacks();
        _inAppPurchases = InAppPurchases.getInstance(this);

        super.onResume();
    }

    @Override
    protected void onPause() {
        _session.setBeginDownloadCallback(null);
        _session.setFinishDownloadCallback(null);

        _loadingDescriptionItems.clear();
        ((LinearLayout) this.findViewById(R.id.loading_description_items_container)).removeAllViews();
        (this.findViewById(R.id.loading_descriptions_container)).setVisibility(View.GONE);

        if (_inAppPurchases != null) {
            _inAppPurchases.destroy();
        }
        _inAppPurchases = null;

        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == AndroidUtil.READ_PHONE_STATE_PERMISSION) {

            Boolean wasGranted = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            synchronized (MainActivity._permissionCallbacks) {
                if (MainActivity._permissionCallbacks.containsKey(PermissionType.READ_PHONE_STATE)) {
                    final List<PermissionCallback> permissionCallbacks = MainActivity._permissionCallbacks.get(PermissionType.READ_PHONE_STATE);
                    MainActivity._permissionCallbacks.put(PermissionType.READ_PHONE_STATE, new LinkedList<PermissionCallback>());

                    for (PermissionCallback callback : permissionCallbacks) {
                        if (callback != null) {
                            callback.run(wasGranted);
                        }
                    }
                    permissionCallbacks.clear();
                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                Dialog.setActivity(this);
                Dialog.confirm("Logout", "Are you sure you want to logout?", new Runnable() {
                    @Override
                    public void run() {
                        _session.clearCredentials();
                        _session.logout();

                        _changePage(new LoginFragment());

                        Toast.makeText(MainActivity.this, "Logged out.", Toast.LENGTH_SHORT).show();
                    }
                }, null);

                return true;
            }
            case R.id.donate: {
                final Activity activity = this;
                InAppPurchases.promptDonateDialog(activity, new Runnable() {
                    @Override
                    public void run() {
                        Dialog.setActivity(activity);
                        Dialog.alert("Donation", "Thank you for the donation!", null);
                        _session.setShouldHidePremiumIconPreference(false);
                    }
                });
                return true;
            }
            default: { } break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (_onBackCallback != null) {
            _onBackCallback.run();
        }
        else {
            super.onBackPressed();
        }
    }
}