package com.recentgames.repository;

import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;

import java.util.List;

import rx.Observable;

public class DefaultGiantBombRepository implements GiantBombRepository {

    @Override
    public Observable<GameDescription> game() {
        return Observable.empty();
    }

    @Override
    public Observable<List<GamePreview>> games() {
        return Observable.empty();
    }

    @Override
    public Observable<List<GamePreview>> search() {
        return Observable.empty();
    }

    @Override
    public Observable<ReviewDescription> review() {
        return Observable.empty();
    }
}
