package com_t.macvision.mv_078.ui.View;

import java.util.ArrayList;

import com_t.macvision.mv_078.base.IBaseView;
import com_t.macvision.mv_078.model.entity.UserEntity;

/**
 * Created by bzmoop on 2016/9/13 0013.
 */
public interface PersonHomeView extends IBaseView {
    void fillData(UserEntity data);

    /**
     * 显示空的View
     */
    void showEmptyView();

    /**
     * 数据加载完成
     */
    void getDataFinish();
}
