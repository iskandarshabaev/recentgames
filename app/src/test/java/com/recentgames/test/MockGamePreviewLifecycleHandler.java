package com.recentgames.test;

import android.support.annotation.NonNull;

import com.recentgames.rxloader.GamePreviewLifecycleHandler;

import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;

public class MockGamePreviewLifecycleHandler implements GamePreviewLifecycleHandler {

    @NonNull
    @Override
    public <T> Observable.Transformer<T, T> load(int id) {
        return observable -> observable;
    }

    @NonNull
    @Override
    public <T> Observable.Transformer<T, T> reload(int id) {
        return observable -> observable;
    }

    @NonNull
    @Override
    public <T> Observable.Transformer<T, T> add(int id) {
        return observable -> observable;
    }

    @Override
    public void clear(int id) {
        // Do nothing
    }
}
