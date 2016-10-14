package com_t.macvision.mv_078.ui.Device;/**
 * Created by bzmoop on 2016/8/24 0024.
 */

import android.app.Activity;
import android.util.Log;
import android.util.Xml;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.util.DateUtil;
import okhttp3.Call;
import rx.Observable;

/**
 * 作者：LiangXiong on 2016/8/24 0024 18:04
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class SDFilePresenter extends DeviceBasePresonter<SDFileView> {
    SweetAlertDialog tipDialog;
    SweetAlertDialog progressDialog;
    static final int MAXCOUNT = 16;
    static int mCurrentIndex = 0;
    static final String ACTION_DIR = "dir";//前路
    static final String ACTION_REARDIR = "reardir";//前路
    static final String PROPERTY_EVENT = "Event";//加锁
    static final String PROPERTY_PHOTO = "Photo";//照片
    static final String PROPERTY_DCIM = "DCIM";//照片
    static final String PROPERTY_Share = "Share";//照片
    public static final int FRONT_VIDOE = 0;
    public static final int REAR_VIDOE = 1;
    public static final int FRONT_EVENT = 1;
    public static final int REAR_EVENT = 3;
    public static final int FRONT_PHOTO = 2;
    public static final int REAR_PHOTO = 5;

    public void resetCurrentPage() {
        mCurrentIndex = 0;
    }

    public SDFilePresenter(Activity context, SDFileView mFileView) {
        super(context, mFileView);
    }

    public void getdata(boolean isappendMoreData, int dir) {
        switch (dir) {
            case FRONT_VIDOE:
                sendRequest(isappendMoreData, ACTION_DIR, PROPERTY_DCIM);
                break;
//            case REAR_VIDOE:
//                sendRequest(isappendMoreData, ACTION_REARDIR, PROPERTY_DCIM);
//                break;
            case FRONT_EVENT:
                sendRequest(isappendMoreData, ACTION_DIR, PROPERTY_EVENT);
                break;
//            case REAR_EVENT:
//                sendRequest(isappendMoreData, ACTION_REARDIR, PROPERTY_EVENT);
//                break;
            case FRONT_PHOTO:
                sendRequest(isappendMoreData, ACTION_DIR, PROPERTY_PHOTO);
                break;
//            case REAR_PHOTO:
//                sendRequest(isappendMoreData, ACTION_REARDIR, PROPERTY_PHOTO);
//                break;
        }
    }

    public void sendRequest(boolean isappendMoreData, String action, String property) {
        String url = "http://" + Constant.IP + Constant.CGI_PATH + "action="
                + action + "&property=" + property + "&format=all&count=" + MAXCOUNT + "&from=" + mCurrentIndex;
        Log.i("moopreq", "url: " + url);
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
                        Log.i("moopreq", "onError: " + id + "----" + e);
                        mView.showErrorView(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("moopreq", "onResponse: " + id + "----" + response);
                        try {
                            InputStream inputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
                            ArrayList<FileEntity> fileEntities = new ArrayList<FileEntity>();
                            Log.i("moopreq", "解析完成" + fileEntities.toString() + "长度");
                            fileEntities.addAll(praseXML(inputStream));
                            if (fileEntities.isEmpty()) {
                                mView.showEmptyView();
                                mView.hasNoMoreData();
                            } else {
                                if (fileEntities.size() < MAXCOUNT) {
                                    mView.hasNoMoreData();
                                } else if (fileEntities.size() == MAXCOUNT) {
                                    mCurrentIndex = mCurrentIndex + 16;
                                }
                                if (isappendMoreData)
                                    mView.appendMoreDataToView(fileEntities);
                                else mView.fillData(fileEntities);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            mView.getDataFinish();
                        }
                    }
                });
    }

    public static List<FileEntity> praseXML(InputStream inputStream) throws Exception {
        List<FileEntity> files = null;
        FileEntity fileEntity = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, "UTF-8");
        int event = parser.getEventType();
        //不等于结束事件，循环读取XML文件并封装成对象
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    files = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("file")) {
                        fileEntity = new FileEntity();
                    } else if (parser.getName().equals("name")) {
                        event = parser.next();
                        fileEntity.setPath(parser.getText());
//                        fileEntity.setName(parser.getText().replace("/SD/Normal/",""));
                        fileEntity.setName(parser.getText().substring(parser.getText().lastIndexOf("/") + 1));
                    } else if (parser.getName().equals("format")) {
                        event = parser.next();
                        fileEntity.setFormat(parser.getText());
                    } else if (parser.getName().equals("size")) {
                        event = parser.next();
                        fileEntity.setSize(DateUtil.FormetFileSize(Integer.parseInt(parser.getText())));
                    } else if (parser.getName().equals("attr")) {
                        event = parser.next();
                        fileEntity.setAttr(parser.getText());
                    } else if (parser.getName().equals("time")) {
                        event = parser.next();
                        fileEntity.setCreateTime(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("file")) {
                        files.add(fileEntity);
                        fileEntity = null;
                    }
                    break;
            }
            event = parser.next();
        }

        return files;
    }

    //    public void delete(ArrayList<FileEntity> fileEntities){
//
//    }
    public void delete(ArrayList<FileEntity> fileEntities) {
        String name = null;
        name = fileEntities.get(0).getPath().replaceAll("/", "\\$");
        Observable<String> fromNetwrok = mDV.deleteFile(name).
                compose(RxResultHelper.<String>handleDeviceResult());
        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<String>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(String s) {
                        Log.i("moopreq", "删除成功" + s);
                        fileEntities.remove(0);
                        if (fileEntities.isEmpty())
                            delete(fileEntities);
                        else
                            Log.i("moopreq", "全部删除完成" + s);
                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i("moopreq", "删除失败" + message);

                    }

                });
    }

    public void download(ArrayList<FileEntity> fileEntities) {
        String url = "http://" + Constant.IP + fileEntities.get(0).getPath();
        File appDir = getAppDir(0);
        File file = new File(appDir, fileEntities.get(0).getName());
        if (file.exists()) {
            tipDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
            tipDialog.setCancelable(false);
            tipDialog.setTitleText("文件已存在")
                    .setContentText("你确定要替换掉已存在的文件吗？")
                    .setCancelText(" 不 ")
                    .setConfirmText("确定")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            fileEntities.remove(0);
                            if (fileEntities.isEmpty()) {
                                Log.i("moopreq", "处理下载完成");
                            } else {
                                download(fileEntities);
                            }
                            sweetAlertDialog.dismiss();
                            return;
                        }
                    }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    file.delete();
                    if (fileEntities.isEmpty()) {
                        Log.i("moopreq", "处理下载完成");
                    } else {
                        download(fileEntities);
                    }
                    sweetAlertDialog.dismiss();
                }
            }).show();
        } else {
            showDialog();
            OkHttpUtils
                    .getInstance()
                    .get()
                    .url(url)
                    .tag("stopDownFile")
                    .build()
                    .execute(new FileCallBack(appDir.getPath() + File.separator, fileEntities.get(0).getName()) {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("moopreq", "下载失败" + e);
                        }

                        @Override
                        public void onResponse(File response, int id) {
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            Log.e("inProgress", "inProgress :" + (int) (100 * progress));
                            if ((int) (100 * progress) == 100) {
                                Log.i("moopreq", "下载完成");
                                fileEntities.remove(0);
                                progressDialog.dismiss();
                                if (fileEntities.isEmpty()) {
                                    Log.i("moopreq", "全部下载完成");
                                    new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("完成!")
                                            .setContentText("文件已经全部下载完成")
                                            .setConfirmText("确定")
                                            .setConfirmClickListener(null)
                                            .show();
                                } else {
                                    download(fileEntities);
                                }
                            }
                        }
                    });
        }
    }

    public void showDialog() {
        progressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText("正在下载中")
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
