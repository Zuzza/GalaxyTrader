package com.example.galaxytrader.models;

import android.content.Context;
import com.example.galaxytrader.database.DataService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    public List<Stock> inCargo;
    public List<Stock> marketplace;
    private Integer cargoLoad;
    private Integer onPlanet;
    private Integer fuelOnBoard;
    private Integer loan;
    public Integer money;

    final public Integer MAX_CARGO = 100;
    final public Integer MAX_FUEL = 10;
    final public Integer MONEY_ON_START = 1000;
    final public Integer LOAN_ON_START = 10000;

    public static final Integer FUEL_PRICE = 50;

    public Player() {
        inCargo = new ArrayList<Stock>();
        cargoLoad = 0;
        onPlanet = 0;
        fuelOnBoard = MAX_FUEL;
        money = MONEY_ON_START;
        loan = LOAN_ON_START;
    }

    public List<Stock> getInCargo() {
        return inCargo;
    }

    public void setInCargo(List<Stock> inCargo) {
        this.inCargo = inCargo;
    }

    public Integer getCargoLoad() {
        return cargoLoad;
    }

    public void setCargoLoad(Integer cargoLoad) {
        this.cargoLoad = cargoLoad;
    }

    public Integer getOnPlanet() {
        return onPlanet;
    }

    public void setOnPlanet(Integer onPlanet) {
        this.onPlanet = onPlanet;
    }

    public Integer getFuelOnBoard() {
        return fuelOnBoard;
    }

    public Integer getLoan() { return loan; }

    public void setFuelOnBoard(Integer fuelOnBoard) {
        this.fuelOnBoard = fuelOnBoard;
    }

    public void subtractMoney(Integer moneyPaid) {
        this.money -= moneyPaid;
    }

    public void useFuel(Integer fuelUsed) { this.fuelOnBoard -= fuelUsed; }

    public void createMarketplace(Context context){
        DataService stockDataService = new DataService();
        stockDataService.init(context);
        stockDataService.seedDatabase();
        marketplace = stockDataService.getStocks();
    }

    public void updateMarketplace(){
        for (Stock s: marketplace) {
            s.generateRandomPrice(s.getMinPrice(), s.getMaxPrice());
        }
    }

    public void addStockToCargo(Stock s){
        boolean found = false;
        money -= (s.getAmount() * s.getPrice());
        cargoLoad += s.getAmount();
        for (Stock cargoStock : this.inCargo) {
            if(cargoStock.getId().equals(s.getId())){
                cargoStock.addAmount(s.getAmount());
                cargoStock.setPrice(s.getPrice());
                found = true;
            }
        }
        if(!found)
            inCargo.add(s);
    }
    public void sellStockFromCargo(Stock s){
        money += (s.getAmount() * s.getPrice());
        cargoLoad -= s.getAmount();
        for (Stock cargoStock : this.inCargo) {
            if(cargoStock.getId().equals(s.getId())){
                int remainingAmount = cargoStock.getAmount() - s.getAmount();
                if(remainingAmount == 0){
                    inCargo.remove(cargoStock);
                    return;
                }else if(remainingAmount > 0) {
                    cargoStock.subtractAmount(s.getAmount());
                    return;
                }
            }
        }
    }

    public Integer getStockAmount(Long stockId){
        for (Stock s: this.inCargo) {
            if(s.id.equals(stockId)){
                return s.getAmount();
            }
        }
        return 0;
    }

    public Integer getStockMarketIndex(Stock s){
        for (Stock stock : this.marketplace) {
            if(s.id.equals(stock.id)){
                return this.marketplace.indexOf(stock);
            }
        }
        return -1;
    }

    public void payBackLoan(Integer payBack) {
        loan -= payBack;
        money -= payBack;
    }
}
