package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.recentgames.R;
import com.recentgames.model.content.ReviewPreview;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_name)
    TextView mReviewName;

    public ReviewsViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(@NonNull ReviewPreview preview) {
        mReviewName.setText(preview.getName());
    }
}
