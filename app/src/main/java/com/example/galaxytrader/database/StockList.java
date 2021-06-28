package com.example.galaxytrader.database;

import com.example.galaxytrader.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockList {

    public static List<Stock> stockList;

    static {
        stockList = new ArrayList<>();

        addStock(new Stock( "Jelly Beans", 35, 5, "jellybeans"));
        addStock(new Stock( "Light bulbs", 75, 45, "lightbulb"));
        addStock(new Stock( "Marbles", 100, 80, "marbles"));
        addStock(new Stock( "Chillies", 130, 100, "chilli"));
        addStock(new Stock( "Rubber Ducks", 145, 120, "rubberduck"));
        addStock(new Stock( "Ammeters", 165, 120, "ammeter"));
        addStock(new Stock( "Pinatas", 195, 155, "pinata"));
        addStock(new Stock( "Tequila", 205, 165, "tequila"));
        addStock(new Stock( "Gum boots", 255, 205, "gumboots"));
        addStock(new Stock( "Gas Masks", 270, 210, "gasmask"));
        addStock(new Stock( "Irons", 310, 245, "iron"));
        addStock(new Stock( "Sombreros", 375, 310, "sombrero"));
        addStock(new Stock( "Accumulators", 400, 325, "acumulator"));
        addStock(new Stock( "Vacuum cleaners", 480, 390, "vacuum"));
        addStock(new Stock( "Dark matter", 520, 420, "matter"));
        addStock(new Stock( "DNA", 645, 525, "dna"));
        addStock(new Stock( "Livers", 900, 500, "liver"));
        addStock(new Stock( "Kidneys", 1000, 750, "kidney"));

    }

    private static void addStock(Stock stock){
        stockList.add(stock);
    }
}
