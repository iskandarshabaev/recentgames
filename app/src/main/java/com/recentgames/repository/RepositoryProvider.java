package com.recentgames.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class RepositoryProvider {

    private static GiantBombRepository sGiantBombRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static GiantBombRepository provideGiantBombRepository() {
        if (sGiantBombRepository == null) {
            sGiantBombRepository = new DefaultGiantBombRepository();
        }
        return sGiantBombRepository;
    }

    public static void setGiantBombRepository(@NonNull GiantBombRepository giantBombRepository) {
        sGiantBombRepository = giantBombRepository;
    }

    @MainThread
    public static void init() {
        sGiantBombRepository = new DefaultGiantBombRepository();
    }
}
