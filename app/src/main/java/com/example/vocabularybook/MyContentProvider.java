package com.example.vocabularybook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import static com.example.vocabularybook.Background.database;

public class MyContentProvider extends ContentProvider
{
    private final int DIR = 0;
    private final int ITEM = 1;
    private final String AUTHORITY = "com.example.vocabularybook.provider";
    private static UriMatcher uriMatcher;
    public MyContentProvider(){

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deletedRows = 0;
        deletedRows = database.delete("Database", selection, selectionArgs);
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long newId = database.insert("Database", null, values);
        Uri returnUri = Uri.parse("content://" + AUTHORITY + "Database/" + newId);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        Cursor cursor = null;
        cursor = database.query("Database", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updateRows = 0;
        updateRows = database.update("Database", values, selection, selectionArgs);

        return updateRows;
    }
}
