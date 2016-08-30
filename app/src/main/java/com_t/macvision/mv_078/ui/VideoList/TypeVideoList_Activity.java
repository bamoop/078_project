package com_t.macvision.mv_078.ui.VideoList;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.VideoListPresenter;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

public class TypeVideoList_Activity extends BaseActivity implements VideoContract.View {
    private final static String TAG = "TypeVideoList_Activity";
    @Bind(R.id.rv_mainvideo)
    RecyclerView rv_mainvideo;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager layoutManager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    int type = 0;
    MainVideoListAdapter mAdapter;

    /**
     * 有更多的数据
     */
    private boolean mHasMoreData = true;
    public int currentPage = 1;
    private List<VideoEntity.VideolistEntity> mDataList;

    private VideoListPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_video_list);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        type = Integer.parseInt(intent.getStringExtra("type"));

        intiView();
        intiData();
    }

    private void intiData() {
        mPresenter.start(currentPage, type, false);
    }

    private void intiView() {
        mPresenter = new VideoListPresenter(this);
        toolbar.setTitle(FragmentMenu1.entity.getData().get(type-1).getVTypeName());
        mDataList = new ArrayList<>();
        mAdapter = new MainVideoListAdapter(this, mDataList);
        layoutManager = new LinearLayoutManager(this);
        rv_mainvideo.setLayoutManager(layoutManager);
        rv_mainvideo.setAdapter(mAdapter);
        rv_mainvideo.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));
        initSwipeLayout();

    }

    @Override
    public void fillData(VideoEntity entity) {
        currentPage++;
        if (currentPage == 1) {
            mDataList.clear();
        }
        mAdapter.updateWithClear(entity.getNewslist());

    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
    }

    @Override
    public void appendMoreDataToView(VideoEntity entity) {
        currentPage++;
        mAdapter.update(entity.getNewslist());
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                onRefreshStarted();
                currentPage = 1;
                showRefresh();
                mPresenter.start(currentPage, type, false);
                mHasMoreData = true;

            }
        });
        rv_mainvideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 1;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    mPresenter.start(currentPage, type, true);
                    Logger.i("onScrolled: onScrolled" + "page=" + currentPage);
                    showRefresh();
                }
            }
        });
    }

    /**
     * 隐藏刷新动画
     */
    public void hideRefresh() {
        // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 1000);
    }

    /**
     * 显示刷新动画
     */
    public void showRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

}
