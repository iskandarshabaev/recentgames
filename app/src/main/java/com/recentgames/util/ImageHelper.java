package com.recentgames.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageHelper {

    private ImageHelper() {
    }

    public static void loadImage(@NonNull ImageView imageView,
                                 @NonNull String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.game_placeholder)
                .noFade()
                .into(imageView);
    }

    public static void loadImage(@NonNull ImageView imageView,
                                 @Nullable GamePreview gamePreview) {
        String url;
        if (gamePreview != null && gamePreview.getImage() != null) {
            url = gamePreview.getImage().getMediumUrl();
        } else {
            url = "null";
        }
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.game_placeholder)
                .noFade()
                .into(imageView);
    }

    public static void loadImage(@NonNull ImageView imageView,
                                 @NonNull String url,
                                 PhotoViewAttacher attacher){
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
