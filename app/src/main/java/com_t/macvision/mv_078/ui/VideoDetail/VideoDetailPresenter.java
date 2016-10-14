package com_t.macvision.mv_078.ui.VideoDetail;/**
 * Created by bzmoop on 2016/8/11 0011.
 */

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.model.entity.CommentEntity;
import com_t.macvision.mv_078.model.entity.NullEntity;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.ui.View.CommentView;
import rx.Observable;

/**
 * 作者：LiangXiong on 2016/8/11 0011 18:06
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class VideoDetailPresenter extends BasePresonter<CommentView> {
    private static final String TAG = "VideoDetailPresenter";
    private int mCurrentPage = 1;
    private static final int PAGE_SIZE = 5;


    public VideoDetailPresenter(Activity context, CommentView view) {
        super(context, view);
    }

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }


    public void saveComment(String token, String cmContent, String userId, String cmVideoId, String beReplyUserId, String beReplyUserName) {
        Observable<NullEntity> fromNetwrok = mZH.saveComment(token, cmContent, userId, cmVideoId, beReplyUserId, beReplyUserName).
                compose(RxResultHelper.<NullEntity>handleServerResult());
        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<NullEntity>(mContext, "正在评论..") {
                    @Override
                    protected void _onNext(NullEntity videoEntity_ts) {
                        Log.i(TAG, "评论成功");
                        mView.saveCommentSucceed();
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i(TAG, "评论失败" + message);
                        mView.saveCommentFail();
                    }
                });
    }

    public void clickLike(String videoId, String userId) {

    }

    public void shareVideo() {

    }


    public void getCommentList(int videoId, Boolean isgetDataMore) {
        Observable<ArrayList<CommentEntity>> fromNetwrok = mZH.getCommentList(videoId, mCurrentPage, PAGE_SIZE).
                compose(RxResultHelper.<ArrayList<CommentEntity>>handleServerResult());

        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<ArrayList<CommentEntity>>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(ArrayList<CommentEntity> videoEntity_ts) {
                        Log.i(TAG, "评论返回" + videoEntity_ts.toString());

                        if (videoEntity_ts.isEmpty()) {
                            mView.showEmptyView();
                            mView.hasNoMoreData();
                        } else {
                            if (videoEntity_ts.size() < PAGE_SIZE) {
                                mView.hasNoMoreData();
                            } else if (videoEntity_ts.size() == PAGE_SIZE) {
                                mCurrentPage++;
                            }
                            if (isgetDataMore)
                                mView.appendMoreDataToView(videoEntity_ts);
                            else
                                mView.fillData(videoEntity_ts);
                        }
                        mView.getDataFinish();
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i(TAG, "_onError" + message);
                        mView.showErrorView(message);
                    }
                });
    }


}
