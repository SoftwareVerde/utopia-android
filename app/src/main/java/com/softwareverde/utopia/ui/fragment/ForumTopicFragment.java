package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Forum;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.adapter.ForumTopicPostAdapter;

public class ForumTopicFragment extends Fragment {
    private Activity _activity;
    private View _view;
    private Session _session;
    private Forum.ForumTopic _forumTopic;
    private ForumTopicPostAdapter _adapter;
    private Integer _pageNumber = 0;

    public void setForumTopic(Forum.ForumTopic forumTopic) {
        _forumTopic = forumTopic;
        _pageNumber = 0;

        if (_session != null) {
            _downloadPosts();
        }
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

    public ForumTopicFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.forum_topic, container, false);
        _view = rootView;

        _showLoadingScreen();

        final ListView listView = (ListView) _view.findViewById(R.id.forum_topic_posts_listview);
        listView.setAdapter(_adapter);

        _view.findViewById(R.id.forum_topic_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _back();
            }
        });
        _view.findViewById(R.id.forum_topic_next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _next();
            }
        });
        _view.findViewById(R.id.forum_topic_reply_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showReplyFragment();
            }
        });

        return rootView;
    }

    private void _drawData() {
        if (_view == null || _forumTopic == null) {
            return;
        }

        final TextView backTextView = (TextView) _view.findViewById(R.id.forum_topic_back_button);
        if (_pageNumber > 0) {
            backTextView.setText("< BACK");
        }
        else {
            backTextView.setText("< TOPICS");
        }

        _adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();

        _adapter = new ForumTopicPostAdapter(_activity);

        if (_forumTopic != null) {
            _downloadPosts();
        }
    }

    private void _back() {
        if (_pageNumber > 0) {
            _pageNumber -= 1;
            _downloadPosts();
        }
        else {
            final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.replace(R.id.container, new ForumFragment());
            transaction.commit();
        }
    }

    private void _next() {
        _pageNumber += 1;
        _downloadPosts();
    }

    private void _showReplyFragment() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.anim.fade_out);
        final ForumTopicReplyFragment forumTopicReplyFragment = new ForumTopicReplyFragment();
        forumTopicReplyFragment.setForumTopic(_forumTopic);
        transaction.replace(R.id.container, forumTopicReplyFragment);
        transaction.commit();
    }

    private void _downloadPosts() {
        _showLoadingScreen();
        _session.downloadForumTopicPosts(_forumTopic, _pageNumber, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                if (! response.getWasSuccess()) {
                    _pageNumber -= 1;
                    if (_pageNumber < 0) { _pageNumber = 0; }

                    _activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _drawData();
                            _hideLoadingScreen();
                        }
                    });

                    return;
                }

                _forumTopic = _session.getForumTopic(_forumTopic.topicId);

                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _adapter.clear();
                        _adapter.addAll(_forumTopic.posts);
                        _drawData();
                        _hideLoadingScreen();
                    }
                });
            }
        });
    }
}