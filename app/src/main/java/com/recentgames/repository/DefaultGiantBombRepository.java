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
import io.realm.RealmList;
import rx.Observable;
import rx.functions.Func1;

public class DefaultGiantBombRepository implements GiantBombRepository {

    @Override
    public Observable<GameDescription> game(int gameId) {
        return ApiFactory.getGiantBombService()
                .game(gameId, QueryParams.GAME_FILED_LIST)
                .map(GiantBombResponse::getResults)
                .flatMap(game ->{
                    Realm realmInstance = Realm.getDefaultInstance();
                    realmInstance.executeTransaction(realm -> realm.insertOrUpdate(game));
                    return Observable.just(game);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realmInstance = Realm.getDefaultInstance();
                    GameDescription game = realmInstance.where(GameDescription.class)
                            .equalTo("mId", gameId)
                            .findFirst();
                    if(game != null) {
                        return Observable.just(realmInstance.copyFromRealm(game));
                    }else {
                        return Observable.error(new Exception("Load failed"));
                    }
                })
                .compose(RxSchedulers.async());
    }

    @Override
    public Observable<List<GamePreview>> games() {
        return Observable.empty();
    }

    @Override
    public Observable<List<GamePreview>> search(String name) {
        int offset = 0;
        return ApiFactory.getGiantBombService()
                .search(name, QueryParams.GAMES_FILED_LIST,QueryParams.LIMIT,offset,QueryParams.RESOURCES)
                .map(GiantBombResponse::getResults);
    }

    @Override
    public Observable<ReviewDescription> review() {
        return Observable.empty();
    }
}
