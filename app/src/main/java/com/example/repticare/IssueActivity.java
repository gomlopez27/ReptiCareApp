package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class IssueActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        String issueName = getIntent().getExtras().getString("Name");
        toolbar = findViewById(R.id.toolbar_issue);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(issueName);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
