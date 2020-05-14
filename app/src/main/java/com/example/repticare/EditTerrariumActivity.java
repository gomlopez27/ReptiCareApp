package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditTerrariumActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terrarium);


        String terrariumName = getIntent().getExtras().getString("terrarium_name");
        toolbar = findViewById(R.id.toolbar_edit_terrarium);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " + terrariumName);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
