package com_t.macvision.mv_078.ui.person_main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.macvision.mv_078.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.ui.customView.OverScrollableScrollView;

public class PersionHome_Activity extends BaseActivity {
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.vp_file)
    ViewPager mViewPager;

    public static final String TAG = "PersionHome_Activity";
    private OverScrollableScrollView mScrollView;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    protected Toolbar mToolbar;
    List<String> mTitle=new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_persion_home);
        initView();
        intiData();
    }

    private void intiData() {
        mTitle.add("动态");
        mTitle.add("视频");
        mTitle.add("照片");
        fragmentArrayList.clear();

    }

    private void initView() {
        mFragmentTableAdapter = new FragmentTableAdapter(getSupportFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
    }
}
