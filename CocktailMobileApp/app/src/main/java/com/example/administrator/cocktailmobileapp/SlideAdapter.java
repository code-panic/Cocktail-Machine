package com.example.administrator.cocktailmobileapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SlideAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SlideAdapter";

    private ArrayList<Fragment> mFragmentList;

    public SlideAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = new ArrayList<>();

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "핑크레이디",
                        "올해 Parents' night의 여주인공!",
                        "달콤쌉쌀한 자몽의 향기가\n" +
                                "매혹적인 그녀를 떠올리게 만드는\n" +
                                "중독성 있는 칵테일입니다",
                        R.drawable.image_cocktail_pink,
                        R.color.colorPink255,
                        R.color.colorOrange255,
                        "0")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "선라이즈",
                        "아아~ 이것이 청춘이다",
                        "맨발로 석양 아래를 뛰어다니는\n" +
                                "청춘만화의 주인공처럼 망고로 만든\n" +
                                "칵테일을 마셔보는 건 어때요?",
                        R.drawable.image_cocktail_orange,
                        R.color.colorOrange255,
                        R.color.colorYellow255,
                        "1")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "파인망고",
                        "하와이 가면 이거 있을껄요?",
                        "망고, 오렌지, 레몬으로\n" +
                                "만든 칵테일로 하와이에서 휴가 보내는\n" +
                                "기분을 느껴보는 건 어떨까요?",
                        R.drawable.image_cocktail_yellow,
                        R.color.colorYellow255,
                        R.color.colorGreen255,
                        "2")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "몰디브",
                        "사상초유의 이병헌급 액션칵테일!",
                        "모히또 가서 몰디브 한 잔 해야지?\n" +
                                "이병헌 같은 망고와 블루레몬을\n" +
                                "즐겨보세요",
                        R.drawable.image_cocktail_green,
                        R.color.colorGreen255,
                        R.color.colorBlue255,
                        "3")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "푸른눈동자",
                        "오늘 하루 장범준 flex~",
                        "흔들리는 푸른눈동자 속에서\n" +
                                "너의 블루레몬이 느껴진거야~\n" +
                                "오버플로우 포장마차에서 칵테일 한 잔?",
                        R.drawable.image_cocktail_blue,
                        R.color.colorBlue255,
                        R.color.colorPurple255,
                        "4")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "아기상어",
                        "아기상어~ 뚜뚜뚜뚜뚜",
                        "자몽~ 뚜뚜뚜뚜뚜뚜\n" +
                                "블루레몬~ 뚜뚜뚜뚜뚜뚜\n" +
                                "맛있어 ~ 뚜뚜뚜뚜뚜뚜",
                        R.drawable.image_cocktail_purple,
                        R.color.colorPurple255,
                        R.color.colorGray255,
                        "5")
        );

        mFragmentList.add(
                SlideFragment.createSlideFragment(
                        "오버플로우",
                        "OVERFLOW의 야심작!!!",
                        "오버플로우 부원들이\n" +
                                "특별히 엄선한 칵테일이죠\n" +
                                "혀 버려도 책임 안 집니다ㅋㅋㅋ",
                        R.drawable.image_cocktail_black,
                        R.color.colorGray255,
                        R.color.colorBlack255,
                        "6")
        );
    }

    /*
    *
    * 칵테일이 무엇으로 만들어졌는지만 간략하게 3줄로 설명해주세요
    *
    * */

    @Override
    public Fragment getItem(int pos) {
        return mFragmentList.get(pos);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
