package com.example.farmer.RetrofitFiles;

import com.example.farmer.dailyprice.Mandi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api1 {

    @GET("9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001ad9c6cae97ba48077bc00cd7abada061&format=json&offset=0&limit=1000")
    Call<Mandi> getMandi();
}
