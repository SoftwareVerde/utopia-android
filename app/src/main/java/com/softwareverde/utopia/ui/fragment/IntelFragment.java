package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.intelsync.IntelSync;
import com.softwareverde.utopia.ui.MainActivity;

public class IntelFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;
    private IntelSync.IntelSyncType _intelSyncType;

    private void _setLoginStatusText(String text) {
        final TextView errorText = (TextView) _view.findViewById(R.id.umunk_message);
        errorText.setText(text);
    }

    public IntelFragment() { }

    private void _updateIntelIcon() {
        if (_session.hasIntelSyncEnabled()) {
            ((MainActivity) _activity).showIntelSyncIcon();
        }
        else {
            ((MainActivity) _activity).hideIntelSyncIcon();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.intel_sync, container, false);
        _view = rootView;

        final Button storeButton = (Button) rootView.findViewById(R.id.intel_sync_store_button);
        final Button clearButton = (Button) rootView.findViewById(R.id.intel_sync_clear_button);
        final EditText domainInput = (EditText) rootView.findViewById(R.id.intel_sync_domain_input);
        final EditText usernameInput = (EditText) rootView.findViewById(R.id.intel_sync_username_input);
        final EditText passwordInput = (EditText) rootView.findViewById(R.id.intel_sync_password_input);

        _updateIntelIcon();

        String intelSyncDomainText = _session.getIntelSyncDomain();
        if (intelSyncDomainText == null || _intelSyncType.equals(IntelSync.IntelSyncType.UPOOPU)) {
            intelSyncDomainText = "";
        }
        domainInput.setText(intelSyncDomainText);
        domainInput.setSelection(intelSyncDomainText.length());

        if (_session.hasIntelSyncEnabled()) {
            _setLoginStatusText("Authenticated.");
        }

        domainInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String subdomain = domainInput.getText().toString();
                if (subdomain.length() == 0) {
                    subdomain = "*";
                }

                final TextView domainTextview = ((TextView) _view.findViewById(R.id.intel_sync_domain_complete));
                if (_intelSyncType.equals(IntelSync.IntelSyncType.UMUNK)) {
                    domainTextview.setText(subdomain + ".umunk.net");
                }
                else if (_intelSyncType.equals(IntelSync.IntelSyncType.STINGER)) {
                    domainTextview.setText("stingernet/"+ subdomain +"/");
                }
                else if (_intelSyncType.equals(IntelSync.IntelSyncType.UPOOPU)) {
                    domainTextview.setText("");
                }
            }
        });

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _setLoginStatusText("Checking credentials...");
                    }
                });

                final ProgressDialog dialog = new ProgressDialog(_activity, ProgressDialog.THEME_HOLO_DARK);
                dialog.setTitle("Loading");
                dialog.setMessage("Checking credentials...");
                dialog.setCancelable(false);

                _activity.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.show();
                    }
                });

                _session.intelSyncAuthenticate(
                    _intelSyncType,
                    domainInput.getText().toString(),
                    usernameInput.getText().toString(),
                    passwordInput.getText().toString(),
                    new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    _updateIntelIcon();
                                }
                            });

                            if (! response.getWasSuccess()) {
                                Dialog.setActivity(_activity);
                                Dialog.alert("IntelSync Authentication", response.getErrorMessage(), null);

                                _activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        _setLoginStatusText("Invalid credentials.");
                                    }
                                });

                                return;
                            }

                            Dialog.setActivity(_activity);

                            final String intelSyncName;
                            if (_intelSyncType.equals(IntelSync.IntelSyncType.UMUNK)) {
                                intelSyncName = "uMunk";
                            }
                            else if (_intelSyncType.equals(IntelSync.IntelSyncType.STINGER)) {
                                intelSyncName = "Stinger";
                            }
                            else {
                                intelSyncName = "UpoUpo";
                            }

                            Dialog.alert("Intel Sync Authentication", "Authentication successful. "+ intelSyncName +" will now sync all intel and ops.", null);
                            _session.intelSyncLogin(null);

                            _activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _setLoginStatusText("Authenticated.");
                                }
                            });

                        }
                    }
                );
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.setActivity(_activity);
                Dialog.confirm(
                    "Clear IntelSync Credentials",
                    "Are you sure you want to clear your IntelSync credentials and cancel intel syncing?",
                    new Runnable() {
                        @Override
                        public void run() {
                            _session.clearIntelSyncCredentials();
                            Dialog.setActivity(_activity);
                            Dialog.alert("Clear IntelSync Credentials", "Credentials cleared. Syncing is now disabled.", null);

                            _setLoginStatusText("");
                            _updateIntelIcon();
                        }
                    },
                    null
                );
            }
        });

        final CheckBox verdeSyncCheckBox = (CheckBox) _view.findViewById(R.id.intel_sync_verde_sync_checkbox);
        verdeSyncCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean shouldBeEnabled = verdeSyncCheckBox.isChecked();
                _session.setVerdeIntelSyncEnabled(shouldBeEnabled);

                if (shouldBeEnabled) {
                    if (! _session.isVerdeIntelSyncLoggedIn()) {
                        _session.verdeIntelSyncLogin();
                    }
                }

                Toast.makeText(_activity, "Utopia-App Sync "+ (shouldBeEnabled ? "Enabled" : "Disabled"), Toast.LENGTH_SHORT).show();
            }
        });
        verdeSyncCheckBox.setChecked(_session.hasVerdeIntelSyncEnabled());

        _view.findViewById(R.id.intel_tab_umunk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, true);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
            _intelSyncType = IntelSync.IntelSyncType.UMUNK;
            _showUmunkSync();
            }
        });
        _view.findViewById(R.id.intel_tab_stinger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, true);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
                _intelSyncType = IntelSync.IntelSyncType.STINGER;
                _showStingerSync();
            }
        });
        _view.findViewById(R.id.intel_tab_upoopu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, true);
                _intelSyncType = IntelSync.IntelSyncType.UPOOPU;
                _showUpoopuSync();
            }
        });
        _view.findViewById(R.id.intel_tab_verde).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, true);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
                AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
                _intelSyncType = IntelSync.IntelSyncType.UPOOPU;
                _showVerdeSync();
            }
        });

        if (_intelSyncType == IntelSync.IntelSyncType.UMUNK) {
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, true);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
            _showUmunkSync();
        }
        else if (_intelSyncType == IntelSync.IntelSyncType.STINGER) {
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, true);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
            _showStingerSync();
        }
        else if (_intelSyncType == IntelSync.IntelSyncType.UPOOPU) {
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, true);
            _showUpoopuSync();
        }
        else {
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_verde, true);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_umunk, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_stinger, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.intel_tab_upoopu, false);
            _showVerdeSync();
        }

        return rootView;
    }

    private void _showUmunkSync() {
        _view.findViewById(R.id.intel_sync_scrollview).setVisibility(View.VISIBLE);
        _view.findViewById(R.id.intel_sync_verde_container).setVisibility(View.GONE);

        ((TextView) _view.findViewById(R.id.intel_sync_title)).setText("uMunk Sync");

        String intelSyncDomainText = _session.getIntelSyncDomain();
        if (intelSyncDomainText == null) {
            intelSyncDomainText = "";
        }

        if (intelSyncDomainText.length() == 0) {
            intelSyncDomainText = "*";
        }

        final TextView domainTextView = ((TextView) _view.findViewById(R.id.intel_sync_domain_complete));
        domainTextView.setText(intelSyncDomainText + ".umunk.net");

        _toggleDomainInput(true);

        if (_session.hasIntelSyncEnabled() && IntelSync.IntelSyncType.UMUNK.equals(_session.getIntelSyncType())) {
            _setLoginStatusText("Authenticated.");
        }
        else {
            _setLoginStatusText("Not Authenticated.");
        }
    }
    private void _showStingerSync() {
        _view.findViewById(R.id.intel_sync_scrollview).setVisibility(View.VISIBLE);
        _view.findViewById(R.id.intel_sync_verde_container).setVisibility(View.GONE);

        ((TextView) _view.findViewById(R.id.intel_sync_title)).setText("Stinger Sync");

        String intelSyncDomainText = _session.getIntelSyncDomain();
        if (intelSyncDomainText == null) {
            intelSyncDomainText = "";
        }

        if (intelSyncDomainText.length() == 0) {
            intelSyncDomainText = "*";
        }

        final TextView domainTextView = ((TextView) _view.findViewById(R.id.intel_sync_domain_complete));
        domainTextView.setText("stingernet/"+ intelSyncDomainText+"/");

        _toggleDomainInput(true);

        if (_session.hasIntelSyncEnabled() && IntelSync.IntelSyncType.STINGER.equals(_session.getIntelSyncType())) {
            _setLoginStatusText("Authenticated.");
        }
        else {
            _setLoginStatusText("Not Authenticated.");
        }
    }
    private void _showUpoopuSync() {
        _view.findViewById(R.id.intel_sync_scrollview).setVisibility(View.VISIBLE);
        _view.findViewById(R.id.intel_sync_verde_container).setVisibility(View.GONE);

        ((TextView) _view.findViewById(R.id.intel_sync_title)).setText("UpoOpu Sync");

        final TextView domainTextView = ((TextView) _view.findViewById(R.id.intel_sync_domain_complete));
        domainTextView.setText("");

        _toggleDomainInput(false);

        if (_session.hasIntelSyncEnabled() && IntelSync.IntelSyncType.UPOOPU.equals(_session.getIntelSyncType()) && _session.isIntelSyncLoggedIn()) {
            _setLoginStatusText("Authenticated.");
        }
        else {
            _setLoginStatusText("Not Authenticated.");
        }
    }
    private void _showVerdeSync() {
        _view.findViewById(R.id.intel_sync_scrollview).setVisibility(View.GONE);
        _view.findViewById(R.id.intel_sync_verde_container).setVisibility(View.VISIBLE);


    }

    private void _toggleDomainInput(final Boolean shouldShow) {
        final EditText domainInput = (EditText) _view.findViewById(R.id.intel_sync_domain_input);
        domainInput.setVisibility(shouldShow ? View.VISIBLE : View.GONE);

        final View domainInputLabelLayout = _view.findViewById(R.id.intel_sync_domain_label_layout);
        domainInputLabelLayout.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
        _intelSyncType = _session.getIntelSyncType();

        if (_intelSyncType == null) {
            _intelSyncType = IntelSync.IntelSyncType.UPOOPU;
        }
    }

    @Override
    public void onDestroy() {
        ((MainActivity) _activity).hideIntelSyncIcon();

        super.onDestroy();
    }
}