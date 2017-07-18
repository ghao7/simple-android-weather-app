package com.example.guhao.myweather.data.network;

import rx.Subscriber;

/**
 * Author: GuHao
 * Date: 7/5/17
 * Time: 3:42 PM
 * Desc:
 */

public class CitySubscriber<T> extends Subscriber<T>{
    private SubscriberOnNextListener subscriberOnNextListener;

    public CitySubscriber(SubscriberOnNextListener subscriberOnNextListener) {
        this.subscriberOnNextListener = subscriberOnNextListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        subscriberOnNextListener.onNext(t);
    }
}
