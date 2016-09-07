package com_t.macvision.mv_078.ui.person_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.PersonHomePresenter;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.util.CircleImageView;
import com_t.macvision.mv_078.util.ImageFromFileCache;

public class PersionHome_Activity extends BaseActivity implements PersonHomeContract.View {
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
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    //    @Bind(R.id.mStickyNavLayout)
//    StickyNavLayout mStickyNavLayout;
    public static final String TAG = "PersionHome_Activity";
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    List<String> mTitle = new ArrayList<>();
    private PersonHomePresenter mPresenter;
    VideoEntity.VideolistEntity videolistEntity;
    static int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_home);
        ButterKnife.bind(this);
        intiData();
        initView();
    }

    private void intiData() {
        mTitle.add("动态");
        mTitle.add("视频");
        mTitle.add("照片");
        fragmentArrayList.clear();
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            fragmentArrayList.add(i, Fragment_Dynamic.DynamicInstance(bundle));
        }
        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId",0);
        Logger.i("userId="+intent.getIntExtra("userId",0)+"userName="+intent.getStringExtra("userName"));
        mToolbar.setTitle(intent.getStringExtra("userName"));
        mPresenter = new PersonHomePresenter(this);
        mPresenter.getData(userId);
    }

    private void initView() {
        LinearLayout heard_layout = (LinearLayout) findViewById(R.id.hand_layout);
        mFragmentTableAdapter = new FragmentTableAdapter(getSupportFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void getDataFinish() {

    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void fillData(UserEntity entity) {
        tv_vReleaseNumber.setText(entity.getData().getVReleaseNumber());
        tv_followNumber.setText(entity.getData().getFollowNumber());
        tv_fansNumber.setText(entity.getData().getFansNumber());
        tv_userAutograph.setText(entity.getData().getUserAutograph());
        Glide.with(this).load(ImageFromFileCache.base64ToBitmap(entity.getData().getAvatarLocation())).into(image_hand);
    }
}
