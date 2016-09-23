package com.recentgames.screen.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;

import java.util.List;

public class SimilarGamesAdapter extends RecyclerView.Adapter<SimilarGamesViewHolder> {

    private List<GamePreview> mGames;
    private OnReviewClickListener mClickListener;

    public SimilarGamesAdapter(@NonNull List<GamePreview> games,
                               @NonNull OnReviewClickListener clickListener) {
        mGames = games;
        mClickListener = clickListener;
    }

    @Override
    public SimilarGamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new SimilarGamesViewHolder(view);
    }

    public void changeDataSet(@NonNull List<GamePreview> games) {
        mGames.clear();
        mGames.addAll(games);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SimilarGamesViewHolder holder, int position) {
        holder.bind(mGames.get(position));
        holder.mView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onClick(mGames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public interface OnReviewClickListener {
        void onClick(GamePreview preview);
    }
}
