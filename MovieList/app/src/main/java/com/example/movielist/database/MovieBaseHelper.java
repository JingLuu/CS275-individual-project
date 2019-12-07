package com.example.movielist.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.movielist.database.MovieDbSchema.MovieTable;

public class MovieBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "movieBase.db";
    public MovieBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MovieTable.NAME+ "(" + " _id integer primary key autoincrement, "
                + MovieTable.Cols.UUID + ", " +MovieTable.Cols.TITLE + ", " +
                MovieTable.Cols.DATE + ", " +
                MovieTable.Cols.SOLVED +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
