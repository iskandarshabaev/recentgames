package com.recentgames.screen.gallery;

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

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagesAdapter extends PagerAdapter {

    private List<Image> mImages;

    public ImagesAdapter(@NonNull List<Image> images) {
        mImages = images;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(collection.getContext());
        View view = inflater.inflate(R.layout.item_gallery_image, collection, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        PhotoViewAttacher  mAttacher = new PhotoViewAttacher(imageView);
        String url = mImages.get(position).getMediumUrl();
        if(url != null) {
            ImageHelper.loadImage(imageView, url, mAttacher);
        }
        collection.addView(view);
        return view;
    }

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
        void onClick(Image image);
    }
}
