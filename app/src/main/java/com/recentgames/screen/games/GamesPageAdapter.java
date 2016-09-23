package com.recentgames.screen.games;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.util.ImageHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GamePreview> mGames;
    private final OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GamePreview game = (GamePreview) view.getTag();
            mOnItemClickListener.onItemClick(view, game);
        }
    };

    public GamesPageAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mGames = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GameViewHolder holder = (GameViewHolder) viewHolder;
        GamePreview game = mGames.get(position);
        holder.bind(game);

        holder.mCover.setOnClickListener(mInternalListener);
        holder.mCover.setTag(game);
    }

    public void addGames(@NonNull List<GamePreview> games) {
        mGames.addAll(games);
        notifyDataSetChanged();
    }

    public void refreshGames(@NonNull List<GamePreview> games) {
        mGames.clear();
        addGames(games);
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public final static class GameViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.game_name)
        TextView mName;

        @BindView(R.id.game_cover)
        ImageView mCover;

        public GameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull GamePreview game) {
            mName.setText(game.getName());
            if (game.getImage() == null || game.getImage().getMediumUrl() == null) return;
            ImageHelper.loadImage(mCover, game.getImage().getMediumUrl());
        }
    }

    public interface OnItemClickListener {

        void onItemClick(@NonNull View view, @NonNull GamePreview game);

    }
}
