package com.example.vocabularybook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment
{
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button btn_add = (Button) view.findViewById(R.id.btn_add);
        if(btn_add != null)
        {
            btn_add.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Background.replaceFragment(Background.mainActivity, new DatabaseUpdateFragment());
                }
            });
        }
        Button btn_showList = (Button) view.findViewById(R.id.btn_showList);
        if(btn_showList != null)
        {
            btn_showList.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Background.replaceFragment(Background.mainActivity, new VocabularyListFragment());
                }
            });
        }
        return view;
    }
}
