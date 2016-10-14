package com_t.macvision.mv_078.ui.Device;/**
 * Created by bzmoop on 2016/8/24 0024.
 */

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.base.BaseToolbarFragment;
import com_t.macvision.mv_078.ui.File.MenuClickListener;
import com_t.macvision.mv_078.ui.VideoList.FragmentTab1;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;

/**
 * 作者：LiangXiong on 2016/8/24 0024 15:01
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class Fragment_SDFile extends BaseToolbarFragment {
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
    static ArrayList<MenuClickListener> menuClickListenerList = new ArrayList<>();

    @Override
    public int getLayout() {
        return R.layout.fragment_file;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initData() {
        ArrayList<Bundle> bundles = new ArrayList<>();
        mTitle.add("循环视频");
        mTitle.add("紧急视频");
        mTitle.add("照片");
        fragmentArrayList.clear();
        for (int i = 0; i < mTitle.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);

            fragmentArrayList.add(SDFileChildFragment.Tab1Instance(bundle));
        }

        mFragmentTableAdapter.notifyDataSetChanged();
    }

    @Override
    protected String setTitle() {
        toolbar.setTitle("");
        return "视频/照片";
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mFragmentTableAdapter = new FragmentTableAdapter(getChildFragmentManager(), fragmentArrayList, mTitle);
        mViewPager.setAdapter(mFragmentTableAdapter);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(5);
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

    public void setMenuClickListener(MenuClickListener menuClickListener) {
        this.menuClickListenerList.add(menuClickListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_watched_video, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    protected void updateMenuTitle(MenuItem item) {
        if (mIsEditState) {
            item.setTitle(R.string.cancel);
            if (0 == mViewPager.getCurrentItem()) {
                this.menuClickListenerList.get(mTitle.size() - 1).onClickCancel();
            } else
                this.menuClickListenerList.get(mViewPager.getCurrentItem() - 1).onClickCancel();

        } else {
            item.setTitle(R.string.edit);
            if (0 == mViewPager.getCurrentItem()) {
                this.menuClickListenerList.get(mTitle.size() - 1).onClickEdit();
            } else
                this.menuClickListenerList.get(mViewPager.getCurrentItem() - 1).onClickEdit();

        }
    }


}
