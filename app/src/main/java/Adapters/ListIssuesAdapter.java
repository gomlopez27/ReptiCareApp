package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repticare.IssueActivity;
import com.example.repticare.R;

import java.util.List;

import Items.IssueItem;

public class ListIssuesAdapter extends RecyclerView.Adapter<ListIssuesAdapter.MyViewHolder> {
    Context mContext;
    List<IssueItem> mData;

    public ListIssuesAdapter(Context mContext, List<IssueItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_my_issues, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.issue_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, IssueActivity.class);
                i.putExtra("Issue", mData.get(viewHolder.getAdapterPosition()));
                mContext.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.issue_name.setText(mData.get(position).getName());

        if(mData.get(position).getResolved()){
            holder.issue_image.setImageResource(R.drawable.ic_check_24dp);
        }
        else {
            holder.issue_image.setImageResource(R.drawable.warning_sign);
        }
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView issue_name;
        RelativeLayout issue_container;
        ImageView issue_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            issue_name = itemView.findViewById(R.id.title_issue);
            issue_container = itemView.findViewById(R.id.container_issue);
            issue_image = itemView.findViewById(R.id.image_issue);
        }
    }

}