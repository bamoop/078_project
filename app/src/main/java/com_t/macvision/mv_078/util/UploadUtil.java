package com_t.macvision.mv_078.util;/**
 * Created by bzmoop on 2016/8/19 0019.
 */

import android.util.Log;

import com_t.macvision.mv_078.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：LiangXiong on 2016/8/19 0019 13:44
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UploadUtil {
    private static final String TAG = "UploadUtil";

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

    public void uploadFile(File file, Map<String, String> map,String url) {
        if (map.get(Constant.userId) == null) {
            Log.i(TAG, "uploadFile: userID为空，上传失败");
            map.put(Constant.userId, "7000001");
        }
        if (map.get(Constant.videoTitle) == null)
            map.put(Constant.videoTitle, "默认标题");

        if (map.get(Constant.videoReleaseAddress) == null)
            map.put(Constant.videoReleaseAddress, "默认地址");

        if (map.get(Constant.videoCaption) == null)
            map.put(Constant.videoReleaseAddress, "这人很懒，什么都没说");

        if (map.get(Constant.videoType) == null)
            map.put(Constant.videoType, "1");

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
                .execute(new MyStringCallback());
    }
}
