package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    Boolean found;
    RecyclerView recyclerView;
    List mList;
    ListTerrariumsAdapter adapter;
    TextView nrOfTerrariums_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_terrariums);


        //recyclerView with adapter
        recyclerView = findViewById(R.id.list_my_terrariums);
        mList = new ArrayList();

        TerrariumItem item1 = new TerrariumItem("Terrarium 1");
        TerrariumItem item2 = new TerrariumItem("Terrarium 2");
        TerrariumItem item3 = new TerrariumItem("Terrarium 3");
        TerrariumItem item4 = new TerrariumItem("Terrarium 4");
        TerrariumItem item5 = new TerrariumItem("Terrarium 5");

        mList.add(item1);
        mList.add(item2);
        mList.add(item3);
        mList.add(item4);
        mList.add(item5);

        getTerrariums();
        adapter = new ListTerrariumsAdapter(ListTerrariumsActivity.this, mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

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
        final ArrayList res = new ArrayList<TerrariumItem>();

        String url = getString(R.string.SERVER_URL_ANDRE) + "terrariums/";


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mList.addAll(parseTerrariums(response));
                        adapter.notifyDataSetChanged();
                        nrOfTerrariums_tv.setText(Integer.toString(mList.size()));
                        Log.i("it", (String.valueOf(mList.size()) + " After Response"));
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


        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


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


    private ArrayList<TerrariumItem> parseTerrariums(JSONArray response){
        try {
            JSONArray items = response;
            ArrayList res = new ArrayList<TerrariumItem>();
            for (int i = 0 ; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                TerrariumItem terrariumItem = new TerrariumItem(item.getString("name"));
                res.add(terrariumItem);
            }

         return res;

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }
}
