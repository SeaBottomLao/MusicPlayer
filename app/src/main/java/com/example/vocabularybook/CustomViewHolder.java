package com.example.vocabularybook;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.vocabularybook.Background.database;

public class CustomViewHolder extends RecyclerView.ViewHolder
{
    private TextView textView;
    private Button btn_edit;
    private Button btn_delete;
    private Vocabulary vocabulary;

    public CustomViewHolder(@NonNull View itemView)
    {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
        btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
        btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setVocabulary(final Vocabulary vocabulary)
    {
        this.vocabulary = vocabulary;
        if(btn_edit != null)
        {
            btn_edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Background.replaceFragment(Background.mainActivity, new DatabaseUpdateFragment(vocabulary));
                }
            });
        }
        if(btn_delete != null)
        {
            btn_delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    final String SQL = "DELETE FROM 'Database' WHERE ID = ?";
                    Log.d("CustomViewHolder", "executing SQL: " + SQL);
                    database.execSQL(SQL, new String[]{Integer.toString(vocabulary.getId())});
                    Background.replaceFragment(Background.mainActivity, new VocabularyListFragment());
                }
            });
        }
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Background.replaceFragment(Background.mainActivity, new CardFragment(vocabulary));
            }
        });
    }
}
