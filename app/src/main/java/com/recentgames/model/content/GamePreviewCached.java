package com.recentgames.model.content;

import java.util.List;

public class GamePreviewCached {

    private boolean mCached = false;

    private List<GamePreview> mGamePreviews;

    public GamePreviewCached(List<GamePreview> gamePreviews) {
        mGamePreviews = gamePreviews;
        mCached = false;
    }

    public GamePreviewCached(List<GamePreview> gamePreviews, boolean cached) {
        mGamePreviews = gamePreviews;
        mCached = cached;
    }

    public boolean isCached() {
        return mCached;
    }

    public List<GamePreview> getGamePreviews() {
        return mGamePreviews;
    }

}
