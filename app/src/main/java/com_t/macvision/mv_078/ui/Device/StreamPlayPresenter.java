package com_t.macvision.mv_078.ui.Device;

import android.app.Activity;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.entity.FileEntity;
import okhttp3.Call;

/**
 * Created by bzmoop on 2016/9/19 0019.
 */
public class StreamPlayPresenter extends DeviceBasePresonter<StreamPlayerView> {
    private static final String TAG = "StreamPlayPresenter";
    SweetAlertDialog progressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
    SweetAlertDialog tipDialog;

    public StreamPlayPresenter(Activity context, StreamPlayerView view) {
        super(context, view);
    }

    public void inspectShardVideo() {
        String url = "http://" + Constant.IP + Constant.CGI_PATH + "action="
                + "dir" + "&property=" + "Share" + "&format=all&count=" + "10" + "&from=" + 0;
        OkHttpUtils
                .getInstance()
                .get()
                .url(url)
                .build()
                .connTimeOut(10000)
                .readTimeOut(10000)
                .writeTimeOut(10000)
                .connTimeOut(10000)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "小视频请求返回: " + response);

                        try {
                            InputStream inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
                            ArrayList<FileEntity> fileEntities = new ArrayList<FileEntity>();
                            fileEntities.addAll(SDFilePresenter.praseXML(inputStream));
                            if (!fileEntities.isEmpty())
                                contrastFile(fileEntities);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void contrastFile(ArrayList<FileEntity> fileEntities) {
        ArrayList<FileEntity> fileEntities1 = new ArrayList<>();
        File appDir = getAppDir(1);
        for (int i = 0; i < fileEntities.size(); i++) {
            File file = new File(appDir, fileEntities.get(i).getName());
            if (!file.exists()) {
                fileEntities1.add(fileEntities.get(i));
            }
        }
        if (!fileEntities1.isEmpty()) {
            tipDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
            tipDialog.setCancelable(false);
            tipDialog.setTitleText("提示")
                    .setContentText("检测到有"+fileEntities1.size()+"个新的小视频文件，是否马上同步到本地？")
                    .setCancelText(" 不 ")
                    .setConfirmText("确定")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    if (!progressDialog.isShowing())
                        showDialog();
                    sweetAlertDialog.dismiss();
                    downloadShareVideo(fileEntities1);
                }
            }).show();
        }

    }

    public void downloadShareVideo(ArrayList<FileEntity> fileEntities) {
        File appDir = getAppDir(1);
        if (fileEntities.isEmpty()) {
            mView.downloadShareVoideSuccess();
            progressDialog.dismiss();
            new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("完成!")
                    .setContentText("文件已经全部下载完成")
                    .setConfirmText("确定")
                    .setConfirmClickListener(null)
                    .show();
        } else {
            mView.downloadShareVideoing();
            String url = "http://" + Constant.IP + fileEntities.get(0).getPath();
            OkHttpUtils
                    .getInstance()
                    .get()
                    .url(url)
                    .tag("stopDownFile")
                    .build()
                    .execute(new FileCallBack(appDir.getPath() + File.separator, fileEntities.get(0).getName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(File response, int id) {
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            super.inProgress(progress, total, id);
                            if ((int) (100 * progress) == 100) {
                                fileEntities.remove(0);
                                downloadShareVideo(fileEntities);
                            }
                        }
                    });
        }
    }


    public void showDialog() {
        progressDialog.setTitleText("正在同步小视频")
                .showCancelButton(true)
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        OkHttpUtils.getInstance().cancelTag("stopDownFile");
                        sweetAlertDialog.dismiss();
                        Log.i("moopreq", "取消下载");
                    }
                }).show();
    }
}
