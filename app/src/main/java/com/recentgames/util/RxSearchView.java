package com.recentgames.util;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * Created by rodionov on 20.09.2016.
 */
public class RxSearchView extends SearchView {

    private PublishSubject<String> mEditTextSubject = PublishSubject.create();
    private Subscription mSubscription;

    private final static int DEBOUNCE_TIMEOUT = 300;

    public RxSearchView(Context context) {
        super(context);
    }

    public RxSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RxSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnRxQueryTextListener(OnRxQueryTextListener listener) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        setOnQueryTextListener(new OnQueryTextListenerImpl());

        mSubscription = mEditTextSubject.asObservable()
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .doOnNext(listener::onRxQueryTextChange)
                .subscribe();
    }


    private class OnQueryTextListenerImpl implements OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            if (mEditTextSubject != null) {
                mEditTextSubject.onNext(newText);
            }

            return true;
        }
    }

    public interface OnRxQueryTextListener {
        void onRxQueryTextChange(String newText);
    }
}
