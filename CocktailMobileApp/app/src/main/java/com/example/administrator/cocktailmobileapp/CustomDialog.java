package com.example.administrator.cocktailmobileapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog {
    private CustomDialog mSelf;

    private int mPos;
    private String mName;

    public CustomDialog(Context context, int pos, String name) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mSelf = this;
        this.mPos = pos;
        this.mName = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_check);

        TextView textView = this.findViewById(R.id.text);
        Button yesButton = this.findViewById(R.id.yes);
        Button noButton = this.findViewById(R.id.no);

        textView.setText("'" + mName + "' 칵테일을 만들까요?");

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.dialog_makeing);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelf.dismiss();
            }
        });
    }
}
