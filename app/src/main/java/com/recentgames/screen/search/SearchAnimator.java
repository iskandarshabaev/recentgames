package com.recentgames.screen.search;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by rodionov on 21.09.2016.
 */

public class SearchAnimator {

    private static final int DURATION = 700;
    private static final float TENSION = 1;
    private static final String PROP_TRANSLATIONY = "translationY";
    private static final String PROP_ALPHA = "alpha";

    @NonNull
    public static AnimatorSet fadeIn(@NonNull View view) {

        float startY = -view.getHeight();
        float endY = 0f;
        float startAlpha = 0f;
        float endAlpha = 1f;

        return init(view,startY,endY,startAlpha,endAlpha);
    }

    @NonNull
    public static AnimatorSet fadeOut(@NonNull View view) {

        float startY = 0f;
        float endY = -view.getHeight();
        float startAlpha = 1f;
        float endAlpha = 0f;

        return init(view,startY,endY,startAlpha,endAlpha);
    }

    @NonNull
    private static AnimatorSet init(@NonNull View view,float startY,float endY, float startAlpha,float endAlpha) {
        ObjectAnimator mover = ObjectAnimator.ofFloat(view, PROP_TRANSLATIONY, startY, endY);
        mover.setDuration(DURATION);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, PROP_ALPHA, startAlpha, endAlpha);
        fadeIn.setDuration(DURATION);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mover).with(fadeIn);
        animatorSet.setInterpolator(new OvershootInterpolator(TENSION));

        return animatorSet;
    }
}
