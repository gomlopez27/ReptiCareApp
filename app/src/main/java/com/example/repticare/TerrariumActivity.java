package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Items.TerrariumItem;

public class TerrariumActivity extends AppCompatActivity {
    Button other_users_button, edit_terrarium_button;
    TextView terrarium_temperature, terrarium_humidity, terrarium_uv,terrarium_owner;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrarium);

        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");

        toolbar = findViewById(R.id.toolbar_terrarium);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(t.getName());

        terrarium_temperature = findViewById(R.id.terrarium_temperature);
        terrarium_humidity = findViewById(R.id.terrarium_humidity);
        terrarium_uv = findViewById(R.id.terrarium_uv);
        terrarium_owner = findViewById(R.id.owner_name);

        terrarium_owner.setText(t.getOwner());
        terrarium_temperature.setText(Double.toString(t.getCurrent_temp()));
        terrarium_humidity.setText(Double.toString(t.getCurrent_humidity()));
        terrarium_uv.setText(Double.toString(t.getCurrent_uv()));

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
                i.putExtra("Terrarium",t);
                startActivity(i);
            }
        });

        other_users_button = findViewById(R.id.other_users_button);
        other_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TerrariumActivity.this, EditUsersActivity.class);
                i.putExtra("Terrarium",t);
                startActivity(i);
            }
        });

    }
}
