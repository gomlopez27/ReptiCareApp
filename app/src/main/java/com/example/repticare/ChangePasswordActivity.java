package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button confirm_button;
    EditText mCurrPassword, nNewPassword, mPasswordConfirm;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar = findViewById(R.id.toolbar_change_password);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        confirm_button = findViewById(R.id.confirm_change_password_button);
        mCurrPassword = findViewById(R.id.current_password_change);
        nNewPassword = findViewById(R.id.new_password_change);
        mPasswordConfirm = findViewById(R.id.password_confirm_change);


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChangePassword();
            }
        });
    }

    private void attemptChangePassword() {
        //TODO fix method

        // Reset errors.
        mCurrPassword.setError(null);
        nNewPassword.setError(null);
        mPasswordConfirm.setError(null);

        // Store values at the time of the login attempt
        String currentPassword = mCurrPassword.getText().toString();
        String newPassword = nNewPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //CURRENT PASSWORD CHECK
        if (TextUtils.isEmpty(currentPassword)) {
            mCurrPassword.setError("É necessario colocar a password atual.");
            focusView = mCurrPassword;
            cancel = true;
        } else if(false) {
            //VERIFICAR SE PASS É IGUAL à DO SERVIDOR
        }

        // NEW PASSWORD CHECK
        if (TextUtils.isEmpty(newPassword)) {
            nNewPassword.setError("É necessário colocar a nova password.");
            focusView = nNewPassword;
            cancel = true;
        } else if (newPassword.length() < 4) {
            nNewPassword.setError("A password tem de ter pelo menos 4 caracteres.");
            focusView = nNewPassword;
            cancel = true;
        }

        //PASSWORD CONFIRMATION CHECK
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPasswordConfirm.setError("É necessário confirmar a password.");
            focusView = mPasswordConfirm;
            cancel = true;
        } else if (!newPassword.equals(passwordConfirm)) {
            mPasswordConfirm.setError("As passwords não são iguais.");
            focusView = mPasswordConfirm;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //pedido REST CHANGE PASSWORD

            url = getString(R.string.SERVER_URL_ANDRE) + "user/register/";

            JSONObject passwordChange = new JSONObject();
            try {
                passwordChange.put("current_password", currentPassword);
                passwordChange.put("new_password", newPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("kk", passwordChange.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, passwordChange, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //textView.setText("Response: " + response.toString());
                            Intent intent = new Intent(ChangePasswordActivity.this, AccountActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("kk",error.toString());
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }
}
