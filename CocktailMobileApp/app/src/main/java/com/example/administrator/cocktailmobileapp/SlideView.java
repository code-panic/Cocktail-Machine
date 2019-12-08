package com.example.administrator.cocktailmobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideView extends Fragment {
    private static final String TAG = "SlideView";

    private TextView mName;
    private TextView mTitle;
    private TextView mDesciption;
    private Button mButton;
    private ImageView mCardImage;
    private RelativeLayout mBackground;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideview, container, false);

        mName = view.findViewById(R.id.name);
        mTitle = view.findViewById(R.id.title);
        mDesciption = view.findViewById(R.id.description);
        mButton = view.findViewById(R.id.button);
        mCardImage = view.findViewById(R.id.card_image);
        mBackground = view.findViewById(R.id.background);

        return view;
    }

    public void setNameText (String text) { mName.setText(text); }

    public void setTitleText (String text) { mTitle.setText(text); }

    public void setDesciptionText (String text) {
        mDesciption.setText(text);
    }

    public void setBackgroundImage (int imageId) { mCardImage.setImageBitmap(getRoundedBitmap(imageId)); }

    public void setBackgroudGradient (GradientDrawable gradientDrawable) { mBackground.setBackgroundDrawable(gradientDrawable);}

    public Button getButton() {
        return mButton;
    }

    private Bitmap getRoundedBitmap(int imageId) {
        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), imageId);
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);

        roundedDrawable.setCornerRadius(32);
        roundedDrawable.setAntiAlias(true);

        return roundedDrawable.getBitmap();
    }
}
