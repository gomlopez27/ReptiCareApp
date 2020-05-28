package com.example.repticare;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.TerrariumItem;

public class EditTerrariumActivity extends AppCompatActivity {
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    Button button_delete_terrarium, button_confirm_changes;
    EditText mName, mMinTemp, mMaxTemp, mMinHum, mMaxHum, mMinUv, mMaxUv;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terrarium);

        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");

        button_delete_terrarium = findViewById(R.id.button_delete_terrarium);
        button_confirm_changes = findViewById(R.id.button_confirm_changes);

        mName = findViewById(R.id.name_edit_terrarium);
        mMinTemp = findViewById(R.id.min_temp_edit_terrarium);
        mMaxTemp = findViewById(R.id.max_temp_edit_terrarium);
        mMinHum = findViewById(R.id.min_hum_edit_terrarium);
        mMaxHum = findViewById(R.id.max_hum_edit_terrarium);
        mMinUv = findViewById(R.id.min_uv_edit_terrarium);
        mMaxUv = findViewById(R.id.max_uv_edit_terrarium);

        mName.setText(t.getName());
        mMinTemp.setText(Double.toString(t.getMin_temp()));
        mMaxTemp.setText(Double.toString(t.getMax_temp()));
        mMinHum.setText(Double.toString(t.getMin_humidity()));
        mMaxHum.setText(Double.toString(t.getMax_humidity()));
        mMinUv.setText(Double.toString(t.getMin_uv()));
        mMaxUv.setText(Double.toString(t.getMax_uv()));


        button_delete_terrarium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTerrariumActivity.this);

                alertDialogBuilder.setTitle("Are you sure you want to delete this terrarium?");

                alertDialogBuilder
                        .setMessage("Click yes if you are sure.")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                attemptDeleteTerrarium(t);
                                button_delete_terrarium.setEnabled(false);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        button_confirm_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditTerrarium(t);
                button_confirm_changes.setEnabled(false);
            }
        });

        toolbar = findViewById(R.id.toolbar_edit_terrarium);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " + t.getName());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                intent.putExtra("Terrarium", t);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");
        Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
        intent.putExtra("Terrarium", t);
        startActivity(intent);
        finish();
    }

    private void attemptDeleteTerrarium(TerrariumItem t) {
        String url = getString(R.string.server_url) + "terrariums/update/" + t.getId();

        StringRequest  jsonObjectRequest = new StringRequest
                (Request.Method.DELETE, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        button_delete_terrarium.setEnabled(true);
                        Intent intent = new Intent(EditTerrariumActivity.this, ListTerrariumsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        button_delete_terrarium.setEnabled(true);
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

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void attemptEditTerrarium(final TerrariumItem t) {
        mName.setError(null);
        mMinTemp.setError(null);
        mMaxTemp.setError(null);
        mMinHum.setError(null);
        mMaxHum.setError(null);
        mMinUv.setError(null);
        mMaxUv.setError(null);

        String name = mName.getText().toString();
        String minTemp = mMinTemp.getText().toString();
        String maxTemp = mMaxTemp.getText().toString();
        String minHum = mMinHum.getText().toString();
        String maxHum = mMaxHum.getText().toString();
        String minUv = mMinUv.getText().toString();
        String maxUv = mMaxUv.getText().toString();

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
            double minTempF = Double.parseDouble(mMinTemp.getText().toString());

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
            double maxTempF = Double.parseDouble(mMaxTemp.getText().toString());

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
            double minHumI = Double.parseDouble(mMinHum.getText().toString());

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
            double maxHumI = Double.parseDouble(mMaxHum.getText().toString());

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
            double minUvI = Double.parseDouble(mMinUv.getText().toString());

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
            double maxUvI = Double.parseDouble(mMaxUv.getText().toString());

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
            String url = getString(R.string.server_url) + "terrariums/update/" + t.getId();

           JSONObject terrarium = new JSONObject();
           String other_users = "";
           List<String> users  = t.getOtherusers();
            for(int j = 0;  j < users.size() ; j++){
                if(j < users.size() - 1)
                    other_users+= users.get(j) + ",";
                else
                    other_users+= users.get(j);
            }

            try {
                terrarium.put("id", t.getId());
                terrarium.put("creator_admin", t.getOwner());
                terrarium.put("name", name);
                terrarium.put("min_temp", minTemp);
                terrarium.put("max_temp", maxTemp);
                terrarium.put("min_humidity", minHum);
                terrarium.put("max_humidity", maxHum);
                terrarium.put("min_uv", minUv);
                terrarium.put("max_uv", maxUv);
                terrarium.put("current_temp", t.getCurrent_temp());
                terrarium.put("current_humidity", t.getCurrent_humidity());
                terrarium.put("current_uv", t.getCurrent_uv());
                terrarium.put("other_users", other_users);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, terrarium, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            button_confirm_changes.setEnabled(true);
                            Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                            intent.putExtra("Terrarium", t);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            button_confirm_changes.setEnabled(true);
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