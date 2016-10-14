package com_t.macvision.mv_078.ui.Device;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.macvision.mv_078.R;

import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.util.NetworkUtil;

public class SDFile_Activity extends BaseActivity {

    @Override
    public void initView(View view) {
        super.initView(view);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = new Fragment_SDFile();
        transaction.add(R.id.stream_ParentLayout, fragment);
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sdfile;
    }
}
