package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.help:
                Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
            case R.id.musics:
                Intent intent1 = new Intent(MainActivity.this,musics.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
        }
        music_data=getMusic();
        final MediaPlayer mediaPlayer = new MediaPlayer();
        listView = (ListView) findViewById(R.id.list_view);
        initMusic();
        adapter = new MusicAdapter(MainActivity.this, R.layout.music_l, music_show);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("position",Integer.toString(position));
                Music music_play = music_show.get(position);
                Bundle bundle =new Bundle();
                bundle.putInt("position",position);
               /* bundle.putString("title",music_play.getTitle());
                bundle.putString("artist",music_play.getArtist());
                bundle.putString("url",music_play.getUrl());
                bundle.putLong("time",music_play.getDuration());*/
                Intent intent = new Intent(MainActivity.this,Music_Play.class);
                intent.putExtras(bundle);
                //finish();
                startActivity(intent);
            }
        });
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int [] grantResults){
        //Log.d("in","first");
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.d("in","second");
                    //init();
                }else{
                    Log.d("in","third");
                    Toast.makeText(this,"已拒绝",Toast.LENGTH_SHORT).show();;
                    finish();
                }
                break;
            default:
        }

    }
    private  void initMusic(){
        for(Music music :music_data) {
            music_show.add(music);
        }
    }
    public List<Music> getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<Music> musicList = new ArrayList<>();
        // moveToFirst() 定位第一行
        if (cursor.moveToFirst()) {
            // getCount()   总数据项数
            for (int i = 0; i < cursor.getCount(); i++) {
                Music m = new Music();
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                // 歌的时长
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                // 歌的大小
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                // 歌的绝对路径
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // 专辑
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                // 如果歌曲符合指定要求，就添加到列表中去
                if (ismusic != 0 && duration / (500 * 60) >= 1) {
                    // 对m对象的元素进行赋值
                    m.setId(id);
                    m.setTitle(title);
                    m.setArtist(artist);
                    m.setDuration(duration);
                    m.setSize(size);
                    m.setUrl(url);
                    m.setAlbum(album);
                    m.setAlbum_id(album_id);
                    musicList.add(m);
                }
                //移动到下一行
                cursor.moveToNext();
            }
        }
        // 返回一个带数据的Music类的列表
        return musicList;

    }
}
