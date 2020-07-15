package com.example.newsapp.Network;

import com.example.newsapp.Model.AllNews.AllNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET("top-headlines")
    Call<AllNewsModel> allNews(@Query("country") String country,
                               @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<AllNewsModel> businessNews(@Query("country") String country,
                                    @Query("category") String category,
                                    @Query("apiKey") String apiKey);

    @GET("everything")
    Call<AllNewsModel> popularNews(@Query("q") String q,
                                   @Query("from") String from,
                                   @Query("to") String to,
                                   @Query("sortBy") String sortBy,
                                   @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<AllNewsModel> techcrunchNews(@Query("sources") String sources,
                                      @Query("apiKey") String apiKey);

}


