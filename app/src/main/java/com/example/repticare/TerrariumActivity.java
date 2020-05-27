package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Items.TerrariumItem;
import Items.TerrariumReadingItem;

public class TerrariumActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    RelativeLayout box_with_other_users, box_without_other_users;
    Button other_users_button, edit_terrarium_button;
    TextView terrarium_temperature, terrarium_humidity, terrarium_uv, terrarium_owner, terrarium_owner2,
            temp_graph_number, hum_graph_number, uv_graph_number, activity_graph_number;
    GraphView tempGraph, humGraph, uvGraph, activityGraph;
    Toolbar toolbar;
    List<TerrariumReadingItem> readingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrarium);

        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");
        getReadings(t.getId());

        terrarium_temperature = findViewById(R.id.terrarium_temperature);
        terrarium_humidity = findViewById(R.id.terrarium_humidity);
        terrarium_uv = findViewById(R.id.terrarium_uv);
        terrarium_owner = findViewById(R.id.owner_name);
        terrarium_owner2 = findViewById(R.id.owner_name2);

        edit_terrarium_button = findViewById(R.id.edit_terrarium_button);
        other_users_button = findViewById(R.id.other_users_button);

        box_with_other_users = findViewById(R.id.box_with_other_users);
        box_without_other_users = findViewById(R.id.box_without_other_users);

        terrarium_owner.setText(t.getOwner());
        terrarium_owner2.setText(t.getOwner());
        terrarium_temperature.setText(Double.toString(t.getCurrent_temp()) + " ºC");
        terrarium_humidity.setText(Double.toString(t.getCurrent_humidity()) + " %");
        terrarium_uv.setText(Double.toString(t.getCurrent_uv()) + " nm");


        SharedPreferences settings = getSharedPreferences("Auth", 0);
        String current_user = settings.getString("user_logged", "");

        if(!current_user.equalsIgnoreCase(t.getOwner())){
            edit_terrarium_button.setVisibility(View.INVISIBLE);
            box_with_other_users.setVisibility(View.GONE);
            box_without_other_users.setVisibility(View.VISIBLE);
        } else {
            edit_terrarium_button.setVisibility(View.VISIBLE);
            box_with_other_users.setVisibility(View.VISIBLE);
            box_without_other_users.setVisibility(View.GONE);
        }

        edit_terrarium_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TerrariumActivity.this, EditTerrariumActivity.class);
                i.putExtra("Terrarium", t);
                startActivity(i);
            }
        });

        other_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TerrariumActivity.this, EditUsersActivity.class);
                i.putExtra("Terrarium", t);
                startActivity(i);
                finish();
            }
        });

        toolbar = findViewById(R.id.toolbar_terrarium);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(t.getName());

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TerrariumActivity.this, ListTerrariumsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TerrariumActivity.this, ListTerrariumsActivity.class);
        setResult(RESULT_OK);
        startActivity(intent);
        finish();
    }

    private void drawGraphs(GraphView graph, String attribute, TextView number_hours){
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setPadding(32);

        int allReadings;
        if(readingList == null) {
            allReadings = 0;
        } else {
            allReadings = readingList.size();
        }
        int readingIndex;

        // Get number of available readings
        int size = 24;
        if(allReadings < size) {
            size = allReadings;
            readingIndex = 0;
        } else {
            readingIndex = allReadings - 24;
        }

        number_hours.setText(Integer.toString(size));
        DataPoint[] dpa = new DataPoint[size];

        for(int i = 0; i < size; i++) {
            switch (attribute) {
                case "t":
                    dpa[i] = new DataPoint(i, readingList.get(readingIndex++).getCurrTemp());
                    break;
                case "h":
                    dpa[i] = new DataPoint(i, readingList.get(readingIndex++).getCurrHum());
                    break;
                case "u":
                    dpa[i] = new DataPoint(i, readingList.get(readingIndex++).getCurrUV());
                    break;
                case "a":
                    graph.getViewport().setMinY(0);
                    graph.getViewport().setMaxY(1.5);
                    graph.getViewport().setYAxisBoundsManual(true);
                    int act = 0;
                    if(readingList.get(readingIndex++).getActivity()) {
                        act = 1;
                    }
                    dpa[i] = new DataPoint(i, act);
                    break;
                default:
                    break;
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpa);
        graph.addSeries(series);
    }

    private void getReadings(int terrarium_id) {
        String url = getString(R.string.server_url) + "readings/get/" + terrarium_id;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if(parseTerrariumReadings(response) == null) {
                                    readingList = null;
                                } else {
                                    readingList = new LinkedList<TerrariumReadingItem>();
                                    readingList.addAll(parseTerrariumReadings(response));

                                    tempGraph = findViewById(R.id.temp_graph);
                                    humGraph = findViewById(R.id.hum_graph);
                                    uvGraph = findViewById(R.id.uv_graph);
                                    activityGraph = findViewById(R.id.activity_graph);
                                    temp_graph_number = findViewById(R.id.temp_graph_number);
                                    hum_graph_number = findViewById(R.id.hum_graph_number);
                                    uv_graph_number = findViewById(R.id.uv_graph_number);
                                    activity_graph_number = findViewById(R.id.activity_graph_number);

                                    drawGraphs(tempGraph, "t", temp_graph_number);
                                    drawGraphs(humGraph, "h", hum_graph_number);
                                    drawGraphs(uvGraph, "u", uv_graph_number);
                                    drawGraphs(activityGraph, "a", activity_graph_number);
                                }
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

    private ArrayList<TerrariumReadingItem> parseTerrariumReadings(JSONArray response){
        try {
            JSONArray items = response;
            ArrayList res = new ArrayList<TerrariumReadingItem>();
            for (int i = 0 ; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);

                TerrariumReadingItem readingItem = new TerrariumReadingItem(
                        item.getInt("id"),
                        item.getInt("terrarium_id"),
                        item.getDouble("current_temp"),
                        item.getDouble("current_humidity"),
                        item.getDouble("current_uv"),
                        item.getBoolean("activity"),
                        item.getString("created_at"));
                res.add(readingItem);
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
