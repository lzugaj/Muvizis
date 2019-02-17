package com.luv2code.android.muvizis.db.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.luv2code.android.muvizis.db.helpers.MovieDatabaseHelper;

public class MovieContentProvider extends ContentProvider {

    private MovieDatabaseHelper database;
    private static String AUTHORITY = "com.luv2code.android.muvizis.db.providers";
    private static String PATH = "movies";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    private static final int MOVIES = 10;
    private static final int MOVIE_ID = 20;
    private static final UriMatcher URI_MATCHER = createUriMatcher();

    private static UriMatcher createUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, MOVIES);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", MOVIE_ID);
        return uriMatcher;
    }

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/movies";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/movie";

    @Override
    public boolean onCreate() {
        database = new MovieDatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case MOVIES:
                return CONTENT_TYPE;
            case MOVIE_ID:
                return CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = database.insert(values);
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        if (URI_MATCHER.match(uri) == MOVIE_ID) {
            id = uri.getPathSegments().get(1);
        }
        return database.query(id, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = null;
        if (URI_MATCHER.match(uri) == MOVIE_ID) {
            id = uri.getPathSegments().get(1);
        }

        return database.update(id, values);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        if (URI_MATCHER.match(uri) == MOVIE_ID) {
            id = uri.getPathSegments().get(1);
        }

        return database.delete(id);
    }
}
