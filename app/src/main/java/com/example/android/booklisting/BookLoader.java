package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Maffey on 2017-07-04.
 *
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    // Url of the network request
    private String mUrl;
    private static final String LOG_TAG = MainActivity.class.getName();

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null
        if (mUrl.length() < 1 || mUrl == null) {
            return null;
        }
        return QueryUtils.fetchBookData(mUrl);
    }
}
