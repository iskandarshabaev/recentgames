package com.recentgames.screen.games;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recentgames.R;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int FOOTER_COUNT = 1;

    private static final int TYPE_BOTTOM = 2;
    private static final int TYPE_ITEM = 1;

    private List<Object> mGames;

    public GamesAdapter(List<Object> games) {
        mGames = games;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_BOTTOM) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
            return new BottomViewHolder(itemView);
        }

        throw new RuntimeException("There is no type that matches the type " +
                viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void addFooter() {
        FOOTER_COUNT++;
    }

    public void removeFooter() {
        FOOTER_COUNT = 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) {
            return TYPE_BOTTOM;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mGames.size() + FOOTER_COUNT;
    }

    public boolean isFooter(int position) {
        return position == mGames.size();
    }

    private final static class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    private final static class BottomViewHolder extends RecyclerView.ViewHolder {
        BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
