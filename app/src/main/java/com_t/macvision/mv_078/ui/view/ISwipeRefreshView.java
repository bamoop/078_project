package com_t.macvision.mv_078.ui.View;

import com_t.macvision.mv_078.base.IBaseView;

/**
 * ListVie列表刷新
 * Created by bzmoop on 2016/7/29 0029.
 */
public interface ISwipeRefreshView extends IBaseView {
    /**
     *加载数据完成
     * */
    void getDataFinish();
    /**
     * 显示空的View
     */
    void showEmptyView();
    /**
     * 显示错误提示的view
     */
    void showErrorView(String massage);
    /**
     * 显示刷新
     */
    void showRefresh();
    /**
     * 隐藏刷新
     */
    void hideRefresh();

}
