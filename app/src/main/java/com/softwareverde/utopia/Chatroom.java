package com.softwareverde.utopia;

import com.softwareverde.util.Util;
import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ChatMessageBundle;
import com.softwareverde.utopia.bundle.ChatroomBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Chatroom {
    public interface PingCallback {
        void run(Message message);
    }

    private ChatCredentials _credentials;
    private List<Message> _messages = new Vector<Message>();
    private Map<String, PingCallback> _pingCallbacks = new HashMap<String, PingCallback>();
    private String _username;
    private final Object _mutex = new Object();

    public static class Message {
        private String _message;
        private String _displayName;
        private String _id;
        private Long _timestamp;

        public Message() {
            _message = null;
            _displayName = null;
            _id = null;
            _timestamp = 0L;
        }

        public void setMessage(final String message) { _message = message; }
        public void setDisplayName(final String displayName) { _displayName = displayName; }
        public void setId(final String id) { _id = id; }
        public void setTimestamp(final Long timestamp) { _timestamp = timestamp; }

        public Message(ChatMessageBundle chatMessageBundle) {
            _id = chatMessageBundle.get(ChatMessageBundle.Keys.ID);
            _timestamp = Util.parseLong(chatMessageBundle.get(ChatMessageBundle.Keys.TIMESTAMP));
            _displayName = chatMessageBundle.get(ChatMessageBundle.Keys.DISPLAY_NAME);
            _message = chatMessageBundle.get(ChatMessageBundle.Keys.MESSAGE);
        }

        public Boolean containsUserPing(String username) {
            final Boolean messageContainsExactUsername = (_message.toLowerCase().contains("@" + username.toLowerCase()));
            final Boolean messageContainsSpacelessUsername = (_message.toLowerCase().contains("@" + username.replaceAll(" ", "").toLowerCase()));
            return (messageContainsExactUsername || messageContainsSpacelessUsername);
        }

        public String getMessage() { return _message; }
        public String getDisplayName() { return _displayName; }
        public String getId() { return _id; }
        public Long getTimestamp() { return  _timestamp; }
    }

    private Boolean _isDuplicateMessage(Message message) {
        for (Message existingMessage : _messages) {
            if (message.getId().equals(existingMessage.getId())) {
                return true;
            }
        }
        return false;
    }

    private void _executePingCallbacks(Map<String, PingCallback> callbacks, Message message) {
        for (PingCallback callback : callbacks.values()) {
            callback.run(message);
        }
    }

    private void _addMessage(final Message message) {
        if (message.getMessage().length() == 0) {
            return;
        }
        if (_isDuplicateMessage(message)) {
            return;
        }

        if (_username != null) {
            if (message.containsUserPing(_username)) {
                _executePingCallbacks(_pingCallbacks, message);
            }
        }

        _messages.add(message);
    }

    public void setCredentials(ChatCredentials credentials) {
        synchronized (_mutex) {
            _credentials = credentials;
        }
    }
    public ChatCredentials getCredentials() {
        synchronized (_mutex) {
            return _credentials;
        }
    }

    public void addMessages(ChatroomBundle chatroomBundle) {
        synchronized (_mutex) {
            if (! chatroomBundle.hasGroupKey(ChatroomBundle.Keys.MESSAGES)) {
                return;
            }

            List<Bundle> messageList = chatroomBundle.getGroup(ChatroomBundle.Keys.MESSAGES);
            for (Integer i = 0; i < messageList.size(); i++) {
                ChatMessageBundle messageBundle = (ChatMessageBundle) messageList.get(i);
                if (!messageBundle.isValid()) {
                    continue;
                }

                Message message = new Message(messageBundle);
                _addMessage(message);
            }
        }
    }
    public List<Message> getMessages() {
        synchronized (_mutex) {
            return _messages;
        }
    }
    public List<Message> getMessagesAfter(String messageId) {
        synchronized (_mutex) {
            List<Message> newMessages = new ArrayList<Message>();
            for (Integer i = 0; i < _messages.size(); i++) {
                Message message = _messages.get(_messages.size() - 1 - i);
                if (message.getId().equals(messageId)) break;

                newMessages.add(message);
            }

            return newMessages;
        }
    }

    public Boolean hasMessages() {
        synchronized (_mutex) {
            return (_messages.size() > 0);
        }
    }
    public Message getLastMessage() {
        synchronized (_mutex) {
            if (_messages.size() == 0) {
                return null;
            }

            return _messages.get(_messages.size() - 1);
        }
    }

    public void addMessage(final Message message) {
        synchronized (_mutex) {
            _addMessage(message);
        }
    }

    public void setUsername(String username) {
        synchronized (_mutex) {
            _username = username;
        }
    }

    public void addPingCallback(String uniqueIdentifier, PingCallback callback) {
        synchronized (_mutex) {
            _pingCallbacks.put(uniqueIdentifier, callback);
        }
    }
    public void removeCallback(String uniqueIdentifier) {
        synchronized (_mutex) {
            if (!_pingCallbacks.containsKey(uniqueIdentifier)) return;

            _pingCallbacks.remove(uniqueIdentifier);
        }
    }
}
