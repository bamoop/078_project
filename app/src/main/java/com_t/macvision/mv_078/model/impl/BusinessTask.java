package com_t.macvision.mv_078.model.impl;

import com_t.macvision.mv_078.core.VideoListService;
import com_t.macvision.mv_078.util.HttpUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bzmoop on 2016/8/11 0011.
 */
public class BusinessTask {

    public void getVideoList(Subscriber<String> subscriber, int currentPage, int pageSize, int type) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).getVideoListData(currentPage, pageSize, type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getVideoDetail(Subscriber<String> subscriber, int videoID) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).getVideoDetail(videoID)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getVideoCommentList(Subscriber<String> subscriber, int videoID, int page, int pageSize) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).getCommentList(videoID, page, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getType(Subscriber<String> subscriber) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).getAllType()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getDynamicList(Subscriber<String> subscriber, int userId, int page, int pageSize, String category) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).getDynamicList(userId, page, pageSize, category)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getPersonHome(Subscriber<String> subscriber, int userId) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).userDetail(userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void saveComment(Subscriber<String> subscriber, String token, String cmContent, String userId, String cmVideoId, String beReplyUserId) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).saveComment(token, cmContent, userId, cmVideoId, beReplyUserId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void clickLike(Subscriber<String> subscriber, String videoId, String userId) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).clickLike(videoId, userId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void payAttention(Subscriber<String> subscriber, String token, String myUserId, String userId, String operation) {
        HttpUtils.getInstance().initRetrofit().create(VideoListService.class).payAttention(token, myUserId, userId,operation)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
