package com.recentgames.util;

import com.recentgames.screen.LoadingView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxSchedulers {

    public static <T> Observable.Transformer<T, T> async(){
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
