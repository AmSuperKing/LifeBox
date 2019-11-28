package com.lifebox.pianotiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;

import com.lifebox.R;

public class Pianotiles extends Activity {
    private CountDownView mCountDownView;
    private PianoTilesView mPianoTilesView;
    private RelativeLayout mMarkRela;
    private AlertScoreDialog mAlertScoreDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pianotiles_page);
        initView();
    }
    private void initView() {
        mPianoTilesView = (PianoTilesView) findViewById(R.id.pianoTilesView);
        mCountDownView = (CountDownView) findViewById(R.id.countTextView);
        mMarkRela = (RelativeLayout) findViewById(R.id.markRela);
        mCountDownView.setData(Arrays.asList("3","2","1","开始"));
        mCountDownView.init();
        mCountDownView.setCountDownListener(new CountDownView.CountDownListener() {
            @Override
            public void finish() {
                mMarkRela.setVisibility(View.GONE);
                mPianoTilesView.setZOrderOnTop(true);
                mPianoTilesView.startGame();
            }
        });

        mAlertScoreDialog = new AlertScoreDialog.Builder(Pianotiles.this)
                .setFinishClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("DEMO","点击点击");
                        finish();
                        mAlertScoreDialog.dismiss();
                    }
                })
                .setRestartClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertScoreDialog.dismiss();
                        mPianoTilesView.restart();
                        mMarkRela.setVisibility(View.VISIBLE);
                        mCountDownView.init();

                    }
                })
                .builder();


        mPianoTilesView.setGameListener(new PianoTilesView.GameListener() {
            @Override
            public void gameEnd(final  String number) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("DEMO","number == "+number);
                        if(mAlertScoreDialog!=null){
                            mAlertScoreDialog.setScore(number);
                            mAlertScoreDialog.show();
                        }

                    }
                });

            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    public void Finish(View v){
        finish();
        return;
    }
}

