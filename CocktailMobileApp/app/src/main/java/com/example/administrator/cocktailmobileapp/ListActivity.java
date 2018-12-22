package com.example.administrator.cocktailmobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static ListActivity instance;

    final String mogito = "1";
    final String sunrise = "2";
    final String sunshine = "3";
    final String cinderella = "4";
    final String pinacolada = "5";

    {
        instance = this;
    }

    ArrayList<CocktailItemView> itemViews = new ArrayList<CocktailItemView>();

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bluetooth.getInstance().checkBTState();

        //드래그 화면전환
        SlidingView sv = new SlidingView(this);

        addItemView("모히또","어니스트 헤밍웨이가 사랑한 칵테일",R.drawable.mojito,mogito,R.color.mogito,R.drawable.mojito_recipe);
        addItemView("선라이즈","떠오르는 칵테일 계의 유망주",R.drawable.sunrise,sunrise,R.color.sunrise,R.drawable.sunrise_recipe);



        for(int i = 0; i < itemViews.size();i++){
            sv.addView(itemViews.get(i));
        }

        setContentView(sv);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void addItemView(String name, String explanation, int cocktialId,String command,int color,int recipeId) {
        CocktailItemView view = new CocktailItemView(getApplicationContext());
        view.setName(name);
        view.setExplanation(explanation);
        view.setCocktailImage(cocktialId);
        view.setrecipeImage(recipeId);
        view.BTCommand = command;
        view.setBackgroundResource(color);
        itemViews.add(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bluetooth.getInstance().BTConnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bluetooth.getInstance().BTConnectCancle();
    }

}

