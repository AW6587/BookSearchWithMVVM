package com.example.booksapiwithviewmodel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://books.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService (Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
