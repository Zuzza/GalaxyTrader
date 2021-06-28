package com.example.galaxytrader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.galaxytrader.Constans;
import com.example.galaxytrader.R;
import com.example.galaxytrader.listView.PlanetListViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DepartActivity extends AppCompatActivity {

    private ListView planetList;
    private FloatingActionButton backBtn;
    private PlanetListViewAdapter planetAdapter;
    private Integer playerOnPlanet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        Intent intent = getIntent();
        playerOnPlanet = (Integer) intent.getIntExtra(Constans.PLANET_KEY, -1);

        backBtn = findViewById(R.id.planetBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });

        planetAdapter = new PlanetListViewAdapter(this, Constans.PLANET_NAME, Constans.PLANET_IMAGE_ID, playerOnPlanet);
        planetList = (ListView)findViewById(R.id.planetListView);
        planetList.setAdapter(planetAdapter);

        planetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    if(playerOnPlanet != position) {
                        Intent goingBack = new Intent();
                        goingBack.putExtra(Constans.PLANET_KEY, position);
                        setResult(RESULT_OK, goingBack);
                        finish();
                    }
            }
        });
    }
    private void cancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }
}