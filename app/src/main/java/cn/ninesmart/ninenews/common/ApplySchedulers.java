package cn.ninesmart.ninenews.common;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   : perqin
 * Date     : 17-1-13
 */

public final class ApplySchedulers {
    private ApplySchedulers() {
        // Prevent construction
    }

    public static <T>Observable.Transformer<T, T> network() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T>Observable.Transformer<T, T> storage() {
        return tObservable -> tObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
