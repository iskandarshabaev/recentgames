package com.recentgames.repository;

import com.recentgames.api.ApiFactory;
import com.recentgames.model.QueryParams;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.ReviewDescription;
import com.recentgames.model.response.GiantBombResponse;
import com.recentgames.util.RxSchedulers;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

public class DefaultGiantBombRepository implements GiantBombRepository {

    @Override
    public Observable<GameDescription> game(int gameId) {
        return ApiFactory.getGiantBombService()
                .game(gameId, QueryParams.GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .flatMap(game ->{
                    Realm realmInstance = Realm.getDefaultInstance();
                    realmInstance.executeTransaction(realm -> realm.insert(game));
                    return Observable.just(game);
                })
                .compose(RxSchedulers.async());
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
