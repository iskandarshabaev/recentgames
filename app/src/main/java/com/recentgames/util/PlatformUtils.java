package com.recentgames.util;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.recentgames.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Iskandar on 19.09.2016.
 */
public class PlatformUtils {

    public static final int PC = 94;
    public static final int XBOX360 = 20;
    public static final int XBOX = 32;
    public static final int XBOX360GS = 86;
    public static final int XBOX_ONE = 145;
    public static final int PS2 = 19;
    public static final int PS3 = 35;
    public static final int PS4 = 146;
    public static final int WII = 36;
    public static final int MAC = 17;
    public static final int IPHONE = 96;
    public static final int IPAD = 121;
    public static final int ANDROID = 123;
    public static final int WIIU = 139;

    public static final List<Integer> platforms =
            Arrays.asList(PC, XBOX_ONE, PS4, WIIU, ANDROID, IPHONE, MAC);

    @DrawableRes
    public static int getDrawableResId(int id) {
        switch (id) {
            case PC:
                return R.drawable.pc;
            case XBOX:
                return R.drawable.xbox;
            case XBOX360:
                return R.drawable.xbox;
            case XBOX_ONE:
                return R.drawable.xbox;
            case XBOX360GS:
                return R.drawable.xbox;
            case MAC:
                return R.drawable.mac;
            case IPAD:
                return R.drawable.iphone;
            case ANDROID:
                return R.drawable.android;
            case WII:
                return R.drawable.wiiu;
            case WIIU:
                return R.drawable.wiiu;
            case IPHONE:
                return R.drawable.iphone;
            case PS2:
                return R.drawable.ps4;
            case PS3:
                return R.drawable.ps4;
            case PS4:
                return R.drawable.ps4;
            default:
                return R.drawable.wiiu;
        }
    }

    @ColorRes
    public static int getColorResId(int id) {
        switch (id) {
            case PC:
                return R.color.pc;
            case XBOX:
                return R.color.xbox;
            case XBOX360:
                return R.color.xbox;
            case XBOX_ONE:
                return R.color.xbox;
            case XBOX360GS:
                return R.color.xbox;
            case MAC:
                return R.color.black;
            case IPAD:
                return R.color.black;
            case ANDROID:
                return R.color.android;
            case WII:
                return R.color.amber_500;
            case WIIU:
                return R.color.amber_500;
            case IPHONE:
                return R.color.black;
            case PS2:
                return R.color.black;
            case PS3:
                return R.color.black;
            case PS4:
                return R.color.black;
            default:
                return R.color.black;
        }
    }
}
