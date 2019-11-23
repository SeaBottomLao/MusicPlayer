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
    private List<Music> music_show = new ArrayList<>();
    static List<Music> music_content ;
    private MusicAdapter adapter = new MusicAdapter(MainActivity.this,R.layout.music_context,music_show);
    ListView listView = (ListView)findViewById(R.id.list_view);
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
        MediaPlayer mediaPlayer = new MediaPlayer();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }//用于读权限验证
        initMusics();
        listView.setAdapter(adapter);//setAdapter写入下方词条
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//listview点击事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music_play = music_show.get(i);
                Bundle bundle = new Bundle();//多线程之间传递数据,得到bundle对象
                bundle.putInt("i",i);
                Intent intent = new Intent(MainActivity.this,Music_Play.class);
                intent.putExtras(bundle);//将数据传到另一个Music_Play活动
                startActivity(intent);
            }
        });

    }
    private long exitTime = 0;
    @Override
    //退出程序提示的类
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
    private void initMusics(){//初始化音乐数据
        for(Music music :music_content){//foreach循环
            music_show.add(music);
        }
    }
    public List<Music> getMusic(){
        //读取
        //Android使用MediaStore获取手机上的文件
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //MediaStore是Android自带的音频数据库，多媒体信息从此获取，利用ContentResolver来调用封装好的接口即可，可以进行对数据库的增删改查
        //EXTERNAL_CONTENT_URI是获取所有歌曲的信息，projection指的是从表中选择的列，此处设为空
        //selections相当于SQL语句中的where子句，代表查询条件，此处为空
        //selectionArgs代表是否含有，此处可以用null
        //order说明查询结果按照什么来排序,此处按照默认的排列顺序
        //Android系统启动时会扫描系统文件，将系统支持的音频，扫描到数据库MediaStore中,从此获取手机上的文件
        //使用Cursor和contentResolver来进行操作
        List<Music> musicList = new ArrayList<>();
        if(cursor.moveToFirst()==true){//将光标移至第一行
            for(int i = 0;i < cursor.getCount();i++){//遍历所有行
                Music music1 = new Music();
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));// 歌的时长
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 歌的大小
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)); // 专辑
                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//判断该歌曲是否符合以下条件，符合即添加到列表
                if (ismusic != 0 && duration / (500 * 60) >= 1) {
                    // 对music1对象的元素进行赋值
                    music1.setId(id);
                    music1.setTitle(title);
                    music1.setArtist(artist);
                    music1.setDuration(duration);
                    music1.setSize(size);
                    music1.setAlbum(album);
                    musicList.add(music1);
                }
                cursor.moveToNext();//将光标移动到下一行
            }
        }
        return musicList;//返回音乐列表（符合扫描条件）
    }


}
