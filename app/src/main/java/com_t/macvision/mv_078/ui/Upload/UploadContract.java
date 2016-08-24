package com_t.macvision.mv_078.ui.Upload;/**
 * Created by bzmoop on 2016/8/18 0018.
 */

import com_t.macvision.mv_078.base.BasePresenter;
import com_t.macvision.mv_078.base.BaseView;
import com_t.macvision.mv_078.model.entity.FileEntity;

import java.io.File;
import java.util.Map;

/**
 * 作者：LiangXiong on 2016/8/18 0018 21:59
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public interface UploadContract {
    interface View extends BaseView<Presenter> {

        /**
         * 上传数据完成
         **/
        void uploadDataFinish();

        /**
         * 上传数据失败
         **/
        void uploadDataFail();
    }

    interface Presenter extends BasePresenter {
        void getData(File file);
        void upload(Map<String,String> map,FileEntity fileEntity);
    }
}
