package com_t.macvision.mv_078.ui.person_main;/**
 * Created by bzmoop on 2016/8/11 0011.
 */

import com_t.macvision.mv_078.base.BasePresenter;
import com_t.macvision.mv_078.base.BaseView;
import com_t.macvision.mv_078.model.entity.VideoEntity;

/**
 * 作者：LiangXiong on 2016/8/11 0011 11:28
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public interface DynamicContract {
    interface View extends BaseView<Presenter> {
        /**
         * 显示数据
         */
        void fillData(VideoEntity entity);

        /**
         * 没有更多数据
         */
        void hasNoMoreData();

        /**
         * 添加更多数据
         */
        void appendMoreDataToView(VideoEntity entity);

        /**
         * 数据加载完成
         */
        void getDataFinish();

        /**
         * 数据加载失败
         */
        void getDataFail();
    }

    interface Presenter extends BasePresenter {
        void getData(int userId, int page, boolean isgetDataMore);
    }

}
