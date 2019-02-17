package com.luv2code.android.muvizis.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "movie.db";
    private static final String TABLE_MOVIE = "movie";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ACTOR = "actor";

    private static final String CREATE_TABLE_MOVIE =
            "CREATE TABLE " + TABLE_MOVIE + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_ACTOR + " text not null );";

    private static final String DROP_TABLE_MOVIE = "DROP TABLE " + TABLE_MOVIE + ";";

    public MovieDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MOVIE);
        onCreate(db);
    }

    public long insert(ContentValues values) {
        return getWritableDatabase().insert(TABLE_MOVIE, null, values);
    }

    public Cursor query(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_MOVIE);

        if (id != null) {
            builder.appendWhere(COLUMN_ID + " = " + id);
        }

        return builder.query(
                getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    public int update(String id, ContentValues values) {
        if (id == null) {
            return getWritableDatabase().update(TABLE_MOVIE, values, null, null);
        } else {
            return getWritableDatabase().update(TABLE_MOVIE, values, "_id=?", new String[]{id});
        }
    }

    public int delete(String id) {
        if (id == null) {
            return getWritableDatabase().delete(TABLE_MOVIE, null, null);
        } else {
            return getWritableDatabase().delete(TABLE_MOVIE, "_id=?", new String[]{id});
        }
    }
}
