package com.example.repticare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    String sex = "";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView totalNrOfTerr = findViewById(R.id.total_nr_terrariums_acc);
        TextView nrUnresolvedIssues = findViewById(R.id.nr_unresolved_issues_acc);
        TextView username = findViewById(R.id.username_account);
        TextView email = findViewById(R.id.email_account);
        ImageView profileImage = findViewById(R.id.profile_image);

        if(sex.equalsIgnoreCase("F")){
            profileImage.setImageResource(R.drawable.female);
        }
        else if(sex.equalsIgnoreCase("M")){
            profileImage.setImageResource(R.drawable.male);
        }
        else
            profileImage.setImageResource(R.drawable.gender_neutral);


        Button editAccButton  = findViewById(R.id.edit_acc_button);
        Button changePwdButton  = findViewById(R.id.change_pwd_button);
        Button notificationsButton  = findViewById(R.id.notifications_button);
        Button logoutButton  = findViewById(R.id.logout_button);

        editAccButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccountActivity.this, EditAccountActivity.class);
                i.putExtra("sex", sex);
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

        // set title
        alertDialogBuilder.setTitle("Are you sure you want to logout?");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click yes if you are sure.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        attemptLogout();
/*
                        //TODO
                        SharedPreferences settings = getSharedPreferences("AUTHENTICATION", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/

                    }


                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void attemptLogout() {
        String url = getString(R.string.SERVER_URL_ANDRE) + "logout";

        JSONObject empty = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, empty, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

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
