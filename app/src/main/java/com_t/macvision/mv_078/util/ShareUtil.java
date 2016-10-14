package com_t.macvision.mv_078.util;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.media.UMImage;

import com_t.macvision.mv_078.model.entity.VideoEntity;
import simbest.com.sharelib.IShareCallback;
import simbest.com.sharelib.ShareModel;
import simbest.com.sharelib.ShareUtils;

/**
 * User : LiangXiong(LiangXiong.sz@foxmail.com)
 * Date : 2016-09-27
 * Time : 15:26
 * QQ   : 294894105 ZH
 * About: 友盟分享封装
 */

public class ShareUtil  {
    private static ShareUtils su;

    public static void openShare(Activity activity,VideoEntity videoEntity) {
        su = new ShareUtils(activity);
        ShareModel model = new ShareModel();
        model.setTitle("互联视讯行车记录仪：" + videoEntity.getVideoTitle());
        model.setContent(videoEntity.getVideoCaption());
        model.setImageMedia(new UMImage(activity, "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"));
//        model.setVideoMedia(new UMVideo( "http://192.168.1.124/demo/video/webView?videoId="+videoEntity.getVideoId()));
        model.setTagUrl("http://192.168.1.124/demo/video/webView?videoId=" + videoEntity.getVideoId());
        su.share(model, new IShareCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaild() {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
