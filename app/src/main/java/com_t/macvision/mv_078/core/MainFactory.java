package com_t.macvision.mv_078.core;



/**
 * Created by bzmoop on 2016/7/28 0028.
 */
public class MainFactory {

    private static ZHService mZHService;
    private static DeviceService mDVService;
    private static CloudService mCloudService;

    protected static final Object monitor = new Object();

    public static final ZHService getZHInstance(){
        synchronized (monitor){
            if (mZHService == null) {
                mZHService = new MainRetrofit().getmService();
            }
            return mZHService;
        }
    }

    public static final DeviceService getDVInstance(){
        synchronized (monitor){
            if (mDVService == null) {
                mDVService = new MainRetrofit().getmDvService();
            }
        }
        return mDVService;
    }
    public static final CloudService getCloudInstance(){
        synchronized (monitor){
            if (mCloudService == null) {
                mCloudService = new MainRetrofit().getCloudService();
            }
        }
        return mCloudService;
    }

}
