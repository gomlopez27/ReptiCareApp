package com.example.repticare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ListTerrariumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_terrariums);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_terrarium);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:

                        break;

                    case R.id.ic_issues_bottom_nav:
                        Intent intent1 = new Intent(ListTerrariumsActivity.this, ListIssuesActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_account_bottom_nav:
                        Intent intent2 = new Intent(ListTerrariumsActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        break;

                }


                return false;
            }
        });

    }
}
