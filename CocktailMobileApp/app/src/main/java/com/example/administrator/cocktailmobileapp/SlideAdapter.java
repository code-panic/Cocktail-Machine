package com.example.administrator.cocktailmobileapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SlideAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SlideAdapter";

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int pos) {
        SlideView slideView = new SlideView();

        return slideView;
    }

    //페이지 개수 지정
    @Override
    public int getCount() {
        return 5;
    }
}
