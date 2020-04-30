package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import Adapters.ListTerrariumsAdapter;
import Items.TerrariumItem;

public class ListTerrariumsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList mList;
    ListTerrariumsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_terrariums);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_terrarium);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        //recyclerView with adapter
        recyclerView = findViewById(R.id.list_my_terrariums);
        mList = new ArrayList<TerrariumItem>();

        TerrariumItem item1 = new TerrariumItem("Terrarium 1");
        TerrariumItem item2 = new TerrariumItem("Terrarium 2");
        TerrariumItem item3 = new TerrariumItem("Terrarium 3");

        mList.add(item1);
        mList.add(item2);
        mList.add(item3);

        adapter = new ListTerrariumsAdapter(getApplicationContext(), mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));



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
