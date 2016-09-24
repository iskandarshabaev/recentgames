package com.recentgames.screen.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.recentgames.model.content.GamePreview;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchGamesHolder> {

    private final List<GamePreview> mGames;
    private final OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GamePreview game = (GamePreview) view.getTag();
            mOnItemClickListener.onItemClick(view, game);
        }
    };

    private final View.OnTouchListener mInternalTouchListner = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mOnItemClickListener.onItemTouch(v,event);
            return true;
        }
    };

    public SearchAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mGames = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    public void changeDataSet(@NonNull List<GamePreview> games) {
        mGames.clear();
        mGames.addAll(games);
        notifyDataSetChanged();
    }

    @Override
    public SearchGamesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SearchGamesHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(SearchGamesHolder holder, int position) {
        GamePreview game = mGames.get(position);
        holder.bind(game);

        holder.itemView.setOnClickListener(mInternalListener);
        holder.itemView.setOnTouchListener(mInternalTouchListner);
        holder.itemView.setTag(game);
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public interface OnItemClickListener {

        void onItemClick(@NonNull View view, @NonNull GamePreview game);
        void onItemTouch(@NonNull View v, @NonNull MotionEvent event);
    }
}
