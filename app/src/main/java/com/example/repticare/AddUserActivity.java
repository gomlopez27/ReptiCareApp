package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText username_et;
    Button addOtherUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        final String terrariumName = getIntent().getExtras().getString("terrarium_name");

        toolbar = findViewById(R.id.toolbar_add_other_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(terrariumName + ": Add User");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, EditUsersActivity.class);
                startActivity(intent);
            }
        });

        username_et = findViewById(R.id.username_to_add);
        addOtherUserButton = findViewById(R.id.add_user_button);
    }
}
