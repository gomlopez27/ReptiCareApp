package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repticare.R;

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            terrarium_name = itemView.findViewById(R.id.title_terrarium);

        }
    }
}
