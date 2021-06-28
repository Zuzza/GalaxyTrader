package com.example.galaxytrader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.galaxytrader.Constans;
import com.example.galaxytrader.R;
import com.example.galaxytrader.SaveLoadService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OptionsActivity extends AppCompatActivity {

    private FloatingActionButton backOptionsBtn;
    private Button restartBtn;
    private boolean ifDeletePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ifDeletePlayer = false;

        backOptionsBtn = findViewById(R.id.backOptionsBtn);
        backOptionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(v);
            }
        });

        restartBtn = findViewById(R.id.restartOptionsBtn);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveLoadService.deleteFile(v.getContext());
                ifDeletePlayer = true;
                goBackToMain(v);
            }
        });
    }

    private void goBackToMain(View v){
        Intent goingBack = new Intent();
        goingBack.putExtra(Constans.IF_DELETE_PLAYER, ifDeletePlayer);
        setResult(RESULT_OK, goingBack);
        finish();
    }
}