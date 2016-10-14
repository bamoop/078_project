package com_t.macvision.mv_078.ui.Device;

import android.app.Activity;
import android.util.Log;

import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.core.Constant;
import rx.Observable;

/**
 * Created by bzmoop on 2016/9/19 0019.
 */
public class ConnectPresonter extends BasePresonter<DeviceConnectView> {
    private static final String TAG = "ConnectPresonter";
    public static final String DEFAULT_RTSP_H264_PCM_URL  = "/liveRTSP/av4";

    public ConnectPresonter(Activity context, DeviceConnectView view) {
        super(context, view);
    }

    /**
     * 验证客户端与机器之间加密
     **/

    public void verifyKey(String property) {
        Observable<String> fromNetwrok = mDV.getInstruct( property).
                compose(RxResultHelper.<String>handleDeviceResult());
        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<String>(mContext, "正在加载中..") {
                    @Override
                    protected void _onNext(String request) {
                        Log.i(TAG, "记录仪请求结果:" + request);
                        String playUrl = null;
                        int av;
                        try {
                            av = Integer.parseInt(request.replace("Camera.Preview.RTSP.av=",""));
                        }catch (Exception e){
                            av = 4;
                            e.printStackTrace();
                        }
                        switch (av) {
                            case 4: //liveRTSP/av4 for RTSP H.264+PCM
                                playUrl = "rtsp://" + Constant.IP + DEFAULT_RTSP_H264_PCM_URL ;
                                break;
                            default:
                                break;
                        }
                        mView.getDataSuccess(playUrl);
                    }
                    @Override
                    protected void _onError(String message) {
                        Log.i(TAG, "记录仪请错误:" + message);
                        mView.getDateError(message);
                    }
                });


    }
}
