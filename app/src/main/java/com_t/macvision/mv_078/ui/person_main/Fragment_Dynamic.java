package com_t.macvision.mv_078.ui.person_main;/**
 * Created by bzmoop on 2016/8/25 0025.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.macvision.mv_078.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.DynamicPresenter;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.customView.OverRecyclerView;


/**
 * 作者：LiangXiong on 2016/8/25 0025 15:32
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class Fragment_Dynamic extends BaseFragment implements DynamicContract.View {
//    @Bind(R.id.rv_dynamic)
    OverRecyclerView rv_dynamic;
    LinearLayoutManager layoutManager;
    DynamicPresenter mPresenter;
    MainVideoListAdapter mainVideoListAdapter;
    private boolean mCanScrollUp = false;

    public static Fragment_Dynamic DynamicInstance(Bundle bundle) {
        Fragment_Dynamic tab1Fragment = new Fragment_Dynamic();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }

    /**
     * 是否有更多数据
     **/
    private boolean mHasMoreData = true;

    public int currentPage = 1;

    private List<VideoEntity.VideolistEntity> mDataList;


    @Override
    protected int getLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView(View view) {
        rv_dynamic = (OverRecyclerView) view.findViewById(R.id.rv_dynamic);
        mPresenter = new DynamicPresenter(this);
        mPresenter.getData(Integer.parseInt(PersionHome_Activity.userId), currentPage, false);
        mDataList = new ArrayList<>();
        mainVideoListAdapter = new MainVideoListAdapter(currentContext, mDataList);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_dynamic.setLayoutManager(layoutManager);
        rv_dynamic.setAdapter(mainVideoListAdapter);
        rv_dynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isShowBottom = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mCanScrollUp = false;
                    Log.i("listview1", "onScrolled: " + true);
                } else
                    mCanScrollUp = true;
            }
        });


    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void fillData(VideoEntity entity) {
        currentPage++;
        if (currentPage == 1) {
            mDataList.clear();
        }
        mainVideoListAdapter.updateWithClear(entity.getNewslist());

    }

    @Override
    public void hasNoMoreData() {

    }

    @Override
    public void appendMoreDataToView(VideoEntity entity) {

    }

    @Override
    public void getDataFinish() {

    }

    @Override
    public void getDataFail() {

    }

}
