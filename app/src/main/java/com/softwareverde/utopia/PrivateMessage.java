package com.softwareverde.utopia;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.PrivateMessageBundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrivateMessage {
    static PrivateMessage fromBundle(PrivateMessageBundle bundle, String currentUtopianDate) {
        PrivateMessage privateMessage = new PrivateMessage();

        privateMessage._title = bundle.get(PrivateMessageBundle.Keys.TITLE);

        if (bundle.hasKey(PrivateMessageBundle.Keys.CONTENT)) {
            privateMessage._content = bundle.get(PrivateMessageBundle.Keys.CONTENT);
        }

        privateMessage._id = Util.parseInt(bundle.get(PrivateMessageBundle.Keys.ID));

        if (bundle.hasKey(PrivateMessageBundle.Keys.UTOPIAN_DATE)) {
            privateMessage._updateTimestampFromUtopianDate(bundle.get(PrivateMessageBundle.Keys.UTOPIAN_DATE), currentUtopianDate);
        }

        if (bundle.hasKey(PrivateMessageBundle.Keys.REAL_DATE)) {
            privateMessage._updateTimestampFromRealDate(bundle.get(PrivateMessageBundle.Keys.REAL_DATE));
        }

        final String token = " of ";
        String sender = bundle.get(PrivateMessageBundle.Keys.SENDER);
        String provinceName = sender.substring(sender.indexOf(token) + token.length()).trim();
        Kingdom.Identifier kingdomIdentifier = new Kingdom.Identifier(
            Util.parseInt(bundle.get(PrivateMessageBundle.Keys.SENDER_PROVINCE_KINGDOM)),
            Util.parseInt(bundle.get(PrivateMessageBundle.Keys.SENDER_PROVINCE_ISLAND))
        );

        Province sendingProvince = new Province();
        sendingProvince.setKingdomIdentifier(kingdomIdentifier);
        sendingProvince.setName(provinceName);
        privateMessage._sendingProvince = sendingProvince;

        return privateMessage;
    }

    public static PrivateMessage generateReply(PrivateMessage privateMessage, Province sendingProvince) {
        PrivateMessage privateMessageReply = new PrivateMessage();

        privateMessageReply._id = privateMessage._id;
        privateMessageReply._timestamp = System.currentTimeMillis();
        privateMessageReply._title = "Re:"+ privateMessage._title;
        privateMessageReply._sendingProvince = sendingProvince;
        privateMessageReply._receivingProvince = privateMessage._sendingProvince;
        privateMessageReply._isReply = true;

        return privateMessageReply;
    }

    private Integer _id;
    private Province _sendingProvince;
    private Province _receivingProvince;
    private String _title;
    private String _content;
    private Long _timestamp;
    private Boolean _isReply = false;

    private void _updateTimestampFromRealDate(String realDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM 'at' HH:mm z");
            Date date = dateFormat.parse(realDate);
            Calendar calendar = Calendar.getInstance();
            Integer currentYear = calendar.get(Calendar.YEAR);
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, currentYear);
            _timestamp = calendar.getTime().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void _updateTimestampFromUtopianDate(String sentUtopianDate, String currentUtopianDate) {
        try {
            final Long now = System.currentTimeMillis();
            Long messageTimestamp = UtopiaUtil.utopianTicksToTimestamp(UtopiaUtil.countTicksByDate(sentUtopianDate)) * 1000L;
            Long currentUtopianDateTimestamp = UtopiaUtil.utopianTicksToTimestamp(UtopiaUtil.countTicksByDate(currentUtopianDate)) * 1000L;
            _timestamp = Util.truncateMinutes(now + messageTimestamp - currentUtopianDateTimestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(PrivateMessageBundle bundle, String currentUtopianDate) {
        _content = bundle.get(PrivateMessageBundle.Keys.CONTENT);

        if (bundle.hasKey(PrivateMessageBundle.Keys.UTOPIAN_DATE)) {
            _updateTimestampFromUtopianDate(bundle.get(PrivateMessageBundle.Keys.UTOPIAN_DATE), currentUtopianDate);
        }

        if (bundle.hasKey(PrivateMessageBundle.Keys.REAL_DATE)) {
            _updateTimestampFromRealDate(bundle.get(PrivateMessageBundle.Keys.REAL_DATE));
        }
    }

    public Boolean isValid() {
        if (_isReply) {
            if (_id == null || _id == 0) {
                return false;
            }
        }

        if (_sendingProvince == null || _receivingProvince == null) {
            return false;
        }

        Kingdom.Identifier identifier = _sendingProvince.getKingdomIdentifier();
        if (identifier == null || ! identifier.isValid()) {
            return false;
        }

        identifier = _receivingProvince.getKingdomIdentifier();
        if (identifier == null || ! identifier.isValid()) {
            return false;
        }

        if (_title == null) {
            return false;
        }

        if (_content == null) {
            return false;
        }

        return true;
    }

    public Integer getMessageId() { return _id; }
    public Province getSendingProvince() { return _sendingProvince; }
    public Province getReceivingProvince() { return _receivingProvince; }
    public String getTitle() { return _title; }
    public String getContent() { return _content; }
    public Long getTimestamp() { return _timestamp; }
    public Boolean getIsReply() { return _isReply; }

    public void setSendingProvince(Province province) { _sendingProvince = province; }
    public void setReceivingProvince(Province province) { _receivingProvince = province; }
    public void setTitle(String title) { _title = title; }
    public void setContent(String content) { _content = content; }
}
