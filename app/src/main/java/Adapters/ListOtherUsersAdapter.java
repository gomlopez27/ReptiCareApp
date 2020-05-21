package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.repticare.EditUsersActivity;
import com.example.repticare.MySingleton;
import com.example.repticare.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.OtherUserItem;

public class ListOtherUsersAdapter extends RecyclerView.Adapter<ListOtherUsersAdapter.MyViewHolder> {
    Context mContext;
    List<OtherUserItem> mData;
    String terrariumId;
    String username;
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";


    public ListOtherUsersAdapter(Context mContext, List<OtherUserItem> mData, String terrariumId) {
        this.mContext = mContext;
        this.mData = mData;
        this.terrariumId = terrariumId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_other_user, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.delete_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set title
                alertDialogBuilder.setTitle("Are you sure you want to remove this user from this terrarium?");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes if you are sure.")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                username = mData.get(viewHolder.getAdapterPosition()).getUsername();
                                removerUserFromTerrarium(username, terrariumId);
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user_name.setText(mData.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;
        RelativeLayout other_user_container;
        Button delete_user_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            other_user_container = itemView.findViewById(R.id.container_other_user);
            delete_user_button = itemView.findViewById(R.id.delete_user_button);
        }
    }

    private void removerUserFromTerrarium(String userToRemove, String terrariumId) {
        String url = mContext.getString(R.string.SERVER_URL_ANDRE) + "terrariums/removeUser"; //TODO: mudar URL para o correto
        final String terrId = terrariumId;

        JSONObject terrarium = new JSONObject();
        try {
            terrarium.put("user_to_remove", userToRemove);
            terrarium.put("terrarium_id", terrId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, terrarium, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(mContext, EditUsersActivity.class);
                        intent.putExtra("terrarium_id", terrId);
                        mContext.startActivity(intent);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("User not removed",error.getMessage());
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);

    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    private final void addSessionCookie(Map<String, String> headers) {
        SharedPreferences settings = mContext.getSharedPreferences("Auth", 0);
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
