package com.example.galaxytrader.database;

import android.content.Context;

import com.example.galaxytrader.models.Stock;

import java.util.List;

public class DataService {
    private StockDatabaseHelper sqlite;

    public void init(Context context){
        sqlite = sqlite.getInstance(context);
    }

    public Long addStock(Stock stock){
        return sqlite.insert(stock.getName(), stock.getMaxPrice(), stock.getMinPrice(), stock.getImageFileName());
    }

    public boolean delete(Stock stock){
        return sqlite.delete(stock.getId());
    }

    public boolean update(Stock stock){
        return sqlite.update(stock.getId(), stock.getName(), stock.getMaxPrice(), stock.getMinPrice(), stock.getImageFileName());
    }

    public List<Stock> getStocks(){
        List<Stock> stocks = sqlite.getStocks();
        return stocks;
    }

    public Stock getStock(Long id){
        return sqlite.getStock(id);
    }

    public void seedDatabase(){
        List<Stock> stockList = StockList.stockList;
        Long count = sqlite.getStockSourceCount();
        if(count == 0){
            for(Stock stock : stockList) {
                addStock(stock);
            }
        }
    }
}
