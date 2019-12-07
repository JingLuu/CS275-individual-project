package com.example.movielist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.movielist.database.MovieBaseHelper;
import com.example.movielist.database.MovieCursorWrapper;
import com.example.movielist.database.MovieDbSchema.MovieTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MovieLab {
    private static MovieLab sMovieLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MovieLab get(Context context) {
        if (sMovieLab == null){
            sMovieLab = new MovieLab(context);
        }
        return sMovieLab;
    }
    private MovieLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new MovieBaseHelper(mContext)
                .getWritableDatabase();

    }
    public void addMovie(Movie m) {
        ContentValues values = getContentValues(m);
        mDatabase.insert(MovieTable.NAME, null, values);
    }
    public void deleteMovie(Movie movie) {
        String uuidString = movie.getId().toString();
        String[] args = {uuidString};
        mDatabase.delete(MovieTable.NAME, MovieTable.Cols.UUID + "=?", args);
    }
    public void deleteAllMovie() {
        List<Movie> movies = getMovies();
        for (Movie movie : movies) {
            deleteMovie(movie);
        }
    }
    public List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();
        MovieCursorWrapper cursor = queryMovies(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                movies.add(cursor.getMovie());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return movies;
    }

    public Movie getMovie(UUID id){
        MovieCursorWrapper cursor = queryMovies(
                MovieTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getMovie();
        } finally {
            cursor.close();
        }
    }
    public void updateMovie(Movie movie) {
        String uuidString = movie.getId().toString();
        ContentValues values = getContentValues(movie);
        mDatabase.update(MovieTable.NAME, values,
                MovieTable.Cols.UUID + " =?",
                new String[]{uuidString});
    }
    private MovieCursorWrapper queryMovies(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                MovieTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new MovieCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Movie movie){
        ContentValues values = new ContentValues();
        values.put(MovieTable.Cols.UUID, movie.getId().toString());
        values.put(MovieTable.Cols.TITLE, movie.getTitle());
        values.put(MovieTable.Cols.DATE, movie.getDate().getTime());
        values.put(MovieTable.Cols.SOLVED, movie.isSolved() ? 1 : 0);
        return values;
    }

}
