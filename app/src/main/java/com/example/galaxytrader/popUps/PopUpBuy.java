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

public class PopUpBuy{

    Button buttonBuy, cancelBuy;
    Integer totalPriceValue, totalAmountToBuy;
    public Stock popupStock;

    public void showPopupWindow(final View view, OnPopUpCloseListener onClose, Stock stock, Integer remainingCargoLoad, Integer money) {
        popupStock = stock.copyStock();
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View popupView = inflater.inflate(R.layout.pop_up_buy, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of window, install the handler
        TextView totalPrice = popupView.findViewById(R.id.totalBuyNumTxt);
        TextView errorMsg = popupView.findViewById(R.id.whyNotMsgTxt);
        EditText amountToBuy = popupView.findViewById(R.id.enterBuyAmountNumTxt);

        buttonBuy = popupView.findViewById(R.id.buyPopupBtn);
        buttonBuy.setEnabled(false);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupStock.setAmount(totalAmountToBuy);
                onClose.onPopUpClose(popupStock, 1);
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

        amountToBuy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!amountToBuy.getText().toString().isEmpty()) {
                    totalAmountToBuy = Integer.valueOf(amountToBuy.getText().toString());
                    totalPriceValue = (totalAmountToBuy * popupStock.getPrice());
                    totalPrice.setText(totalPriceValue.toString());

                    if (totalPriceValue > money) {
                        buttonBuy.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_money);
                    } else if (totalAmountToBuy > remainingCargoLoad) {
                        buttonBuy.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_cargo_space);
                    } else {
                        buttonBuy.setEnabled(true);
                        errorMsg.setText("");
                    }

                } else {
                    buttonBuy.setEnabled(false);
                    totalPrice.setText("0");
                }
            }
        });
    }
}
