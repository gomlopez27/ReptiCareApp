package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class ListIssuesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList mList;
    //ListIssuesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_issues);

        recyclerView = findViewById(R.id.list_my_issues);

        //adapter = new ListIssuesAdapter(getApplicationContext(), mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_issues);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:
                        Intent intent1 = new Intent(ListIssuesActivity.this, ListTerrariumsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ic_issues_bottom_nav:
                        break;

                    case R.id.ic_account_bottom_nav:
                        Intent intent2 = new Intent(ListIssuesActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }
}
