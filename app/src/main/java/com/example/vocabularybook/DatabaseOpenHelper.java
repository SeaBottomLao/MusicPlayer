package com.example.vocabularybook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper
{

    private final String CREATE_TABLE = new String("CREATE TABLE Database ("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "ENG TEXT UNIQUE, " + "CHN TEXT, "
            + "SAMPLE TEXT )");

    public DatabaseOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Log.d("DatabaseOpenHelper", "create a new database, executing SQL: " + CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
