package com.recentgames.screen.reviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;

import com.squareup.picasso.Picasso;

public class URLImageParser implements Html.ImageGetter {

    private Context mContext;
    private View mView;
    private int mWidth;
    private int mHeight;

    public URLImageParser(View t, Context c, int width, int height) {
        this.mContext = c;
        this.mView = t;
        mWidth = width;
        mHeight = height;
    }

    public Drawable getDrawable(String source) {
        PicassoTargetDrawable d = new PicassoTargetDrawable(mContext, mWidth, mHeight, mCallback);
        Picasso.with(mContext)
                .load(source)
                .into(d);
        d.setBounds(0, 0, mWidth, mHeight);
        return d;
    }

    private PicassoTargetDrawable.Callback mCallback = new PicassoTargetDrawable.Callback() {
        @Override
        public void invalidate() {
            mView.invalidate();
        }
    };
}
