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
                        "선샤인",
                        "석양을 떠올리게 하는 칵테일",
                        "그레나딘 시럽부터 오렌지,\n" +
                                "레몬, 파인애플까지 다양한 재료가\n" +
                                "입에서 톡톡 터진다",
                        R.drawable.image_sunshine,
                        GradientDrawable.Orientation.BR_TL,
                        R.color.colorSunshineLight,
                        R.color.colorSunshineDark,
                        pos);
            case 1:
                return SlideFragment.createSlideFragment(
                        "모히또",
                        "어니스트 헤밍웨이가 사랑한 칵테일",
                        "쿠바에서 만들어진 칵테일로\n" +
                                "달콤한 사이다와 상큼한 라임과 레몬의 배합이\n" +
                                "청럄하고 산뜻한 맛을 선사한다",
                        R.drawable.image_mojito,
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        R.color.colorMojitoLight,
                        R.color.colorMojitoDark,
                        pos);

            case 2:
                return SlideFragment.createSlideFragment(
                        "피나콜라다",
                        "스페인어로 파인애플이 무성한 언덕",
                        "파인애플과 콜라다믹스의\n" +
                                "달달한 배합이 은은하게 입에\n" +
                                "퍼지는 칵테일이다",
                        R.drawable.image_pinacolada,
                        GradientDrawable.Orientation.BR_TL,
                        R.color.colorPinacoladaLight,
                        R.color.colorPinacoladaDark,
                        pos);

            case 3:
                return SlideFragment.createSlideFragment(
                        "선라이즈",
                        "석양을 떠올리게 하는 칵테일",
                        "그레나딘 시럽 위로\n" +
                                "쌓여진 오렌지의 그라데이션을 보며\n" +
                                "마실수록 다른 맛을 느낄 수 있다",
                        R.drawable.image_sunrise,
                        GradientDrawable.Orientation.BR_TL,
                        R.color.colorSunriseLight,
                        R.color.colorSunriseDark,
                        pos);
            case 4:
                return SlideFragment.createSlideFragment(
                        "신데렐라",
                        "일상을 동화처럼 바꾸는 칵테일",
                        "레몬, 오렌지, 파인애플의\n" +
                                "강렬한 조합이 입과 목구멍을\n" +
                                "강타하는 강렬한 칵테일이다",
                        R.drawable.image_cinderella,
                        GradientDrawable.Orientation.BR_TL,
                        R.color.colorCinderellaLight,
                        R.color.colorCinderellaDark,
                        pos);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
