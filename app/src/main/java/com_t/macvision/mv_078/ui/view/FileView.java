package com_t.macvision.mv_078.ui.View;

import java.util.ArrayList;

import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.base.IBaseView;

/**
 * Created by bzmoop on 2016/9/12 0012.
 */
public interface FileView extends IBaseView {

    void fillData(ArrayList<FileEntity> data);

    /**
     * 显示空的View
     */
    void showEmptyView();

    /**
     * 数据加载完成
     */
    void getDataFinish();

}
