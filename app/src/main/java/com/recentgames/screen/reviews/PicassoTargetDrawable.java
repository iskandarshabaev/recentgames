package com.recentgames.screen.reviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoTargetDrawable extends Drawable implements Target {

    private Context context;
    private Drawable mDrawable;
    private int mWidth;
    private int mHeight;
    private Callback mCallback;

    public PicassoTargetDrawable(@NonNull Context context, int width,
                                 int height, @NonNull Callback callback) {
        this.context = context.getApplicationContext();
        mWidth = width;
        mHeight = height;
        mCallback = callback;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawable != null) {
            mDrawable.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mDrawable = new BitmapDrawable(context.getResources(), bitmap);
        mDrawable.setBounds(calcBounds(mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight(),
                mWidth, mHeight));
        context = null;
        invalidateSelf();
        if (mCallback != null) {
            mCallback.invalidate();
        }
    }

    private Rect calcBounds(double width, double height, double boundWidth, double boundHeight) {
        double widthScale = 0;
        double heightScale = 0;
        double resWidth = 0;
        double resHeight = 0;
        if (width > height) {
            widthScale = width > boundWidth ? width / boundWidth : boundWidth / width;
            resWidth = width * widthScale;
            resHeight = height * widthScale;
            if (resHeight > boundHeight) {
                resWidth = resWidth * (boundHeight / resHeight);
                resHeight = resHeight * (boundHeight / resHeight);
            }
        } else {
            heightScale = height > boundHeight ? height / boundHeight : boundHeight / height;
            resWidth = width * heightScale;
            resHeight = height * heightScale;
            if (resWidth > boundWidth) {
                resWidth = resWidth * (boundWidth / resWidth);
                resHeight = resHeight * (boundWidth / resWidth);
            }
        }
        double left = (boundWidth - resWidth)/2.d;
        double top = (boundHeight - resHeight)/2.d;
        return new Rect((int)left, (int)top, (int)(resWidth + left), (int)(resHeight + top));
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    public interface Callback {

        void invalidate();

    }
}
