package com_t.macvision.mv_078.core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com_t.macvision.mv_078.util.scalars.ScalarsConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by bzmoop on 2016/7/29 0029.
 */
public class MainRetrofit {

    final ZHService mService;
    final DeviceService mDvService;
    final CloudService mCloudService;

//    private static final String HEADER_X_HB_Client_Type = "X-HB-Client-Type";
//    private static final String FROM_ANDROID = "ayb-android";
    //统一日期格式请求


    MainRetrofit() {
        // log拦截器  打印所有的log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        //设置 请求的缓存
//        File cacheFile = new File(Application.getInstance().getCacheDir(), "cache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
//                .addInterceptor(mInterceptor)
//                .cache(cache)
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .callFactory(client)
                .baseUrl(Constant.DeviceBaseUrl)
//                .baseUrl(Constant.BaseVideoListUrl)
                .build();
        mDvService = retrofit1.create(DeviceService.class);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .callFactory(client)
                .baseUrl(Constant.BaseVideoListUrl)
                .build();
        mService = retrofit.create(ZHService.class);

        Retrofit retrofit2= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .callFactory(client)
                .baseUrl(Constant.BaseCloudUrl)
                .build();
        mCloudService = retrofit2.create(CloudService.class);




    }


    public DeviceService getmDvService() {
        return mDvService;
    }

    public ZHService getmService() {
        return mService;
    }

    public CloudService getCloudService() {
        return mCloudService;
    }


    /**
     * 拦截器  给所有的请求添加消息头
     */
    private static Interceptor mInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
//                    .addHeader(HEADER_X_HB_Client_Type, FROM_ANDROID)
                    .build();
            return chain.proceed(request);
        }
    };
}
