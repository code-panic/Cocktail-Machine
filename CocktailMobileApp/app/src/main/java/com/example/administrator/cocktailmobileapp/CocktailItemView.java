package com.example.administrator.cocktailmobileapp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public void init(Context context){
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
                Log.d("msg",BTCommand);
                Bluetooth.getInstance().sendData(BTCommand);
            }
        });
    }

    public void setName(String name){
        textView.setText(name);
    }

    public void setExplanation(String explanation){
        textView2.setText(explanation);
    }

    public void setCocktailImage(int resId){ imageView.setImageResource(resId); }

    public void setMaterialImage(int resId){ ImageView2.setImageResource(resId); }
}
