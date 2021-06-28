package com.example.galaxytrader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galaxytrader.Constans;
import com.example.galaxytrader.R;

public class SpaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        ImageView spaceship = findViewById(R.id.spaceShipImg);
        getSupportActionBar().hide();
        TextView youWonTxt = findViewById(R.id.youWonTxt);

        Intent intent = getIntent();
        Boolean ifWin = intent.getBooleanExtra(Constans.WIN, false);

        if(!ifWin) {
            Integer width = Resources.getSystem().getDisplayMetrics().widthPixels;
            ObjectAnimator mover = ObjectAnimator.ofFloat(spaceship, "x", -450, width);
            mover.setDuration(2500);
            mover.start();

            new CountDownTimer(2700, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    finish();
                }
            }.start();
        }else{
            youWonTxt.setVisibility(View.VISIBLE);
            ObjectAnimator rotator = ObjectAnimator.ofFloat(spaceship,
                    "rotation", 720);
            rotator.setDuration(8000);
            rotator.start();
            new CountDownTimer(8000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    finish();
                }
            }.start();
        }
    }
}