package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/18 0018.
 */

import com_t.macvision.mv_078.Constant;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.ui.Upload.UploadContract;
import com_t.macvision.mv_078.util.UploadUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LiangXiong on 2016/8/18 0018 22:05
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UploadPresenter implements UploadContract.Presenter {
    private UploadContract.View mUploadView;

    public UploadPresenter(UploadContract.View mUploadView) {
        this.mUploadView = mUploadView;
    }

    @Override
    public void getData(File file) {

    }

    @Override
    public void upload(HashMap<String, String> map, FileEntity fileEntity) {
        new UploadUtil().uploadFile(new File(fileEntity.getPath()),map, Constant.UploadUrl);
    }
}
