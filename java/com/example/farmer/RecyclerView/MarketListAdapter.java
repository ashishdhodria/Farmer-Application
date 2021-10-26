package com.example.farmer.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmer.AddItem;
import com.example.farmer.R;
import com.example.farmer.data.User;
import com.example.farmer.listener.ItemClickListener;

import java.util.List;

public class MarketListAdapter extends RecyclerView.Adapter<MarketListAdapter.MyViewHolder> {
    List<User> userList;
    Context context;
    ItemClickListener itemClickListener;

    public MarketListAdapter(List<User> userList, Context context, ItemClickListener itemClickListener) {
        this.userList = userList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.marketlist, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.name.setText("Name : "+user.getName());
        holder.phone.setText("Phone : "+user.getPhone());
        holder.state.setText("State : "+user.getState());
        holder.district.setText("District : "+user.getDistrict());
        holder.market.setText("Market : "+user.getMarket());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Select");
                alertDialog.setMessage("choose the option");
                alertDialog.setPositiveButton("Show", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemClickListener.onItemClick(user.getUid(), "1");
                    }
                }).setNegativeButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemClickListener.onItemClick(user.getPhone(), "2");
                    }
                });
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, state, district, market, phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            state = itemView.findViewById(R.id.state);
            district = itemView.findViewById(R.id.district);
            market = itemView.findViewById(R.id.market);
        }
    }
}
