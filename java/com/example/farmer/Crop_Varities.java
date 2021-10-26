package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.farmer.RecyclerView.CropListAdapter;
import com.example.farmer.RetrofitFiles.RetrofitClient;
import com.example.farmer.example.Crop;
import com.example.farmer.example.Datum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Crop_Varities extends AppCompatActivity {

    RecyclerView recyclerView;
    CropListAdapter cropListAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop__varities);

        recyclerView = findViewById(R.id.recycler);
        searchView = findViewById(R.id.searchView);
        getData("all");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void getData(String value) {
        Call<Crop> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCrops(value);

        call.enqueue(new Callback<Crop>() {
            @Override
            public void onResponse(Call<Crop> call, Response<Crop> response) {
                Crop crop = response.body();
                List<Datum> datumList = crop.getData();
                Toast.makeText(Crop_Varities.this, ""+datumList.size(), Toast.LENGTH_SHORT).show();
                CropListAdapter cropListAdapter = new CropListAdapter(datumList, Crop_Varities.this);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Crop_Varities.this));
                recyclerView.setAdapter(cropListAdapter);
            }

            @Override
            public void onFailure(Call<Crop> call, Throwable t) {

            }
        });
    }
}