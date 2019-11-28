package com.lifebox.note;

import androidx.appcompat.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import com.lifebox.R;

/*
 * 这个类主要包括五个点击事件，分别为
 * ListView的长按点击事件，用来AlertDialog来判断是否删除数据。
 * ListView的点击事件，跳转到第二个界面，用来修改数据
 * 新建便签按钮的点击事件，跳转到第二界面，用来新建便签
 */

public class NoteActivity extends Activity {

    ImageButton imageButton;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Cuns> array;
    MyDataBase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_note_page);
        lv=(ListView) findViewById(R.id.lv_bwlList);
        imageButton=(ImageButton) findViewById(R.id.btnAdd);
        inflater=getLayoutInflater();

        mdb=new MyDataBase(this);
        array=mdb.getArray();
        MyAdapter adapter=new MyAdapter(inflater,array);
        lv.setAdapter(adapter);
        /*
         * 点击listView里面的item,用来修改日记
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getApplicationContext(),NewNote.class);
                intent.putExtra("ids",array.get(position).getIds() );
                startActivity(intent);
                NoteActivity.this.finish();
            }
        });

        /*
         * 长点后来判断是否删除数据
         */
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //AlertDialog,来判断是否删除日记。
                new AlertDialog.Builder(NoteActivity.this)
                        .setTitle("删除")
                        .setMessage("是否删除笔记")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mdb.toDelete(array.get(position).getIds());
                                array=mdb.getArray();
                                MyAdapter adapter=new MyAdapter(inflater,array);
                                lv.setAdapter(adapter);
                            }
                        })
                        .create().show();
                return true;
            }
        });
        /*
         * 按钮点击事件，用来新建日记
         */
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewNote.class);
                startActivity(intent);
                NoteActivity.this.finish();
            }
        });
    }

    public void Finish(View v){
        finish();
        return;
    }


}

