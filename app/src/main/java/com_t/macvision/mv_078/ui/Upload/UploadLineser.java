package com_t.macvision.mv_078.ui.Upload;/**
 * Created by bzmoop on 2016/8/19 0019.
 */

/**
 * 作者：LiangXiong on 2016/8/19 0019 19:09
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public interface UploadLineser {
    /**
     * 上传成功
     **/
    void upLoadFinish();

    /**
     * 上传失败
     **/
    void upLoadFail();

    /**
     * 上传中回调
     **/
    void UpLoad(float progress, long total, int id);
}
