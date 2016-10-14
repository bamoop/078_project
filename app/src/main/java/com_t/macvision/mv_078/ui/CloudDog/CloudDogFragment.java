package com_t.macvision.mv_078.ui.CloudDog;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.macvision.mv_078.R;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com_t.macvision.mv_078.base.BaseToolbarFragment;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.util.MD5Util;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class CloudDogFragment extends BaseToolbarFragment<CloudDogPresenter> implements CloudDogView {
    private static final String TAG = "CloudDogFragment";

    @Bind(R.id.tv_imei)
    TextView tvImei;
    @Bind(R.id.tv_linkStatus)
    TextView tvLinkStatus;
    @Bind(R.id.location)
    Button location;
    @Bind(R.id.btn_track)
    Button btnTrack;
    @Bind(R.id.btn_longSnap)
    Button btnLongSnap;
    @Bind(R.id.btn_setting)
    Button btnSetting;
    @Bind(R.id.tv_comment_status)
    TextView tvCommentStatus;

    @Override
    public int getLayout() {
        return R.layout.fragment_menu2;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }


    @Override
    public void loadData() {
        super.loadData();
        mPresenter.login("Ted1234", MD5Util.md5("123456789"));
        mPresenter.getAppToken("ydac4f3fb2bf26c9f7", MD5Util.md5("123456"), Long.toString(new Date().getTime()));
        tvImei.setText(Constant.IMEI);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new CloudDogPresenter(currentContext, this);
    }

    @Override
    protected String setTitle() {
        return "云狗";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.location, R.id.btn_track, R.id.btn_longSnap, R.id.btn_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location:
                MainActivity.startActivity(getMyActivity(), null, LocationActivity.class);
                break;
            case R.id.btn_track:
                MainActivity.startActivity(getMyActivity(), null, TrackActivity.class);
                break;
            case R.id.btn_longSnap:
                break;
            case R.id.btn_setting:
                break;
        }
    }


    @Override
    public void getAppTokenSuccess(String appToken) {
        SharedPreferencesUtils.setParam(currentContext, "CloudAppToken", appToken);
        Log.i(TAG, "appid=" + appToken);

    }

    @Override
    public void getAppTokenError(String massage) {

    }

    @Override
    public void loginSuccess(String userToken) {
        SharedPreferencesUtils.setParam(currentContext, "CloudUserToken", userToken);
        Log.i(TAG, "登陆成功=" + userToken);
        tvCommentStatus.setText("已连接到云狗");
    }

    @Override
    public void loginError(String massage) {
        tvCommentStatus.setText("未连接");
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerError() {

    }
}
