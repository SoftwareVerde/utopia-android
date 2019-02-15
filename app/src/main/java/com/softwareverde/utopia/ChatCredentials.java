package com.softwareverde.utopia;
import com.softwareverde.json.Json;

import java.net.URLEncoder;

public class ChatCredentials {
    public static String extractCredentials(String html) {
        String beginToken = "setupReverb(";
        String endToken = ");});</script>";

        Integer begin = html.lastIndexOf(beginToken);
        if (begin < 0) return "";
        begin += beginToken.length();

        Integer end = html.indexOf(endToken, begin);
        if (end < 0) return "";

        String jsonString = html.substring(begin, end);
        // System.out.println(jsonString);

        return jsonString;
    }
    public static ChatCredentials fromJson(String jsonString) {
        Json json = Json.parse(jsonString);

        ChatCredentials chatCredentials = new ChatCredentials();
        chatCredentials._developerId = json.get("developer_id", Json.Types.STRING);
        chatCredentials._channelName = json.get("channel_name", Json.Types.STRING);
        chatCredentials._ticket = json.get("ticket", Json.Types.STRING);

        return chatCredentials;
    }

    private static String _url = "http://reverb.utopia-game.com/chatserver/";
    private static String _baseUri = "chat/widget/";
    private static String _sendMessageUri = "chat/send_message/";
    private static String _messagesUri = "channel/ticketed-";

    private String _developerId = "";
    private String _channelName = "";
    private String _ticket = "";

    private ChatCredentials() { }

    public String getUrl() {
        String url = "";
        try {
            url = ChatCredentials._url + ChatCredentials._baseUri + URLEncoder.encode(_developerId, "UTF-8") + "/" + URLEncoder.encode(_channelName, "UTF-8") + "/?ticket=" + URLEncoder.encode(_ticket, "UTF-8");
        } catch (Exception e) { }

        return url;
    }

    public String getMessagesUrl(String lastMessageId) {
        String url = "";
        try {
            url = ChatCredentials._url + ChatCredentials._messagesUri + URLEncoder.encode(_developerId, "UTF-8") + "-" + URLEncoder.encode(_channelName, "UTF-8") + "?ticket=" + URLEncoder.encode(_ticket, "UTF-8");
        } catch (Exception e) { }

        if (lastMessageId != null && lastMessageId.length() > 0) {
            try {
                url += "&last_message=" + URLEncoder.encode(lastMessageId, "UTF-8");
            } catch (Exception e) { }
        }

        return url;
    }

    public String getSendMessageUrl() {
        String url = "";
        try {
            url = ChatCredentials._url + ChatCredentials._sendMessageUri + URLEncoder.encode(_developerId, "UTF-8") + "/" + URLEncoder.encode(_channelName, "UTF-8");
        } catch (Exception e) { }

        return url;
    }

    public String getTicket() {
        return _ticket;
    }
}