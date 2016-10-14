package com_t.macvision.mv_078.presenter;

import android.app.Activity;
import com.orhanobut.logger.Logger;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.ui.View.PersonHomeView;
import rx.Observable;

/**
 * 作者：LiangXiong on 2016/8/26 0026 10:46
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class PersonHomePresenter extends BasePresonter<PersonHomeView> {
    private static final String TAG = "PersonHomePresenter";

    public PersonHomePresenter(Activity context, PersonHomeView mFileView) {
        super(context, mFileView);
    }

    public void getData(int userId) {
        getPersonHomeData(userId);
    }

    private void getPersonHomeData(int userId) {
        Observable<UserEntity> fromNetwrok = mZH.userDetail(userId).
                compose(RxResultHelper.handleServerResult());

        RxRetrofitCache.load(mContext, "cacheKey_user", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<UserEntity>(mContext, "正在加载中..") {

                               @Override
                               protected void _onNext(UserEntity userEntities) {
                                   Logger.i("用户信息"+userEntities.toString());
                                   mView.fillData(userEntities);
                                   mView.getDataFinish();
                               }

                               @Override
                               protected void _onError(String message) {
                                   Logger.i("用户信息请求失败："+message);

                               }
                });

    }
}
