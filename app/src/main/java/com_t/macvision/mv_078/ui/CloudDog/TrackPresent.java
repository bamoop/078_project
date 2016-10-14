package com_t.macvision.mv_078.ui.CloudDog;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.model.CloudModel.CloudNullEntity;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;
import com_t.macvision.mv_078.model.entity.NullEntity;
import rx.Observable;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-24
 * Time: 09:35
 * QQ  : 294894105 ZH
 */

public class TrackPresent extends BasePresonter<TreckView> {
    private static final String TAG = "VideoDetailPresenter";

    public TrackPresent(Activity context, TreckView view) {
        super(context, view);
    }

    public void getLocation(String userToken, String imei, String begin, String end) {
        Observable<ArrayList<LocationModel>> fromNetwrok = mCloud.history(userToken, imei, begin, end).
                compose(RxResultHelper.<ArrayList<LocationModel>>handleCloudResult());
        RxRetrofitCache.load(mContext, "loactionCacheKey", 10, fromNetwrok, false)
                .subscribe(new RxSubscribe<ArrayList<LocationModel>>(mContext, "正在加载历史轨迹") {
                    @Override
                    protected void _onNext(ArrayList<LocationModel> locationModel) {
                        Log.i(TAG, "获取定位成功");
                        mView.getTrackSuccess(locationModel);
                        if (locationModel.isEmpty()) {
                            mView.getTrackEmpty();
                        }else mView.getTrackSuccess(locationModel);
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i(TAG, "获取定位失败" + message);
                        mView.getTrackError(message);

                    }
                });
    }
}
