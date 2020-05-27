package com.example.repticare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    Button button_resolve_issue;
    TextView text_issue_description;
    Toolbar toolbar;
    IssueItem issueItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        issueItem = (IssueItem) getIntent().getExtras().getSerializable("Issue");

        button_resolve_issue = findViewById(R.id.button_resolve_issue);
        text_issue_description = findViewById(R.id.text_issue_description);
        text_issue_description.setText(issueItem.getDesc());

        button_resolve_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptResolveIssue();
                button_resolve_issue.setEnabled(false);
            }
        });

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
    }

    private void attemptResolveIssue() {
        String url = getString(R.string.server_url) + "issues/" + issueItem.getId();

        JSONObject issue = new JSONObject();
        try {
            issue.put("name", issueItem.getName());
            issue.put("resolved", true);
            issue.put("desc", issueItem.getDesc());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PATCH, url, issue, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        button_resolve_issue.setEnabled(true);
                        Intent intent = new Intent(IssueActivity.this, ListIssuesActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        button_resolve_issue.setEnabled(true);
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
