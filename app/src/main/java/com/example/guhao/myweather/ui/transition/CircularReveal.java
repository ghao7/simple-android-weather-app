package com.example.guhao.myweather.ui.transition;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.transition.Visibility;

/**
 * Author: GuHao
 * Date: 7/18/17
 * Time: 2:52 PM
 * Desc:
 */

public class CircularReveal extends Visibility{
    private Point center;

    public void setCenter(@NonNull Point center) {
        this.center = center;
    }
}
