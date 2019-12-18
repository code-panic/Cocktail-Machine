package com.example.administrator.cocktailmobileapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideFragment extends Fragment {
    private static final String TAG = "SlideView";

    private TextView mName;
    private TextView mTitle;
    private TextView mDesciption;
    private Button mButton;
    private ImageView mCardImage;
    private RelativeLayout mBackground;

    public static SlideFragment createSlideFragment (String name, String title, String description, int imageID , int gradientStartColor, int gradientEndColor, int position) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putInt("imageID", imageID);
        bundle.putInt("gradientStartColor", gradientStartColor);
        bundle.putInt("gradientEndColor", gradientEndColor);
        bundle.putInt("pos", position);

        slideFragment.setArguments(bundle);

        return slideFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideview, container, false);

        mName = view.findViewById(R.id.name);
        mTitle = view.findViewById(R.id.title);
        mDesciption = view.findViewById(R.id.description);
        mButton = view.findViewById(R.id.button);
        mCardImage = view.findViewById(R.id.card_image);
        mBackground = view.findViewById(R.id.background);

        final Bundle bundle = getArguments();

        mName.setText(bundle.getString("name"));
        mTitle.setText(bundle.getString("title"));
        mDesciption.setText(bundle.getString("description"));
        mCardImage.setImageResource(bundle.getInt("imageID"));

        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {getResources().getColor(bundle.getInt("gradientStartColor")), getResources().getColor(bundle.getInt("gradientEndColor"))});
        gradientDrawable.setCornerRadius(0f);
        mBackground.setBackgroundDrawable(gradientDrawable);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog.getInstance(getContext()).show(bundle.getInt("pos"), bundle.getString("name"));
            }
        });

        Log.d(TAG, "onCreateView");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "stop");
        recycleBitmap(mCardImage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "destroy");
        recycleBitmap(mCardImage);
    }

    private void recycleBitmap (ImageView imageView) {
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            bitmap.recycle();
        }

        drawable.setCallback(null);
    }
}
