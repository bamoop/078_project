package com_t.macvision.mv_078.ui.VideoList;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.macvision.mv_078.R;

import com_t.macvision.mv_078.model.entity.TypeEntity;
import com_t.macvision.mv_078.model.impl.BusinessTask;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;

/**
 * 作者：LiangXiong on 2016/8/3 0003 16:07
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentMenu1 extends BaseFragment {
    private static final String TAG = "FragmentMenu1";
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.vp_video)
    ViewPager mViewPager;
    ArrayList<Fragment> mFragmentlist = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    public static TypeEntity entity;
    List<String>mTitle=new ArrayList<>();

    private void initData(List<TypeEntity.DataBean> entityList) {

        for (int i = 0; i < entityList.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i +1);
            mTitle.add(entityList.get(i).getVTypeName());
            mFragmentlist.add(FragmentTab1.Tab1Instance(bundle));
        }
        mFragmentTableAdapter.notifyDataSetChanged();

    }

    @Override
    public void initView(View view) {
        getType();
        mFragmentTableAdapter = new FragmentTableAdapter(getChildFragmentManager(), mFragmentlist, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_menu1;
    }

    public void getType() {
        new BusinessTask().getType(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e);
            }


            @Override
            public void onNext(String s) {
                if (!TextUtils.isEmpty(s)) {
                    entity= GsonUtil.changeGsonToBean(s, TypeEntity.class);
                    initData(entity.getData());
                }
            }
        });
    }
}
