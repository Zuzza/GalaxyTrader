package com.example.galaxytrader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galaxytrader.Interfaces.OnPopUpFuelCloseListener;
import com.example.galaxytrader.Interfaces.OnPopUpLoanCloseListener;
import com.example.galaxytrader.activities.CargoActivity;
import com.example.galaxytrader.activities.DepartActivity;
import com.example.galaxytrader.activities.MarketplaceActivity;
import com.example.galaxytrader.activities.OptionsActivity;
import com.example.galaxytrader.popUps.PopUpBuyFuel;
import com.example.galaxytrader.activities.SpaceActivity;
import com.example.galaxytrader.models.Player;
import com.example.galaxytrader.popUps.PopUpLoan;

public class MainActivity extends AppCompatActivity implements OnPopUpFuelCloseListener, OnPopUpLoanCloseListener {

    private Button departBtn, marketplaceBtn, fuelBtn, inCargoBtn, optionsBtn, loanBtn;
    private SeekBar fuelBar, cargoLoadBar;
    private ImageView planetImg;
    private TextView welcomeText, moneyTxt;
    private Player player;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planetImg = findViewById(R.id.planetImg);
        welcomeText = findViewById(R.id.welcomeTxt);
        moneyTxt = findViewById(R.id.moneyTxt);
        player = new Player();

        departBtn = (Button)findViewById(R.id.departBtn);
        departBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPlanet(v);
            }
        });
        marketplaceBtn = (Button)findViewById(R.id.marketplaceBtn);
        marketplaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarketplace(v);
            }
        });
        fuelBtn = (Button)findViewById(R.id.fuelBtn);
        fuelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpBuyFuel(v);
            }
        });
        inCargoBtn = (Button)findViewById(R.id.cargoBtn);
        inCargoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCargo(v);
            }
        });
        optionsBtn = (Button)findViewById(R.id.optionBtn);
        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOptions(v);
            }
        });
        loanBtn = (Button)findViewById(R.id.payLoanBtn);
        loanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpLoan(v);
            }
        });
        fuelBar = findViewById(R.id.fuelBar);
        fuelBar.setMax(player.MAX_FUEL);
        fuelBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        updateFuel();
        cargoLoadBar = findViewById(R.id.cargoBar);
        cargoLoadBar.setMax(player.MAX_CARGO);
        cargoLoadBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        updateCargo();
        createPlayer();
    }

    private void goToPlanet(View v){
        if(player.getFuelOnBoard() == 0){
            Toast.makeText(this, R.string.not_enough_fuel_msg, Toast.LENGTH_LONG).show();
            return;
        }
        Intent goToPlanetView = new Intent(this, DepartActivity.class);
        goToPlanetView.putExtra(Constans.PLANET_KEY, player.getOnPlanet());
        startActivityForResult(goToPlanetView, Constans.DEPART_ACTIVITY_CODE);
    }

    private void goToMarketplace(View v){
        Intent goToMarketplaceView = new Intent(this, MarketplaceActivity.class);
        goToMarketplaceView.putExtra(Constans.PLAYER_KEY, player);
        startActivityForResult(goToMarketplaceView, Constans.MARKETPLACE_ACTIVITY_CODE);
    }

    private void goToCargo(View v){
        Intent goToInCargoView = new Intent(this, CargoActivity.class);
        goToInCargoView.putExtra(Constans.PLAYER_KEY, player);
        startActivityForResult(goToInCargoView, Constans.CARGO_ACTIVITY_CODE);
    }

    private void goToOptions(View v){
        Intent goToOptionsView = new Intent(this, OptionsActivity.class);
        startActivityForResult(goToOptionsView, Constans.OPTIONS_ACTIVITY_CODE);
    }

    private void goToTravel(){
        Intent goToTravelView = new Intent(this, SpaceActivity.class);
        startActivityForResult(goToTravelView, Constans.TRAVEL_ACTIVITY_CODE);
    }

    private void goToWin(){
        Intent goToWinView = new Intent(this, SpaceActivity.class);
        goToWinView.putExtra(Constans.WIN, true);
        startActivityForResult(goToWinView, Constans.TRAVEL_ACTIVITY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constans.DEPART_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                updatePlanet(data);
                goToTravel();
            }
        }
        if( requestCode == Constans.MARKETPLACE_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                player = (Player)data.getSerializableExtra(Constans.PLAYER_KEY);
                updateMoney();
                updateCargo();
            }
        }
        if( requestCode == Constans.OPTIONS_ACTIVITY_CODE){
            if(resultCode == RESULT_OK){
                if(data.getBooleanExtra(Constans.IF_DELETE_PLAYER, false))
                    createPlayer();
            }
        }
    }

    private void showPopUpBuyFuel(View v){
        PopUpBuyFuel popUpBuyFuel= new PopUpBuyFuel();
        popUpBuyFuel.showPopupWindow(v, this , (player).MAX_FUEL-player.getFuelOnBoard(), player.money);
    }

    @Override
    public void onPopUpClose(Integer fuelBought){
        Integer fuel = player.getFuelOnBoard();
        player.setFuelOnBoard(fuel + fuelBought);
        player.subtractMoney(fuelBought * Player.FUEL_PRICE);
        updateFuel();
        updateMoney();
    }

    private void showPopUpLoan(View v){
        PopUpLoan popUpLoan = new PopUpLoan();
        popUpLoan.showPopupWindow(v, this , player.money, player.getLoan());
    }

    @Override
    public void onPopUpLoanClose(Integer payBack){
        player.payBackLoan(payBack);
        updateMoney();
        updateLoan();
        if(player.getLoan() == 0)
            win();
    }

    private void updateMoney(){ moneyTxt.setText(player.money.toString()); }

    private void updateFuel(){
        fuelBar.setProgress(player.getFuelOnBoard());
    }

    private void updateCargo(){
        cargoLoadBar.setProgress(player.getCargoLoad());
    }

    private void updateLoan(){ loanBtn.setText(getResources().getString(R.string.loan) + " " + player.getLoan().toString()); }

    private void updatePlanet(Intent data) {
        int planetID;
        if(data.hasExtra(Constans.PLANET_KEY)){
            planetID = data.getExtras().getInt(Constans.PLANET_KEY);
            player.setOnPlanet(planetID);
            if(planetID >= 0){
                updatePlanetImg(planetID);
                player.updateMarketplace();
                player.useFuel(1);
                updateFuel();
            }
        }
    }

    private void updatePlanetImg(int planetId){
        planetImg.setImageResource(Constans.PLANET_IMAGE_ID[planetId]);
        welcomeText.setText(getResources().getString(R.string.welcome_to_main) + " " + Constans.PLANET_NAME[planetId]);
    }

    private void createPlayer(){
        player = SaveLoadService.loadJsonPlayer(this);
        if(player == null){
            player = new Player();
            player.createMarketplace(this);
        }
        updateMoney();
        updatePlanetImg(player.getOnPlanet());
        updateFuel();
        updateCargo();
        updateLoan();
    }

    private void win(){
        goToWin();
    }

    @Override
    protected void onPause() {
        if(player != null)
            SaveLoadService.saveJsonPlayer(player, this.getApplicationContext());
        super.onPause();
    }
}