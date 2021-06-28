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
import com.example.galaxytrader.Interfaces.OnPopUpFuelCloseListener;
import com.example.galaxytrader.R;
import com.example.galaxytrader.models.Player;
import com.example.galaxytrader.models.Stock;

public class PopUpBuyFuel {

    Button buttonBuy, cancelBuy;
    TextView howMuch, totalPrice, errorMsg;
    Integer totalPriceValue, totalAmountToBuy;
    public Stock popupStock;

    public void showPopupWindow(final View view, OnPopUpFuelCloseListener onClose, Integer remainingFuelLoad, Integer money) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View popupView = inflater.inflate(R.layout.pop_up_buy, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        howMuch = popupView.findViewById(R.id.howMuchBuySellTxt);
        howMuch.setText(R.string.how_many_tons_of_fuel);
        totalPrice = popupView.findViewById(R.id.totalBuyNumTxt);
        errorMsg = popupView.findViewById(R.id.whyNotMsgTxt);
        EditText amountToBuy = popupView.findViewById(R.id.enterBuyAmountNumTxt);

        buttonBuy = popupView.findViewById(R.id.buyPopupBtn);
        buttonBuy.setEnabled(false);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose.onPopUpClose(totalAmountToBuy);
                popupWindow.dismiss();
            }
        });
        cancelBuy = popupView.findViewById(R.id.cancelBuyPopupBtn);
        cancelBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose.onPopUpClose(0);
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
                    totalPriceValue = (totalAmountToBuy * Player.FUEL_PRICE);
                    totalPrice.setText(totalPriceValue.toString());

                    if (totalPriceValue > money) {
                        buttonBuy.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_money);
                    } else if (totalAmountToBuy > remainingFuelLoad) {
                        buttonBuy.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_fuel_space);
                    } else {
                        buttonBuy.setEnabled(true);
                        errorMsg.setText("");
                    }

                } else {
                    errorMsg.setText("");
                    buttonBuy.setEnabled(false);
                    totalPrice.setText("0");
                }
            }
        });
    }
}
