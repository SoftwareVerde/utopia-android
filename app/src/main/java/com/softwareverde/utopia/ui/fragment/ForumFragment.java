package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Forum;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.ui.adapter.ForumTopicAdapter;

import java.util.List;

public class ForumFragment extends Fragment {
    private Activity _activity;
    private Session _session;
    private View _view;
    private ForumTopicAdapter _adapter;
    private ListView _listView;

    public ForumFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void _drawData() { }

    private void _displayForumTopic(final Forum.ForumTopic forumTopic) {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final ForumTopicFragment forumTopicFragment = new ForumTopicFragment();
        forumTopicFragment.setForumTopic(forumTopic);
        transaction.replace(R.id.container, forumTopicFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    private void _displayNewTopicFragment() {
        final FragmentTransaction transaction = ((AppCompatActivity) _activity).getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        final NewForumTopicFragment newForumTopicFragment = new NewForumTopicFragment();
        transaction.replace(R.id.container, newForumTopicFragment);

        AndroidUtil.closeKeyboard(_activity);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.forum, container, false);
        _view = rootView;

        _listView = (ListView) _view.findViewById(R.id.forum_topics_listview);

        _adapter = new ForumTopicAdapter(_activity);
        _listView.setAdapter(_adapter);

        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Forum.ForumTopic forumTopic = _adapter.getItem(position);
                _displayForumTopic(forumTopic);
            }
        });

        _view.findViewById(R.id.forum_topic_new_topic_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _displayNewTopicFragment();
            }
        });

        _drawData();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();

        _session.downloadForumTopics(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                if (! response.getWasSuccess()) {
                    return;
                }

                final List<Forum.ForumTopic> forumTopics = _session.getForumTopics();
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        _adapter.clear();
                        _adapter.addAll(forumTopics);
                    }
                });
            }
        });
    }
}