package com.softwareverde.utopia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.softwareverde.utopia.config.BuildConfiguration;
import com.softwareverde.utopia.config.ServiceBuildConfiguration;

public class KingdomChatService extends Service {
    public static void startService(final Context context, final ProvinceCredentials provinceCredentials) {
        final Intent chatServiceIntent = new Intent(context.getApplicationContext(), KingdomChatService.class);

        chatServiceIntent.putExtra("HAS_CREDENTIALS", "1");
        chatServiceIntent.putExtra("USERNAME", provinceCredentials.getUsername());
        chatServiceIntent.putExtra("PASSWORD", provinceCredentials.getPassword());
        chatServiceIntent.putExtra("PROVINCE_NAME", provinceCredentials.getProvinceName());
        chatServiceIntent.putExtra("KINGDOM", provinceCredentials.getKingdomId());
        chatServiceIntent.putExtra("ISLAND", provinceCredentials.getIslandId());

        context.startService(chatServiceIntent);
    }

    private BuildConfiguration _buildConfig = new ServiceBuildConfiguration();
    private Session _session = null;
    private HaltableThread _thronePollThread = null;

    private void _initSession() {
        _session.enableVibrateOnPing();
        _session.enableNotificationOnPing();

        _session.setOnLoggedOutCallback(new Session.OnLoggedOutCallback() {
            @Override
            public void run(Session.OnLoggedBackInCallback onLoggedBackInCallback) {
                System.out.println("SERVICE: Logged Out.");
                // _session.autoLogin(null);
            }
        });

        _session.downloadThrone(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _session.downloadKingdom(new Session.DownloadKingdomCallback() {
                    @Override
                    public void run(Session.SessionResponse response, Kingdom.Identifier kingdomIdentifier) {
                        _session.startDownloadMessagesThread();
                    }
                });
            }
        });
    }

    private void _initializeService(final Intent intent) {
        _buildConfig.configureDependencies(this.getApplicationContext());

        _session = Session.getInstance();

        _session.resume(new Runnable() {
            @Override
            public void run() {
                final Session.Callback loginCallback = new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        if (! response.getWasSuccess()) {
                            System.out.println("SERVICE: Logged Out. Unable to log back in.");
                            return;
                        }

                        _initSession();
                    }
                };

                if (intent != null && intent.hasExtra("HAS_CREDENTIALS")) {
                    System.out.println("SERVICE: using credentials.");
                    final String username = intent.getStringExtra("USERNAME");
                    final String password = intent.getStringExtra("PASSWORD");

                    _session.login(username, password, loginCallback);
                }
                else {
                    System.out.println("SERVICE: NOT using credentials.");
                    _session.autoLogin(loginCallback);
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (_session == null) {
            _initializeService(null);
        }
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (_session == null) {
            _initializeService(intent);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (_thronePollThread != null) {
            _thronePollThread.halt();
            _thronePollThread = null;
        }

        _session.stopDownloadMessagesThread();

        super.onDestroy();
    }
}
