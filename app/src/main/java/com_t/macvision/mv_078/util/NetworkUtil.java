package com_t.macvision.mv_078.util;/**
 * Created by bzmoop on 2016/8/24 0024.
 */

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

/**
 * 作者：LiangXiong on 2016/8/24 0024 13:43
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class NetworkUtil {

    public static String getIP(Context context) {
        String mIp = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo != null && dhcpInfo.gateway != 0) {
            mIp = ((dhcpInfo.gateway & 0xFF) + "." +
                    ((dhcpInfo.gateway >>>= 8) & 0xFF) + "." +
                    ((dhcpInfo.gateway >>>= 8) & 0xFF) + "." +
                    ((dhcpInfo.gateway >>>= 8) & 0xFF));
        }
        return mIp;
    }
}
