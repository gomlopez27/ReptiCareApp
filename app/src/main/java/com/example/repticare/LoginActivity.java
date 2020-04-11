package com.example.repticare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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



        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                }

        });

    }

    private void attemptLogin() {


        // Reset errors.
        mUsername.setError(null);
        mPassword.setError(null);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("É necessario colocar uma password.");
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError("É necessario colocar um username.");
            focusView = mUsername;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        else {
          //pedido REST LOGIN

            RequestQueue queue = Volley.newRequestQueue(this);
            url = getString(R.string.SERVER_URL);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //textView.setText("Response is: "+ response.substring(0,500));

                            if(response.equals("200")){
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                mPassword.setError("Password errada"); //private TextInputLayout textInputPwd;
                                mPassword.requestFocus();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //textView.setText("That didn't work!");
                    mPassword.setError("Password errada"); //private TextInputLayout textInputPwd;
                    mPassword.requestFocus();
                }
            });
            queue.add(stringRequest);
        }






    }
}
