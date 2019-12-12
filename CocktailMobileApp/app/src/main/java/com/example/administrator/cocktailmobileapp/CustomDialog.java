package com.example.administrator.cocktailmobileapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

                final ImageView stateImageView = mSelf.findViewById(R.id.state_image);
                final TextView stateTextView = mSelf.findViewById(R.id.state_text);

                //Bluetooth.getInstance().writeData(mPos + "");

                Bluetooth.getInstance().setBluetoothCall(new Bluetooth.BluetoothCall() {
                    @Override
                    public void onMaking() {
                        /* 후 수정 */
                        if (stateTextView.getText().equals("칵테일을 만드는 중입니다 .")) {
                            stateImageView.setImageResource(R.drawable.image_making_2);
                            stateTextView.setText("칵테일을 만드는 중입니다 . .");
                        } else if (stateTextView.getText().equals("칵테일을 만드는 중입니다 . .")) {
                            stateImageView.setImageResource(R.drawable.image_making_3);
                            stateTextView.setText("칵테일을 만드는 중입니다 . . .");
                        } else if (stateTextView.getText().equals("칵테일을 만드는 중입니다 . . .")) {
                            stateImageView.setImageResource(R.drawable.image_making_1);
                            stateTextView.setText("칵테일을 만드는 중입니다 .");
                        }
                    }

                    @Override
                    public void onComplete() {
                        setContentView(R.layout.dialog_complete);


                    }
                });

                Bluetooth.getInstance().start();
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
