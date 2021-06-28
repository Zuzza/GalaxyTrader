package com.example.galaxytrader.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Stock;

import java.util.List;

public class StockListViewAdapter extends ArrayAdapter<Stock> {

    private Context context;
    private List<Stock> stockList;
    public ImageView stockImageView;
    public TextView stockNameEditText;
    public TextView priceText;
    public TextView minPriceText;
    public TextView maxPriceText;
    public TextView inCargoText;

    public StockListViewAdapter(@NonNull Context context, List<Stock> inCargo) {
        super(context, 0, inCargo);

        this.context=context;
        this.stockList = inCargo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View itemView, @NonNull ViewGroup parent) {
        View listItem = itemView;

        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.stock_cargo_list_view,parent,false);

        Stock stock = stockList.get(position);

        stockImageView = listItem.findViewById(R.id.stockCargoImage);
        stockNameEditText = listItem.findViewById(R.id.stockCargoName);
        priceText = listItem.findViewById(R.id.priceCargoNumber);
        minPriceText = listItem.findViewById(R.id.minPriceNumCargoTxt);
        maxPriceText = listItem.findViewById(R.id.maxPriceNumCargoTxt);
        inCargoText = listItem.findViewById(R.id.inCargoNumCargoTxt);

        View rootView = stockImageView.getRootView();
        int resID = rootView.getResources().getIdentifier(stock.imageFileName , "drawable" , rootView.getContext().getPackageName()) ;
        stockImageView.setImageResource(resID);
        stockNameEditText.setText(stock.getName());
        priceText.setText(stock.getPrice().toString());
        minPriceText.setText(stock.getMinPrice().toString());
        maxPriceText.setText(stock.getMaxPrice().toString());
        inCargoText.setText(stock.getAmount().toString());

        return listItem;
    }
}
