package com.recentgames.repository;

import com.recentgames.api.ApiFactory;
import com.recentgames.exception.LimitReachedException;
import com.recentgames.exception.RefreshException;
import com.recentgames.model.QueryParams;
import com.recentgames.model.content.GameDescription;
import com.recentgames.model.content.GamePreview;
import com.recentgames.model.content.GamePreviewCached;
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
                .flatMap(game -> {
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
    public Observable<GamePreviewCached> games(int type, int offset) {
        return ApiFactory.getGiantBombService()
                .games(
                        QueryParams.GAMES_FILED_LIST,
                        QueryParams.getFilter(type),
                        QueryParams.LIMIT_COUNT,
                        offset)
                .map(GiantBombResponse::getResults)
                .flatMap(this::cacheGamePreviews)
                .onErrorResumeNext(throwable -> getCachedGamePreviews(throwable, offset, type))
                .compose(RxSchedulers.async());
    }

    private Observable<GamePreviewCached> cacheGamePreviews(List<GamePreview> gamePreviews) {
        if (gamePreviews.isEmpty()) throw new LimitReachedException("No more games for you guys");
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.executeTransaction(realm -> realm.insertOrUpdate(gamePreviews));
        return Observable.just(new GamePreviewCached(gamePreviews));
    }

    private Observable<GamePreviewCached> getCachedGamePreviews(Throwable throwable, int offset, int type) {
        if (throwable instanceof LimitReachedException)
            throw new LimitReachedException("No more games for you guys");
        if (offset != 0) throw new RefreshException("Useless cache");
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GamePreview> repositories = realm.where(GamePreview.class)
                .between("mReleaseDate", QueryParams.getStartDate(type), QueryParams.getCurrentDate())
                .findAll();
        return Observable.just(new GamePreviewCached(realm.copyFromRealm(repositories), true));
    }

    @Override
    public Observable<List<GamePreview>> search(String name) {
        return ApiFactory.getGiantBombService()
                .search(name, QueryParams.GAMES_FILED_LIST, QueryParams.LIMIT_COUNT, QueryParams.RESOURCES)
                .map(GiantBombResponse::getResults)
                .compose(RxSchedulers.async());
    }

    @Override
    public Observable<ReviewDescription> review() {
        return Observable.empty();
    }
}
