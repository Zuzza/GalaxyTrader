package com.example.galaxytrader.Interfaces;

import com.example.galaxytrader.models.Stock;

public interface OnBuyStockListener {
    void onBuySellStockClick(Stock stock, int buySell);
}
