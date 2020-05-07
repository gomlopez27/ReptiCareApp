package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Adapters.ListIssuesAdapter;
import Items.IssueItem;


public class ListIssuesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList mList;
    ListIssuesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_issues);

        recyclerView = findViewById(R.id.list_my_issues);
        mList = new ArrayList<IssueItem>();

        IssueItem item1 = new IssueItem("Issue 1");
        IssueItem item2 = new IssueItem("Issue 2");
        IssueItem item3 = new IssueItem("Issue 3");
        IssueItem item4 = new IssueItem("Issue 4");
        IssueItem item5 = new IssueItem("Issue 5");

        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        mList.add(item4);
        mList.add(item5);

        adapter = new ListIssuesAdapter(getApplicationContext(), mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


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
