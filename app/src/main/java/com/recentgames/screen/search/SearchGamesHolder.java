package com.recentgames.screen.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recentgames.R;
import com.recentgames.model.content.GamePreview;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchGamesHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    CircleImageView mImageView;

    @BindView(R.id.game_name)
    TextView mTextView;

    @NonNull
    public static SearchGamesHolder create(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_game, parent, false);
        return new SearchGamesHolder(view);
    }

    private SearchGamesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull GamePreview game) {

        if(game.getImage() != null) {
            Picasso.with(mImageView.getContext())
                    .load(game.getImage().getMediumUrl())
                    .noFade()
                    .into(mImageView);
        }

        mTextView.setText(game.getName());
    }
}
