package com.app.doctorappointmentsystem.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;

public class CustomNotification {
    public static void showNotification(Context c, String title, String message){
        Intent intent = new Intent(c, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "chanelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(c,CHANNEL_ID)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(R.mipmap.ic_launcher_adaptive_fore)
                .setLargeIcon(BitmapFactory. decodeResource (c.getResources() , R.drawable.doctor_blank_icon ))
                .setShowWhen(true)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =  (NotificationManager) c.getSystemService(c.NOTIFICATION_SERVICE);
        int notihigh = NotificationManager.IMPORTANCE_HIGH;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Notification", notihigh);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}
