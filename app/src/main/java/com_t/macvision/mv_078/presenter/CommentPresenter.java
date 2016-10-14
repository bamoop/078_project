package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/25 0025.
 */

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.ui.View.IMainVideoView;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import rx.Observable;

/**
 * 作者：LiangXiong on 2016/8/25 0025 15:44
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class CommentPresenter extends BasePresonter<IMainVideoView> {
    private static final String TAG = "CommentPresenter";
    private int mCurrentPage = 1;
    private static final int PAGE_SIZE = 2;

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }

    public CommentPresenter(Activity context, IMainVideoView view) {
        super(context, view);
    }

    public void getVideoList(int userId, boolean isappendMoreData) {

        Observable<ArrayList<VideoEntity>> fromNetwrok = mZH.getUserDynamicList(userId, mCurrentPage, PAGE_SIZE, "").
                compose(RxResultHelper.<ArrayList<VideoEntity>>handleServerResult());

        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<ArrayList<VideoEntity>>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(ArrayList<VideoEntity> videoEntity_ts) {
//                        Log.i(TAG, videoEntity_ts.toString());
                        Log.i(TAG, "_onNext" + mCurrentPage);
                        if (videoEntity_ts.isEmpty()) {
                            mView.showEmptyView();
                            mView.hasNoMoreData();
                        } else {
                            if (videoEntity_ts.size() < PAGE_SIZE) {
                                mView.hasNoMoreData();
                            } else if (videoEntity_ts.size() == PAGE_SIZE) {
                                mCurrentPage++;
                            }
                            if (isappendMoreData)
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
