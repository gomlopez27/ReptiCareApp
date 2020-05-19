package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static int TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences settings = getSharedPreferences("AUTHENTICATION", 0);
                SharedPreferences.Editor editor = settings.edit();
                String logged = settings.getString("logged", "false");


                if(logged.equalsIgnoreCase("false")){
                    editor.clear();
                    editor.commit();

                   // Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Intent intent = new Intent(getApplicationContext(), ListTerrariumsActivity.class);

                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ListTerrariumsActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, TIME);
    }
}
