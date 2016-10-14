package com_t.macvision.mv_078.ui.Device;

import android.app.Activity;
import android.util.Log;

import com_t.macvision.mv_078.core.RxResultHelper;
import com_t.macvision.mv_078.core.RxRetrofitCache;
import com_t.macvision.mv_078.core.RxSubscribe;
import com_t.macvision.mv_078.base.BasePresonter;
import rx.Observable;

/**
 * Created by bzmoop on 2016/9/19 0019.
 */
public class DeviceBasePresonter<VV extends DeviceBaseView > extends BasePresonter {
    public VV mView;

    private static final String TAG = "DeviceBasePresonter";
    public static String COMMAND_VIDEOCAPTURE = "capture";
    public static String PROPERTY_VIDEORECORD = "Video";
    public static String PROPERTY_SOUND = "SoundIndicator";
    public static String PROPERTY_PARK = "Camera.Menu.ParkGsensorLevel";
    public static String PROPERTY_LDWS = "Camera.Menu.LDWS";
    public static String PROPERTY_FCWS = "Camera.Menu.FCWS";
    public static String PROPERTY_RES = "Videores";
    public static String PROPERTY_CLIPTIME = "VideoClipTime";
    public static String PROPERTY_TVOUT = "Camera.Cruise.ActiveSeq";
    public static String PROPERTY_FLICKER = "Flicker";
    public static String PROPERTY_FORMATSD = "SD0";
    public static String PROPERTY_FACTORYRESET = "FactoryReset";
    public static String PROPERTY_SWITCHCamera = "Camera.Preview.Source.1.Camid";
    public static String RECORD_START = "record_start";
    public static String RECORD_STOP = "record_stop";
    public static String RECORD_SHORT = "rec_short";


    public DeviceBasePresonter(Activity context, VV view) {
        super(context, view);
        mView = view;

    }

    /**
     * 录像开关
     **/
    public void setRecord(boolean record_start) {
        if (record_start)
            sendRequest(PROPERTY_VIDEORECORD, RECORD_START);
        else sendRequest(PROPERTY_VIDEORECORD, RECORD_STOP);
    }

    /**
     * 录音开关
     **/
    public void setSound(boolean sound_on) {
        if (sound_on)
            sendRequest(PROPERTY_SOUND, "ON");
        else sendRequest(PROPERTY_SOUND, "OFF");
    }

    /**
     * 抓拍
     **/
    public void snapshot() {
        sendRequest(PROPERTY_VIDEORECORD, COMMAND_VIDEOCAPTURE);
    }
    /**
     * 小视频抓拍
     **/
    public void minVideo() {
        sendRequest(PROPERTY_VIDEORECORD, RECORD_SHORT);
    }

    /**
     * 停车监控
     **/
    public void setPark(boolean park_on) {
        if (park_on)
            sendRequest(PROPERTY_PARK, "1");
        else sendRequest(PROPERTY_PARK, "0");
    }

    /**
     * 车道偏移
     **/
    public void setLDWS(boolean LDWS_on) {
        if (LDWS_on)
            sendRequest(PROPERTY_LDWS, "1");
        else sendRequest(PROPERTY_LDWS, "0");

    }

    /**
     * 移动侦测
     **/
    public void setFCWS(boolean FCWS_on) {
        if (FCWS_on)
            sendRequest(PROPERTY_FCWS, "1");
        else sendRequest(PROPERTY_FCWS, "0");
    }

    /**
     * 录像质量
     **/
    public void setVideoRes(int level) {
        String defaultValue = "1296P30";
        switch (level) {
            case 0:
                defaultValue = "1296P30";
                break;
            case 1:
                defaultValue = "1296P30";
                break;
            case 2:
                defaultValue = "1296P30";
                break;
            default:
                break;
        }
        sendRequest(PROPERTY_RES, defaultValue);
    }

    /**
     * 视频分段
     **/
    public void setVideoClipTime(int level) {
        String defaultValue = "1MIN";
        switch (level) {
            case 0:
                defaultValue = "1MIN";
                break;
            case 1:
                defaultValue = "3MIN";
                break;
            case 2:
                defaultValue = "5MIN";
                break;
            default:
                break;
        }
        sendRequest(PROPERTY_CLIPTIME, defaultValue);
    }

    /**
     * 视频输出
     **/
    public void setTVOUT(int level) {
        String defaultValue = "0";
        switch (level) {
            case 0:
                defaultValue = "0";
                break;
            case 1:
                defaultValue = "1";
                break;
            case 2:
                defaultValue = "2";
                break;
            default:
                break;
        }
        sendRequest(PROPERTY_TVOUT, defaultValue);
    }

    /**
     * 闪烁频率
     **/
    public void setFlicker(int level) {
        String defaultValue = "50Hz";
        switch (level) {
            case 0:
                defaultValue = "50Hz";
                break;
            case 1:
                defaultValue = "60Hz";
                break;
            case 2:
                defaultValue = "70Hz";
                break;
            default:
                break;
        }
        sendRequest(PROPERTY_FLICKER, defaultValue);
    }

    /**
     * 格式化SD卡
     **/
    public void setFormatSDCard() {
        sendRequest(PROPERTY_FORMATSD,"format");

    }

    /**
     * 恢复出厂设置
     **/
    public void resetDevice() {
        sendRequest(PROPERTY_FORMATSD,"Camera");

    }

    public void sendRequest(String property, String value) {
        Observable<String> fromNetwrok = mDV.setInstruct(property, value).
                compose(RxResultHelper.<String>handleDeviceResult());
        RxRetrofitCache.load(mContext, "cacheKey", 10 * 60 * 60, fromNetwrok, false)
                .subscribe(new RxSubscribe<String>(mContext, "") {
                    @Override
                    protected void _onNext(String s) {
                        Log.i("http", "指令成功: " + property+"value="+value);

                    }

                    @Override
                    protected void _onError(String message) {
                        Log.i("http", "指令失败: " + property+"value="+value+"/\n"+message);

                    }

                });
    }
}
