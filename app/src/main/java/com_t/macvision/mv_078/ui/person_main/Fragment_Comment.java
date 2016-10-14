package com_t.macvision.mv_078.ui.person_main;/**
 * Created by bzmoop on 2016/8/25 0025.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseSwipeRefreshFragment;
import com_t.macvision.mv_078.ui.View.IMainVideoView;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.CommentPresenter;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;


/**
 * 作者：LiangXiong on 2016/8/25 0025 15:32
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class Fragment_Comment extends BaseSwipeRefreshFragment implements IMainVideoView<VideoEntity> {
    private static final String TAG = "Fragment_Comment";

    @Bind(R.id.rv_dynamic)
    RecyclerView rv_dynamic;

    LinearLayoutManager layoutManager;
    CommentPresenter mPresenter;
    MainVideoListAdapter mAdapter;

    public static Fragment_Comment DynamicInstance(Bundle bundle) {
        Fragment_Comment tab1Fragment = new Fragment_Comment();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }

    /**
     * 是否有更多数据
     **/
    private boolean mHasMoreData = true;

    private List<VideoEntity> mDataList;


    @Override
    public int getLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mDataList = new ArrayList<>();
        mAdapter = new MainVideoListAdapter(currentContext, mDataList);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_dynamic.setLayoutManager(layoutManager);
        rv_dynamic.setAdapter(mAdapter);
        initRecycleView();
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new CommentPresenter(currentContext,this);

    }

    @Override
    protected void onRefreshStarted() {
        mPresenter.resetCurrentPage();
        mHasMoreData = true;
        mPresenter.getVideoList(PersionHome_Activity.userId, false);
    }
    @Override
    public void initData() {
        mPresenter.getVideoList(PersionHome_Activity.userId, false);
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

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(getActivity());
        rv_dynamic.setLayoutManager(layoutManager);
        rv_dynamic.setAdapter(mAdapter);
        rv_dynamic.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));


        rv_dynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 2;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    mPresenter.getVideoList(PersionHome_Activity.userId, true);
                    Logger.i("onScrolled: onScrolled" + "type");
                    showRefresh();
                }
            }
        });
        mSwipeRefreshLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

    }
}
