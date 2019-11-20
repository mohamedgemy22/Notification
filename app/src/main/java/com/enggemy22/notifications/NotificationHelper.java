package com.enggemy22.notifications;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.enggemy22.notifications.MainActivity.Channel_ID;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body) {

        //Notification Biulder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, Channel_ID)
                        .setSmallIcon(R.drawable.ic_message_black_24dp)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        //Notification Manager

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());

    }
}
