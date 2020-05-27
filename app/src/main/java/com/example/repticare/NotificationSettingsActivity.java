package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.pusher.pushnotifications.PushNotifications;

public class NotificationSettingsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        // Set notification switches to their previous state or to false, by default
        SharedPreferences notification_settings = getSharedPreferences("NOTIFICATIONS", 0);
        
        SwitchCompat issuesNotifications = findViewById(R.id.issues_notifications);
        Boolean notifyIssues = notification_settings.getBoolean("isIssueNotificationOn", false);
        issuesNotifications.setChecked(notifyIssues);

        SwitchCompat foodNotifications = findViewById(R.id.food_calendar_notifications);
        Boolean notifyFood = notification_settings.getBoolean("isFoodNotificationOn", false);
        foodNotifications.setChecked(notifyFood);


        issuesNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences notification_settings = getSharedPreferences("NOTIFICATIONS", 0);
                SharedPreferences.Editor notification_editor = notification_settings.edit();
                notification_editor.putBoolean("isIssueNotificationOn", b);
                notification_editor.commit();

                SharedPreferences settings = getSharedPreferences("Auth", 0);
                String interest = settings.getString("user_logged", "admin");
                if(b) {
                    PushNotifications.addDeviceInterest(interest);
                } else {
                    PushNotifications.removeDeviceInterest(interest);
                }
            }
        });


        foodNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences notification_settings = getSharedPreferences("NOTIFICATIONS", 0);
                SharedPreferences.Editor notification_editor = notification_settings.edit();
                notification_editor.putBoolean("isFoodNotificationOn", b);
                notification_editor.commit();

                //TODO: Food notifications
            }
        });

        toolbar = findViewById(R.id.toolbar_notifications_settings);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationSettingsActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}