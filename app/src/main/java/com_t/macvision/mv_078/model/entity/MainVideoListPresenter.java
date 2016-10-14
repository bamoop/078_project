package com_t.macvision.mv_078.model.entity;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.ui.View.IMainVideoView;
import rx.Observable;

/**
 * Created by bzmoop on 2016/9/9 0009.
 */
public class MainVideoListPresenter extends BasePresonter<IMainVideoView> {
    private static final String TAG = "MainVideoListPresenter";

    private int mCurrentPage = 1;
    private static final int PAGE_SIZE = 5;

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }

    /**
     * 只有当前只加载了第一页 那么下拉刷新才应该去执行数据请求，如果加载的页数超过两页，
     * 则不去执行重新加载的数据请求，此时的刷新为假刷新，不去请求数据。这是一种良好的用户体验。愚以为~
     *
     * @return
     */
    public boolean shouldRefillGirls() {
        return mCurrentPage <= 2;
    }

    public MainVideoListPresenter(Activity context, IMainVideoView view) {
        super(context, view);
    }

    public void getVideoList( int type, boolean isappendMoreData) {
        Log.i(TAG, "请求的type=" + type);

        Observable<ArrayList<VideoEntity>> fromNetwrok = mZH.getVideoListData(mCurrentPage, PAGE_SIZE, type, Constant.TEST_USERID).
                compose(RxResultHelper.<ArrayList<VideoEntity>>handleServerResult());

        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<ArrayList<VideoEntity>>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(ArrayList<VideoEntity> videoEntity_ts) {
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

    public void clicklike(String voideID,String userID){
        Observable<NullEntity> fromNetwrok=mZH.clickLike(voideID,"7000018").compose(RxResultHelper.handleServerResult());
        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<NullEntity>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(NullEntity nullEntity) {
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                });
    }

}
