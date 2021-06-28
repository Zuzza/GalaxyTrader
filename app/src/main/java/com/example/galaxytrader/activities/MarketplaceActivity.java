package com.example.galaxytrader.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.galaxytrader.Constans;
import com.example.galaxytrader.Interfaces.OnBuyStockListener;
import com.example.galaxytrader.Interfaces.OnPopUpCloseListener;
import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Player;
import com.example.galaxytrader.models.Stock;
import com.example.galaxytrader.popUps.PopUpBuy;
import com.example.galaxytrader.popUps.PopUpSell;
import com.example.galaxytrader.stockRecyclerView.StockRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MarketplaceActivity extends AppCompatActivity implements OnBuyStockListener, OnPopUpCloseListener {

    private FloatingActionButton backBtn;
    private StockRecyclerViewAdapter adapter;
    private View rootView;
    private RecyclerView stocksRecyclerView;
    private Player player;
    private TextView moneyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        Intent intent = getIntent();
        player = (Player)intent.getSerializableExtra(Constans.PLAYER_KEY);

        backBtn = findViewById(R.id.marketplaceBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(v);
            }
        });

        rootView = findViewById(android.R.id.content).getRootView();

        stocksRecyclerView = findViewById(R.id.marketplaceRecyclerView);
        moneyTxt = findViewById(R.id.moneyMarketplaceTxt);
        updateMoney();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stocksRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new StockRecyclerViewAdapter(player.marketplace, this, this, player);
        stocksRecyclerView.setAdapter(adapter);
    }

    private void goBackToMain(View v){
        Intent goingBack = new Intent();
        goingBack.putExtra(Constans.PLAYER_KEY, player);
        setResult(RESULT_OK, goingBack);
        finish();
    }

    @Override
    public void onBuySellStockClick(Stock stock, int buySell) {
        if(buySell == 1)
            showPopUpBuy(stock);
        if(buySell == 2)
            showPopUpSell(stock);
    }

    private void showPopUpBuy(Stock stock) {
        PopUpBuy popUpBuy = new PopUpBuy();
        popUpBuy.showPopupWindow(rootView, this, stock, (player.MAX_CARGO - player.getCargoLoad()), player.money);
    }
    private void showPopUpSell(Stock stock) {
        PopUpSell popUpSell = new PopUpSell();
        popUpSell.showPopupWindow(rootView, this, stock, player.getStockAmount(stock.id));
    }

    @Override
    public void onPopUpClose(Stock s, int buySell){getPopUpValue(s, buySell);}

    private void getPopUpValue(Stock s, int buySell) {
        if(s != null && buySell == 1){
            player.addStockToCargo(s);
            updateMoney();
            updateStockAmount(s);
        }else if(s != null && buySell == 2){
            player.sellStockFromCargo(s);
            updateMoney();
            updateStockAmount(s);
        }
    }

    private void updateMoney(){
        moneyTxt.setText(player.money.toString());
    }

    private void updateStockAmount(Stock stock){
        adapter.updateItemAmount(player.getStockMarketIndex(stock));
    }
}