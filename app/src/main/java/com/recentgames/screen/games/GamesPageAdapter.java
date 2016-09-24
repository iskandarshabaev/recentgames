package com.recentgames.screen.games;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;

import java.util.ArrayList;
import java.util.List;

class GamesPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
        return new GamesPageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GamesPageViewHolder holder = (GamesPageViewHolder) viewHolder;
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

    public interface OnItemClickListener {

        void onItemClick(@NonNull View view, @NonNull GamePreview game);

    }
}
