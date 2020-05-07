package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repticare.R;
import com.example.repticare.TerrariumActivity;

import java.util.List;

import Items.TerrariumItem;

public class ListTerrariumsAdapter extends RecyclerView.Adapter<ListTerrariumsAdapter.MyViewHolder> {
    Context mContext;
    List<TerrariumItem> mData;


    public ListTerrariumsAdapter(Context mContext, List<TerrariumItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_my_terrarriums, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.terrarium_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, TerrariumActivity.class);
                i.putExtra("Name", mData.get(viewHolder.getAdapterPosition()).getName());

                //TODO push extra info

                mContext.startActivity(i);
            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.terrarium_name.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView terrarium_name;
        RelativeLayout terrarium_container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            terrarium_name = itemView.findViewById(R.id.title_terrarium);
            terrarium_container = itemView.findViewById(R.id.container_terrarium);


        }
    }
}
