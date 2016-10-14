package com_t.macvision.mv_078.base;/**
 * Created by bzmoop on 2016/8/1 0001.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：LiangXiong on 2016/8/1 0001 16:34
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class BaseActivity<T extends BasePresonter> extends AppCompatActivity implements IBaseView {
    protected T mPresenter;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        Logger.init(this.getClass().getCanonicalName());
        setContentView(getLayout());
        ButterKnife.bind(this);
        initPresenter();
        initData();
        initView(null);

    }
    @Override
    public void initView(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            onUnsubscribe();
    }

    public void destroy(){};

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void initPresenter() {
        if (mPresenter == null)
            mPresenter = getPresenter();
    }

    protected T getPresenter() {
        return null;
    }



    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return 0;
    }
}
