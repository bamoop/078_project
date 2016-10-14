package com_t.macvision.mv_078.core;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


import com.macvision.mv_078.R;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import com.zhy.m.permission.ShowRequestPermissionRationale;

import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.ui.VideoList.FragmentMenu1;
import com_t.macvision.mv_078.ui.CloudDog.CloudDogFragment;
import com_t.macvision.mv_078.ui.Device.FragmentMenu3;
import com_t.macvision.mv_078.ui.File.FileFragment;
import com_t.macvision.mv_078.ui.Person.FragmentMenu5;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private long touchTime = 0;
    private static final long DOUBLE_CLICK_INTEVAL = 2 * 1000;

    @Bind({R.id.tab_community, R.id.tab_clouddog, R.id.tab_device, R.id.tab_file, R.id.tab_person})
    List<RadioButton> mTabs;
    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    private Fragment mTab05;
    private static final int REQUECT_CODE_SDCARD = 2;
    private static final int REQUECT_CODE_PHONE_STATE = 3;
    FragmentManager fm;
    FragmentTransaction transaction;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setSelect(0);

    }

    @Override
    public void initData() {
        super.initData();

    }

    @OnClick({R.id.tab_community, R.id.tab_clouddog, R.id.tab_device, R.id.tab_file, R.id.tab_person})
    void bottomClick(View view) {
        resetImgs();
        switch (view.getId()) {
            case R.id.tab_community:
                setSelect(0);
                break;
            case R.id.tab_clouddog:
                setSelect(1);
                break;
            case R.id.tab_device:
//                MPermissions.requestPermissions(this, REQUECT_CODE_PHONE_STATE, Manifest.permission.RECEIVE_SMS);
                setSelect(2);
                break;
            case R.id.tab_file:
//                MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.READ_EXTERNAL_STORAGE);
                setSelect(3);
                break;
            case R.id.tab_person:
                setSelect(4);
                break;
        }
    }

    private void setSelect(int i) {
         fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new FragmentMenu1();
                    transaction.add(R.id.id_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                mTabs.get(0).setChecked(true);
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new CloudDogFragment();
                    transaction.add(R.id.id_content, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                mTabs.get(1).setChecked(true);

                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new FragmentMenu3();
                    transaction.add(R.id.id_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                mTabs.get(2).setChecked(true);

                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new FileFragment();
                    transaction.add(R.id.id_content, mTab04);
                } else {
                    transaction.show(mTab04);
                }

                mTabs.get(3).setChecked(true);
                break;

            case 4:


                    if (mTab05 == null) {
                        mTab05 = new FragmentMenu5();
                        transaction.add(R.id.id_content, mTab05);
                    } else {
                        transaction.show(mTab05);
                    }
                    mTabs.get(4).setChecked(true);
                break;

            default:
                break;
        }

        transaction.commit();
    }

    /**
     * 切换图片至暗色
     */
    private void resetImgs() {
        mTabs.get(0).setChecked(true);
        mTabs.get(1).setChecked(false);
        mTabs.get(2).setChecked(false);
        mTabs.get(3).setChecked(false);
        mTabs.get(4).setChecked(false);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {

            transaction.hide(mTab04);
        }
        if (mTab05 != null) {
            transaction.hide(mTab05);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        if (outState != null)

        {
            String FRAGMENTS_TAG = "Android:support:fragments";
            outState.remove(FRAGMENTS_TAG);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    public static void startActivity(Context context, Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            finish();
//            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);//正常退出App
        }
        return true;
    }


    @ShowRequestPermissionRationale(REQUECT_CODE_SDCARD)
    public void whyNeedSdCard()
    {
        Toast.makeText(this, "访问本地视频需要请求SD卡访问权限", Toast.LENGTH_SHORT).show();
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, Manifest.permission.ACCESS_WIFI_STATE);
    }
    @ShowRequestPermissionRationale(REQUECT_CODE_PHONE_STATE)
    public void whyNeedPhone()
    {
        Toast.makeText(this, "需要提供手机状态权限", Toast.LENGTH_SHORT).show();
        MPermissions.requestPermissions(this, REQUECT_CODE_PHONE_STATE, Manifest.permission.RECEIVE_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess()
    {
        Toast.makeText(this, "获取SD卡权限成功", Toast.LENGTH_SHORT).show();
        setSelect(3);
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed()
    {
        Toast.makeText(this, "获取SD卡权限失败", Toast.LENGTH_SHORT).show();
        setSelect(0);
    }

    @PermissionGrant(REQUECT_CODE_PHONE_STATE)
    public void requestPhoneSuccess()
    {
        Toast.makeText(this, "获取手机状态权限成功", Toast.LENGTH_SHORT).show();
        setSelect(2);
    }

    @PermissionDenied(REQUECT_CODE_PHONE_STATE)
    public void requestPhoneFailed()
    {
        Toast.makeText(this, "获取手机状态权限失败", Toast.LENGTH_SHORT).show();
        setSelect(0);
    }

}
