package com.example.galaxytrader.Interfaces;

import com.example.galaxytrader.models.Stock;

public interface OnPopUpCloseListener {
    void onPopUpClose(Stock s, int buySell);
}
