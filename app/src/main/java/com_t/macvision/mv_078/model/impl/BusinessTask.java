package com_t.macvision.mv_078.model.impl;

import com_t.macvision.mv_078.core.ZHService;
import com_t.macvision.mv_078.util.HttpUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bzmoop on 2016/8/11 0011.
 */
public class BusinessTask {

    public void getVideoDetail(Subscriber<String> subscriber, int videoID) {
        HttpUtils.getInstance().initRetrofit().create(ZHService.class).getVideoDetail(videoID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getType(Subscriber<String> subscriber) {
        HttpUtils.getInstance().initRetrofit().create(ZHService.class).getAllType()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
