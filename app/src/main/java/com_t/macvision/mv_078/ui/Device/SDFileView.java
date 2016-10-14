package com_t.macvision.mv_078.ui.Device;

import java.util.ArrayList;

import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.base.IBaseView;
import com_t.macvision.mv_078.ui.View.ISwipeRefreshView;

/**
 * Created by bzmoop on 2016/9/13 0013.
 */
public interface SDFileView extends DeviceBaseView {
    void fillData(ArrayList<FileEntity> data);


    void appendMoreDataToView(ArrayList<FileEntity> data);

    void hasNoMoreData();
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
