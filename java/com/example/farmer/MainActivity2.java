package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmer.RetrofitFiles.RetrofitClient2;
import com.example.farmer.exampleWeather.Main;
import com.example.farmer.exampleWeather.Weather;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private TextView maxtv, ciyttv, raintv, humitv, windtv, country_tv;
    SearchView searchView;
    String city = "kota";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        maxtv = findViewById(R.id.maxtemp);
        ciyttv = findViewById(R.id.city);
        country_tv = findViewById(R.id.country);

        raintv = findViewById(R.id.rain);
        humitv = findViewById(R.id.humidity);
        windtv = findViewById(R.id.wind);

        searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                city = query;
                getWeather(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getWeather(city);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    private void getWeather(final String city) {

        Call<Weather> call = RetrofitClient2
                .getInstance()
                .getApi2()
                .getWeather(city);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                Main main = weather.getMain();
                maxtv.setText((main.getTempMax() - 273)+"");
                country_tv.setText(weather.getSys().getCountry());
                ciyttv.setText(weather.getName());
                raintv.setText(weather.getWeather().get(0).getDescription());
                humitv.setText(main.getHumidity()+"%");
                windtv.setText(weather.getWind().getSpeed()+" Km/hr");
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
    }

    public void cropType(View view) {
        Intent intent = new Intent(MainActivity2.this,Crop_Varities.class);
        startActivity(intent);
    }

    public void dailyPrice(View view) {
        Intent intent = new Intent(MainActivity2.this,MarketPrice.class);
        startActivity(intent);
    }

    public void setProfile(View view) {
        Intent intent = new Intent(MainActivity2.this,Profile.class);
        startActivity(intent);
    }

    public void addItem(View view) {
        Intent intent = new Intent(MainActivity2.this,AddItem.class);
        startActivity(intent);
    }

    public void allData(View view) {
        Intent intent = new Intent(MainActivity2.this,Market_Price.class);
        startActivity(intent);
    }

}