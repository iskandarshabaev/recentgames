package com.recentgames.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageHelper {

    private ImageHelper(){}

    public static void loadImage(@NonNull ImageView imageView,
                                 @NonNull String url){
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .into(imageView);
    }
}
