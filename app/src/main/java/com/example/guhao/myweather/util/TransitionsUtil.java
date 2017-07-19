package com.example.guhao.myweather.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionSet;

/**
 * Author: GuHao
 * Date: 7/18/17
 * Time: 2:50 PM
 * Desc:
 */

public class TransitionsUtil {
    public static @Nullable
    Transition findTransition(
            @NonNull TransitionSet set,
            @NonNull Class<? extends Transition> clazz,
            @IdRes int targetId) {
        for (int i = 0; i < set.getTransitionCount(); i++) {
            Transition transition = set.getTransitionAt(i);
            if (transition.getClass() == clazz) {
                if (transition.getTargetIds().contains(targetId)) {
                    return transition;
                }
            }
            if (transition instanceof TransitionSet) {
                Transition child = findTransition((TransitionSet) transition, clazz, targetId);
                if (child != null) return child;
            }
        }
        return null;
    }
}
