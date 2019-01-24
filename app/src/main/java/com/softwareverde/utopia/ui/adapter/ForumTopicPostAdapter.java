package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.Forum;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumTopicPostAdapter extends BaseAdapter {
    Activity _activity;
    Session _session;
    LayoutInflater _inflater;
    ArrayList<Forum.ForumTopicPost> _dataSet = new ArrayList<Forum.ForumTopicPost>();

    public ForumTopicPostAdapter(Activity activity) {
        _activity = activity;
        _session = Session.getInstance();
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public Forum.ForumTopicPost getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.forum_topic_post_item, viewGroup, false);
        }

        final Forum.ForumTopicPost forumTopicPost = this.getItem(position);

        final TextView posterTextView = (TextView) view.findViewById(R.id.forum_topic_post_item_poster);
        posterTextView.setText(forumTopicPost.poster);

        final TextView dateTextView = (TextView) view.findViewById(R.id.forum_topic_post_item_date);
        final Long postTime = UtopiaUtil.utopianTicksToTimestamp(forumTopicPost.postTick - UtopiaUtil.countTicksByDate(_session.getCurrentUtopiaDate()));
        dateTextView.setText(new SimpleDateFormat("yyyy-MM-dd HH:00").format(new Date(postTime * 1000L)));

        final TextView contentTextView = (TextView) view.findViewById(R.id.forum_topic_post_item_content);
        contentTextView.setText(forumTopicPost.content);

        return view;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (_dataSet.size() == 0);
    }

    public void add(Forum.ForumTopicPost forumTopicPost) {
        _dataSet.add(forumTopicPost);
        this.notifyDataSetChanged();
    }

    public void addAll(List<Forum.ForumTopicPost> forumTopicPostList) {
        _dataSet.addAll(forumTopicPostList);
        this.notifyDataSetChanged();
    }

    public void clear() {
        _dataSet.clear();
        this.notifyDataSetChanged();
    }
}
