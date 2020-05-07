package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikhaellopez.circularimageview.CircularImageView;

public class EditAccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    CircularImageView profileImage;
    final static int Gallery_Pick = 1;
    Button editProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        toolbar = findViewById(R.id.toolbar_edit_acc);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profileImage = findViewById(R.id.profile_image);

        editProfileImage = (Button) findViewById(R.id.button_change_image);

        //faz o user escolher uma foto da galeria
        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Gallery_Pick);
            }
        });



    }
}
