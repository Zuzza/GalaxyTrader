package com.example.galaxytrader.stockRecyclerView;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxytrader.Interfaces.OnBuyStockListener;
import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Stock;

public class StockViewHolder extends RecyclerView.ViewHolder  {

    public final ImageView stockImageView;
    public final TextView stockNameEditText;
    public final TextView priceText;
    public final TextView minPriceText;
    public final TextView maxPriceText;
    public final TextView inCargoText;
    public final CardView card;
    public Button buyBtn, sellBtn;
    private final OnBuyStockListener onBuySellStockListener;

    public StockViewHolder(@NonNull View itemView, OnBuyStockListener onBuySellStockListener) {
        super(itemView);

        this.onBuySellStockListener = onBuySellStockListener;

        stockImageView = itemView.findViewById(R.id.stockImage);
        stockNameEditText = itemView.findViewById(R.id.stockName);
        priceText = itemView.findViewById(R.id.priceNumber);
        minPriceText = itemView.findViewById(R.id.minPriceNumTxt);
        maxPriceText = itemView.findViewById(R.id.maxPriceNumTxt);
        inCargoText = itemView.findViewById(R.id.inCargoNumTxt);
        buyBtn = itemView.findViewById(R.id.buyBtn);
        sellBtn = itemView.findViewById(R.id.sellBtn);
        card = itemView.findViewById(R.id.stockCardView);
    }

    public void updateStock(Stock stock, Integer inCargoAmount){
        View rootView = stockImageView.getRootView();
        int resID = rootView.getResources().getIdentifier(stock.imageFileName , "drawable" , rootView.getContext().getPackageName()) ;
        stockImageView.setImageResource(resID);
        this.stockNameEditText.setText(stock.getName());
        this.priceText.setText(stock.getPrice().toString());
        this.minPriceText.setText(stock.getMinPrice().toString());
        this.maxPriceText.setText(stock.getMaxPrice().toString());
        this.inCargoText.setText(inCargoAmount.toString());
        if(inCargoAmount > 0)
        {
            int color = ResourcesCompat.getColor(rootView.getResources(), R.color.primaryLightColor, null);
            this.card.setCardBackgroundColor(color);
        }else
            this.card.setCardBackgroundColor(Color.WHITE);
    }

    public void bind(final Stock stock, final OnBuyStockListener onBuySellStockListener){
        this.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuySellStockListener.onBuySellStockClick(stock, 1);
            }
        });
        this.sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBuySellStockListener.onBuySellStockClick(stock, 2);
            }
        });
    }
}
