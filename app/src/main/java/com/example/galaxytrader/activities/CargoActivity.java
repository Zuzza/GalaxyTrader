package com.example.galaxytrader.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.galaxytrader.Constans;
import com.example.galaxytrader.R;
import com.example.galaxytrader.listView.StockListViewAdapter;
import com.example.galaxytrader.models.Player;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CargoActivity extends AppCompatActivity {

    private FloatingActionButton backBtn;
    private StockListViewAdapter stockAdapter;
    private ListView listView;
    private TextView cargoTxt;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo);
        Intent intent = getIntent();
        player = (Player)intent.getSerializableExtra(Constans.PLAYER_KEY);

        TextView test = findViewById(R.id.cargoEmptyText);
        if(player.inCargo.isEmpty())
            test.setText(R.string.Cargo_is_empty);

        stockAdapter = new StockListViewAdapter(this, player.inCargo);
        listView = (ListView)findViewById(R.id.cargoListView);
        listView.setAdapter(stockAdapter);

        backBtn = findViewById(R.id.backFloatingCargoBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel(v);
            }
        });

        cargoTxt = findViewById(R.id.spaceNumCargoTxt);
        updateCargo();
    }

    private void cancel(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    private void updateCargo(){
        cargoTxt.setText(player.getCargoLoad() + "/" + player.MAX_CARGO);
    }
}