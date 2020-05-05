package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView totalNrOfTerr = findViewById(R.id.total_nr_terrariums_acc);
        TextView nrUnresolvedIssues = findViewById(R.id.nr_unresolved_issues_acc);

        Button editAccButton  = findViewById(R.id.edit_acc_button);
        Button changePwdButton  = findViewById(R.id.change_pwd_button);
        Button notificationsButton  = findViewById(R.id.notifications_button);
        Button logoutButton  = findViewById(R.id.logout_button);

        editAccButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, EditAccountActivity.class);
                startActivity(i);
            }
        });

        changePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, NotificationSettingsActivity.class);
                startActivity(i);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO: popup a confirmar se quer mesmo fazer logout
            }
        });

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
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ic_issues_bottom_nav:
                        Intent intent2 = new Intent(AccountActivity.this, ListIssuesActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ic_account_bottom_nav:
                        break;
                }
                return false;
            }
        });
    }
}
