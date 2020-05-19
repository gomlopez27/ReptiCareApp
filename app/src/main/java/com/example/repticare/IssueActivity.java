package com.example.repticare;

import android.content.Intent;
import android.os.Bundle;
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

public class IssueActivity extends AppCompatActivity {
    Button button_resolve_issue;
    EditText text_issue_description;
    String url;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: buscar texto de descricao e fazer display
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        final String issueName = getIntent().getExtras().getString("Name");
        toolbar = findViewById(R.id.toolbar_issue);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(issueName);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_resolve_issue = findViewById(R.id.button_resolve_issue);
        button_resolve_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptResolveIssue();
            }
        });
    }

    private void attemptResolveIssue() {

        //pedido RESOLVE ISSUE

        final String issueName = getIntent().getExtras().getString("Name");

        url = getString(R.string.SERVER_URL_ANDRE) + "resolveIssue/";

        JSONObject issue = new JSONObject();
        try {
            issue.put("issue_name", issueName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("kk", issue.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, issue, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //textView.setText("Response: " + response.toString());
                        Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                        startActivity(intent);
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
