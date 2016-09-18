package com.recentgames.utils;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class RxSchedulersHooks {

    private static RxAndroidSchedulersHook hook;

    public static void immediate(){
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());
        if(hook == null){
            hook = new RxAndroidSchedulersHook() {
                @Override
                public Scheduler getMainThreadScheduler() {
                    return Schedulers.immediate();
                }
            };
            RxAndroidPlugins.getInstance().registerSchedulersHook(hook);
        }
    }
}
