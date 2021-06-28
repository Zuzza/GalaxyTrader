package com.example.galaxytrader.popUps;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.galaxytrader.Interfaces.OnPopUpCloseListener;
import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Stock;

public class PopUpSell {

    Button buttonSell, cancelBuy;
    TextView howMuchTxt, totalTxt, errorMsg;
    Integer totalPriceValue, totalAmountToSell;
    public Stock popupStock;

    public void showPopupWindow(final View view, OnPopUpCloseListener onClose, Stock stock, Integer onBoard) {
        popupStock = stock.copyStock();
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View popupView = inflater.inflate(R.layout.pop_up_buy, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        TextView totalPrice = popupView.findViewById(R.id.totalBuyNumTxt);
        EditText amountToSell = popupView.findViewById(R.id.enterBuyAmountNumTxt);
        errorMsg = popupView.findViewById(R.id.whyNotMsgTxt);
        howMuchTxt = popupView.findViewById(R.id.howMuchBuySellTxt);
        howMuchTxt.setText(R.string.how_much_sell);
        totalTxt = popupView.findViewById(R.id.totalBuySellTxt);
        totalTxt.setText(R.string.total);

        buttonSell = popupView.findViewById(R.id.buyPopupBtn);
        buttonSell.setText(R.string.sell);
        buttonSell.setEnabled(false);
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupStock.setAmount(totalAmountToSell);
                onClose.onPopUpClose(popupStock, 2);
                popupWindow.dismiss();
            }
        });
        cancelBuy = popupView.findViewById(R.id.cancelBuyPopupBtn);
        cancelBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupStock = null;
                popupWindow.dismiss();
            }
        });

        amountToSell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!amountToSell.getText().toString().isEmpty()) {
                    totalAmountToSell = Integer.valueOf(amountToSell.getText().toString());
                    totalPriceValue = (totalAmountToSell * popupStock.getPrice());
                    totalPrice.setText(totalPriceValue.toString());

                    if (totalAmountToSell > onBoard) {
                        buttonSell.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_stock);
                    } else {
                        buttonSell.setEnabled(true);
                        errorMsg.setText("");
                    }

                } else {
                    buttonSell.setEnabled(false);
                    totalPrice.setText("0");
                }
            }
        });
    }
}
