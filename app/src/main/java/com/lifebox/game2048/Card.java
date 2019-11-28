package com.lifebox.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by King
 */

public class Card extends FrameLayout{
    private TextView label;  //呈现的文字
    private int num = 0;     //绑定的编号
    // 设置背景色
    private int defaultBackColor = 0x33bfbfbf;

    public Card(Context context) {
        super(context);
        label = new TextView(getContext());   //显示下
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        LayoutParams lp = new LayoutParams(-1,-1);  //创建个布局，填充满整个父局容器
        lp.setMargins(15,15,0,0);
        addView(label,lp);   //然后扔进去
        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        label.setBackgroundColor(getBackground(num));
        if (this.num<= 0)
        {
            label.setText("");

        }else {
            label.setText(num + "");
        }

    }

    private int getBackground(int num) {
        int bgcolor = defaultBackColor;
        switch (num) {
            case 0:
                bgcolor = 0xffFFFFFF;
                break;
            case 2:
                bgcolor = 0xff98FB98;
                break;
            case 4:
                bgcolor = 0xff7FFFD4;
                break;
            case 8:
                bgcolor = 0xff66CDAA;// #F2B179
                break;
            case 16:
                bgcolor = 0xff00FA9A;
                break;
            case 32:
                bgcolor = 0xff40E0D0;
                break;
            case 64:
                bgcolor = 0xff20B2AA;
                break;
            case 128:
                bgcolor = 0xff008080;
                break;
            case 256:
                bgcolor = 0xff008B8B;
                break;
            case 512:
                bgcolor = 0xff2F4F4F;
                break;
            case 1024:
                bgcolor = 0xff00CED1;
                break;
            case 2048:
                bgcolor = 0xff00FFFF;
                break;
            default:
                bgcolor = 0xffffffff;
                break;
        }
        return bgcolor;
    }


    /**
     * 判断卡片的数字是否相同
     * @param
     * @return
     */
    public boolean equals(Card o) {
        return getNum()==o.getNum();
    }
}


