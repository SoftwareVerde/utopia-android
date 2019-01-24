package com.softwareverde.utopia.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softwareverde.utopia.Chatroom;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatroomMessageAdapter extends BaseAdapter {
    private Activity _activity;
    private LayoutInflater _inflater;
    private ArrayList<Chatroom.Message> _dataSet = new ArrayList<Chatroom.Message>();

    private String _lastReadMessageId = null;
    private Province _province = null;

    public ChatroomMessageAdapter(Activity activity) {
        _activity = activity;
        _inflater = LayoutInflater.from(_activity.getApplicationContext());
    }

    private void _sortDataSet() {
        Collections.sort(_dataSet, new Comparator<Chatroom.Message>() {
            public int compare(Chatroom.Message lhs, Chatroom.Message rhs) {
                return rhs.getTimestamp().compareTo(lhs.getTimestamp());
            }
        });
    }

    @Override
    public int getCount() {
        return _dataSet.size();
    }

    @Override
    public Chatroom.Message getItem(int position) {
        return _dataSet.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = _inflater.inflate(R.layout.chatroom_message_item, viewGroup, false);
        }

        final Chatroom.Message message = this.getItem(position);

        final TextView displayNameView = ((TextView) view.findViewById(R.id.chatroom_message_item_display_name));
        final TextView timestampView = ((TextView) view.findViewById(R.id.chatroom_message_item_timestamp));
        final TextView messageBodyView = ((TextView) view.findViewById(R.id.chatroom_message_item_body));
        final View boundaryView = view.findViewById(R.id.chatroom_message_boundary_view);

        displayNameView.setText(message.getDisplayName());
        timestampView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getTimestamp()));
        messageBodyView.setText(message.getMessage());

        Integer boundaryColor = Color.parseColor("#000000");
        if (message.getId().equals(_lastReadMessageId)) {
            boundaryColor = Color.parseColor("#8090FF");
        }
        boundaryView.setBackgroundColor(boundaryColor);

        Integer textColor = Color.parseColor("#FFFFFF");
        Integer backgroundColor = Color.parseColor("#404040");
        if (_province != null) {
            if (message.containsUserPing(_province.getName()) || message.getDisplayName().equals(_province.getName())) {
                backgroundColor = Color.parseColor("#202040");
                textColor = Color.parseColor("#ABCDEF");
            }
        }

        displayNameView.setTextColor(textColor);
        timestampView.setTextColor(textColor);
        messageBodyView.setTextColor(textColor);
        view.setBackgroundColor(backgroundColor);

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

    public void setLastReadMessageId(final String messageId) {
        _lastReadMessageId = messageId;
    }

    public void setProvince(final Province province) {
        _province = province;
    }

    public void add(Chatroom.Message message) {
        _dataSet.add(message);

        _sortDataSet();

        this.notifyDataSetChanged();
    }

    public void addAll(List<Chatroom.Message> messageList) {
        for (Chatroom.Message message : messageList) {
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
