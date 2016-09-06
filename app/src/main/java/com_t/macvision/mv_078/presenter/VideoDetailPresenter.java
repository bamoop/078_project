package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/11 0011.
 */

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.model.entity.CommentEntity;
import com_t.macvision.mv_078.model.entity.VideoDetailEntity;
import com_t.macvision.mv_078.model.impl.BusinessTask;
import com_t.macvision.mv_078.ui.VideoDetail.VideoDetailContract;
import com_t.macvision.mv_078.util.GsonUtil;
import rx.Subscriber;

/**
 * 作者：LiangXiong on 2016/8/11 0011 18:06
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class VideoDetailPresenter implements VideoDetailContract.Presenter {
    private BusinessTask mVideoTask;
    private VideoDetailContract.View mVideoView;
    private final static int PAGESIZE = 10;

    public VideoDetailPresenter(VideoDetailContract.View mVideoView) {
        this.mVideoView = mVideoView;
        mVideoTask = new BusinessTask();
    }

    @Override
    public void getData(int videoID) {
//        getVideoDetail(videoID);
    }

    @Override
    public void getComment(int videoid, int page, Boolean isgetDataMore) {
        getCommentList(videoid, page, PAGESIZE, isgetDataMore);
    }

    @Override
    public void saveComment(String token, String cmContent, String userId, String cmVideoId, String beReplyUserId) {
        mVideoTask.saveComment(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                mVideoView.saveCommentFill(e);

            }

            @Override
            public void onNext(String s) {
                mVideoView.saveCommentFinish();
                Log.i("moop","评论成功返回=" + s);

            }
        },token,cmContent,userId,cmVideoId,beReplyUserId);

    }

    @Override
    public void clickLike( String videoId, String userId) {

    }

    @Override
    public void shareVideo() {

    }


    public void getCommentList(int videoId, int page, int pageSize, Boolean isgetDataMore) {
        Log.i("comment", "getCommentList:");
        mVideoTask.getVideoCommentList(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("comment", "onCompleted:");
            }
            @Override
            public void onError(Throwable e) {
                mVideoView.getDataFinish();
            }
            @Override
            public void onNext(String s) {
                if (!TextUtils.isEmpty(s)) {
                    if (isgetDataMore)
                        mVideoView.appendMoreDataToView(GsonUtil.changeGsonToBean(s, CommentEntity.class));
                    else
                        mVideoView.fillCommentData(GsonUtil.changeGsonToBean(s, CommentEntity.class));
                    if (GsonUtil.changeGsonToBean(s, CommentEntity.class).getData().size() < PAGESIZE) {
                        mVideoView.hasNoMoreData();
                    }
                }
                mVideoView.getDataFinish();

            }
        }, videoId, page, pageSize);
    }


}
