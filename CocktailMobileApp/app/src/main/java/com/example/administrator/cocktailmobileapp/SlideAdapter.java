package com.example.administrator.cocktailmobileapp;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class SlideAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SlideAdapter";

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        SlideView slideView = new SlideView();

        switch (pos) {
            case 0:
                setSlideView(slideView,
                        "Mojito",
                        "Tastes like summer",
                        "모히토는 쿠바에서 만들어진 칵테일로\n" +
                        "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                        "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);
                break;

            case 1:
                setSlideView(slideView,
                        "Sunrise",
                        "Tastes like tropical",
                        "마치 석양을 보는 것\n" +
                                "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                                "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.BL_TR,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);
                break;


            case 2:
                setSlideView(slideView,
                        "Sunrise",
                        "Tastes like tropical",
                        "마치 석양을 보는 것\n" +
                                "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                                "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.BL_TR,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);
                break;


            case 3:
                setSlideView(slideView,
                        "Sunrise",
                        "Tastes like tropical",
                        "마치 석양을 보는 것\n" +
                                "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                                "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.BL_TR,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);
                break;


            case 4:
                setSlideView(slideView,
                        "Sunrise",
                        "Tastes like tropical",
                        "마치 석양을 보는 것\n" +
                                "미국 소설가이자 저널리스트였던 어니스트 헤밍웨이가\n" +
                                "즐겨 마셨던 것으로 유명하다.",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.BL_TR,
                        R.color.colorWhite,
                        R.color.colorGreen,
                        pos);
                break;


        }
        return slideView;
    }

    @Override
    public int getCount() {
        return 2;
    }



    public void setSlideView (SlideView slideView, String name, String title, String description, int imageId , GradientDrawable.Orientation gradientAngle, int gradientStartColor, int gradientEndColor, int pos) {
        slideView.setNameText(name);
        slideView.setTitleText(title);
        slideView.setDesciptionText(description);
        slideView.setBackgroundImage(imageId);
        slideView.setBackgroudGradient(new GradientDrawable(gradientAngle, new int[] {gradientStartColor, gradientEndColor}));

        slideView.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
