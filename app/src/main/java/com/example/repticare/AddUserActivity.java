package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;
import java.util.Map;

import Items.TerrariumItem;

public class AddUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText username_et;
    Button addOtherUserButton;
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        final TerrariumItem terrarium = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");


        toolbar = findViewById(R.id.toolbar_add_other_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(terrarium.getName() + ": Add User");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddUserActivity.this, EditUsersActivity.class);
                intent.putExtra("Terrarium",terrarium);
                startActivity(intent);
            }
        });

        username_et = findViewById(R.id.username_to_add);
        addOtherUserButton = findViewById(R.id.add_user_button);

        addOtherUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddOtherUser(terrarium,username_et.getText().toString());
            }
        });
    }

    private void attemptAddOtherUser(final TerrariumItem t, String username){
        String url = getString(R.string.SERVER_URL_ANDRE) + "terrariums/update/" + t.getId();

        JSONObject terrarium = new JSONObject();
        String other_users = "";
        List<String> users  = t.getOtherusers();
        users.add(username);
        t.setOtherusers(users);
        for(int j = 0;  j < users.size() ; j++){
            if(j < users.size() - 1)
                other_users+= users.get(j) + ",";
            else
                other_users+= users.get(j);
        }

        try {
            terrarium.put("id",t.getId());
            terrarium.put("name", t.getName());
            terrarium.put("min_temp", t.getMin_temp());
            terrarium.put("max_temp", t.getMax_temp());
            terrarium.put("min_humidity", t.getMin_humidity());
            terrarium.put("max_humidity", t.getMax_humidity());
            terrarium.put("min_uv", t.getMin_uv());
            terrarium.put("max_uv", t.getMax_uv());
            terrarium.put("current_temp", t.getCurrent_temp());
            terrarium.put("current_humidity", t.getCurrent_humidity());
            terrarium.put("current_uv", t.getCurrent_uv());
            terrarium.put("other_users",other_users);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, terrarium, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "User Added succesfully", Toast.LENGTH_SHORT).show();
                        username_et.setText("");

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AddUser",error.getMessage());
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
