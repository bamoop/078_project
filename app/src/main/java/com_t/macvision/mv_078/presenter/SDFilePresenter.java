package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/24 0024.
 */

import android.app.Activity;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com_t.macvision.mv_078.Constant;
import com_t.macvision.mv_078.ui.Device.SDFileContract;
import com_t.macvision.mv_078.util.UploadUtil;
import okhttp3.Call;

/**
 * 作者：LiangXiong on 2016/8/24 0024 18:04
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class SDFilePresenter implements SDFileContract.Presenter {

    static final int MAXCOUNT = 16;
    private SDFileContract.View mView;

    public SDFilePresenter(SDFileContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getData(String dir, Activity context, int from) {
        String url = "http://" + Constant.IP + Constant.DEFAULT_PATH + "?action=dir&property=" +
                Constant.m_DCIM + "&format=all&count=" + MAXCOUNT + "&from=" + from;
        Log.i("moopreq", "getData: " + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .connTimeOut(10000)
                .readTimeOut(10000)
                .writeTimeOut(10000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("moopreq", "onError: " + id + "----" + e);
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.i("moopreq", "onResponse: " + id + "----" + response);
                        try {
                            InputStream updatedStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
                            Document document = factory.newDocumentBuilder().parse(updatedStream) ;

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void delete(String name) {

    }

    @Override
    public void download(String name) {

    }

}
