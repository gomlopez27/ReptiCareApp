package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static int TIME = 1000;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences settings = getSharedPreferences("Auth", 0);
                SharedPreferences.Editor editor = settings.edit();
                String logged = settings.getString("user_logged", "");


                if(logged.equalsIgnoreCase("")){
                    editor.clear();
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //Intent intent = new Intent(getApplicationContext(), ListTerrariumsActivity.class);

                    startActivity(intent);
                    finish();
                } else {

                    String url = getString(R.string.server_url) + "user/";

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){

                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getApplicationContext(), ListTerrariumsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            addSessionCookie(params);
                            return params;
                        }
                    };

                    MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
                }

            }
        }, TIME);


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
