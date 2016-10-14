package com_t.macvision.mv_078.base;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;


import java.io.File;

import com_t.macvision.mv_078.base.IBaseView;
import com_t.macvision.mv_078.core.CloudService;
import com_t.macvision.mv_078.core.DeviceService;
import com_t.macvision.mv_078.core.MainFactory;
import com_t.macvision.mv_078.core.ZHService;
import com_t.macvision.mv_078.presenter.Presenter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bzmoop on 2016/9/9 0009.
 */
public class BasePresonter<GV extends IBaseView> implements Presenter {
    protected GV mView;
    /**
     * TODO 这里用是否用Activity待考证
     */
    public Activity mContext;

    public static final ZHService mZH = MainFactory.getZHInstance();
    public static final DeviceService mDV = MainFactory.getDVInstance();
    public static final CloudService mCloud = MainFactory.getCloudInstance();
    private CompositeSubscription mCompositeSubscription;
    public static String sAppDir = "";


    public BasePresonter(Activity context, GV view) {
        mContext = context;
        mView = view;
        sAppDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "互联视讯";
    }

    public static File getAppDir(int type) {
          File appDir = null;
        switch (type) {
            case 0:
                appDir = new File(sAppDir + "/Video");
                break;
            case 1:
                appDir = new File(sAppDir + "/LittleVideo");
                break;
            case 2:
                appDir = new File(sAppDir + "/Picture");
                break;
        }

        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir;
    }

    @Override
    public void attachView(Object view) {
        this.mView = mView;

    }

    @Override
    public void detachView() {
        this.mView = null;

    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}
