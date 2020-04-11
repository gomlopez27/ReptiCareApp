package com.example.repticare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
    Button button_login;
    EditText mUsername, mPassword;
    String url;
    // Instantiate the RequestQueue.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = findViewById(R.id.login_button);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

        RequestQueue queue = Volley.newRequestQueue(this);
        url = getString(R.string.SERVER_URL);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reset errors.
                mUsername.setError(null);
                mPassword.setError(null);

                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                boolean cancel = false;


                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("É necessario colocar um username.");
                    cancel = true;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("É necessario colocar uma password.");
                    cancel = true;
                }


                if(cancel == false) {

                        if(user.getUser().equals(username)){
                            if(user.getPassword().equals(password)) {



                                cancel=true;
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                mPassword.setError("Password errada"); //private TextInputLayout textInputPwd;
                                mPassword.requestFocus();

                            }
                        }
                    }
                    if(cancel==false){
                        mPassword.setError("Password errada"); //private TextInputLayout textInputPwd;
                        mPassword.requestFocus();
                    }

                }

        });

    }
}
