package com.example.vocabularybook;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class Background
{
    public static SQLiteDatabase database;
    public static MainActivity mainActivity;
    public static String[] arr = new String[]{""};
    public static AutoCompleteTextView autoCompleteTextView;
    public static void replaceFragment(AppCompatActivity activity, Fragment fragment)
    {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    public static void refreshAdapter()
    {
        if(autoCompleteTextView == null)
            return;
        final String SQL = "SELECT * FROM 'Database'";
        Cursor cursor = database.rawQuery(SQL, null);
        if(cursor.moveToFirst())
        {
            arr = new String[cursor.getCount()];
            for(int i = 0; i < arr.length; i++)
            {
                arr[i] = cursor.getString(1);
                cursor.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainActivity, R.layout.support_simple_spinner_dropdown_item, arr);
        autoCompleteTextView.setAdapter(adapter);
    }

}
