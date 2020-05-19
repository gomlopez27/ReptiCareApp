package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TerrariumActivity extends AppCompatActivity {
    Button other_users_button, edit_terrarium_button;
    EditText terrarium_temperature, terrarium_humidity, terrarium_uv;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: buscar valores de temp, hum e uv ao terrario
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrarium);

        final String terrariumName = getIntent().getExtras().getString("Name");
        toolbar = findViewById(R.id.toolbar_terrarium);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(terrariumName);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TerrariumActivity.this, ListTerrariumsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit_terrarium_button = findViewById(R.id.edit_terrarium_button);
        edit_terrarium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TerrariumActivity.this, EditTerrariumActivity.class);
                i.putExtra("terrarium_name", terrariumName);
                startActivity(i);
            }
        });

        other_users_button = findViewById(R.id.other_users_button);
        other_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TerrariumActivity.this, EditUsersActivity.class);
                i.putExtra("terrarium_name", terrariumName);
                startActivity(i);
            }
        });

    }
}
