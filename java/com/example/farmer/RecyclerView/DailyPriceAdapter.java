package com.example.farmer.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmer.R;
import com.example.farmer.dailyprice.Record;

import java.util.ArrayList;
import java.util.List;

public class DailyPriceAdapter extends RecyclerView.Adapter<DailyPriceAdapter.MyViewHolder> implements Filterable {

    public DailyPriceAdapter(List<Record> recordList, Context context) {
        this.recordList = recordList;
        this.context = context;

        recordListFull = new ArrayList<>(recordList);
    }

    List<Record> recordList;
    List<Record> recordListFull;
    Context context;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.dailyprice, parent, false);

        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.state.setText("State : "+record.getState());
        holder.district.setText("District : "+record.getDistrict());
        holder.market.setText("Market : "+record.getMarket());
        holder.commodity.setText("Commodity : "+record.getCommodity());
        holder.variety.setText("Variety : "+record.getVariety());
        holder.arrival_date.setText("Arrival_date : "+record.getArrivalDate());
        holder.min_price.setText("Min_price : "+record.getMinPrice());
        holder.max_price.setText("Max_price : "+record.getMaxPrice());
        holder.modal_price.setText("Modal_price : "+record.getModalPrice());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Record> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(recordListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Record item : recordListFull) {
                    if (item.getState().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recordList.clear();
            recordList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView state, district, market, commodity, variety, arrival_date, min_price, max_price, modal_price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.state);
            district = itemView.findViewById(R.id.district);
            market = itemView.findViewById(R.id.market);
            commodity = itemView.findViewById(R.id.commodity);
            variety = itemView.findViewById(R.id.variety);
            arrival_date = itemView.findViewById(R.id.arrival_date);
            min_price = itemView.findViewById(R.id.min_price);
            max_price = itemView.findViewById(R.id.max_price);
            modal_price = itemView.findViewById(R.id.modal_price);
        }
    }
}
