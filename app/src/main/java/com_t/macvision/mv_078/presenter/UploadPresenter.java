package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/18 0018.
 */

import android.app.Activity;

import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.ui.Upload.UploadView;
import com_t.macvision.mv_078.util.UploadUtil;

import java.io.File;
import java.util.HashMap;

/**
 * 作者：LiangXiong on 2016/8/18 0018 22:05
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UploadPresenter extends BasePresonter<UploadView> {
    private static final String TAG = "UploadPresenter";

    public UploadPresenter(Activity context, UploadView view) {
        super(context, view);
    }


    public void getData(File file) {

    }

    public void upload(HashMap<String, String> map, FileEntity fileEntity) {
        new UploadUtil().uploadFile(new File(fileEntity.getPath()),map, Constant.UploadUrl,mContext);
    }
}
