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
    RecyclerView recyclerView;
    ListOtherUsersAdapter adapter;
    TerrariumItem terrarium;
    List mList;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        terrarium = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");

        recyclerView = findViewById(R.id.list_other_users);
        mList = new ArrayList();

        List<String> other_users = terrarium.getOtherusers();

        for(String user:other_users){
            if(!user.equalsIgnoreCase(terrarium.getOwner()) && !user.equalsIgnoreCase("admin"))
                mList.add(new OtherUserItem(user));
        }

        adapter = new ListOtherUsersAdapter(EditUsersActivity.this, mList, terrarium);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = findViewById(R.id.add_other_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EditUsersActivity.this, AddUserActivity.class);
                intent1.putExtra("Terrarium", terrarium);
                startActivity(intent1);
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar_edit_other_users);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(terrarium.getName() + ": Other Users");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUsersActivity.this, TerrariumActivity.class);
                intent.putExtra("Terrarium", terrarium);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditUsersActivity.this, TerrariumActivity.class);
        intent.putExtra("Terrarium", terrarium);
        setResult(RESULT_OK);
        startActivity(intent);
        finish();
    }

}