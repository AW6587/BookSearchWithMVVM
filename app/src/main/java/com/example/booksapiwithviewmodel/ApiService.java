package com.example.booksapiwithviewmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// creates book query parameters
public interface ApiService {
    @GET ("volumes")
    Call<BookApiResponse> getBookList (
            @Query("q") String bookName,
            @Query("maxResults") String maxResults,
            @Query("printType") String printType);
}