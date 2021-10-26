package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.farmer.RecyclerView.DailyPriceAdapter;
import com.example.farmer.RetrofitFiles.RetrofitClient1;
import com.example.farmer.dailyprice.Mandi;
import com.example.farmer.dailyprice.Record;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketPrice extends AppCompatActivity {

    RecyclerView recyclerView;
    DailyPriceAdapter dailyPriceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);
        recyclerView = findViewById(R.id.recycler);

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                dailyPriceAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getData() {
        Call<Mandi> call = RetrofitClient1
                .getInstance()
                .getApi1()
                .getMandi();
        call.enqueue(new Callback<Mandi>() {
            @Override
            public void onResponse(Call<Mandi> call, Response<Mandi> response) {
                Mandi mandi = response.body();
                List<Record> recordList = mandi.getRecords();
                dailyPriceAdapter = new DailyPriceAdapter(recordList, MarketPrice.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MarketPrice.this));
                recyclerView.setAdapter(dailyPriceAdapter);
                //Toast.makeText(MarketPrice.this, ""+mandi.getRecords().size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Mandi> call, Throwable t) {

            }
        });
    }

}