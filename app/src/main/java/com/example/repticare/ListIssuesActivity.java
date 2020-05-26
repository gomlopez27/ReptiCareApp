package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapters.ListIssuesAdapter;
import Items.IssueItem;
import Items.TerrariumItem;


public class ListIssuesActivity extends AppCompatActivity {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    RecyclerView recyclerView;
    ArrayList mList;
    ListIssuesAdapter adapter;
    TextView nrOfIssues_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_issues);

        recyclerView = findViewById(R.id.list_my_issues);
        mList = new ArrayList<IssueItem>();
        getIssues();

        adapter = new ListIssuesAdapter(ListIssuesActivity.this, mList);
        //recyclerView.addItemDecoration(new HorizontalItemsDecoration(10));
        recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        nrOfIssues_tv = findViewById(R.id.nr_of_curr_unresolved_issues);
        int listSize = mList.size();
        nrOfIssues_tv.setText(Integer.toString(0));



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav_issues);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_terrarium_bottom_nav:
                        Intent intent1 = new Intent(ListIssuesActivity.this, ListTerrariumsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        break;

                    case R.id.ic_issues_bottom_nav:
                        break;

                    case R.id.ic_account_bottom_nav:
                        Intent intent2 = new Intent(ListIssuesActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }

    private void getIssues(){
        final ArrayList res = new ArrayList<TerrariumItem>();

        String url1 = getString(R.string.server_url) + "issues/unresolved/";
        String url2 = getString(R.string.server_url) + "issues/resolved/";


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url1,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ArrayList aux = parseIssues((response));
                                nrOfIssues_tv.setText(Integer.toString(aux.size()));
                                mList.addAll(aux);
                                adapter.notifyDataSetChanged();
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



        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                (Request.Method.GET, url2,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                mList.addAll(parseIssues(response));
                                adapter.notifyDataSetChanged();
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
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest2);
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

    private ArrayList<IssueItem> parseIssues(JSONArray response){
        try {
            JSONArray items = response;
            Log.i("it",response.toString());
            ArrayList res = new ArrayList<IssueItem>();
            for (int i = 0 ; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                IssueItem issueItem = new IssueItem(item.getString("name"),item.getBoolean("resolved"),item.getString("desc"),item.getInt("id"));
                res.add(issueItem);
            }

            return res;

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }

}
