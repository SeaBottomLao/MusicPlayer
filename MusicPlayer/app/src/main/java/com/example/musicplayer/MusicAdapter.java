package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {
    private int resourceId;
     public MusicAdapter(Context context, int textViewResourceId, List<Music> objects){
         super(context,textViewResourceId);
         resourceId = textViewResourceId;
     }
     public View getView(int postion, View convertView, ViewGroup parent){
         Music music  = getItem(postion);
         View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
         ImageView imageView = (ImageView)view.findViewById(R.id.background);
         TextView artist = (TextView)view.findViewById(R.id.music_artist);
         TextView title = (TextView)view.findViewById(R.id.music_title);
         imageView.setImageResource(music.getAlbum_id());
         artist.setText(music.getArtist());
         title.setText(music.getTitle());
         return view;
     }

}
