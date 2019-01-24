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
import android.widget.TextView;

import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.PrivateMessage;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

import java.text.SimpleDateFormat;

public class ReadPrivateMessageFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;
    private PrivateMessage _privateMessage;

    public void setPrivateMessage(PrivateMessage privateMessage) {
        _privateMessage = privateMessage;
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

    public ReadPrivateMessageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.private_message, container, false);
        _view = rootView;

        _showLoadingScreen();

        _session.downloadPrivateMessage(_privateMessage, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _drawData();
                        _hideLoadingScreen();
                    }
                });
            }
        });

        _view.findViewById(R.id.private_message_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _back();
            }
        });

        _view.findViewById(R.id.reply_private_message_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _displayReplyDialog();
            }
        });

        return rootView;
    }

    private void _drawData() {
        if (_view == null || _privateMessage == null) {
            return;
        }

        Province sendingProvince = _privateMessage.getSendingProvince();
        ((TextView) _view.findViewById(R.id.private_message_sender_name)).setText(sendingProvince.getName() +" "+ sendingProvince.getKingdomIdentifier());
        ((TextView) _view.findViewById(R.id.private_message_title)).setText(_privateMessage.getTitle());
        ((TextView) _view.findViewById(R.id.private_message_date)).setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(_privateMessage.getTimestamp()));
        ((TextView) _view.findViewById(R.id.private_message_content)).setText(_privateMessage.getContent());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
    }

    private void _back() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CommunicationFragment communicationFragment = new CommunicationFragment();
        communicationFragment.setTab(CommunicationFragment.Tab.PRIVATE_MESSAGES);

        transaction.replace(R.id.container, communicationFragment);
        transaction.commit();
    }

    private void _displayReplyDialog() {
        FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.anim.fade_out);

        ReplyPrivateMessageFragment replyPrivateMessageFragment = new ReplyPrivateMessageFragment();
        replyPrivateMessageFragment.setPrivateMessage(_privateMessage);

        transaction.replace(R.id.container, replyPrivateMessageFragment);
        transaction.commit();
    }
}