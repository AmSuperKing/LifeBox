package com.lifebox.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lifebox.Adapter.MyAdapter;
import com.lifebox.R;
import com.lifebox.Utils.Utils;
import com.lifebox.bean.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalMusic extends Activity {
    public static LocalMusic instance = null;
    MediaPlayer mediaPlayer;
    ListView mylist;
    List<Song> list;
    private TextView music_name;
    private SeekBar seekBar;
    private ImageButton previous;
    private ImageButton play_pause;
    private ImageButton next;
    private boolean isStartTrackingTouch;
    private Handler handler = new Handler();
    int current;
    boolean isPlay;
    boolean isPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_music);
        instance = this;
        mediaPlayer = new MediaPlayer();
        //播放器监听器
        mediaPlayer.setOnCompletionListener(new MyPlayerListener());
        music_name = (TextView) findViewById(R.id.music_name);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        previous = (ImageButton) findViewById(R.id.previous);
        play_pause = (ImageButton) findViewById(R.id.play_pause);
        next = (ImageButton) findViewById(R.id.next);

        mylist = (ListView) findViewById(R.id.mylist);
        list = new ArrayList<>();
        list = Utils.getmusic(this);
        MyAdapter myAdapter = new MyAdapter(this, list);
        mylist.setAdapter(myAdapter);

        seekBar.setOnSeekBarChangeListener(new MySeekBarListener());
        previous.setOnClickListener(new Previous());
        play_pause.setOnClickListener(new Play_Pause());
        next.setOnClickListener(new Next());
        // 给ListView添加点击事件，实现点击哪首音乐就进行播放
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p = list.get(i).path;//获得歌曲的地址
                current = i;
                music_name.setText(list.get(current).song);
                play(p);
            }
        });

        //意图过滤器
        IntentFilter filter = new IntentFilter();
        //播出电话暂停音乐播放
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(new PhoneListener(), filter);
        //创建一个电话服务
        TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //监听电话状态，接电话时停止播放
        manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);

    }

    /**
     * 收到广播时暂停
     */
    private final class PhoneListener extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            pause();
            play_pause.setImageDrawable(getResources().getDrawable(R.drawable.stop));
        }
    }

    /**
     * 监听电话状态
     */
    private final class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            play_pause.setImageDrawable(getResources().getDrawable(R.drawable.stop));
            pause();
        }
    }

    /**
     * 播放音乐
     * @param path
     */
    public void play(String path) {
        try {
            // 重置音频文件，防止多次点击会报错
            mediaPlayer.reset();
//        调用方法传进播放地址
            mediaPlayer.setDataSource(path);
//            异步准备资源，防止卡顿
            mediaPlayer.prepareAsync();
//            调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    //设置进度条长度
                    seekBar.setMax(mediaPlayer.getDuration());
                    play_pause.setImageDrawable(getResources().getDrawable(R.drawable.play));
                    isPlay = true ;
                }
            });
            //发送一个Runnable, handler收到之后就会执行run()方法
            handler.post(new Runnable() {
                public void run() {
                    // 更新进度条状态
                    if (!isStartTrackingTouch)
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    // 1秒之后再次发送
                    handler.postDelayed(this, 1000);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放器监听器
     */
    private final class MyPlayerListener implements MediaPlayer.OnCompletionListener {
        //歌曲播放完后自动播放下一首歌曲
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    }
    /**
     * 播放按钮控制
     */
    class Play_Pause implements View.OnClickListener{
        public void onClick(View v){
            //默认点击随机歌曲开始播放
            if (!mediaPlayer.isPlaying() && !isPause) {
                int music_num = list.size();
                int random=(int)(Math.random()*(music_num+1));
                String random_music = list.get(random).path;
                music_name.setText(list.get(random).song);
                current = random;
                play(random_music);
            }

            //暂停/播放按钮
            if (isPlay) {
                pause();
                play_pause.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                isPlay = false;
            } else if (!isPlay && mediaPlayer != null){
                resume();
                play_pause.setImageDrawable(getResources().getDrawable(R.drawable.play));
                isPlay = true;
            }
        }
    }
    /**
     * 上一首
     */
    class Previous implements View.OnClickListener{
        public void onClick(View v){
            previous();
        }
    }
    /**
     * 下一首
     */
    class Next implements View.OnClickListener{
        public void onClick(View v){
            next();
        }
    }
    /**
     * 暂停事件
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }
    /**
     * 开始事件
     */
    private void resume() {
        if (isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    /**
     * 播放前一首歌
     */
    private void previous() {
        current = current - 1 < 0 ? list.size() - 1 : current - 1;
        String previous_path = list.get(current).path;
        music_name.setText(list.get(current).song);
        play(previous_path);
    }

    /**
     * 播放下一首歌
     */
    private void next() {
        current = (current + 1) % list.size();
        String next_path = list.get(current).path;
        music_name.setText(list.get(current).song);
        play(next_path);
    }
    /**
     * 进度条监听器
     */
    private final class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {
        //移动触发
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }
        //起始触发
        public void onStartTrackingTouch(SeekBar seekBar) {
            isStartTrackingTouch = true;
        }
        //结束触发
        public void onStopTrackingTouch(SeekBar seekBar) {
            mediaPlayer.seekTo(seekBar.getProgress());
            isStartTrackingTouch = false;
        }
    }

    /**
     * 动态获取本地存储卡（SD卡）读写权限
     */
    private void setRegist(){
        /**
         *动态获取权限，Android 6.0新特性，一些保护权限，如，文件读写除了
         *要在AndroidManifest中声明权限，还需要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) { //大于23是指Android 6.0以后的版本
            int REQUEST_CODE_CONTACT = 101;
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[]  PERMISSIONS_STORAGE= {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            //验证是否许可权限
            for (String str : PERMISSIONS_STORAGE){
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED){
                    //申请权限
                    this.requestPermissions(PERMISSIONS_STORAGE, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }

    //返回键行为
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ){
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            LocalMusic.this.startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //顶部返回
    public void Finish(View v){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        LocalMusic.this.startActivity(new Intent(this, MainActivity.class));
        return;
    }

}