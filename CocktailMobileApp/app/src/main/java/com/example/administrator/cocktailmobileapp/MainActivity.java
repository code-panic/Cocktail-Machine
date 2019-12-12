package com.example.administrator.cocktailmobileapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewpager);

        SlideAdapter slideAdapter = new SlideAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(slideAdapter);
        mViewPager.setCurrentItem(0);

    }
}

