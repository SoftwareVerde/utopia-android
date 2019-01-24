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

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Forum;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

public class NewForumTopicFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;

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

    public NewForumTopicFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.forum_topic_new, container, false);
        _view = rootView;

        _view.findViewById(R.id.forum_topic_new_topic_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _back();
            }
        });

        _view.findViewById(R.id.forum_topic_new_topic_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Forum.ForumTopic forumTopic = new Forum.ForumTopic();
                forumTopic.title = ((EditText) _view.findViewById(R.id.forum_topic_new_topic_title)).getText().toString();
                forumTopic.content = ((EditText) _view.findViewById(R.id.forum_topic_new_topic_content)).getText().toString();

                _showLoadingScreen();
                _session.submitForumTopic(forumTopic, new Session.Callback() {
                    @Override
                    public void run(Session.SessionResponse response) {
                        _hideLoadingScreen();

                        if (response.getWasSuccess()) {
                            Dialog.setActivity(_activity);
                            Dialog.alert("Forum Post", "Post created!", new Runnable() {
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
                    }
                });
            }
        });

        // Padding Hack: (Bug in Android)
        Integer[] padLeftIds = new Integer[] {
            R.id.forum_topic_new_topic_title, R.id.forum_topic_new_topic_content
        };
        for (Integer viewId : padLeftIds) {
            EditText editText = ((EditText) _view.findViewById(viewId));
            editText.setPadding(40, editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
        }

        return rootView;
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
        transaction.setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);

        ForumFragment forumFragment = new ForumFragment();

        transaction.replace(R.id.container, forumFragment);
        transaction.commit();
    }
}