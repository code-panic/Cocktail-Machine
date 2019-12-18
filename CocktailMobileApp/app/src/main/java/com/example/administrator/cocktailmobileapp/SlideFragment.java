package com.example.administrator.cocktailmobileapp;

import android.content.Context;
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
import android.support.annotation.Nullable;
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

    private View mView;

    private TextView mName;
    private TextView mTitle;
    private TextView mDesciption;
    private Button mButton;
    private ImageView mCardImage;
    private RelativeLayout mBackground;

    public static SlideFragment createSlideFragment (String name, String title, String description, int imageID , int gradientStartColor, int gradientEndColor, String message) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putInt("imageID", imageID);
        bundle.putInt("gradientStartColor", gradientStartColor);
        bundle.putInt("gradientEndColor", gradientEndColor);
        bundle.putString("message", message);

        slideFragment.setArguments(bundle);

        return slideFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = layoutInflater.inflate(R.layout.fragment_slideview, null);

        mName = mView.findViewById(R.id.name);
        mTitle = mView.findViewById(R.id.title);
        mDesciption = mView.findViewById(R.id.description);
        mButton = mView.findViewById(R.id.button);
        mCardImage = mView.findViewById(R.id.card_image);
        mBackground = mView.findViewById(R.id.background);

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
                CustomDialog.getInstance(getContext()).show(bundle.getString("message"), bundle.getString("name"));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mView;
    }

    @Override
    public void onStop() {
        super.onStop();

        recycleBitmap(mCardImage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
