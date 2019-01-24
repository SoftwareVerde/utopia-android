package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.Settings;
import com.softwareverde.utopia.ui.MainActivity;

public class LoginFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;
    private ProgressDialog _dialog = null;

    private Runnable _initView = new Runnable() {
        @Override
        public void run() {
            if (_session.hasSavedCredentials()) {
                _setLoginStatusText("Logging in...");
                _showLoadingDialog();

                _session.resume(new Runnable() {
                    public void run() {
                        (new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                } catch (Exception e) { }

                                _session.autoLogin(_loginCallback);
                            }
                        }).start();
                    }
                });
            }

            final Button loginButton = (Button) _view.findViewById(R.id.login_button);
            final EditText passwordInput = (EditText) _view.findViewById(R.id.password_input);

            passwordInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        textView.clearFocus();
                        AndroidUtil.closeKeyboard(_activity);

                        _attemptLogin();

                        return true;
                    }

                    return false;
                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _setLoginStatusText("Logging in...");
                        }
                    });

                    _attemptLogin();
                }
            });
        }
    };

    private void _attemptLogin() {
        final EditText usernameInput = (EditText) _view.findViewById(R.id.username_input);
        final EditText passwordInput = (EditText) _view.findViewById(R.id.password_input);

        _showLoadingDialog();
        _session.login(usernameInput.getText().toString(), passwordInput.getText().toString(), _loginCallback);
    }

    private void _setLoginStatusText(String text) {
        final TextView errorText = (TextView) _view.findViewById(R.id.login_message);
        errorText.setText(text);
    }

    private void _showLoadingDialog() {
        _dialog = new ProgressDialog(_activity, ProgressDialog.THEME_HOLO_DARK);
        _dialog.setTitle("Loading");
        _dialog.setMessage("Logging in...");
        _dialog.setCancelable(false);

        _activity.runOnUiThread(new Runnable() {
            public void run() {
                _dialog.show();
            }
        });
    }

    private Session.Callback _loginCallback = new Session.Callback() {
        public void run(final Session.SessionResponse response) {
            _activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (! response.getWasSuccess()) {
                        _setLoginStatusText(response.getErrorMessage());

                        if (_dialog != null && _dialog.isShowing()) {
                            _dialog.dismiss();
                        }

                        return;
                    }

                    _setLoginStatusText("Logged in.");
                    AndroidUtil.closeKeyboard(_activity);

                    if (_dialog != null && _dialog.isShowing()) {
                        _dialog.dismiss();
                    }
                }
            });
        }
    };

    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.login, container, false);
        _view = rootView;

        Dialog.setActivity(_activity);
        Dialog.showProgress("Authentication", "Please wait.. authenticating app.");

        final MainActivity mainActivity = (MainActivity) _activity;
        mainActivity.authenticateApp(new Runnable() {
            @Override
            public void run() {
                Dialog.hideProgress("Authentication");

                if (! mainActivity.isAppAuthenticated()) {
                    // Authentication failed. Kill the app after 5 seconds...
                    (new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) { }

                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _activity.finish();
                                }
                            });
                        }
                    }).start();

                    Dialog.setActivity(_activity);
                    Dialog.alert("Authentication", "Authentication for this app failed. \n\nPlease make sure you have an internet connection.", new Runnable() {
                        @Override
                        public void run() {
                            _activity.finish();
                        }
                    });

                    return;
                }
                else {
                    _activity.runOnUiThread(_initView);
                }
            }
        });

        _view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Settings.getRegistrationUrl()));
                LoginFragment.this.startActivity(intent);
            }
        });

        View.OnClickListener facebookHelpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.setActivity(_activity);
                Dialog.confirm(
                    "Facebook Login",
                    "If you normally login to Utopia via Facebook, you must use the email address associated with your Facebook account as your username.\n\nYou must also set a password for Utopia via the browser.\n\nDo you want to create your password now?",
                    new Runnable() {
                        @Override
                        public void run() {
                            final Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(Settings.getForgottenPasswordUrl()));
                            LoginFragment.this.startActivity(intent);
                        }
                    },
                    null
                );
            }
        };
        _view.findViewById(R.id.login_fb_button).setOnClickListener(facebookHelpClickListener);
        _view.findViewById(R.id.login_fb_help).setOnClickListener(facebookHelpClickListener);

        _view.findViewById(R.id.login_clear_data_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.setActivity(_activity);
                Dialog.confirm(
                    "Reset Application Data?",
                    "Are you sure you want to reset your stored data?\n\nThis is usually a good idea when the age resets or if you're encountering problems.\n\nAll stored province and operation data will be lost.",
                    new Runnable() {
                        @Override
                        public void run() {
                            _session.clearAllData();
                            Dialog.alert("Reset Application Data", "Data reset.", null);
                        }
                    },
                    null
                );
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
    }
}