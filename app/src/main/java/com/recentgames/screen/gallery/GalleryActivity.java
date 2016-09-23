package com.recentgames.screen.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.recentgames.R;
import com.recentgames.model.content.Image;
import com.recentgames.util.ImageHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    public static final String POSITION_KEY = "position";
    public static final String IMAGES_KEY = "images";
    public static final String IMAGE = "image";

    public static void showActivity(@NonNull AppCompatActivity activity, @NonNull View transitionImage, int position,
                                    @NonNull ArrayList<Image> images){
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGES_KEY, images);
        bundle.putInt(POSITION_KEY, position);
        Intent intent = new Intent(activity, GalleryActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());*/
    }

    @BindView(R.id.images)
    ViewPager mImagesViewPager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        //prepareWindowForAnimation();
        initToolbar();
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt(POSITION_KEY);
        Serializable serializable = bundle.getSerializable(IMAGES_KEY);
        if(serializable instanceof ArrayList) {
            ArrayList<Image> images = (ArrayList<Image>)serializable;
            initViewPager(position, images);
        }
        //ViewCompat.setTransitionName(mImagesViewPager, IMAGE);
    }

    private void initToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setTitle(R.string.images);
    }

    private void initViewPager(int position, List<Image> images){
        ImagesAdapter imagesAdapter = new ImagesAdapter(images);
        mImagesViewPager.setAdapter(imagesAdapter);
        mImagesViewPager.setCurrentItem(position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    /*private void prepareWindowForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(mImagesViewPager, true);
            transition.excludeChildren(mImagesViewPager, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }*/
}
