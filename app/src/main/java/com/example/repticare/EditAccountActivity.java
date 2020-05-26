package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    CircularImageView profileImage;
    final static int Gallery_Pick = 1;
    Button editProfileImage;
    EditText inputEmail;
    Button saveChangeButton;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        toolbar = findViewById(R.id.toolbar_edit_acc);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String sex = settings.getString("user_sex", "");

        ImageView profileImage = findViewById(R.id.profile_image_edit);

        if(sex.equalsIgnoreCase("F")){
            profileImage.setImageResource(R.drawable.female);
        }
        else if(sex.equalsIgnoreCase("M")){
            profileImage.setImageResource(R.drawable.male);
        }
        else
            profileImage.setImageResource(R.drawable.gender_neutral);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
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
            SharedPreferences settings = getSharedPreferences("Auth", 0);
            String current_user = settings.getString("user_logged", "");
            Integer current_user_id = settings.getInt("user_id", 0);
            String url = getString(R.string.SERVER_URL_ANDRE) + "user/get/" + current_user_id;


            JSONObject user = new JSONObject();
            try {
                user.put("username", current_user);
                user.put("email", newEmail);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("user",user.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, user, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("AddTerra",error.getMessage());
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
