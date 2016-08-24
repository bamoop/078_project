package com_t.macvision.mv_078.ui.adapter;/**
 * Created by bzmoop on 2016/8/23 0023.
 */

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 作者：LiangXiong on 2016/8/23 0023 17:50
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class ViewPager_View_Adapter extends PagerAdapter {
    List<View> list;
    List<String> mTitleList;

    public ViewPager_View_Adapter(List<View> list,List<String> mTitleList) {
        super();
        this.list = list;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //销毁Item
    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView(list.get(position));
    }

    //实例化Item
    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(list.get(position), 0);
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("moop", "getPageTitle: ");
        return mTitleList.get(position);
    }
}
