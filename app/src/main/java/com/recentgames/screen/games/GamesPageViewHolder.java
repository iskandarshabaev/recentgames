package com.recentgames.screen.games;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.recentgames.util.ImageHelper;
import com.recentgames.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

class GamesPageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.game_name)
    TextView mName;

    @BindView(R.id.game_cover)
    SquareImageView mCover;

    public GamesPageViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull GamePreview game) {
        if (mName != null) mName.setText(game.getName());
        ImageHelper.loadImage(mCover, game);
    }
}