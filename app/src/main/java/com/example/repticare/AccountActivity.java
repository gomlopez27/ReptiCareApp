package com.example.repticare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_account);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:
                        Intent intent1 = new Intent(AccountActivity.this, ListTerrariumsActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_issues_bottom_nav:
                        Intent intent2 = new Intent(AccountActivity.this, ListIssuesActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_account_bottom_nav:

                        break;

                }


                return false;
            }
        });
    }
}
