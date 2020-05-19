package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Adapters.ListOtherUsersAdapter;
import Items.OtherUserItem;
import Items.TerrariumItem;

public class EditUsersActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List mList;
    ListOtherUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");

        toolbar = findViewById(R.id.toolbar_edit_other_users);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(t.getName() + ": Other Users");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUsersActivity.this, TerrariumActivity.class);
                startActivity(intent);
            }
        });

        //recyclerView with adapter
        recyclerView = findViewById(R.id.list_other_users);
        mList = new ArrayList();
/*
        OtherUserItem item1 = new OtherUserItem("OtherUser 1");
        OtherUserItem item2 = new OtherUserItem("OtherUser 2");
        OtherUserItem item3 = new OtherUserItem("OtherUser 3");
        OtherUserItem item4 = new OtherUserItem("OtherUser 4");
        OtherUserItem item5 = new OtherUserItem("OtherUser 5");

        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        mList.add(item4);
        mList.add(item5);
*/
        List<String> other_users = t.getOtherusers();

        for(String user:other_users){
            mList.add(new OtherUserItem(user));
        }


        adapter = new ListOtherUsersAdapter(EditUsersActivity.this, mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = findViewById(R.id.add_other_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EditUsersActivity.this, AddUserActivity.class);
                intent1.putExtra("Terrarium", t);
                startActivity(intent1);
            }
        });
    }
}
