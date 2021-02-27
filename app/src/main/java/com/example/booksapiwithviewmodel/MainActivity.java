package com.example.booksapiwithviewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);

        // get ViewModel
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        // get liveData and observer for updates on the asynch book search query
        bookViewModel.getLiveData().observe(this, new Observer<BookApiResponse>() {
            @Override
            public void onChanged(BookApiResponse bookApiResponse) {
                displayBook(bookApiResponse);
            }
        });
    }

    public void searchBooks(View view) {
        // hide the keyboard
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // get the search string from the input field
        String queryString = mBookInput.getText().toString();

        // check queryString is available
        if (queryString.length() != 0) {
            //start book query, results updated with live data observer
            bookViewModel.searchBooks(queryString);

            // change textView to "loading..." until the async query finishes
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);

        } else {   // no string was entered
            mAuthorText.setText("");
            mTitleText.setText(R.string.no_search_term);
        }
    }

    private void displayBook(BookApiResponse bookApiResponse) {
        if (bookApiResponse != null) {
            List<BookItem> bookItems = bookApiResponse.getItems();
            String title = null;
            List<String> authorsList;
            String authors = "";

            // this should stop after first book, but loops in case book info is missing
            for (int i = 0; i < bookItems.size() && title == null; i++){
                BookInfo book = bookItems.get(i).getVolumeInfo();
                try {
                    title = book.getTitle();
                    authorsList = book.getAuthors();

                    // might be more than one author
                    for (int j = 0; j < authorsList.size(); j++) {
                        // add a comma before next author if it's not the first author
                        if (j != 0) {
                            authors += ", ";
                        }
                        authors = authors + authorsList.get(j);
                    }

                } catch (Exception e) { /* do nothing, check next book */ }
            }

            // finally, display book and author
            if (title != null && authors != null) {
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            } else {
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }


        } else {
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
        }
    }
}