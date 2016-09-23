package com.recentgames.rxloader;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.recentgames.model.content.GamePreviewCached;

import rx.Observable;

public class GamePreviewLoaderLifecycleHandler implements GamePreviewLifecycleHandler {

    private final Context mContext;
    private final LoaderManager mLoaderManager;

    /**
     * Creates a new instance of {@link GamePreviewLifecycleHandler}
     * You don't have to store it somewhere in a variable, since it has no state
     *
     * @param context       - typically it's your activity instance
     * @param loaderManager - loader manager of your activity or fragment
     * @return instance of GamePreviewLifecycleHandler
     */
    @NonNull
    public static GamePreviewLifecycleHandler create(@NonNull Context context, @NonNull LoaderManager loaderManager) {
        return new GamePreviewLoaderLifecycleHandler(context, loaderManager);
    }

    private GamePreviewLoaderLifecycleHandler(@NonNull Context context, @NonNull LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
    }

    @NonNull
    @Override
    public Observable.Transformer<GamePreviewCached, GamePreviewCached> load(@IdRes final int loaderId) {
        return new Observable.Transformer<GamePreviewCached, GamePreviewCached>() {
            @Override
            public Observable<GamePreviewCached> call(final Observable<GamePreviewCached> observable) {
                if (mLoaderManager.getLoader(loaderId) == null) {
                    mLoaderManager.initLoader(loaderId, Bundle.EMPTY, new RxLoaderCallbacks<>(observable));
                }
                final RxLoaderGamePreview loader = (RxLoaderGamePreview) mLoaderManager.getLoader(loaderId);
                loader.addData(false);
                return loader.createObservable();
            }
        };
    }

    @NonNull
    @Override
    public Observable.Transformer<GamePreviewCached, GamePreviewCached> reload(@IdRes final int loaderId) {
        return new Observable.Transformer<GamePreviewCached, GamePreviewCached>() {
            @Override
            public Observable<GamePreviewCached> call(final Observable<GamePreviewCached> observable) {
                mLoaderManager.restartLoader(loaderId, Bundle.EMPTY, new RxLoaderCallbacks<>(observable));
                final RxLoaderGamePreview<GamePreviewCached> loader = (RxLoaderGamePreview) mLoaderManager.getLoader(loaderId);
                return loader.createObservable();
            }
        };
    }

    @NonNull
    public Observable.Transformer<GamePreviewCached, GamePreviewCached> add(@IdRes final int loaderId) {
        return new Observable.Transformer<GamePreviewCached, GamePreviewCached>() {
            @Override
            public Observable<GamePreviewCached> call(final Observable<GamePreviewCached> observable) {
                if (mLoaderManager.getLoader(loaderId) == null) {
                    mLoaderManager.initLoader(loaderId, Bundle.EMPTY, new RxLoaderCallbacks<>(observable));
                }
                final RxLoaderGamePreview loader = (RxLoaderGamePreview) mLoaderManager.getLoader(loaderId);
                loader.addData(true);
                loader.changeObservable(observable);
                return loader.createObservable();
            }
        };
    }

    @Override
    public void clear(int id) {
        mLoaderManager.destroyLoader(id);
    }

    private class RxLoaderCallbacks<D> implements LoaderManager.LoaderCallbacks<D> {

        private final Observable<D> mObservable;

        public RxLoaderCallbacks(@NonNull Observable<D> observable) {
            mObservable = observable;
        }

        @NonNull
        @Override
        public Loader<D> onCreateLoader(int id, Bundle args) {
            return new RxLoaderGamePreview(mContext, mObservable);
        }

        @Override
        public void onLoadFinished(Loader<D> loader, D data) {
            // Do nothing
        }

        @Override
        public void onLoaderReset(Loader<D> loader) {
            // Do nothing
        }
    }
}
