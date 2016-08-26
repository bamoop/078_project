package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/26 0026.
 */

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.model.impl.BusinessTask;
import com_t.macvision.mv_078.ui.person_main.PersonHomeContract;
import com_t.macvision.mv_078.util.GsonUtil;
import rx.Subscriber;

/**
 * 作者：LiangXiong on 2016/8/26 0026 10:46
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class PersonHomePresenter implements PersonHomeContract.Presenter {
    private PersonHomeContract.View mView;
    private BusinessTask mTask;

    public PersonHomePresenter(PersonHomeContract.View view) {
        this.mView = view;
        mTask = new BusinessTask();
    }

    @Override
    public void getData(int userId) {
        getPersonHomeData(userId);
    }

    private void getPersonHomeData(int userId) {
        mTask.getPersonHome(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("moop815", "onError: " + e);

            }

            @Override
            public void onNext(String s) {
                Log.i("moop815", "onNext: " + s);
                Logger.i(s);
                if (!TextUtils.isEmpty(s)) {
                    mView.fillData(GsonUtil.changeGsonToBean(s, UserEntity.class));
                }
                mView.getDataFinish();
            }
        }, userId);
    }
}
