package com.example.galaxytrader.stockRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxytrader.Interfaces.OnBuyStockListener;
import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Player;
import com.example.galaxytrader.models.Stock;

import java.util.List;

public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockViewHolder>{

    private List<Stock> stocks;
    private Context context;
    private OnBuyStockListener onBuyStockListener;
    private Player player;

    public List<Stock> getStocks() {
        return stocks;
    }

    public StockRecyclerViewAdapter(List<Stock> stocks, Context context, OnBuyStockListener onBuyStockListener, Player player) {
        this.player = player;
        this.stocks = stocks;
        this.context = context;
        this.onBuyStockListener = onBuyStockListener;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View stockView = inflater.inflate(R.layout.stock_view, parent, false);

        //Return a new holder instance.
        StockViewHolder stockViewHolder = new StockViewHolder(stockView, onBuyStockListener);
        return stockViewHolder;
    }

    /**
     * Takes a ViewHolder object and sets the proper list data (from the list) on the view
     * @param holder    an object of MonsterViewHolder class, representing each item (CardView content)
     *                  in the recyclerView
     * @param position  the position of the monster in the monsters list
     */
    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.updateStock(stock, player.getStockAmount(stock.id));
        holder.bind(stock, onBuyStockListener);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void addItem(Stock stock) {
        stocks.add(stock);
        notifyItemInserted(getItemCount());
    }

    public void updateItemAmount(int position) {
        notifyItemChanged(position);
    }
}
