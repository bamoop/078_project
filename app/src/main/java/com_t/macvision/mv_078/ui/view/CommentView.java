package com_t.macvision.mv_078.ui.View;

import java.util.ArrayList;

import com_t.macvision.mv_078.base.BaseModel;

/**
 * Created by bzmoop on 2016/9/13 0013.
 */
public interface CommentView<T extends BaseModel> extends ISwipeRefreshView {
    /**
     * load data successfully
     * @param data
     */
    void fillData(ArrayList<T> data);

    /**
     * append data to history list(load more)
     * @param data
     */
    void appendMoreDataToView(ArrayList<T> data);

    /**
     * no more data
     */
    void hasNoMoreData();

    void saveCommentSucceed();
    void saveCommentFail();
}
