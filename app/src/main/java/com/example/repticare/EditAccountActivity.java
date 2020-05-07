package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikhaellopez.circularimageview.CircularImageView;

public class EditAccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    CircularImageView profileImage;
    final static int Gallery_Pick = 1;
    Button editProfileImage;
    EditText inputEmail;
    Button saveChangeButton;


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


        //TODO fazer upload de imagens
        profileImage = findViewById(R.id.profile_image);
        editProfileImage = findViewById(R.id.button_change_image);
        //faz o user escolher uma foto da galeria
        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Gallery_Pick);
            }
        });

        inputEmail = findViewById(R.id.change_email);
        saveChangeButton = findViewById(R.id.edit_acc_save_changes);
        saveChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditAccount();
            }
        });


    }

    private void attemptEditAccount() {
        //TODO fix method

        // Reset errors.
        inputEmail.setError(null);

        String newEmail = inputEmail.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // EMAIL CHECK
        if (TextUtils.isEmpty(newEmail)) {
            inputEmail.setError("É necessário colocar um email.");
            focusView = inputEmail;
            cancel = true;
        } else if (!isEmailValid(newEmail)) {
            inputEmail.setError("O email não é válido.");
            focusView = inputEmail;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //pedido REST SAVE EDIT ACCOUNT

        }
    }

    private boolean isEmailValid(String email) {
        boolean res = true;
        if(email.contains("@")){
            String[] aux = email.split("@");
            if(aux[1].contains(".")){
                res = true;
            }
        } else
            res = false;
        return res;
    }
}
