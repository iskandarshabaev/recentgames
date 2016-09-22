package com.recentgames.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (lastVisibleItemPosition == (totalItemCount - 1)) {
            onBottomReached();
        }
    }

    public abstract void onBottomReached();
}
