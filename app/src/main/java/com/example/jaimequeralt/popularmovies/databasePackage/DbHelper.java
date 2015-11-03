package com.example.jaimequeralt.popularmovies.databasePackage;

/**
 * Created by jaimequeralt on 10/12/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lola on 11/07/2015.
 */
public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE movies (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "movie_id INTEGER UNIQUE," +
                "title TEXT UNIQUE, " +
                "poster_path TEXT UNIQUE, " +
                "overview TEXT UNIQUE" +
                ", releaseDate TEXT, " +
                "rating REAL," +
                "image_data BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

