package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TerrariumActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrarium);

        String terrariumName = getIntent().getExtras().getString("Name");
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

        findViewById(R.id.edit_terrarium_button);


        //TODO: onclick do botao dos settings
        //Intent i = new Intent(TerrariumActivity.this, EditTerrariumActivity.class);
        //i.putExtra("terrarium_name", terrariumName);
    }
}
