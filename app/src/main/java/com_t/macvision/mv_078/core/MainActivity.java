package com_t.macvision.mv_078.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;


import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.social.UMSocialService;

import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.ui.VideoList.FragmentMenu1;
import com_t.macvision.mv_078.ui.CloudDog.FragmentMenu2;
import com_t.macvision.mv_078.ui.Device.FragmentMenu3;
import com_t.macvision.mv_078.ui.File.FileFragment;
import com_t.macvision.mv_078.ui.Person.FragmentMenu5;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private long touchTime = 0;
    private static final long DOUBLE_CLICK_INTEVAL = 2 * 1000;
    public static String sAppDir = "";


    public static File getAppDir() {
        File appDir = new File(sAppDir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        return appDir;
    }

    @Bind({R.id.tab_community, R.id.tab_clouddog, R.id.tab_device, R.id.tab_file, R.id.tab_person})
    List<RadioButton> mTabs;
    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;
    private Fragment mTab05;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSelect(0);
        sAppDir = Environment.getExternalStorageDirectory().getPath() + File.separator + "069";
        Logger.i("sAPPDir="+sAppDir);
        getAppDir();
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
                setSelect(2);
                break;
            case R.id.tab_file:
                setSelect(3);
                break;
            case R.id.tab_person:
                setSelect(4);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
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
                    mTab02 = new FragmentMenu2();
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
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
