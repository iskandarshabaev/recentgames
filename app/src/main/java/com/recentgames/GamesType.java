package com.recentgames;

import android.support.annotation.IntDef;

//GameType == position at viewpager
@IntDef({GamesType.WEEK, GamesType.MONTH, GamesType.YEAR})
public @interface GamesType {
    int WEEK = 0;
    int MONTH = 1;
    int YEAR = 2;
}
