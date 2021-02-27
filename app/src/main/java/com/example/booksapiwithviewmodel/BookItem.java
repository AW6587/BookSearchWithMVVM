package com.example.booksapiwithviewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookItem {

    @SerializedName("volumeInfo")
    @Expose
    private BookInfo volumeInfo;

    public BookInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(BookInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
