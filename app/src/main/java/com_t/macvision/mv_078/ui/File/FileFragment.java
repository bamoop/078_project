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

import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileFragment extends BaseFragment {
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.vp_file)
    ViewPager mViewPager;
    protected static boolean mIsEditState = false;

    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    //    private String[] mTitle;
    FragmentTableAdapter mFragmentTableAdapter;
    LinearLayoutManager layoutManager;
    protected Toolbar mToolbar;
    static MenuClickListener menuClickListener;
    public MenuItem items;
    List<String> mTitle = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_file;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected void initView(View view) {
        initData();
        mFragmentTableAdapter = new FragmentTableAdapter(getChildFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);

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

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        mToolbar.setTitle(R.string.localFile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
    protected void lazyLoad() {

    }

    private void initData() {
//        mTitle = new String[]{"前视频", "小视频", "照片"};
        mTitle.add(currentContext.getString(R.string.frontVideo));
        mTitle.add(currentContext.getString(R.string.minVideo));
        mTitle.add(currentContext.getString(R.string.photo));
        fragmentArrayList.clear();
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            fragmentArrayList.add(FileChildFragment.Tab1Instance(bundle));
        }
    }

    public void setMenuClickListener(MenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_watched_video, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void updateMenuTitle(MenuItem item) {
        if (mIsEditState) {
            item.setTitle(R.string.cancel);
            this.menuClickListener.onClickCancel();
        } else {
            item.setTitle(R.string.edit);
            this.menuClickListener.onClickEdit();
        }
    }


}
