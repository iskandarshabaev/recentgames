package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.recentgames.R;
import com.recentgames.model.content.ReviewPreview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_name)
    TextView mReviewName;

    @BindView(R.id.icon)
    ImageView mIconImageView;

    View mView;

    public ReviewsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
    }

    public void bind(@NonNull ReviewPreview preview) {
        mReviewName.setText(preview.getName());
        if (preview.getName() != null) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(preview.getName());
            String text = preview.getName().substring(0, 2);
            TextDrawable drawable = TextDrawable.builder().buildRound(text, color);
            mIconImageView.setImageDrawable(drawable);
        }
    }
}
