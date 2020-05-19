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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Items.TerrariumItem;

public class EditTerrariumActivity extends AppCompatActivity {
    Button button_delete_terrarium, button_confirm_changes;
    EditText mName, mMinTemp, mMaxTemp, mMinHum, mMaxHum, mMinUv, mMaxUv;
    String url;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final TerrariumItem t = (TerrariumItem) getIntent().getExtras().getSerializable("Terrarium");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terrarium);

        toolbar = findViewById(R.id.toolbar_edit_terrarium);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " + t.getName());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_delete_terrarium = findViewById(R.id.button_delete_terrarium);

        button_delete_terrarium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptDeleteTerrarium();
            }
        });


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


        button_confirm_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditTerrarium();
            }
        });
    }

    private void attemptDeleteTerrarium() {

        String name = mName.getText().toString();

        //pedido REST DELETE TERRARIUM

        url = getString(R.string.SERVER_URL_GI) + "deleteTerrarium/";

        JSONObject terrarium = new JSONObject();
        try {
            terrarium.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("kk", terrarium.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, terrarium, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        SharedPreferences settings = getSharedPreferences("AUTHENTICATION", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "true");
                        editor.commit();

                        Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("kk",error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    private void attemptEditTerrarium() {
        // Reset errors.
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
            if (maxUvI < 25 || maxUvI > 85) {
                mMaxUv.setError("A radiação UV tem de estar entre 200 e 370nm.");
                focusView = mMaxUv;
                cancel = true;
            }
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //pedido REST EDIT TERRARIUM

            url = getString(R.string.SERVER_URL_GI) + "editTerrarium/";

            JSONObject terrarium = new JSONObject();
            try {
                terrarium.put("name", name);
                terrarium.put("minTemp", minTemp);
                terrarium.put("maxTemp", maxTemp);
                terrarium.put("minHum", minHum);
                terrarium.put("maxHum", maxHum);
                terrarium.put("minUv", minUv);
                terrarium.put("maxUv", maxUv);
                //user.put("otherUsers", otherUsers);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("kk", terrarium.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, terrarium, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //textView.setText("Response: " + response.toString());
                            SharedPreferences settings = getSharedPreferences("AUTHENTICATION", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("logged", "true");
                            editor.commit();

                            Intent intent = new Intent(EditTerrariumActivity.this, TerrariumActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("kk",error.toString());
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }
    }
}
