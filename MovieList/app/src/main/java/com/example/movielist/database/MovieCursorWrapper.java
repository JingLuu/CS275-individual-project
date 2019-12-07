package com.example.movielist.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.movielist.database.MovieDbSchema.MovieTable;
import com.example.movielist.Movie;

import java.util.UUID;
import java.util.Date;

public class MovieCursorWrapper extends CursorWrapper {
    public MovieCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Movie getMovie() {
        String uuidString = getString(getColumnIndex(MovieTable.Cols.UUID));
        String title = getString(getColumnIndex(MovieTable.Cols.TITLE));
        long date = getLong(getColumnIndex(MovieTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(MovieTable.Cols.SOLVED));

        Movie movie = new Movie(UUID.fromString(uuidString));
        movie.setTitle(title);
        movie.setDate(new Date(date));
        movie.setSolved(isSolved != 0);
        return movie;

    }
}
