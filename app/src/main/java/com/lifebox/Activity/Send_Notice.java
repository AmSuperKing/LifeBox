package com.lifebox.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.lifebox.R;

public class Send_Notice extends Activity {
    EditText notice_title, notice_content;
    Button add_notice_cancel, send_notice;
    String title, content;
    public boolean sendok;

    public static final int NO_1 = 0x1;
    public int Notice_Num =1;//初始通知数量为1
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notice);
        notice_title = (EditText) findViewById(R.id.notice_title);
        notice_content = (EditText) findViewById(R.id.notice_content);
        add_notice_cancel = (Button) findViewById(R.id.add_notice_cancel);
        send_notice = (Button) findViewById(R.id.send_notice);
        add_notice_cancel.setOnClickListener(new Add_notice_cancel());
        send_notice.setOnClickListener(new Send_notice());
    }

    class Add_notice_cancel implements View.OnClickListener{
        public void onClick(View v){
            finish();
        }
    }

    class Send_notice implements View.OnClickListener{
        public void onClick(View v){
            title = notice_title.getText().toString();
            content = notice_content.getText().toString();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(Send_Notice.this);
            builder.setSmallIcon(R.drawable.tips); //设置图标
            builder.setTicker("备忘提示");
            builder.setContentTitle(title); //设置标题
            builder.setContentText(content); //消息内容
            builder.setWhen(System.currentTimeMillis()); //发送时间
            builder.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
            builder.setOngoing(true);
            builder.setAutoCancel(true);//打开程序后图标消失
            Intent intent = new Intent(Send_Notice.this, com.lifebox.Activity.MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Send_Notice.this, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
            Notification notification1 = builder.build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NotificationCompat.PRIORITY_MAX, notification1);
            if (!sendok){
                notice_title.setText("");
                notice_content.setText("");
                sendok = true;
            }
        }

    }
    public void Finish(View v){
        finish();
        return;
    }

}
