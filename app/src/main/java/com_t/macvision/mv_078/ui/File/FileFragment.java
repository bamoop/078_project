package com_t.macvision.mv_078.ui.File;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.base.BaseToolbarFragment;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileFragment extends BaseToolbarFragment {
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.vp_file)
    ViewPager mViewPager;
    protected static boolean mIsEditState = false;


    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    FragmentTableAdapter mFragmentTableAdapter;
    LinearLayoutManager layoutManager;
    static ArrayList<MenuClickListener> menuClickListenerList=new ArrayList<>();
    public MenuItem items;
    List<String> mTitle = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment_file;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void initData() {
        super.initData();
        Log.i("moop","授权访问SD卡申请");
        fragmentArrayList.clear();
        mTitle.add(currentContext.getString(R.string.frontVideo));
        mTitle.add(currentContext.getString(R.string.minVideo));
        mTitle.add(currentContext.getString(R.string.photo));
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragmentArrayList.add(FileChildFragment.Tab1Instance(bundle));
        }
        mFragmentTableAdapter.notifyDataSetChanged();

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mFragmentTableAdapter = new FragmentTableAdapter(getChildFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (items != null) {
                    mIsEditState = false;
                    updateMenuTitle(items);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_edit) {
                    mIsEditState = !mIsEditState;
                    updateMenuTitle(item);
                    items = item;
                }
                return false;
            }
        });
    }

    @Override
    protected String setTitle() {
        return "本地文件";
    }


    public void setMenuClickListener(MenuClickListener menuClickListener) {
//        this.menuClickListenerList.add(menuClickListener);
        this.menuClickListenerList.add(menuClickListener);
        Logger.i("setMenuClickListener-----------"+menuClickListener);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_watched_video, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void updateMenuTitle(MenuItem item) {
        if (mIsEditState) {
            item.setTitle(R.string.cancel);
            switch (mViewPager.getCurrentItem()){
                case 0:
                    this.menuClickListenerList.get(2).onClickCancel();

                    break;
                case 1:
                    this.menuClickListenerList.get(0).onClickCancel();
                    break;
                case 2:
                    this.menuClickListenerList.get(1).onClickCancel();
                default:
                    break;
            }

            Logger.i("updateMenuTitleNo-----------"+mViewPager.getCurrentItem()+"---setMenuClickListener="+this.menuClickListenerList.get(mViewPager.getCurrentItem())+"menuClickListenerList.size="+menuClickListenerList.size());
        } else {
            item.setTitle(R.string.edit);
//            this.menuClickListenerList.get(mViewPager.getCurrentItem()).onClickEdit();
            Logger.i("updateMenuTitleOff");
            switch (mViewPager.getCurrentItem()){
                case 0:
                    this.menuClickListenerList.get(2).onClickEdit();
                    break;
                case 1:
                    this.menuClickListenerList.get(0).onClickEdit();
                    break;
                case 2:
                    this.menuClickListenerList.get(1).onClickEdit();
                default:
                    break;
            }
        }
    }


}
