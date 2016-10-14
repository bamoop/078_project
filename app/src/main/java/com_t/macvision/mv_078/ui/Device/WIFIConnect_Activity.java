package com_t.macvision.mv_078.ui.Device;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.util.NetworkUtil;

public class WIFIConnect_Activity extends BaseActivity<ConnectPresonter> implements DeviceConnectView {
    private static final String TAG = "WIFIConnect_Activity";

    @Bind(R.id.image_tutorial1)
    ImageView image_tutorial1;
    @Bind(R.id.image_tutorial2)
    ImageView image_tutorial2;
    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.btn_connect)
    LinearLayout btn_connect;
    boolean clickConnect = false;

    @Override
    public int getLayout() {

        if (NetworkUtil.isNetworkConnected())
            Log.i(TAG, "有可用网络");
        else
            Log.i(TAG, "无可用网络");

        return R.layout.activity_wificonnect;
    }

    @Override
    public void initPresenter() {
        mPresenter = new ConnectPresonter(this, this);

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        Glide.with(this).load(R.mipmap.tutorial1).into(image_tutorial1);
        Glide.with(this).load(R.mipmap.tutorial2).into(image_tutorial2);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickConnect = true;
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                } else {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "网络类型=" + NetworkUtil.getNetworkType() + "Constant.IP=" + Constant.IP);
        if (NetworkUtil.getNetworkType().equals("WIFI") && clickConnect) {
            mPresenter.verifyKey("Camera.Preview.RTSP.av");
        } else if (!clickConnect)
            finish();
    }

    @Override
    public void getDateError(String massage) {

    }

    @Override
    public void getDataSuccess(String playUrl) {
        Intent intent = new Intent(WIFIConnect_Activity.this, StreamPlayerActivity.class);
        intent.putExtra("playUrl", playUrl);
        clickConnect = false;
        startActivity(intent);
    }

}
