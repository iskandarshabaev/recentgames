package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.recentgames.R;
import com.recentgames.model.content.GamePreview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarGamesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_name)
    TextView mReviewName;

    @BindView(R.id.icon)
    ImageView mIconImageView;

    View mView;

    public SimilarGamesViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
    }

    public void bind(@NonNull GamePreview game) {
        mReviewName.setText(game.getName());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        String text = game.getName().substring(0, 2);
        int color = generator.getColor(game.getName());
        TextDrawable drawable = TextDrawable.builder().buildRound(text, color);
        mIconImageView.setImageDrawable(drawable);
    }
}
