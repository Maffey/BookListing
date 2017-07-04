package com.example.android.booklisting;

/**
 * Created by Maffey on 2017-07-04.
 */

/**
 * An {@link Book} object contains information related to a single book.
 */
public class Book {


    /**
     * Title of the book
     **/
    private String mTitle;
    /**
     * Author of the book
     **/
    private String mAuthor;

    public Book(String title, String author) {
        mTitle = title;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getAuthor() {
        return mAuthor;
    }
}
