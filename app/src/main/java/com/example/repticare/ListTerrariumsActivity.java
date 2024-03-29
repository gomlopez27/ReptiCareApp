package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pusher.pushnotifications.PushNotifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.ListTerrariumsAdapter;
import Items.TerrariumItem;

public class ListTerrariumsActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    TextView nrOfTerrariums_tv;
    RecyclerView recyclerView;
    ListTerrariumsAdapter adapter;
    List mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_terrariums);

        mList = new ArrayList();

        recyclerView = findViewById(R.id.list_my_terrariums);
        getTerrariums();
        adapter = new ListTerrariumsAdapter(ListTerrariumsActivity.this, mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        nrOfTerrariums_tv = findViewById(R.id.nr_of_curr_monitored);
        int listSize = mList.size();
        nrOfTerrariums_tv.setText(Integer.toString(listSize));

        FloatingActionButton fab = findViewById(R.id.add_terrarium_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListTerrariumsActivity.this, AddTerrariumActivity.class);
                startActivity(intent1);
            }
        });

        // Issue Notifications
        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String interest = settings.getString("user_logged", "");

        if(getIntent().getExtras() != null)
            interest = getIntent().getExtras().getString("user_logged");

        SharedPreferences notification_settings = getSharedPreferences("NOTIFICATIONS", 0);
        Boolean notify = notification_settings.getBoolean("isIssueNotificationOn", false);
        PushNotifications.start(getApplicationContext(), "ac2c54bd-7122-4618-ae8f-7d1def4df1d3");

        if(notify) {
            PushNotifications.addDeviceInterest(interest);
        } else {
            PushNotifications.removeDeviceInterest(interest);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_terrarium);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:
                        break;
                    case R.id.ic_issues_bottom_nav:
                        Intent intent1 = new Intent(ListTerrariumsActivity.this, ListIssuesActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.ic_account_bottom_nav:
                        Intent intent2 = new Intent(ListTerrariumsActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }

    private void getTerrariums() {
        String url = getString(R.string.server_url) + "terrariums/";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mList.addAll(parseTerrariums(response));
                        adapter.notifyDataSetChanged();
                        nrOfTerrariums_tv.setText(Integer.toString(mList.size()));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
    }

    private ArrayList<TerrariumItem> parseTerrariums(JSONArray response){
        try {
            JSONArray items = response;
            ArrayList res = new ArrayList<TerrariumItem>();
            for (int i = 0 ; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                JSONArray users = item.getJSONArray("other_users");
                ArrayList<String> other_users =  new ArrayList<String>();
                for( int j = 0 ; j < users.length(); j++){
                    other_users.add(users.getString(j));
                }

                TerrariumItem terrariumItem = new TerrariumItem(item.getInt("id"),
                        item.getString("creator_admin"),
                        item.getString("name"),
                        item.getDouble("min_temp"),
                        item.getDouble("max_temp"),
                        item.getDouble("min_humidity"),
                        item.getDouble("max_humidity"),
                        item.getDouble("min_uv"),
                        item.getDouble("max_uv"),
                        item.getDouble("current_temp"),
                        item.getDouble("current_humidity"),
                        item.getDouble("current_uv"),
                        other_users);
                res.add(terrariumItem);
            }
            return res;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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