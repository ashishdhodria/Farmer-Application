package com.example.farmer.RetrofitFiles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    private static final String BASE_URL="http://api.openweathermap.org/data/2.5/";
    private static RetrofitClient2 mInstance;
    private Retrofit retrofit;

    //Define retrofit object
    private RetrofitClient2()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //create synchronized singleton
    public static synchronized RetrofitClient2 getInstance()
    {
        if (mInstance==null)
            mInstance = new RetrofitClient2();
        return mInstance;
    }
    //Create method to get the api
    public Api2 getApi2()
    {
        return retrofit.create(Api2.class);
    }

}
