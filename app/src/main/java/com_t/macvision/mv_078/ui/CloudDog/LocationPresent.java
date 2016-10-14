package com_t.macvision.mv_078.ui.CloudDog;

import android.app.Activity;
import android.util.Log;

import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;
import com_t.macvision.mv_078.model.entity.NullEntity;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;
import rx.Observable;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-24
 * Time: 09:35
 * QQ  : 294894105 ZH
 */

public class LocationPresent extends BasePresonter<LocationView> {
    private static final String TAG = "VideoDetailPresenter";

    public LocationPresent(Activity context, LocationView view) {
        super(context, view);
    }

    public void getLocation(String userToken, String imei) {
        Observable<LocationModel> fromNetwrok = mCloud.location(userToken, imei).
                compose(RxResultHelper.<LocationModel>handleCloudResult());
        RxRetrofitCache.load(mContext, "loactionCacheKey", 10, fromNetwrok, false)
                .subscribe(new RxSubscribe<LocationModel>(mContext, "正在获取实时位置") {
                    @Override
                    protected void _onNext(LocationModel locationModel) {
                        Log.i(TAG, "获取定位成功");
                        mView.getLocationSuccess(locationModel);
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i(TAG, "获取定位失败" + message);
                        mView.getLocationError(message);

                    }
                });
    }
}
