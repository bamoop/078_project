package com_t.macvision.mv_078.ui.Upload;

import com_t.macvision.mv_078.base.IBaseView;

/**
 * Created by bzmoop on 2016/9/21 0021.
 */
public interface UploadView extends IBaseView {

        /**
         * 上传数据完成
         **/
        void uploadDataFinish();

        /**
         * 上传数据失败
         **/
        void uploadDataFail();

}
