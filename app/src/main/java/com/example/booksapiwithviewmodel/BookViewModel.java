package com.example.booksapiwithviewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {

    private LiveData<BookApiResponse> bookResponse;
    private BookRepository bookRepository;

    public BookViewModel(){
        bookRepository = BookRepository.getInstance();
        bookResponse = bookRepository.getLiveData();
    }

    public LiveData<BookApiResponse> getLiveData() {
        return bookResponse;
    }

    // asynchronously load book info
    public void searchBooks(String queryString){
        bookRepository.searchBooks(queryString);
    }
}
