package com.example.jaimequeralt.popularmovies.contentProviderPackage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.jaimequeralt.popularmovies.databasePackage.DbHelper;

/**
 * Created by jaimequeralt on 10/17/15.
 */
public class MoviesProvider extends ContentProvider {
    private static final String URI = "content://com.jaimequeralt.popularmovies.contentproviders/movies";
    public static final Uri CONTENT_URI = Uri.parse(URI);
    //Data Base
    private DbHelper dbHelper;
    private static final String DB_NAME = "MPM";
    private static final int DB_VERSION = 1;
    private static final String TABLE_MOVIES = "movies";
    //URI Maatcher
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.jaimequeralt.popularmovies.contentproviders", "movies", MOVIES);
        uriMatcher.addURI("com.jaimequeralt.popularmovies.contentproviders", "movies/#", MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext(), DB_NAME, null, DB_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //if the query is with an ID
        String where = selection;
        if (uriMatcher.match(uri) == MOVIES_ID) {
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TABLE_MOVIES, projection, where, selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.jaimequeralt.movie";
            case MOVIES_ID:
                return "vnd.android.cursor.item/vnd.jaimequeralt.movie";
            default:
                return null;
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long regId = 1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        regId = db.insert(TABLE_MOVIES, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        String where = selection;
        if (uriMatcher.match(uri) == MOVIES_ID) {
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cont = db.delete(TABLE_MOVIES, where, selectionArgs);
        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int cont;

        String where = selection;
        if (uriMatcher.match(uri) == MOVIES_ID) {
            where = "_id=" + uri.getLastPathSegment();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cont = db.update(TABLE_MOVIES, values, where, selectionArgs);
        return cont;
    }

    public static final class Movies implements BaseColumns {

        //Column names
        public static final String COL_TITLE = "title";
        public static final String COL_POSTER = "poster_path";
        public static final String COL_OVERVIEW = "overview";
        public static final String COL_RELEASE = "releaseDate";
        public static final String COL_RATING = "rating";

        private Movies() {
        }


    }
}
