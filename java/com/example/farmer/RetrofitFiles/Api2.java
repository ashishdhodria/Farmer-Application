package com.example.farmer.RetrofitFiles;

import com.example.farmer.exampleWeather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api2 {
    @GET("weather?appid=1e66f9232c913149830f5568849da495")
    Call<Weather> getWeather(@Query("q") String city);
}
