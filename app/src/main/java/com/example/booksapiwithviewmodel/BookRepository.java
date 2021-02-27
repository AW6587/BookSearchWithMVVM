package com.example.booksapiwithviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// singleton class handles network data fetches
public class BookRepository {

    // Parameter that limits search results
    private static final String MAX_RESULTS = "1";
    // Parameter to filter by print type
    private static final String PRINT_TYPE = "books";

    private static BookRepository bookRepository;
    private ApiService apiService;
    private MutableLiveData<BookApiResponse> bookData;

    // singleton class instance constructor
    public static BookRepository getInstance(){
        if (bookRepository == null) {
            bookRepository = new BookRepository();
        }
        return bookRepository;
    }
    // constructor
    public BookRepository() {
        apiService = RetrofitBuilder.createService(ApiService.class);
        bookData = new MutableLiveData<>();
    }

    public LiveData<BookApiResponse> getLiveData(){
        return bookData;
    }

    public void searchBooks (String bookNameQuery){
        // asynch task
        apiService.getBookList(bookNameQuery, MAX_RESULTS, PRINT_TYPE).enqueue(new Callback<BookApiResponse>() {
            @Override
            public void onResponse(Call<BookApiResponse> call, Response<BookApiResponse> response) {
                if (response.isSuccessful()) {
                    // change liveData value
                    bookData.setValue(response.body());
                }
                else { bookData.setValue(null); }
            }

            @Override
            public void onFailure(Call<BookApiResponse> call, Throwable t) {
                // Network connection issue: set to null, will handle when parsing data
                bookData.setValue(null);
            }
        });
    }
}
