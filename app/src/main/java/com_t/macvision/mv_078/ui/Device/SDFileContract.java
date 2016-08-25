package com_t.macvision.mv_078.ui.Device;/**
 * Created by bzmoop on 2016/8/24 0024.
 */

import android.app.Activity;

import java.io.File;
import java.util.List;

import com_t.macvision.mv_078.base.BasePresenter;
import com_t.macvision.mv_078.base.BaseView;
import com_t.macvision.mv_078.model.DeviceModle.FileNode;

/**
 * 作者：LiangXiong on 2016/8/24 0024 17:39
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class SDFileContract {
   public interface View extends BaseView<Presenter> {
        /**
         * 显示数据
         */
        void fillData(List<FileNode> nodeList);

        /**
         * 没有更多数据
         */
        void hasNoMoreData();

        /**
         * 添加更多数据
         */
        void appendMoreDataToView(List<FileNode> nodeList);

        /**
         * 数据加载完成
         */
        void getDataFinish();

        /**
         * 数据加载失败
         */
        void getDatafail();

    }

    public interface Presenter extends BasePresenter {

        void getData(String dir, Activity context,int from);

        void delete(String name);

        void download(String name);
    }
}
