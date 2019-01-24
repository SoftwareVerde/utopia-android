package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.PrivateMessage;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

public class ReplyPrivateMessageFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;
    private PrivateMessage _privateMessage;
    private PrivateMessage _privateMessageReply;

    public void setPrivateMessage(PrivateMessage privateMessage) {
        _privateMessage = privateMessage;

        _privateMessageReply = null;
        if (_session != null) {
            _privateMessageReply = PrivateMessage.generateReply(_privateMessage, _session.getProvince());
        }

        _drawData();
    }

    protected void _showLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showLoadingScreen(_activity);
            }
        });
    }
    protected void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    public ReplyPrivateMessageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.reply_private_message, container, false);
        _view = rootView;

        _view.findViewById(R.id.reply_private_message_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _back();
            }
        });

        _view.findViewById(R.id.reply_private_message_reply_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _privateMessageReply.setContent(((EditText) _view.findViewById(R.id.reply_private_message_content)).getText().toString());

                _showLoadingScreen();
                _session.sendPrivateMessage(_privateMessageReply, new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        _hideLoadingScreen();
                        Dialog.setActivity(_activity);
                        Dialog.alert("Private Message", "Reply sent!", new Runnable() {
                            @Override
                            public void run() {
                                _activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        _back();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        // Padding Hack: (Bug in Android)
        Integer[] padLeftIds = new Integer[] {
                R.id.reply_private_message_content
        };
        for (Integer viewId : padLeftIds) {
            EditText editText = ((EditText) _view.findViewById(viewId));
            editText.setPadding(40, editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
        }

        return rootView;
    }

    private void _drawData() {
        if (_view == null || _privateMessageReply == null) {
            return;
        }

        Province receivingProvince = _privateMessageReply.getReceivingProvince();
        ((TextView) _view.findViewById(R.id.reply_private_message_recipient_name)).setText(receivingProvince.getName() +" "+ receivingProvince.getKingdomIdentifier());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();

        if (_privateMessage != null) {
            _privateMessageReply = PrivateMessage.generateReply(_privateMessage, _session.getProvince());
        }
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);

        ReadPrivateMessageFragment readPrivateMessageFragment = new ReadPrivateMessageFragment();
        readPrivateMessageFragment.setPrivateMessage(_privateMessage);

        transaction.replace(R.id.container, readPrivateMessageFragment);
        transaction.commit();
    }
}