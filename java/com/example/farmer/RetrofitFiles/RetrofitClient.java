package com.example.farmer.RetrofitFiles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL="https://trefle.io/api/v1/plants/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    //Define retrofit object
    private RetrofitClient()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //create synchronized singleton
    public static synchronized RetrofitClient getInstance()
    {
        if(mInstance==null)
            mInstance=new RetrofitClient();
        return mInstance;
    }
    //Create method to get the api
    public Api getApi()
    {
        return retrofit.create(Api.class);
    }
}

