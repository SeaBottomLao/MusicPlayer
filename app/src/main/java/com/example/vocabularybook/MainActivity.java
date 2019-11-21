package com.example.vocabularybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import static com.example.vocabularybook.Background.*;

public class MainActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opton_normal_1:
                Intent intent =new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this, "VocabularyBook.db", null, 1);
        database = databaseOpenHelper.getWritableDatabase();
        Log.d("MainActivity", "successfully get the instance of database");
        setContentView(R.layout.activity_main);
        autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.textfield);
        refreshAdapter();
        replaceFragment(new MainFragment());
        //replaceFragment(new DatabaseUpdateFragment(1));
        Button button = (Button) findViewById(R.id.btn_search);
        if(button != null)
        {
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String eng = autoCompleteTextView.getText().toString();
                    final String SQL = "SELECT * FROM 'Database' WHERE ENG = ?";
                    Cursor cursor = database.rawQuery(SQL, new String[]{eng});
                    if(cursor.moveToFirst())
                    {
                        Vocabulary vocabulary = new Vocabulary(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        Background.replaceFragment(mainActivity, new CardFragment(vocabulary));
                    }
                    else
                        Background.replaceFragment(mainActivity, new CardFragment());
                }
            });
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.left_frame);
        if(frameLayout != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.left_frame, new VocabularyListFragment());
            transaction.commit();
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

}
