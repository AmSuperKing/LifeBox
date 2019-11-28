package com.lifebox.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import com.lifebox.bean.Song;
import com.lifebox.Utils.Utils;
import com.lifebox.R;

/**
 * 适配器
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    List<Song> list;
    public MyAdapter(Context context, List<Song> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Myholder myholder;
        if (view == null) {
            myholder = new Myholder();
            view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item, null);
            myholder.t_position = view.findViewById(R.id.t_postion);
            myholder.t_song = view.findViewById(R.id.t_song);
            myholder.t_singer = view.findViewById(R.id.t_singer);
            myholder.t_duration = view.findViewById(R.id.t_duration);
            view.setTag(myholder);
        } else {
            myholder = (Myholder) view.getTag();
        }
        myholder.t_song.setText(list.get(i).song.toString());
        myholder.t_singer.setText(list.get(i).singer.toString());
        String time = Utils.formatTime(list.get(i).duration);
        myholder.t_duration.setText(time);
        myholder.t_position.setText(i + 1 + "");
        return view;
    }

    public class Myholder {
        TextView t_position, t_song, t_singer, t_duration;
    }
}
