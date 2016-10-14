package com_t.macvision.mv_078.ui.VideoList;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.model.entity.MainVideoListPresenter;
import com_t.macvision.mv_078.base.BaseSwipeRefreshActivity;
import com_t.macvision.mv_078.ui.View.IMainVideoView;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;
import com_t.macvision.mv_078.util.ShareUtil;

public class TypeVideoList_Activity extends BaseSwipeRefreshActivity<MainVideoListPresenter> implements MainVideoListAdapter.IClickMainItem,IMainVideoView<VideoEntity> {


    private final static String TAG = "TypeVideoList_Activity";
    @Bind(R.id.rv_mainvideo)
    RecyclerView rv_mainvideo;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager layoutManager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    int type = 0;
    MainVideoListAdapter mAdapter;

    /**
     * 有更多的数据
     */
    private boolean mHasMoreData = true;
    public int currentPage = 1;
    private List<VideoEntity> mDataList;

    @Override
    public int getLayout() {
        return R.layout.activity_type_video_list;
    }

    @Override
    public void initPresenter() {
        mPresenter = new MainVideoListPresenter(this, this);
    }



    @Override
    public void initData() {
        super.initData();
        Intent intent = this.getIntent();
        type = Integer.parseInt(intent.getStringExtra("type"));
        mPresenter.getVideoList(type, false);
        mDataList = new ArrayList<>();
        mAdapter = new MainVideoListAdapter(this, mDataList);
        layoutManager = new LinearLayoutManager(this);
    }



    @Override
    public void initView(View view) {
        super.initView(view);
        toolbar.setTitle("");
        toolbar_title.setText(FragmentMenu1.entity.getData().get(type-2).getVTypeName());
        mAdapter.setClickItem(this);
        initRecycleView();
    }

    @Override
    protected void onRefreshStarted() {

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
    public void hasNoMoreData() {
        mHasMoreData = false;
    }

    @Override
    public void getDataFinish() {
        super.getDataFinish();
        hideRefresh();
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        rv_mainvideo.setLayoutManager(layoutManager);
        rv_mainvideo.setAdapter(mAdapter);
        rv_mainvideo.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 1,
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
    public void onClickItemDetail(View view, VideoEntity videoEntity) {

    }

    @Override
    public void onClickTitle(View view, VideoEntity videoEntity) {

    }

    @Override
    public void onClickZan(CheckBox mCheckBox, VideoEntity videoEntity) {

    }

    @Override
    public void onClickPing(View view, VideoEntity videoEntity) {

    }

    @Override
    public void onClickFen(View view, VideoEntity videoEntity) {
        ShareUtil.openShare(this, videoEntity);

    }

    @Override
    public void onClickLinks(View view, VideoEntity videolistEntity) {

    }
}
