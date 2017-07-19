package com.example.guhao.myweather.ui.transition;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: GuHao
 * Date: 7/19/17
 * Time: 3:58 PM
 * Desc:
 */

public class CardReveal extends Visibility{
    private final String TAG = "";
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int height = view.getHeight();

        Log.d(TAG, "captureStartValues: "+ height);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
//        super.captureEndValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }


}
