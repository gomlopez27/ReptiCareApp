package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTerrariumActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    Button button_add_terrarium;
    EditText mName, mMinTemp, mMaxTemp, mMinHum, mMaxHum, mMinUv, mMaxUv, mOtherUsers;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terrarium);


        button_add_terrarium = findViewById(R.id.button_add_terrarium);
        mName = findViewById(R.id.name_add_terrarium);
        mMinTemp = findViewById(R.id.min_temp_add_terrarium);
        mMaxTemp = findViewById(R.id.max_temp_add_terrarium);
        mMinHum = findViewById(R.id.min_hum_add_terrarium);
        mMaxHum = findViewById(R.id.max_hum_add_terrarium);
        mMinUv = findViewById(R.id.min_uv_add_terrarium);
        mMaxUv = findViewById(R.id.max_uv_add_terrarium);
        mOtherUsers = findViewById(R.id.add_terrarium_other_users);

        button_add_terrarium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddTerrarium();
            }
        });

        toolbar = findViewById(R.id.toolbar_add_terrarium);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListTerrariumsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void attemptAddTerrarium() {
        // Reset errors.
        mName.setError(null);
        mMinTemp.setError(null);
        mMaxTemp.setError(null);
        mMinHum.setError(null);
        mMaxHum.setError(null);
        mMinUv.setError(null);
        mMaxUv.setError(null);
        mOtherUsers.setError(null);

        String name = mName.getText().toString();
        String minTemp = mMinTemp.getText().toString();
        String maxTemp = mMaxTemp.getText().toString();
        String minHum = mMinHum.getText().toString();
        String maxHum = mMaxHum.getText().toString();
        String minUv = mMinUv.getText().toString();
        String maxUv = mMaxUv.getText().toString();
        String otherUsers = mOtherUsers.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mName.setError("É necessário colocar um nome.");
            focusView = mName;
            cancel = true;
        }

        // Check for a valid min temp, if the user entered one.
        if (TextUtils.isEmpty(minTemp)) {
            mMinTemp.setError("É necessário colocar uma temperatura mínima.");
            focusView = mMinTemp;
            cancel = true;
        }
        else {
            float minTempF = Float.parseFloat(mMinTemp.getText().toString());

            // Check for a valid min temp, if the user entered one.
            if (minTempF < 5 || minTempF > 40) {
                mMinTemp.setError("A temperatura tem de estar entre 5 e 40ºC.");
                focusView = mMinTemp;
                cancel = true;
            }
        }

        // Check for a valid max temp, if the user entered one.
        if (TextUtils.isEmpty(maxTemp)) {
            mMaxTemp.setError("É necessário colocar uma temperatura máxima.");
            focusView = mMaxTemp;
            cancel = true;
        }
        else {
            float maxTempF = Float.parseFloat(mMaxTemp.getText().toString());

            // Check for a valid max temp, if the user entered one.
            if (maxTempF < 5 || maxTempF > 40) {
                mMaxTemp.setError("A temperatura tem de estar entre 5 e 40ºC.");
                focusView = mMaxTemp;
                cancel = true;
            }
        }

        // Check for a valid min hum, if the user entered one.
        if (TextUtils.isEmpty(minHum)) {
            mMinHum.setError("É necessário colocar uma humidade mínima.");
            focusView = mMinHum;
            cancel = true;
        }
        else {
            int minHumI = Integer.parseInt(mMinHum.getText().toString());

            // Check for a valid min hum, if the user entered one.
            if (minHumI < 25 || minHumI > 85) {
                mMinHum.setError("A humidade tem de estar entre 25 e 85%.");
                focusView = mMinHum;
                cancel = true;
            }
        }

        // Check for a valid max hum, if the user entered one.
        if (TextUtils.isEmpty(maxHum)) {
            mMaxHum.setError("É necessário colocar uma humidade máxima.");
            focusView = mMaxHum;
            cancel = true;
        }
        else {
            int maxHumI = Integer.parseInt(mMaxHum.getText().toString());

            // Check for a valid max hum, if the user entered one.
            if (maxHumI < 25 || maxHumI > 85) {
                mMaxHum.setError("A humidade tem de estar entre 25 e 85%.");
                focusView = mMaxHum;
                cancel = true;
            }
        }

        // Check for a valid min uv, if the user entered one.
        if (TextUtils.isEmpty(minUv)) {
            mMinUv.setError("É necessário colocar a radiação UV mínima.");
            focusView = mMinUv;
            cancel = true;
        }
        else {
            int minUvI = Integer.parseInt(mMinUv.getText().toString());

            // Check for a valid min uv, if the user entered one.
            if (minUvI < 200 || minUvI > 370) {
                mMinUv.setError("A radiação UV tem de estar entre 200 e 370nm.");
                focusView = mMinUv;
                cancel = true;
            }
        }

        // Check for a valid max uv, if the user entered one.
        if (TextUtils.isEmpty(maxUv)) {
            mMaxUv.setError("É necessário colocar a radiação UV máxima.");
            focusView = mMaxUv;
            cancel = true;
        }
        else {
            int maxUvI = Integer.parseInt(mMaxUv.getText().toString());

            // Check for a valid max uv, if the user entered one.
            if (maxUvI < 200 || maxUvI > 370) {
                mMaxUv.setError("A radiação UV tem de estar entre 200 e 370nm.");
                focusView = mMaxUv;
                cancel = true;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            //pedido REST ADD TERRARIUM

            String url = getString(R.string.server_url) + "terrariums/register/";

            JSONObject terrarium = new JSONObject();
            try {
                terrarium.put("name", name);
                terrarium.put("min_temp", minTemp);
                terrarium.put("max_temp", maxTemp);
                terrarium.put("min_humidity", minHum);
                terrarium.put("max_humidity", maxHum);
                terrarium.put("min_uv", minUv);
                terrarium.put("max_uv", maxUv);
                terrarium.put("other_users", otherUsers);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, terrarium, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(AddTerrariumActivity.this, ListTerrariumsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            JSONObject my_error = null;
                            String errors = "";
                            if (response != null && response.data != null) {
                                try {
                                    my_error = new JSONObject(new String(response.data));
                                    errors = my_error.getString("message");
                                    Log.i("log error", my_error.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
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
