package com_t.macvision.mv_078.ui.person_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.Constant;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.model.entity.UserEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.PersonHomePresenter;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.ui.customView.OverScrollableScrollView;
import com_t.macvision.mv_078.util.CircleImageView;

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
    public static final String TAG = "PersionHome_Activity";
    private OverScrollableScrollView mScrollView;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    List<String> mTitle = new ArrayList<>();
    private PersonHomePresenter mPresenter;
    VideoEntity.VideolistEntity videolistEntity;
    static String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_home);
        ButterKnife.bind(this);
        intiData();
        initView();
        mScrollView.smoothScrollTo(0,20);

    }

    private void intiData() {
        mTitle.add("动态");
        mTitle.add("视频");
        mTitle.add("照片");
        fragmentArrayList.clear();
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            fragmentArrayList.add(i,Fragment_Dynamic.DynamicInstance(bundle));
        }
        Intent intent = this.getIntent();
        videolistEntity = (VideoEntity.VideolistEntity) intent.getSerializableExtra("VideoEntity");
        mPresenter = new PersonHomePresenter(this);
        mPresenter.getData(Integer.parseInt(videolistEntity.getUserId()));
        userId = videolistEntity.getUserId();
    }

    private void initView() {
        mScrollView = (OverScrollableScrollView) findViewById(R.id.scroll);
        ViewTreeObserver vto2 = person_parent_layout.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                person_parent_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                initScrollView();
                Log.d(TAG, "-----");
            }
        });
        mRoot = person_parent_layout;

    }

    private void initScrollView() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
        LinearLayout heard_layout = (LinearLayout) findViewById(R.id.heard_layout);
        params.height = mRoot.getHeight() - mTableLayout.getHeight()-
                mToolbar.getHeight()-mTableLayout.getHeight()/4;
        Log.i(TAG, "initScrollView: params.height="+mTableLayout.getHeight());
        mViewPager.setLayoutParams(params);
        mFragmentTableAdapter = new FragmentTableAdapter(getSupportFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == FragmentTableAdapter.TAB_PARAMS_INDEX) {
                    Fragment_Dynamic fragment = (Fragment_Dynamic) mFragmentTableAdapter.getItem(position);
                    mScrollView.setController(fragment);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        Glide.with(this).load(Constant.BaseVideoPlayUrl + entity.getData().getAvatarLocation()).centerCrop().into(image_hand);

    }
}
