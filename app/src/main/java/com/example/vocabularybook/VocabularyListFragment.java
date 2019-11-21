package com.example.vocabularybook;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.vocabularybook.Background.database;

public class VocabularyListFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final String SQL = "SELECT * FROM 'Database'";
        List<Vocabulary> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(SQL, null);
        Log.d("VocabularyListFragment", "executing SQL: " + SQL);
        Log.d("VocabularyListFragment", "cursor.getCount() = " + Integer.toString(cursor.getCount()));
        Log.d("VocabularyListFragment", Integer.toString(cursor.getColumnIndex("ID")));
        Log.d("VocabularyListFragment", Integer.toString(cursor.getColumnIndex("ENG")));
        Log.d("VocabularyListFragment", Integer.toString(cursor.getColumnIndex("CHN")));
        Log.d("VocabularyListFragment", Integer.toString(cursor.getColumnIndex("SAMPLE")));
        Log.d("VocabularyListFragment", Integer.toString(cursor.getColumnIndex("ABC")));
        if(cursor.moveToFirst())
        {
            for(int i = 0; i < cursor.getCount(); i++)
            {
                int id = cursor.getInt(0);
                String eng = cursor.getString(1);
                String chn = cursor.getString(2);
                String sample = cursor.getString(3);
                Log.d("VocabularyListFragment", "Query result: " + Integer.toString(id) + eng + chn + sample);
                list.add(new Vocabulary(id, eng, chn, sample));
                cursor.moveToNext();
            }
        }
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        if(container != null)
        {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            VocabularyAdapter vocabularyAdapter = new VocabularyAdapter(list);
            recyclerView.setAdapter(vocabularyAdapter);
        }
        Button btn_back = (Button) view.findViewById(R.id.btn_back);
        if(btn_back != null)
        {
            btn_back.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Background.replaceFragment(Background.mainActivity, new MainFragment());
                }
            });
        }
        return view;
    }
}
