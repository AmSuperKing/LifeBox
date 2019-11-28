package com.lifebox.gobang;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.lifebox.R;

public class GobangActivity extends Activity {
    private ChessBoardView chessBoardView;
    Button restart_btn,exit_gobang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gobang_page);
        chessBoardView = (ChessBoardView) findViewById(R.id.boardView);
        restart_btn = (Button) findViewById(R.id.restart_btn);
        restart_btn.setOnClickListener(new Restart());
        exit_gobang = (Button) findViewById(R.id.exit_gobang);
        exit_gobang.setOnClickListener(new Exit_gobang());
    }

    class Restart implements View.OnClickListener{
        public void onClick(View v){
            chessBoardView.start();
            return;
        }
    }
    class Exit_gobang implements View.OnClickListener{
        public void onClick(View v){
            finish();
        }
    }

    public void Finish(View v){
        finish();
        return;
    }


}

