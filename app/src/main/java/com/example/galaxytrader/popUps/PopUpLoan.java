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

import com.example.galaxytrader.Interfaces.OnPopUpLoanCloseListener;
import com.example.galaxytrader.R;

public class PopUpLoan {

    Button buttonPayBack, cancelBuy;
    TextView howMuch, moneyOwnedTxt, errorMsg, moneyAmountTxt;
    Integer totalAmountToPayBack;

    public void showPopupWindow(final View view, OnPopUpLoanCloseListener onClose, Integer money, Integer loan) {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View popupView = inflater.inflate(R.layout.pop_up_buy, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        howMuch = popupView.findViewById(R.id.howMuchBuySellTxt);
        howMuch.setText(R.string.how_much_pay_back_loan);
        moneyAmountTxt = popupView.findViewById(R.id.totalBuyNumTxt);
        moneyAmountTxt.setText(loan.toString());
        moneyOwnedTxt = popupView.findViewById(R.id.totalBuySellTxt);
        moneyOwnedTxt.setText(R.string.you_owe_money);
        errorMsg = popupView.findViewById(R.id.whyNotMsgTxt);
        EditText amountToBuy = popupView.findViewById(R.id.enterBuyAmountNumTxt);

        buttonPayBack = popupView.findViewById(R.id.buyPopupBtn);
        buttonPayBack.setEnabled(false);
        buttonPayBack.setText(R.string.pay_back);
        buttonPayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose.onPopUpLoanClose(totalAmountToPayBack);
                popupWindow.dismiss();
            }
        });
        cancelBuy = popupView.findViewById(R.id.cancelBuyPopupBtn);
        cancelBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose.onPopUpLoanClose(0);
                popupWindow.dismiss();
            }
        });

        amountToBuy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!amountToBuy.getText().toString().isEmpty()) {
                    totalAmountToPayBack = Integer.valueOf(amountToBuy.getText().toString());

                    if (totalAmountToPayBack > money) {
                        buttonPayBack.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_money);
                    } else if(totalAmountToPayBack > loan) {
                        buttonPayBack.setEnabled(false);
                        errorMsg.setText(R.string.not_enough_loan);
                    }else {
                        buttonPayBack.setEnabled(true);
                        errorMsg.setText("");
                    }

                } else {
                    errorMsg.setText("");
                    buttonPayBack.setEnabled(false);
                }
            }
        });
    }
}
