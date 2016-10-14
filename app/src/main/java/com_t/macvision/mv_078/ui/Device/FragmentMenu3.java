package com_t.macvision.mv_078.ui.Device;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.core.MainActivity;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseToolbarFragment;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;
import com_t.macvision.mv_078.util.WIFIUtil;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentMenu3 extends BaseToolbarFragment {
    @Bind(R.id.play_stream)
    RelativeLayout play_stream;
    private WIFIUtil mWIFIUtil;

    @Override
    public int getLayout() {
        return R.layout.fragment_menu3;
    }


    @Override
    public void initView(View view) {
        super.initView(view);
        play_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MainActivity.startActivity(getMyActivity(), bundle, WIFIConnect_Activity.class);

            }
        });
    }

    @Override
    protected String setTitle() {
        return "连接记录仪";
    }

    @Override
    public void initData() {
        super.initData();
        mWIFIUtil = new WIFIUtil(currentContext);
        int wifiNetId=mWIFIUtil.getNetworkId();
        Logger.i("getNetworkId=" + wifiNetId);
        Logger.i("getIPAddress=" + mWIFIUtil.getIPAddress());
        Logger.i("getWifiInfo=" + mWIFIUtil.getWifiInfo());
        Logger.i("checkState=" + mWIFIUtil.checkState());
        if (wifiNetId >= 0)
            SharedPreferencesUtils.setParam(currentContext, "wifiNetId", wifiNetId);
        else
            SharedPreferencesUtils.setParam(currentContext, "wifiNetId", "-1");

    }
}
