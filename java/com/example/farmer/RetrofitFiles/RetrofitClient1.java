package com.example.farmer.RetrofitFiles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient1 {
    private static final String BASE_URL="https://api.data.gov.in/resource/";
    private static RetrofitClient1 mInstance;
    private Retrofit retrofit;

    //Define retrofit object
    private RetrofitClient1()
    {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //create synchronized singleton
    public static synchronized RetrofitClient1 getInstance()
    {
        if(mInstance==null)
           mInstance = new RetrofitClient1();
        return mInstance;
    }
    //Create method to get the api
    public Api1 getApi1()
    {
        return retrofit.create(Api1.class);
    }

}
