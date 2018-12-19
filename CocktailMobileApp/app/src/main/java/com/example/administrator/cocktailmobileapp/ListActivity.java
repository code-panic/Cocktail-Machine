package com.example.administrator.cocktailmobileapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final String mohito = "1";
    final String sunrise = "2";
    final String sunshine = "3";
    final String cinderella = "4";
    final String pinacolada = "5";

    ArrayList<CocktailItemView> itemViews = new ArrayList<CocktailItemView>();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bluetooth.getInstance().checkBTState();

        //드래그 화면전환
        SlidingView sv = new SlidingView(this);

        addItemView("모히토","어니스트 헤밍웨이가 사랑한 칵테일",R.drawable.mohito,mohito,R.color.mohito);
        addItemView("선라이즈","떠오르는 칵테일 계의 유망주",R.drawable.sunrise,sunrise,R.color.sunrise);
        addItemView("선샤인","따뜻한 햇살의 상쾌한 칵테일",R.drawable.sunshine,sunshine,R.color.sunshine);
        addItemView("신데렐라","동화 속 왕비의 칵테일",R.drawable.cinderella,cinderella,R.color.cinderella);
        addItemView("피나콜라다", "파인애플이 무성한 언덕", R.drawable.pinacolada,pinacolada,R.color.pinacolada);

        for(int i = 0; i < itemViews.size();i++){
            sv.addView(itemViews.get(i));
        }

        setContentView(sv);
    }

    private void addItemView(String name, String explanation, int resCocktialId,String command,int color) {
        CocktailItemView view = new CocktailItemView(getApplicationContext());
        view.setName(name);
        view.setExplanation(explanation);
        view.setCocktailImage(resCocktialId);

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

