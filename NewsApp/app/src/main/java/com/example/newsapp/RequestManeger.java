package com.example.newsapp;

import android.content.Context;
import android.widget.Toast;

import com.example.newsapp.Models.NewsApiRespone;
import com.example.newsapp.Models.NewsHeadLine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManeger {
    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManeger(Context context) {

        this.context = context;

    }


    public interface CallApi {
        @GET("top-headlines")
        Call<NewsApiRespone> callHeadLine(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }

    public void getNewsHeadLines(OnPetchDataListener listener, String category, String query) {
        CallApi callApi = retrofit.create(CallApi.class);
        Call<NewsApiRespone> call = callApi.callHeadLine("us", category, query, "bb21ffb86ee643fd8d0f218045eceedd");

        try {
            call.enqueue(new Callback<NewsApiRespone>() {
                @Override
                public void onResponse(Call<NewsApiRespone> call, Response<NewsApiRespone> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    }
                    listener.OnPetchData(response.body().getArticles(), response.message());

                }

                @Override
                public void onFailure(Call<NewsApiRespone> call, Throwable t) {
                    listener.Error("Call Fail");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
