package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repticare.R;

import java.util.List;

import Items.OtherUserItem;

public class ListOtherUsersAdapter extends RecyclerView.Adapter<ListOtherUsersAdapter.MyViewHolder> {
    Context mContext;
    List<OtherUserItem> mData;


    public ListOtherUsersAdapter(Context mContext, List<OtherUserItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
                             //TODO
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
        holder.issue_name.setText(mData.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView issue_name;
        RelativeLayout issue_container;
        Button delete_user_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            issue_name = itemView.findViewById(R.id.user_name);
            issue_container = itemView.findViewById(R.id.container_other_user);
            delete_user_button = itemView.findViewById(R.id.delete_user_button);
        }
    }
}
