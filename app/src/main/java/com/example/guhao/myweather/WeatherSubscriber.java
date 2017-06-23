package com.example.guhao.myweather;

import android.content.Context;

import rx.Subscriber;

/**
 * Author: GuHao
 * Date: 6/23/17
 * Time: 5:53 PM
 * Desc:
 */

public class WeatherSubscriber<T> extends Subscriber<T> {
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;

    public WeatherSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }
}
