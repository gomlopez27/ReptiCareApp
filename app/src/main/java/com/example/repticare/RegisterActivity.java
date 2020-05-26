package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button register_button;
    EditText mUsername, mEmail, mPassword, mPasswordConfirm;
    String url;
    String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar_register);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        register_button = findViewById(R.id.register_button);
        mUsername = findViewById(R.id.username_register);
        mEmail = findViewById(R.id.email_register);
        mPassword = findViewById(R.id.password_register);
        mPasswordConfirm = findViewById(R.id.password_confirm_register);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        RadioGroup radioGender = findViewById(R.id.radio_group);

        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                sex = radioButton.getText().toString().toLowerCase();
                if(sex.equalsIgnoreCase("other")){
                    sex = "o";
                }
            }
        });
    }

    private void attemptRegister() {

        // Reset errors.
        mUsername.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //PASSWORD CHECK
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("É necessario colocar uma password.");
            focusView = mPassword;
            cancel = true;
        } else if(password.length() < 4) {
            mPasswordConfirm.setError("A password tem de ter pelo menos 4 caracteres.");
            focusView = mPassword;
            cancel = true;
        }

        //PASSWORD CONFIRMATION CHECK
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPasswordConfirm.setError("É necessário confirmar a password.");
            focusView = mPasswordConfirm;
            cancel = true;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordConfirm.setError("As passwords não são iguais.");
            focusView = mPasswordConfirm;
            cancel = true;
        }

        // USERNAME CHECK
        if (TextUtils.isEmpty(username)) {
            mUsername.setError("É necessário colocar um username.");
            focusView = mUsername;
            cancel = true;
        } else if (username.length() < 4 && username.length() > 15) {
            mUsername.setError("O username tem de ter entre 4 e 15 caracteres.");
            focusView = mUsername;
            cancel = true;
        }

        // EMAIL CHECK
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("É necessário colocar um email.");
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError("O email não é válido.");
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //pedido REST REGISTER

            url = getString(R.string.server_url) + "user/register/";

            JSONObject user = new JSONObject();
            try {
                user.put("username", username);
                user.put("email", email);
                user.put("password", password);
                user.put("sex", sex);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("kk", user.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, user, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //textView.setText("Response: " + response.toString());
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            JSONObject my_error = null;
                            String errors = "";
                            if (response != null && response.data != null) {
                                try {
                                    my_error = new JSONObject(new String(response.data));
                                    Log.i("log error",my_error.toString());
                                    if(my_error.has("username")) {
                                        errors = errors + my_error.getString("username").split("\"")[1];
                                        if (my_error.has("email"))
                                            errors = errors + " \n A" + my_error.getString("email").split("\"")[1];
                                    }
                                    if (my_error.has("email"))
                                        errors = errors + my_error.getString("email").split("\"")[1];

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
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
