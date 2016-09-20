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
import io.realm.RealmResults;
import rx.Observable;

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
                            .findAll()
                            .first();
                    return Observable.just(realmInstance.copyFromRealm(game));
                })
                .compose(RxSchedulers.async());
    }

    @Override
    public Observable<List<GamePreview>> games(int type, int offset) {
        return ApiFactory.getGiantBombService()
                .games(
                        QueryParams.GAMES_FILED_LIST,
                        QueryParams.getFilter(type),
                        QueryParams.GAME_SORT_BY_REVIEWS_COUNT,
                        QueryParams.LIMIT_COUNT,
                        offset)
                .map(GiantBombResponse::getResults)
                .flatMap(this::cacheGamePreviews)
                .onErrorResumeNext(throwable -> getCachedGamePreviews(type))
                .compose(RxSchedulers.async());
    }

    private Observable<List<GamePreview>> cacheGamePreviews(List<GamePreview> gamePreviews) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.executeTransaction(realm -> realm.insert(gamePreviews));
        return Observable.just(gamePreviews);
    }

    private Observable<List<GamePreview>> getCachedGamePreviews(int type) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GamePreview> repositories = realm.where(GamePreview.class)
                .between("mReleaseDate", QueryParams.getStartDate(type), QueryParams.getCurrentDate())
                .findAll();
        return Observable.just(realm.copyFromRealm(repositories));
    }

    @Override
    public Observable<List<GamePreview>> search(String name) {
        /*int offset = 0;
        return ApiFactory.getGiantBombService()
                .search(name, QueryParams.GAMES_FILED_LIST,QueryParams.LIMIT,offset,QueryParams.RESOURCES)
                .map(GiantBombResponse::getResults);*/
        return Observable.empty();
    }

    @Override
    public Observable<ReviewDescription> review() {
        return Observable.empty();
    }
}
