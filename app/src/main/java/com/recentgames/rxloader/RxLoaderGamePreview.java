package com.recentgames.rxloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import com.recentgames.model.content.GamePreview;

import java.util.List;

import rx.AsyncEmitter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.MainThreadSubscription;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
class RxLoaderGamePreview<T> extends Loader<List<GamePreview>> {

    private Observable<List<GamePreview>> mObservable;

    private AsyncEmitter<List<GamePreview>> mEmitter;

    private Subscription mSubscription;

    @Nullable
    private List<GamePreview> mData;

    private boolean mIsErrorReported = false;

    @Nullable
    private Throwable mError;

    private boolean mIsCompleted = false;

    private boolean mAddData = false;

    void addData(boolean addData) {
        mAddData = addData;
    }

    void changeObservable(Observable observable) {
        mObservable = observable;
    }

    public RxLoaderGamePreview(@NonNull Context context, @NonNull Observable observable) {
        super(context);
        mObservable = observable;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        subscribe();
    }

    @Override
    protected void onStopLoading() {
        /**
         * TODO : allow configure clearing subscription and caching policy in release 0.2.0
         */
        super.onStopLoading();
    }

    @Override
    protected void onReset() {
        clearSubscription();
        mObservable = null;
        mData = null;
        mError = null;
        mIsCompleted = false;
        mIsErrorReported = false;
        mEmitter = null;
        super.onReset();
    }

    private void subscribe() {
        if (mEmitter != null && mSubscription == null && !mIsCompleted && mError == null || mAddData) {
            mSubscription = mObservable.subscribe(new LoaderSubscriber());
        }
    }

    @NonNull
    Observable<List<GamePreview>> createObservable() {
        return Observable.fromEmitter(new Action1<AsyncEmitter<List<GamePreview>>>() {
            @Override
            public void call(AsyncEmitter<List<GamePreview>> asyncEmitter) {
                mEmitter = asyncEmitter;
                mEmitter.setSubscription(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        clearSubscription();
                    }
                });

                /**
                 * TODO : fix in 0.2.0
                 *
                 * here is possible data loosing if Observable emits items too quickly
                 * since we cache only last result, previous items could be lost during rotation
                 */
                if (mData != null && !mAddData) {
                    mEmitter.onNext(mData);
                }
                if (mIsCompleted && !mAddData) {
                    mEmitter.onCompleted();
                } else if (mError != null && !mIsErrorReported) {
                    mEmitter.onError(mError);
                    mIsErrorReported = true;
                }

                subscribe();
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

    private void clearSubscription() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    private class LoaderSubscriber extends Subscriber<List<GamePreview>> {

        @Override
        public void onNext(List<GamePreview> d) {
            if (mAddData && mData != null) {
                mData.addAll(d);
            } else if (mData == null) {
                mData = d;
            }
            if (mEmitter != null && isStarted()) {
                if (mAddData) {
                    mEmitter.onNext(d);
                } else {
                    mEmitter.onNext(mData);
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            mError = throwable;
            if (mEmitter != null && isStarted()) {
                mEmitter.onError(throwable);
                mIsErrorReported = true;
            }
        }

        @Override
        public void onCompleted() {
            mIsCompleted = true;
            if (mEmitter != null && isStarted()) {
                mEmitter.onCompleted();
            }
        }
    }
}