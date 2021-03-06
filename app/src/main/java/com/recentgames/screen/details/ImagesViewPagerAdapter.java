package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.recentgames.R;
import com.recentgames.model.content.Image;
import com.recentgames.util.ImageHelper;

import java.util.List;

public class ImagesViewPagerAdapter extends PagerAdapter {

    private List<Image> mImages;
    private OnImageClickListener mOnClickListener;

    public ImagesViewPagerAdapter(@NonNull List<Image> images,
                                  @NonNull OnImageClickListener onClickListener) {
        mImages = images;
        mOnClickListener = onClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(collection.getContext());
        View view = inflater.inflate(R.layout.item_image, collection, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setOnClickListener(v -> {
            if(mOnClickListener != null){
                mOnClickListener.onClick(position, imageView);
            }
        });
        ImageHelper.loadImage(imageView, mImages.get(position).getMediumUrl());
        collection.addView(view);
        return view;
    }

    /*@Override
    public float getPageWidth(int position) {
        return 0.4f;
    }*/

    public void changeDataSet(@NonNull List<Image> images){
        mImages.clear();
        mImages.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public interface OnImageClickListener{
        void onClick(int position, View view);
    }
}
