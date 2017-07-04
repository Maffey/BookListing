package com.example.android.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyView;
    /** Progress bar that shows progress when the content is being loaded on network request. **/
    private ProgressBar bar;

    /** Adapter for the list of books */
    private BookAdapter mAdapter;

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 0;

    /** URL for earthquake data from the dataset */
    private static String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyView);
        bar = (ProgressBar) findViewById(R.id.loading_view);
        bar.setVisibility(View.INVISIBLE);

        //Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Check internet connection and handle it.
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Setting onClickListener to make search toolbar work.
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.edit_text);
                String userInput = editText.getText().toString();
                String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
                String freshUrl = baseUrl + userInput;
                BOOKS_REQUEST_URL = freshUrl.replaceAll(" ","+");

                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();
                if (loaderManager.getLoader(BOOK_LOADER_ID) != null) {
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }
                if (isConnected) {
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    bar.setVisibility(View.INVISIBLE);
                    mEmptyView.setText(R.string.no_connection);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        bar.setVisibility(View.VISIBLE);
        return new BookLoader(this, BOOKS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Handling fluid user experience by setting empty_view and loading indicator.
        bar.setVisibility(View.INVISIBLE);

        mEmptyView.setText(R.string.empty_view);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bar.setVisibility(View.VISIBLE);
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
