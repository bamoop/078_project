package com_t.macvision.mv_078.ui.VideoList;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.macvision.mv_078.R;

import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.base.BaseSwipeRefreshFragment;
import com_t.macvision.mv_078.ui.View.IMainVideoView;
import com_t.macvision.mv_078.model.entity.MainVideoListPresenter;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.ui.VideoDetail.VideoDetails_Activiey;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.media.UMImage;

import org.videolan.vlc.VLCApplication;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import com_t.macvision.mv_078.ui.person_main.PersionHome_Activity;
import com_t.macvision.mv_078.util.ShareUtil;
import simbest.com.sharelib.IShareCallback;
import simbest.com.sharelib.ShareModel;
import simbest.com.sharelib.ShareUtils;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentTab1 extends BaseSwipeRefreshFragment<MainVideoListPresenter> implements MainVideoListAdapter.IClickMainItem, IMainVideoView<VideoEntity> {
    private static final String TAG = "FragmentTab1";
    @Bind(R.id.rv_mainvideo)
    RecyclerView rv_mainvideo;

    LinearLayoutManager layoutManager;
    MainVideoListAdapter mAdapter;

    /**
     * 有更多的数据
     */
    private boolean mHasMoreData = true;


    private List<VideoEntity> mDataList;

    private int type = 0;

    public static FragmentTab1 Tab1Instance(Bundle bundle) {
        Logger.i("FragmentTab1");
        FragmentTab1 tab1Fragment = new FragmentTab1();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_tab1;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainVideoListPresenter(currentContext, this);
    }

    @Override
    protected void onRefreshStarted() {
        Logger.i("刷新请求数据");
        mPresenter.resetCurrentPage();
        mHasMoreData = true;
        mPresenter.getVideoList(type, false);
    }

    @Override
    public void loadData() {
        super.loadData();
        Logger.i("懒加载--");
        type = getArguments().getInt("type");
        mPresenter.getVideoList(type, false);
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mDataList = new ArrayList<>();
        mAdapter = new MainVideoListAdapter(getActivity(), mDataList);
        mAdapter.setClickItem(this);
        initRecycleView();
        Logger.i("type" + type);
    }


    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(VLCApplication.getAppContext());
        rv_mainvideo.setLayoutManager(layoutManager);
        rv_mainvideo.setAdapter(mAdapter);
        rv_mainvideo.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));

        rv_mainvideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 2;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    mPresenter.getVideoList(type, true);
                    Logger.i("onScrolled: onScrolled" + "type");
                    showRefresh();
                }
            }
        });
    }

    @Override
    public void fillData(ArrayList<VideoEntity> data) {
        mDataList.clear();
        mAdapter.updateWithClear(data);
    }

    @Override
    public void appendMoreDataToView(ArrayList<VideoEntity> data) {
        mAdapter.update(data);
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(String massage) {
        Log.i(TAG, "showErrorView");
        hideRefresh();
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
    }


    @Override
    public void onClickItemDetail(View view, VideoEntity videoEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("VideoEntity", videoEntity);
        MainActivity.startActivity(currentContext, bundle, VideoDetails_Activiey.class);
    }

    @Override
    public void onClickTitle(View view, VideoEntity videoEntity) {
        Intent intent = new Intent(getMyActivity(), PersionHome_Activity.class);
        intent.putExtra("userId", Integer.parseInt(videoEntity.getUserId()));
        intent.putExtra("userName", videoEntity.getUserName());
        currentContext.startActivity(intent);
    }



    @Override
    public void onClickZan(CheckBox checkBox, VideoEntity videoEntity) {
        Logger.i("点赞");

        if (videoEntity.getIsLike()){
            videoEntity.setIsLike("0");
        }else {
            videoEntity.setIsLike("1");
        }
        checkBox.setChecked(checkBox.isChecked());
//        checkBox.setChecked(videoEntity.getIsLike());
        mPresenter.clicklike(videoEntity.getVideoId(),videoEntity.getUserId());

    }
    @Override
    public void onClickPing(View view, VideoEntity videoEntity) {

    }

    @Override
    public void onClickFen(View view, VideoEntity videoEntity) {

        ShareUtil.openShare(getMyActivity(), videoEntity);
    }

    @Override
    public void onClickLinks(View view, VideoEntity videolistEntity) {
//        Intent intent = new Intent(getActivity(), TypeVideoList_Activity.class);
//        intent.putExtra("type", videolistEntity.getVideoType());
//        startActivity(intent);
    }

}
