package com.example.administrator.cocktailmobileapp;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CocktailItemView extends LinearLayout {

    ImageView imageView;
    ImageView ImageView2;
    TextView textView;
    TextView textView2;
    Button button;
    String BTCommand;

    public CocktailItemView(Context context){
        super(context);
        init(context);
    }

    public CocktailItemView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(final Context context){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cocktail_item,this,true);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView);
        ImageView2 = (ImageView) findViewById(R.id.imageView2);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("msg", "clickeventer " + Bluetooth.getInstance().getExtracting());
                if (!Bluetooth.getInstance().getExtracting()) {
                    button.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            button.setEnabled(true);
                        }
                    },3000);
                    Bluetooth.getInstance().setExtracting(true);
                    if(Bluetooth.getInstance().getExtracting())
                        Log.d("msg", "꺄르르르ㅡ를");
                    Log.d("msg","나");
                    Toast.makeText(context,"추출을 시작합니다.",Toast.LENGTH_SHORT).show();
                    Bluetooth.getInstance().sendData(BTCommand);
                    Bluetooth.getInstance().thread = new BackgroundThread();
                    Bluetooth.getInstance().thread.start();
                } else {
                    Log.d("msg","다");
                    Toast.makeText(context,"이미 추출 중입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setName(String name){
        textView.setText(name);
    }

    public void setExplanation(String explanation){
        textView2.setText(explanation);
    }

    public void setCocktailImage(int cocktailId){ imageView.setImageResource(cocktailId); }

    public void setrecipeImage(int recipeId){ ImageView2.setImageResource(recipeId); }
}
