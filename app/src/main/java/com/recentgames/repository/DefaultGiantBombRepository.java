package com.recentgames.repository;

import com.recentgames.api.ApiFactory;
import com.recentgames.model.QueryParams;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.response.GiantBombResponse;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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
    public Observable<List<GamePreview>> search(String name) {
        return ApiFactory.getGiantBombService()
                .search(name, QueryParams.GAMES_FILED_LIST,20,0)
                .map(GiantBombResponse::getResults);
    }

    @Override
    public Observable<ReviewDescription> review() {
        return Observable.empty();
    }
}
