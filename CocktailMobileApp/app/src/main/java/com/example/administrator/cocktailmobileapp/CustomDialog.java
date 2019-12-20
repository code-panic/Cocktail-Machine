package com.example.administrator.cocktailmobileapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomDialog extends Dialog {
    private static final String TAG = "CustomDialog";

    private static CustomDialog instance;

    private int mMakingNum;
    private Handler mHandler;

    public static CustomDialog getInstance(Context context) {
        if (instance == null) {
            instance = new CustomDialog(context);
        }
        return instance;
    }

    private CustomDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        /* 백그라운드 테마 설정 */
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
//        setCancelable(true);
//        setCanceledOnTouchOutside(true);

        mHandler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void show (final String message, String name) {
        final CustomDialog self = this;

        self.show();

        setContentView(R.layout.dialog_check);

        TextView textView = this.findViewById(R.id.text);
        Button yesButton = this.findViewById(R.id.yes);
        Button noButton = this.findViewById(R.id.no);

        textView.setText("'" + name + "' 칵테일을 만들까요?");

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.dialog_makeing);

                final ImageView stateImageView = findViewById(R.id.state_image);
                final TextView stateTextView = findViewById(R.id.state_text);

                Bluetooth.getInstance().writeData(message);

                mMakingNum = 1;

                Bluetooth.getInstance().readData(mHandler, new Bluetooth.BluetoothCall() {
                    @Override
                    public void onMaking() {
                        if (mMakingNum % 5 == 1) {
                            stateImageView.setImageResource(R.drawable.image_making_1);
                            stateTextView.setText("칵테일을 만드는 중입니다 .");
                            Log.d(TAG, "1");
                        } else if (mMakingNum % 5 == 2) {
                            stateImageView.setImageResource(R.drawable.image_making_2);
                            stateTextView.setText("칵테일을 만드는 중입니다 . .");
                            Log.d(TAG, "2");
                        } else if (mMakingNum % 5 == 3) {
                            stateImageView.setImageResource(R.drawable.image_making_3);
                            stateTextView.setText("칵테일을 만드는 중입니다 . . .");
                            Log.d(TAG, "3");
                        } else if (mMakingNum % 5 == 4) {
                            stateImageView.setImageResource(R.drawable.image_making_4);
                            stateTextView.setText("칵테일을 만드는 중입니다 . . . .");
                        } else if (mMakingNum % 5 == 0) {
                            stateImageView.setImageResource(R.drawable.image_making_5);
                            stateTextView.setText("칵테일을 만드는 중입니다 . . . . .");
                        }

                        mMakingNum++;
                    }

                    @Override
                    public void onComplete() {
                        stateImageView.setImageResource(R.drawable.image_complete);
                        stateTextView.setText("칵테일을 완성했습니다!");

                        self.findViewById(R.id.background_dialog).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                self.dismiss();
                            }
                        });
                    }
                });
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.dismiss();
            }
        });
    }
}
