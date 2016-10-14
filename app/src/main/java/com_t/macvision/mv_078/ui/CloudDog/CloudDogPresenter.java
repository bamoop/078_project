package com_t.macvision.mv_078.ui.CloudDog;

import android.app.Activity;
import android.util.Log;


import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.model.CloudModel.AppTokenModel;
import com_t.macvision.mv_078.model.CloudModel.CloudNullEntity;
import rx.Observable;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-22
 * Time: 16:56
 * QQ  : 294894105 ZH
 */

public class CloudDogPresenter extends BasePresonter<CloudDogView> {
    private static final String TAG = "CloudDogPresenter";

    public CloudDogPresenter(Activity context, CloudDogView view) {
        super(context, view);
    }

    public void login(String userName, String passWord) {
        Log.i("token01", "云狗登陆请求");

        Observable<AppTokenModel> fromNetwrok = mCloud.login(userName, passWord).
                compose(RxResultHelper.<AppTokenModel>handleCloudResult());
        RxRetrofitCache.load(mContext, "", 0, fromNetwrok, false)
                .subscribe(new RxSubscribe<AppTokenModel>(mContext, "正在登陆..") {
                    @Override
                    protected void _onNext(AppTokenModel appTokenModel) {
                        mView.loginSuccess(appTokenModel.getToken());
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.loginError(message);
                    }
                });
    }

    public void getAppToken(String appid, String passWord, String time) {

        Observable<AppTokenModel> fromNetwrok = mCloud.getToken(appid, passWord, time).
                compose(RxResultHelper.<AppTokenModel>handleCloudResult());
        RxRetrofitCache.load(mContext, "", 0, fromNetwrok, false)
                .subscribe(new RxSubscribe<AppTokenModel>(mContext, "正在登陆..") {
                    @Override
                    protected void _onNext(AppTokenModel cloudNullEntity) {
                        mView.getAppTokenSuccess(cloudNullEntity.getToken());
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.getAppTokenError(message);
                    }
                });
    }

}
