package com.example.vocabularybook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CardFragment extends Fragment
{
    private Vocabulary vocabulary;

    public CardFragment()
    {
        vocabulary = null;
    }

    public CardFragment(Vocabulary vocabulary)
    {
        this.vocabulary = vocabulary;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textview_eng);
        if(vocabulary == null) {
            textView.setText("单词本中无此单词！");
            textView = (TextView) view.findViewById(R.id.textview_chn);
            textView.setText("");
            textView = (TextView) view.findViewById(R.id.textview_sample);
            textView.setText("");
        }
        else
        {
            textView.setText(vocabulary.getEng());
            textView = (TextView) view.findViewById(R.id.textview_chn);
            textView.setText(vocabulary.getChn());
            textView = (TextView) view.findViewById(R.id.textview_sample);
            textView.setText(vocabulary.getSample());
        }
        Button button = (Button) view.findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Background.replaceFragment(Background.mainActivity, new MainFragment());
            }
        });
        return view;
    }
}
