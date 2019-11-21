package com.example.vocabularybook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.vocabularybook.Background.database;

public class DatabaseUpdateFragment extends Fragment
{
    private Integer id;
    private Vocabulary vocabulary;

    public DatabaseUpdateFragment()
    {
        id = null;
        vocabulary = null;
    }

    public DatabaseUpdateFragment(Vocabulary vocabulary)
    {
        this.id = vocabulary.getId();
        this.vocabulary = vocabulary;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        Button btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Background.replaceFragment(Background.mainActivity, new MainFragment());
            }
        });
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        final EditText input_eng = (EditText) view.findViewById(R.id.input_eng);
        final EditText input_chn = (EditText) view.findViewById(R.id.input_chn);
        final EditText input_sample = (EditText) view.findViewById(R.id.input_sample);
        if(vocabulary != null)
        {
            input_eng.setText(vocabulary.getEng());
            input_chn.setText(vocabulary.getChn());
            input_sample.setText(vocabulary.getSample());
        }
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(input_eng.getText() == null || input_eng.getText().length() == 0)
                    Toast.makeText(Background.mainActivity, "英文单词未输入", Toast.LENGTH_LONG).show();
                else if(input_chn.getText() == null || input_chn.getText().length() == 0)
                    Toast.makeText(Background.mainActivity, "中文翻译未输入", Toast.LENGTH_LONG).show();
                else if(input_sample.getText() == null || input_sample.getText().length() == 0)
                    Toast.makeText(Background.mainActivity, "例句未输入", Toast.LENGTH_LONG).show();
                else
                {
                    String[] values = new String[3];
                    values[0] = input_eng.getText().toString();
                    values[1] = input_chn.getText().toString();
                    values[2] = input_sample.getText().toString();
                    if(id == null)
                    {
                        final String SQL = "INSERT INTO 'Database' (ENG, CHN,SAMPLE) VALUES(?, ?, ?)";
                        Log.d("DatabaseUpdateFragment", "Executing SQL: " + SQL);
                        Log.d("DatabaseUpdateFragment", "values: " + values[0] + " " + values[1] + " " + values[2]);
                        try
                        {
                            database.execSQL(SQL, values);
                            Background.replaceFragment(Background.mainActivity, new MainFragment());
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(Background.mainActivity, "单词已存在！", Toast.LENGTH_LONG).show();
                            Log.e("DatabaseUpdateFragment", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try
                        {
                            final String SQL = "UPDATE Database SET ENG = ?, CHN = ?, SAMPLE = ? WHERE ID = ?";
                            Log.d("DatabaseUpdateFragment", "Executing SQL: " + SQL);
                            database.execSQL(SQL, new String[]{values[0], values[1], values[2], id.toString()});
                            Background.replaceFragment(Background.mainActivity, new MainFragment());
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(Background.mainActivity, "单词已存在！", Toast.LENGTH_LONG).show();
                            Log.e("DatabaseUpdateFragment", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    Background.refreshAdapter();
                }
            }
        });
        return view;
    }
}
