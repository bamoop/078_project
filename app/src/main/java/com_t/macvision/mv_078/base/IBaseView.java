package com_t.macvision.mv_078.base;

import android.view.View;

/**
 * 基础View接口
 * Created by xybcoder on 2016/3/1.
 */
public interface IBaseView {

    /**
     * 初始化
     */
    void initView(View view);

    void initData();

    int getLayout();

}