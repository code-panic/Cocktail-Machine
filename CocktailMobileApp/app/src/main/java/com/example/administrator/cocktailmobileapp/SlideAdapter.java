package com.example.administrator.cocktailmobileapp;

import android.graphics.drawable.GradientDrawable;
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
        switch (pos) {
            case 0:
                return SlideFragment.createSlideFragment(
                        "모히또",
                        "쿠바의 작가가 사랑한 칵테일",
                        "모히토는 쿠바에서 만들어진 칵테일로\n" +
                        "미국 소설가이자 저널리스트였던\n" +
                        "어니스트 헤밍웨이가 즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);

            case 1:
                return SlideFragment.createSlideFragment(
                        "선라이즈",
                        "열대과일 같은 상큼함",
                        "마치 석양을 보는 것\n" +
                                "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                                "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_sunrise,
                        GradientDrawable.Orientation.BR_TL,
                        R.color.colorSunriseLight,
                        R.color.colorSunriseDark,
                        pos);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
