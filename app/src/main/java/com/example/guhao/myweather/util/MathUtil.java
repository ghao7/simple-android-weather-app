package com.example.guhao.myweather.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Author: GuHao
 * Date: 7/20/17
 * Time: 1:36 PM
 * Desc:
 */

public class MathUtil {
    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}
