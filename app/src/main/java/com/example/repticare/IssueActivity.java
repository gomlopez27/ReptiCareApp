package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Map;

import Items.IssueItem;

public class IssueActivity extends AppCompatActivity {
    Button button_resolve_issue;
    TextView text_issue_description;
    String url;
    Toolbar toolbar;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        final IssueItem issueItem = (IssueItem) getIntent().getExtras().getSerializable("Issue");
        toolbar = findViewById(R.id.toolbar_issue);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(issueItem.getName());

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_dark_green));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        text_issue_description = findViewById(R.id.text_issue_description);
        text_issue_description.setText(issueItem.getDesc());

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

        final IssueItem issueItem = (IssueItem) getIntent().getExtras().getSerializable("Issue");

        url = getString(R.string.SERVER_URL_ANDRE) + "issues/" + issueItem.getId();

        JSONObject issue = new JSONObject();
        try {
            issue.put("name", issueItem.getName());
            issue.put("resolved", true);
            issue.put("desc", issueItem.getDesc());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("kk", issue.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PATCH, url, issue, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("kk", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
}
