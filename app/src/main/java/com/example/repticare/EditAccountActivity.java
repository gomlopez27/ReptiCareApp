package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EditAccountActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    EditText inputEmail;
    Button saveChangeButton;
    ImageView profileImage;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        profileImage = findViewById(R.id.profile_image_edit);
        inputEmail = findViewById(R.id.change_email);
        saveChangeButton = findViewById(R.id.edit_acc_save_changes);

        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String sex = settings.getString("user_sex", "");
        if(sex.equalsIgnoreCase("F")){
            profileImage.setImageResource(R.drawable.female);
        }
        else if(sex.equalsIgnoreCase("M")){
            profileImage.setImageResource(R.drawable.male);
        }
        else
            profileImage.setImageResource(R.drawable.gender_neutral);


        saveChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditAccount();
                saveChangeButton.setEnabled(false);
            }
        });

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

    }

    private void attemptEditAccount() {
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
            focusView.requestFocus();
        } else {
            SharedPreferences settings = getSharedPreferences("Auth", 0);
            String current_user = settings.getString("user_logged", "");
            Integer current_user_id = settings.getInt("user_id", 0);

            String url = getString(R.string.server_url) + "user/get/" + current_user_id;


            JSONObject user = new JSONObject();
            try {
                user.put("username", current_user);
                user.put("email", newEmail);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, user, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            saveChangeButton.setEnabled(true);
                            Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            saveChangeButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
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
