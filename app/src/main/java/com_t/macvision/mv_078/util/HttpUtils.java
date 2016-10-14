package com_t.macvision.mv_078.util;

/**
 * Created by bzmoop on 2016/8/11 0011.
 */

import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.util.scalars.ScalarsConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 作者：LiangXiong on 2016/8/11 0011 12:08
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class HttpUtils {
    private static final int DEFAULT_TIMEOUT = 5;
    private static HttpUtils mHttpUtils;

    private HttpUtils() {

    }

    //获取单例
    public static HttpUtils getInstance(){
        if(mHttpUtils==null){
            synchronized (HttpUtils.class){
                if(mHttpUtils==null){
                    mHttpUtils = new HttpUtils();
                }
            }
        }
        return mHttpUtils;
    }



    public Retrofit initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BaseVideoListUrl)
                .build();
        return retrofit;
    }
}
