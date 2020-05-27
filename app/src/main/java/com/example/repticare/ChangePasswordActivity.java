package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    Button confirm_button;
    EditText mCurrPassword, nNewPassword, mPasswordConfirm;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        confirm_button = findViewById(R.id.confirm_change_password_button);
        mCurrPassword = findViewById(R.id.current_password_change);
        nNewPassword = findViewById(R.id.new_password_change);
        mPasswordConfirm = findViewById(R.id.password_confirm_change);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptChangePassword();
                confirm_button.setEnabled(false);
            }
        });

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
    }

    private void attemptChangePassword() {
        mCurrPassword.setError(null);
        nNewPassword.setError(null);
        mPasswordConfirm.setError(null);

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
            focusView.requestFocus();
        } else {
            SharedPreferences settings = getSharedPreferences("Auth", 0);
            String current_user = settings.getString("user_logged", "");
            String current_email = settings.getString("user_email", "");
            Integer current_user_id = settings.getInt("user_id", 0);

            String url = getString(R.string.server_url) + "user/change/" + current_user_id;

            JSONObject passwordChange = new JSONObject();
            try {
                passwordChange.put("email", current_email);
                passwordChange.put("username", current_user);
                passwordChange.put("password", newPassword);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, passwordChange, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            confirm_button.setEnabled(true);
                            Intent intent = new Intent(ChangePasswordActivity.this, AccountActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            confirm_button.setEnabled(true);
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    addSessionCookie(params);
                    return params;
                }

            };

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    private final void addSessionCookie(Map<String, String> headers) {
        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String sessionId = settings.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

}
