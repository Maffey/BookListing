package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Maffey on 2017-07-04.
 *
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook =getItem(position);
        TextView titleView = listItemView.findViewById(R.id.book_title);
        assert currentBook != null;
        String title = currentBook.getTitle();
        titleView.setText(title);

        TextView authorView = listItemView.findViewById(R.id.book_author);
        String author = currentBook.getAuthor();
        authorView.setText(author);

        return listItemView;
    }
}
