package com.example.vocabularybook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<CustomViewHolder>
{
    private List<Vocabulary> vocabularyList;

    public VocabularyAdapter(List<Vocabulary> vocabularyList)
    {
        this.vocabularyList = vocabularyList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocabulary, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position)
    {
        Vocabulary vocabulary = vocabularyList.get(position);
        holder.getTextView().setText(vocabulary.getEng());
        holder.setVocabulary(vocabulary);
    }

    @Override
    public int getItemCount()
    {
        return vocabularyList.size();
    }
}
