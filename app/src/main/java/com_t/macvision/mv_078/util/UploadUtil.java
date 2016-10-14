package com_t.macvision.mv_078.util;/**
 * Created by bzmoop on 2016/8/19 0019.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com_t.macvision.mv_078.core.Constant;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.ui.Upload.UploadActivity;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：LiangXiong on 2016/8/19 0019 13:44
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UploadUtil {
    private static final String TAG = "UploadUtil";
    UploadStatu mUploadStatus;

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
//            setTitle("loading...");
            Log.i(TAG, "onBefore: 上传中");
        }

        @Override
        public void onAfter(int id) {
//            setTitle("Sample-okHttp");
            Log.i(TAG, "onAfter: 上传中");

        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            mTv.setText("onError:" + e.getMessage());
            Log.i(TAG, "onError: 上传失败" + e);

        }

        @Override
        public void onResponse(String response, int id) {
//            Log.e(TAG, "onResponse：complete");
//            mTv.setText("onResponse:" + response);
            Log.i(TAG, "上传响应" + response);

            switch (id) {
                case 100:
//                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
//                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
//            Log.e(TAG, "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }

    public void uploadFile(File file, HashMap<String, String> map, String url, Activity context) {

        SweetAlertDialog wattingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在上传中");
        wattingDialog.show();

        Log.i(TAG, (map.get(Constant.userId) == "") + "-" + Constant.userId + "-" + map.get(Constant.videoTitle) + "---" + map.get(Constant.videoReleaseAddress) + "====" + map.get(Constant.userId) + "======" + (map.get(Constant.userId) == ""));
        if (map.get(Constant.userId) == "" || map.get(Constant.userId) == null) {
            map.put(Constant.userId, "7000001");
            Log.i(TAG, "uploadFile: userID为空，上传失败");
        }
        if (map.get(Constant.videoTitle) == "" || map.get(Constant.videoTitle) == null)
            map.put(Constant.videoTitle, "默认标题");

        if (map.get(Constant.videoReleaseAddress) == "" || map.get(Constant.videoReleaseAddress) == null)
            map.put(Constant.videoReleaseAddress, "默认地址");

        if (map.get(Constant.videoCaption) == "" || map.get(Constant.videoCaption) == null)
            map.put(Constant.videoReleaseAddress, "这人很懒，什么都没说");

        if (map.get(Constant.videoType) == "" || map.get(Constant.videoType) == null)
            map.put(Constant.videoType, "1");

        map.put("token", "abcdefghijklmn");
        if (!file.exists()) {
            //文件不存在
            Log.i(TAG, "uploadFile: 文件不存在");
            return;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");
        OkHttpUtils.post()//
                .addFile(file.getName(), file.getName(), file)//
                .url(url)//
                .params(map)//
                .headers(headers)//
                .build()//
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Log.e(TAG, "inProgress:" + progress);
                        if ((int) (100 * progress) == 100) {
                            wattingDialog.setTitleText("上传完成!")
                                    .setConfirmText("确定")
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            wattingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    context.finish();
                                }
                            });
                        }
                    }
                });
    }

    interface UploadStatu {
        void UploadStart();

        /**
         * 上传成功
         **/
        void UploadSucceed();

        /**
         * 上传失败
         **/
        void UploadFail(Exception e);

        /**
         * 上传进度
         **/
        void UploadProgress(float progress, long total, int id);
    }


}
