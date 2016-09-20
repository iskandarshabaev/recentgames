package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.model.content.ReviewPreview;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    private List<ReviewPreview> mPreviews;
    private OnReviewClickListener mClickListener;

    public ReviewsAdapter(@NonNull List<ReviewPreview> previews,
                          @NonNull OnReviewClickListener clickListener) {
        mPreviews = previews;
        mClickListener = clickListener;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewsViewHolder(view);
    }

    public void changeDataSet(@NonNull List<ReviewPreview> previews) {
        mPreviews.clear();
        mPreviews.addAll(previews);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.bind(mPreviews.get(position));
        holder.mView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onClick(mPreviews.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPreviews.size();
    }

    public interface OnReviewClickListener {
        void onClick(ReviewPreview reviewPreview);
    }
}
