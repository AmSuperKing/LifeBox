package com.lifebox.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.lifebox.Adapter.ContentAdapter;
import com.lifebox.R;
import com.lifebox.Activity.LocalMusic;

public class MainActivity extends Activity implements OnClickListener, ViewPager.OnPageChangeListener {

    Context context;
    ViewPager mViewPager;
    // 底部菜单4个Linearlayout
    private LinearLayout tab_note;
    private LinearLayout tab_music;
    private LinearLayout tab_box;
    private LinearLayout tab_setting;

    // 底部菜单4个ImageView
    private ImageView img_note;
    private ImageView img_music;
    private ImageView img_box;
    private ImageView img_setting;

    // 底部菜单4个菜单标题
    private TextView txt_note;
    private TextView txt_music;
    private TextView txt_box;
    private TextView txt_setting;

    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;

    private List<View> views;

    PopupMenu popupMenu = null ;
    boolean isSee, isSee4, isSee6, isSee8, isSeeNote;

    public static MainActivity mainActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        this.context = context;
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();

    }


    private void initEvent() {
        // 设置按钮监听
        tab_note.setOnClickListener(this);
        tab_music.setOnClickListener(this);
        tab_box.setOnClickListener(this);
        tab_setting.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    private void initView() {

        // 底部菜单4个Linearlayout
        this.tab_note = (LinearLayout) findViewById(R.id.tab_note);
        this.tab_music = (LinearLayout) findViewById(R.id.tab_music);
        this.tab_box = (LinearLayout) findViewById(R.id.tab_box);
        this.tab_setting = (LinearLayout) findViewById(R.id.tab_setting);

        // 底部菜单4个ImageView
        this.img_note = (ImageView) findViewById(R.id.img_note);
        this.img_music = (ImageView) findViewById(R.id.img_music);
        this.img_box = (ImageView) findViewById(R.id.img_box);
        this.img_setting = (ImageView) findViewById(R.id.img_setting);

        // 底部菜单4个菜单标题
        this.txt_note = (TextView) findViewById(R.id.txt_note);
        this.txt_music = (TextView) findViewById(R.id.txt_music);
        this.txt_box = (TextView) findViewById(R.id.txt_box);
        this.txt_setting = (TextView) findViewById(R.id.txt_setting);

        // 中间内容区域ViewPager
        this.viewPager = (ViewPager) findViewById(R.id.vp_content);


        // 适配器
        View note_page = View.inflate(MainActivity.this, R.layout.note_page, null);
        View music_page = View.inflate(MainActivity.this, R.layout.music_page, null);
        View box_page = View.inflate(MainActivity.this, R.layout.box_page, null);
        View setting_page = View.inflate(MainActivity.this, R.layout.setting_page, null);
        Intent intent1 = new Intent(MainActivity.this, LocalMusic.class);
        View view1 = getWindow().getDecorView();
        views = new ArrayList<View>();
        views.add(note_page);
        views.add(music_page);
        views.add(box_page);
        views.add(setting_page);

        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.tab_note:
                img_note.setImageResource(R.drawable.note_pressed);
                txt_note.setTextColor(getResources().getColor(R.color.colorPrimary));
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_music:
                img_music.setImageResource(R.drawable.music_pressed);
                txt_music.setTextColor(getResources().getColor(R.color.colorPrimary));
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab_box:
                img_box.setImageResource(R.drawable.box_pressed);
                txt_box.setTextColor(getResources().getColor(R.color.colorPrimary));
                viewPager.setCurrentItem(2);
                break;
            case R.id.tab_setting:
                img_setting.setImageResource(R.drawable.setting_pressed);
                txt_setting.setTextColor(getResources().getColor(R.color.colorPrimary));
                viewPager.setCurrentItem(3);
                break;

            default:
                break;
        }

    }

    private void restartBotton() {
        // ImageView置为灰色
        img_note.setImageResource(R.drawable.note_normal);
        img_music.setImageResource(R.drawable.music_normal);
        img_box.setImageResource(R.drawable.box_normal);
        img_setting.setImageResource(R.drawable.setting_normal);

        // TextView置为白色
        txt_note.setTextColor(0xffbfbfbf);
        txt_music.setTextColor(0xffbfbfbf);
        txt_box.setTextColor(0xffbfbfbf);
        txt_setting.setTextColor(0xffbfbfbf);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (arg0) {
            case 0:
                img_note.setImageResource(R.drawable.note_pressed);
                txt_note.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                img_music.setImageResource(R.drawable.music_pressed);
                txt_music.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                img_box.setImageResource(R.drawable.box_pressed);
                txt_box.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                img_setting.setImageResource(R.drawable.setting_pressed);
                txt_setting.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;

            default:
                break;
        }

    }

    /**
     * 启动便签记事本
     */
    /**
     * PopupMenu
     */
    public void onPopupButtonClick(View button){
        //创建PopupMenu对象
        popupMenu = new PopupMenu(this, button);
        //将 R.menu.menu_main 菜单资源加载到popup中
        getMenuInflater().inflate(R.menu.menu_main,popupMenu.getMenu());
        //为popupMenu选项添加监听器
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.note:
                        Intent intent_note = new Intent(MainActivity.this, com.lifebox.note.NoteActivity.class);
                        startActivity(intent_note);
                        break;
                    case R.id.plain_item:
                        Intent intent_send_notice = new Intent(MainActivity.this, com.lifebox.Activity.Send_Notice.class);
                        startActivity(intent_send_notice);
                        break;
                    default:
                        Toast.makeText(MainActivity.this,"nothing clicked", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void setNoteSee(View v) {
        LinearLayout note = (LinearLayout) findViewById(R.id.note);
        LinearLayout send = (LinearLayout) findViewById(R.id.send_notice);
        if (!isSeeNote){
            note.setVisibility(View.VISIBLE);
            send.setVisibility(View.VISIBLE);
            isSeeNote = true;
            return;
        }else {
            note.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            isSeeNote = false;
            return;
        }
    }
    public void StartNote(View v){
        Intent intent_note = new Intent(MainActivity.this, com.lifebox.note.NoteActivity.class);
        startActivity(intent_note);
    }
    public void StartSendNotice(View v){
        Intent intent_send_notice = new Intent(MainActivity.this, com.lifebox.Activity.Send_Notice.class);
        startActivity(intent_send_notice);
    }
    /**
     * 启动本地音乐界面
     */
    public void GOTOLocalMusic(View v){
            Intent intent1 = new Intent();
            intent1.setClass(MainActivity.this, com.lifebox.Activity.LocalMusic.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent1);
            return;
    }
    /**
     * 启动千千音乐界面
     */
    public void GoToQianqianMusic(View v){
        Intent intent2 = new Intent();
        intent2.setClass(MainActivity.this, com.lifebox.Activity.StartQianMusic.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent2);
        return;
    }
    /**
     * 启动5nd音乐电台页面
     */
    public void GoToFivendMusic(View v){
        Intent intent2 = new Intent();
        intent2.setClass(MainActivity.this, com.lifebox.Activity.StartFivendMusic.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent2);
        return;
    }
    /**
     * 启动清沫广播电台页面
     */
    public void GoToQingmo(View v){
        Intent intent3 = new Intent();
        intent3.setClass(MainActivity.this, com.lifebox.Activity.Qingmo.class);
        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent3);
        return;
    }
    /**
     * 启动豆瓣电台页面
     */
    public void Douban(View v){
        Intent intent4 = new Intent();
        intent4.setClass(MainActivity.this, com.lifebox.Activity.Douban.class);
        intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent4.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent4);
        return;
    }
    /**
     * 启动喜马拉雅电台页面
     */
    public void Xilamaya(View v){
        Intent intent5 = new Intent();
        intent5.setClass(MainActivity.this, com.lifebox.Activity.Ximalaya.class);
        intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent5.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent5);
        return;
    }

    /**
     * 可收纳菜单之生活社区栏控制
     */
    public void setSee(View v) {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.ts2);
        ImageView down = (ImageView) findViewById(R.id.down);
        if (!isSee){
            linearLayout1.setVisibility(View.VISIBLE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.up));
            isSee = true;
            return;
        }else {
            linearLayout1.setVisibility(View.GONE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.down));
            isSee = false;
            return;
        }
    }

    /**
     * 在线办公栏控制
     * @param v
     */
    public void set4See(View v) {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.ts4);
        ImageView down = (ImageView) findViewById(R.id.down3);
        if (!isSee4){
            linearLayout1.setVisibility(View.VISIBLE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.up));
            isSee4 = true;
            return;
        }else {
            linearLayout1.setVisibility(View.GONE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.down));
            isSee4 = false;
            return;
        }
    }

    /**
     * 图片工具栏控制
     * @param v
     */
    public void set6See(View v) {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.ts6);
        ImageView down = (ImageView) findViewById(R.id.down5);
        if (!isSee6){
            linearLayout1.setVisibility(View.VISIBLE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.up));
            isSee6 = true;
            return;
        }else {
            linearLayout1.setVisibility(View.GONE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.down));
            isSee6 = false;
            return;
        }
    }

    /**
     *趣味游戏栏控制
     * @param v
     */
    public void set8See(View v) {
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.ts8);
        ImageView down = (ImageView) findViewById(R.id.down7);
        if (!isSee8){
            linearLayout1.setVisibility(View.VISIBLE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.up));
            isSee8 = true;
            return;
        }else {
            linearLayout1.setVisibility(View.GONE);
            down.setImageDrawable(getResources().getDrawable(R.drawable.down));
            isSee8 = false;
            return;
        }
    }
    /**
     * Convert Page，文件转换
     */
    public void Convert(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Convert.class);
        startActivity(intent);
        return;
    }
    /**
     * 二维码工具
     */
    public void Erweima(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Erweima.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动微博网页页面
     */
    public void Weibo(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Weibo.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动丁香医生网页页面
     */
    public void Dingxiang(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Dingxiang.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动InfoQ网页页面
     */
    public void InfoQ(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.InfoQ.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动Readhub网页页面
     */
    public void Readhub(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Readhub.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动装逼表情网页
     */
    public void Biaoqing(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Biaoqing.class);
        startActivity(intent);
        return;
    }
    /**
     * 启动笑话段子网页
     */
    public void Haha(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Haha.class);
        startActivity(intent);
        return;
    }
    /**
     * 稿定抠图
     */
    public void Koutu(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.Koutu.class);
        startActivity(intent);
        return;
    }
    /**
     * 图片压缩
     */
    public void PicCompressed(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.PicCompressed.class);
        startActivity(intent);
        return;
    }
    /**
     * 图片拼接
     */
    public void PingPic(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.PingPic.class);
        startActivity(intent);
        return;
    }
    /**
     * 照片特效
     */
    public void AmazingPic(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.Activity.AmazingPic.class);
        startActivity(intent);
        return;
    }

    /**
     * 启动game2048游戏
     * @param v
     */
    public void GameOne(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.game2048.Game2048.class);
        startActivity(intent);
        return;
    }

    /**
     * 启动自由博弈游戏
     * @param v
     */
    public void Gobang(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.gobang.GobangActivity.class);
        startActivity(intent);
        return;
    }

    /**
     * 启动别踩白块
     * @param v
     */
    public void Piano(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.pianotiles.Pianotiles.class);
        startActivity(intent);
        return;
    }
    public void Puzzle(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, com.lifebox.puzzle.Puzzle.class);
        startActivity(intent);
        return;
    }

    /**
     * 关于盒子
     * @param v
     */
    public void About_lifebox(View v){
        Intent intent = new Intent(getApplicationContext(), About_lifebox.class);
        startActivity(intent);
    }

    /**
     *返回键控制
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent intent = new Intent();// 创建Intent对象
            intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
            intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
            startActivity(intent);// 将Intent传递给Activity
            Toast.makeText(getApplicationContext(), "生活盒子正在后台运行",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
    public void Set_Theme(View v){

        Toast.makeText(getApplicationContext(), "开发人员偷懒了呢！\n还没开发出来呢！>_<",
                Toast.LENGTH_SHORT).show();
        return;
    }
    /**
     * 反馈对话框
     * @param v
     */
    public void Reflect_advice(View v){
//        AlertDialog.Builder about_dialog = new AlertDialog.Builder(MainActivity.this);
//        about_dialog.setTitle("使用反馈");
//        about_dialog.setIcon(R.mipmap.ic_launcher);
//        about_dialog.setMessage("很抱歉呢！\n应用让您不欢喜了.\n可以添加客服小姐姐微信反馈哦！\n微信号：lifeboxservice");
//        about_dialog.setPositiveButton("确定", new okClick());
//        about_dialog.create();
//        about_dialog.show();
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("使用反馈")
                .setMessage("很抱歉呢！\n应用让您不欢喜了.\n可以添加客服小姐姐微信反馈哦！\n微信号：lifeboxservice")
                .setNegativeButton("复制微信号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = "lifeboxservice";
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(text);
                        Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    public void Join_us(View v){
//        AlertDialog.Builder about_dialog = new AlertDialog.Builder(MainActivity.this);
//        about_dialog.setTitle("加入我们");
//        about_dialog.setIcon(R.mipmap.ic_launcher);
//        about_dialog.setMessage("您的加入，\n是我们更强大的动力.\n可以将您的资料发送到下放邮箱：\nlifebox@service.com");
//        about_dialog.setPositiveButton("投递资料", new mailClick());
//        about_dialog.setPositiveButton("确定", new okClick());
//        about_dialog.create();
//        about_dialog.show();
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("加入我们")
                .setMessage("您的加入，\n是我们更强大的动力.\n可以将您的资料发送到下放邮箱：\nlifebox@service.com")
                .setNegativeButton("现在就投递", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data=new Intent(Intent.ACTION_SENDTO);
                        data.setData(Uri.parse("mailto:2513205092@qq.com"));
                        data.putExtra(Intent.EXTRA_SUBJECT, "投递");
                        data.putExtra(Intent.EXTRA_TEXT, "请将您的简历发送给我们。");
                        startActivity(data);
                    }
                })
                .setPositiveButton("了解", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

    class mailClick implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){
            Intent data=new Intent(Intent.ACTION_SENDTO);
            data.setData(Uri.parse("mailto:2513205092@qq.com"));
            data.putExtra(Intent.EXTRA_SUBJECT, "投递");
            data.putExtra(Intent.EXTRA_TEXT, "请将您的简历发送给我们。");
            startActivity(data);
        }
    }
    class okClick implements DialogInterface.OnClickListener{
        public void onClick(DialogInterface dialog, int which){
            dialog.cancel();
        }
    }

    /**
     * 退出应用
     * @param v
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Exit_APP(View v){

        LocalMusic.instance.finish();
        Intent intent = new Intent();// 创建Intent对象
        intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
        intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
        startActivity(intent);// 将Intent传递给Activity

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }

        android.os.Process.killProcess(android.os.Process.myPid());

        return;

    }

}
