package com.lifebox.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lifebox.R;

public class About_lifebox extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_lifebox);
    }

    public void Finish(View v){
        finish();
        return;
    }
}
