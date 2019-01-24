package com.softwareverde.utopia.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat.Builder;

import com.softwareverde.utopia.Chatroom;
import com.softwareverde.utopia.NotificationMaker;
import com.softwareverde.utopia.R;

public class ProvinceTagNotificationMaker implements NotificationMaker {
    private static String GROUP_KEY = "PingNotification";

    private Context _context;

    public ProvinceTagNotificationMaker(final Context context) {
        _context = context;
    }

    @Override
    public void showNotification(Chatroom.Message message) {
        final Intent intent = new Intent(_context, MainActivity.class);
        intent.putExtra("NAVIGATE", "CHATROOM");
        final PendingIntent onClickIntent = PendingIntent.getActivity(_context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Builder builder = new Builder(_context);
        builder.setGroup(ProvinceTagNotificationMaker.GROUP_KEY);
        builder.setLargeIcon(BitmapFactory.decodeResource(_context.getResources(), R.drawable.icon));
        builder.setSmallIcon(R.drawable.icon);
        builder.setLocalOnly(true);
        builder.setLights(Color.WHITE, 1000, 250);
        builder.setContentTitle("Utopia Chat");
        builder.setContentText(message.getDisplayName());
        builder.setSubText(message.getMessage());
        builder.setContentIntent(onClickIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        int notificationId = 001;
        NotificationManager notificationManager =  (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
