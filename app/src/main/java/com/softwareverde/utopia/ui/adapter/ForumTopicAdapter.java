package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.Forum;
import com.softwareverde.utopia.R;

import java.util.ArrayList;
import java.util.List;

public class ForumTopicAdapter extends BaseAdapter {
    Activity _activity;
    LayoutInflater _inflater;
    ArrayList<Forum.ForumTopic> _dataSet = new ArrayList<Forum.ForumTopic>();

    public ForumTopicAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public Forum.ForumTopic getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.forum_topic_item, viewGroup, false);
        }

        final Forum.ForumTopic forumTopic = this.getItem(position);

        final TextView titleTextView = (TextView) view.findViewById(R.id.forum_topic_item_title);
        titleTextView.setText(forumTopic.title);

        final TextView postCountTextView = (TextView) view.findViewById(R.id.forum_topic_item_post_count);
        postCountTextView.setText(Util.formatNumberString(forumTopic.postCount));

        final TextView creatorTextView = (TextView) view.findViewById(R.id.forum_topic_item_creator);
        creatorTextView.setText(forumTopic.creator);

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

    public void add(Forum.ForumTopic forumTopic) {
        _dataSet.add(forumTopic);
        this.notifyDataSetChanged();
    }

    public void addAll(List<Forum.ForumTopic> forumTopicList) {
        _dataSet.addAll(forumTopicList);
        this.notifyDataSetChanged();
    }

    public void clear() {
        _dataSet.clear();
        this.notifyDataSetChanged();
    }
}
