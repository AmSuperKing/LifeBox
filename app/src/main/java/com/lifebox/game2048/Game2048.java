package com.lifebox.game2048;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lifebox.R;

public class Game2048 extends Activity implements View.OnClickListener{
    public TextView tvScrore;//计分的
    public TextView tvBestScore;//最高分
    public int score = 0;
    private int bestScores;//历史最高成绩
    private Button bt, exit;

    private static Game2048 game2048 = null;
    public Game2048(){
        game2048 = this;
    }

    public static Game2048 getGame2048() {
        return game2048;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_one);
        inital();

    }

    @SuppressLint("SetTextI18n")
    public void inital() {
        tvBestScore = (TextView) findViewById(R.id.maxSorce);
        tvScrore = (TextView) findViewById(R.id.tvSorce);
        bt = (Button)findViewById(R.id.bt_cx);
        exit= (Button) findViewById(R.id.bt_exit);
        exit.setOnClickListener(new ClickExit());
        bt.setOnClickListener(this);
        BestScode bestScode = new BestScode(this);
        bestScores = bestScode.getBestScode();
        tvBestScore.setText(bestScores+"");
    }

    @Override
    public void onClick(View v) {
        GameView.getGameView().startGame();
    }

    class ClickExit implements View.OnClickListener{
        public void onClick(View v){
            finish();
        }
    }

    public void clearScore(){
        score = 0;
        showScore();
    }
    public void showScore(){
        tvScrore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
        if (score > bestScores){
            bestScores = score;
            BestScode bs = new BestScode(this);
            bs.setBestScode(bestScores);
            tvBestScore.setText(bestScores+"");
        }
    }
    public void Finish(View v){
        finish();
        return;
    }

    /**
     * 菜单、返回键响应
     */
    private long exitTime = 0;

    @SuppressLint("WrongConstant")
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出",1000).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


