package com.example.farmer.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmer.R;
import com.example.farmer.data.Item;
import com.example.farmer.listener.ItemClickListener;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {

    List<Item> itemList;
    Context context;
    ItemClickListener itemClickListener;

    public ItemListAdapter(List<Item> itemList, Context context, ItemClickListener itemClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.listitem, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.comm_tv.setText("Commodity : "+item.getCommodity());
        holder.pric_tv.setText("Price : "+item.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item.getUniqueID(), item.getPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView comm_tv, pric_tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comm_tv = itemView.findViewById(R.id.commodity);
            pric_tv = itemView.findViewById(R.id.price);
        }
    }
}
