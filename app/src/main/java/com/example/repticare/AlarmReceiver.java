package com.example.repticare;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    SharedPreferences notificationsPrefs;
    SharedPreferences.Editor editorNotificationsPrefs;
    private static final int INTERVAL_ONE_MINUTE = 60*1000;


    @Override
    public void onReceive(Context context, Intent intent) {
        notificationsPrefs = context.getSharedPreferences("NOTIFICATIONS", 0);
        editorNotificationsPrefs = notificationsPrefs.edit();

        int notificationId = notificationsPrefs.getInt("notificationID", 0);


        String issueTitle = intent.getStringExtra("issueTitle");
        String issueDescription = intent.getStringExtra("issueDescription");

        Intent mainIntent = new Intent(context, ListIssuesActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent1 = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle(issueTitle)
                .setContentText(issueDescription)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent1)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert myNotificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            myNotificationManager.createNotificationChannel(notificationChannel);
        }

        assert myNotificationManager != null;
        myNotificationManager.notify(notificationId, builder.build());

        editorNotificationsPrefs.putInt("notificationID", ++notificationId);
        editorNotificationsPrefs.commit();

       // setupNotifications(context);
    }

    private void setupNotifications(Context context){

        int notificationId = notificationsPrefs.getInt("notificationID", 0);
        SharedPreferences.Editor editorNotificationsPrefs = notificationsPrefs.edit();

        editorNotificationsPrefs.putBoolean("hasSetup", true);

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("issueTitle", "Issue 1"); //TODO: ir buscar
        intent.putExtra("issueDescription", "bla bla bla bla");//TODO: ir buscar

        alarmIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, INTERVAL_ONE_MINUTE, alarmIntent);



    }

}
