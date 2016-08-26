package com_t.macvision.mv_078.ui.person_main;/**
 * Created by bzmoop on 2016/8/26 0026.
 */

import com_t.macvision.mv_078.base.BasePresenter;
import com_t.macvision.mv_078.base.BaseView;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;

/**
 * 作者：LiangXiong on 2016/8/26 0026 10:42
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public interface PersonHomeContract {

    interface View extends BaseView<Presenter> {
        /**
         * 数据加载完成
         */
        void getDataFinish();

        /**
         * 数据加载失败
         */
        void getDataFail();

        /**
         * 显示数据
         */
        void fillData(UserEntity entity);
    }

    interface Presenter extends BasePresenter {
        void getData(int userId);
    }
}
