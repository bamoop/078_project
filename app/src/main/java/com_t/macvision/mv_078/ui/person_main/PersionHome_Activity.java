package com_t.macvision.mv_078.ui.person_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseToolbarActivity;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.presenter.PersonHomePresenter;
import com_t.macvision.mv_078.ui.View.PersonHomeView;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.util.CircleImageView;
import com_t.macvision.mv_078.util.ImageFromFileCache;

public class PersionHome_Activity extends BaseToolbarActivity<PersonHomePresenter> implements PersonHomeView {
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.vp_file)
    ViewPager mViewPager;
    @Bind(R.id.tv_vReleaseNumber)
    TextView tv_vReleaseNumber;
    @Bind(R.id.tv_followNumber)
    TextView tv_followNumber;
    @Bind(R.id.tv_fansNumber)
    TextView tv_fansNumber;
    @Bind(R.id.image_hand)
    CircleImageView image_hand;
    @Bind(R.id.tv_userAutograph)
    TextView tv_userAutograph;
    @Bind(R.id.tv_focus)
    TextView tv_focus;
    @Bind(R.id.btn_Focus)
    Button btn_Focus;
    @Bind(R.id.person_parent_layout)
    RelativeLayout person_parent_layout;
    private View mRoot;
    @Bind(R.id.tv_userName)
    TextView tv_userName;
    String userName="--";
    public static final String TAG = "PersionHome_Activity";

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    List<String> mTitle = new ArrayList<>();
    public static int userId;


    @Override
    public int getLayout() {
        return R.layout.activity_persion_home;
    }


    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new PersonHomePresenter(this,this);
    }

    @Override
    public void initData() {
        super.initData();
        mTitle.add("动态");
        mTitle.add("视频");
        mTitle.add("照片");
        fragmentArrayList.clear();
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            fragmentArrayList.add(i, Fragment_Comment.DynamicInstance(bundle));
        }
        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId", 0);
        Logger.i("userId=" + intent.getIntExtra("userId", 0) + "userName=" + intent.getStringExtra("userName"));
        userName=intent.getStringExtra("userName");
        tv_userName.setText(intent.getStringExtra("userName"));
        mPresenter.getData(userId);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mFragmentTableAdapter = new FragmentTableAdapter(getSupportFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }


    @Override
    public void showEmptyView() {

    }

    @Override
    public void getDataFinish() {

    }


    @Override
    public void fillData(UserEntity entitys) {
        tv_vReleaseNumber.setText(entitys.getVReleaseNumber());
        tv_followNumber.setText(entitys.getFollowNumber());
        tv_fansNumber.setText(entitys.getFansNumber());
        tv_userAutograph.setText(entitys.getUserAutograph());
        Glide.with(this).load(ImageFromFileCache.base64ToBitmap(entitys.getAvatarLocation())).into(image_hand);
    }

    @Override
    protected String setTitle() {
        Logger.i("个人设置标题="+userName);
        return userName;
    }
}
