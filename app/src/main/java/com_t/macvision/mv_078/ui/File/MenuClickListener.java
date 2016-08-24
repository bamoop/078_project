package com_t.macvision.mv_078.ui.File;

/**
 * Created by bzmoop on 2016/8/18 0018.
 */
public interface MenuClickListener {
    /**
     * 编辑是否有效
     **/
    boolean menuEditEnable();

    /**
     * 点击操作
     **/
    void onClickCancel();

    void onClickEdit();

    void onClickDelete();

    void selectAll();
}
