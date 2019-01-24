package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.R;
import com.softwareverde.utopia.news.NewsEvent;

import java.util.ArrayList;
import java.util.List;

public class NewsItemAdapter extends BaseAdapter {
    private Activity _activity;
    private LayoutInflater _inflater;
    private ArrayList<NewsEvent> _dataSet = new ArrayList<NewsEvent>();
    private Boolean _displayInReverse = false;

    public NewsItemAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public NewsEvent getItem(int position) {
        return _dataSet.get(position);
    }

    // Note: If _displayInReverse is set, position is assumed to be the position of the displayed item,
    //          not the position of the item in the dataSet.
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.news_item, viewGroup, false);
        }

        NewsEvent newsEvent;
        if (_displayInReverse) {
            newsEvent = this.getItem(_dataSet.size() - position - 1);
        }
        else {
            newsEvent = this.getItem(position);
        }

        TextView title = (TextView) view.findViewById(R.id.news_item_title);
        title.setText(newsEvent.getDate());

        TextView body = (TextView) view.findViewById(R.id.news_item_body);
        body.setText(newsEvent.getNews());

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

    public void add(NewsEvent newsEvent) {
        _dataSet.add(newsEvent);

        this.notifyDataSetChanged();
    }

    public void addAll(List<NewsEvent> newsEventList) {
        for (NewsEvent item : newsEventList) {
            _dataSet.add(item);
        }
        this.notifyDataSetChanged();
    }

    public void clear() {
        _dataSet.clear();
        this.notifyDataSetChanged();
    }

    public void setDisplayInReverse(Boolean reverse) {
        _displayInReverse = reverse;
    }
}
