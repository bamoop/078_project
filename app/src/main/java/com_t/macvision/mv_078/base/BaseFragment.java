package com_t.macvision.mv_078.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bzmoop on 2016/9/9 0009.
 */
public class BaseFragment<T extends BasePresonter> extends Fragment implements IBaseView {

    public Activity currentContext;
    public View view;
    protected T mPresenter;
    private boolean isFirstLoadData = true;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, view);
            if (mPresenter == null)
                initPresenter();
            initView(view);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 子类实现加载数据的方法
     */
    public void loadData() {
        isFirstLoadData = false;
    }

    /**
     * 可见
     */
    protected void onVisible() {
        if (mPresenter == null)
            initPresenter();
        if (isFirstLoadData)
            loadData();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.currentContext = activity;
    }

    public Activity getMyActivity() {
        return currentContext;
    }

    private CompositeSubscription mCompositeSubscription;

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
    }

    @Override
    public int getLayout() {
        return 0;
    }

    protected T getPresenter() {
        return null;
    }

    public void initPresenter() {
        if (mPresenter == null)
            mPresenter = getPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            onUnsubscribe();
    }


}
