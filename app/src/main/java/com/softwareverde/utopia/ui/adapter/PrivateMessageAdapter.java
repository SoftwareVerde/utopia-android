package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.PrivateMessage;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrivateMessageAdapter extends BaseAdapter {
    Activity _activity;
    LayoutInflater _inflater;
    ArrayList<PrivateMessage> _dataSet = new ArrayList<PrivateMessage>();
    Session _session;

    public PrivateMessageAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
        _session = Session.getInstance();
    }

    private void _sortDataSet() {
        Collections.sort(_dataSet, new Comparator<PrivateMessage>() {
            public int compare(PrivateMessage lhs, PrivateMessage rhs) {
                return rhs.getTimestamp().compareTo(lhs.getTimestamp());
            }
        });
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public PrivateMessage getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.private_message_item, viewGroup, false);
        }

        PrivateMessage message = this.getItem(position);

        ((TextView) view.findViewById(R.id.private_message_item_title)).setText(message.getTitle());
        ((TextView) view.findViewById(R.id.private_message_item_sender_name)).setText(message.getSendingProvince().getName());
        ((TextView) view.findViewById(R.id.private_message_item_date)).setText(new SimpleDateFormat("h a\nyyyy-MM-dd").format(message.getTimestamp()));

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

    public void add(PrivateMessage message) {
        _dataSet.add(message);

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void addAll(List<PrivateMessage> messageList) {
        for (PrivateMessage message : messageList) {
            _dataSet.add(message);
        }

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void clear() {
        _dataSet.clear();
        this.notifyDataSetChanged();
    }
}
