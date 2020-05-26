package com.example.repticare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pusher.pushnotifications.PushNotifications;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    ImageView profileImage;
    TextView totalNrOfTerr, nrUnresolvedIssues, username;
    Button editAccButton, changePwdButton, notificationsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        totalNrOfTerr = findViewById(R.id.total_nr_terrariums_acc);
        nrUnresolvedIssues = findViewById(R.id.nr_unresolved_issues_acc);
        username = findViewById(R.id.username_account);
        profileImage = findViewById(R.id.profile_image);
        editAccButton  = findViewById(R.id.edit_acc_button);
        changePwdButton  = findViewById(R.id.change_pwd_button);
        notificationsButton  = findViewById(R.id.notifications_button);
        logoutButton  = findViewById(R.id.logout_button);

        updateMetrics();

        SharedPreferences settings = getSharedPreferences("Auth", 0);
        username.setText(settings.getString("user_logged","username"));

        String sex = settings.getString("user_sex", "");
        if(sex.equalsIgnoreCase("F")){
            profileImage.setImageResource(R.drawable.female);
        }
        else if(sex.equalsIgnoreCase("M")){
            profileImage.setImageResource(R.drawable.male);
        }
        else
            profileImage.setImageResource(R.drawable.gender_neutral);


        editAccButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, EditAccountActivity.class);
                startActivity(i);
            }
        });

        changePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, NotificationSettingsActivity.class);
                startActivity(i);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLogout();
            }

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_account);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:
                        Intent intent1 = new Intent(AccountActivity.this, ListTerrariumsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ic_issues_bottom_nav:
                        Intent intent2 = new Intent(AccountActivity.this, ListIssuesActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ic_account_bottom_nav:
                        break;
                }
                return false;
            }
        });
    }

    private void confirmLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Are you sure you want to logout?");

        alertDialogBuilder
                .setMessage("Click yes if you are sure.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        attemptLogout();
                    }


                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void attemptLogout() {

        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String interest = settings.getString("user_logged", "");

        PushNotifications.removeDeviceInterest(interest);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void updateMetrics(){
      String  url = getString(R.string.server_url) + "terrariums/";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                totalNrOfTerr.setText(Integer.toString(response.length()));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("resp", error.toString());
                            }
                        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                addSessionCookie(params);
                return params;
            }

        };

        String  url2 = getString(R.string.server_url) + "issues/unresolved/";
        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                (Request.Method.GET, url2,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                nrUnresolvedIssues.setText(Integer.toString(response.length()));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("resp", error.toString());
                            }
                        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                addSessionCookie(params);
                return params;
            }

        };

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest2);
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
