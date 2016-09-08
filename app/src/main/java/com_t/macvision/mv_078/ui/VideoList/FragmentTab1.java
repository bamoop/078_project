package com_t.macvision.mv_078.ui.VideoList;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.macvision.mv_078.R;

import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.VideoListPresenter;
import com_t.macvision.mv_078.ui.VideoDetail.VideoDetails_Activiey;
import com_t.macvision.mv_078.ui.adapter.MainVideoListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import com_t.macvision.mv_078.ui.person_main.PersionHome_Activity;
import simbest.com.sharelib.IShareCallback;
import simbest.com.sharelib.ShareModel;
import simbest.com.sharelib.ShareUtils;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentTab1 extends BaseFragment implements VideoContract.View, MainVideoListAdapter.IClickMainItem {

    @Bind(R.id.rv_mainvideo)
    RecyclerView rv_mainvideo;
    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayoutManager layoutManager;
    private VideoListPresenter mPresenter;
    MainVideoListAdapter mAdapter;
    private ShareUtils su;
    SHARE_MEDIA platform = null;

    /**
     * 有更多的数据
     */
    private boolean mHasMoreData = true;

    public int currentPage = 1;

    private List<VideoEntity.VideolistEntity> mDataList;

    private int type = 0;

    public static FragmentTab1 Tab1Instance(Bundle bundle) {
        FragmentTab1 tab1Fragment = new FragmentTab1();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_tab1;
    }

    @Override
    protected void initView(View view) {

        type = getArguments().getInt("type");
        mPresenter = new VideoListPresenter(this);
        su = new ShareUtils(getMyActivity());

        mDataList = new ArrayList<>();
        mAdapter = new MainVideoListAdapter(getActivity(), mDataList);
        mAdapter.setClickItem(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_mainvideo.setLayoutManager(layoutManager);
        rv_mainvideo.setAdapter(mAdapter);
        rv_mainvideo.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));
        initSwipeLayout();
        initEvent();
        Logger.i("type" + type);

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
                    Logger.i("onScrolled: onScrolled" + "type" + currentPage);
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


    private void initEvent() {
        mPresenter.start(currentPage, type, false);
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


    @Override
    public void onClickItemDetail(View view, VideoEntity.VideolistEntity videoEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("VideoEntity", videoEntity);
        MainActivity.startActivity(currentContext, bundle, VideoDetails_Activiey.class);
    }

    @Override
    public void onClickTitle(View view, VideoEntity.VideolistEntity videoEntity) {
        Intent intent = new Intent(getMyActivity(), PersionHome_Activity.class);
        intent.putExtra("userId", Integer.parseInt(videoEntity.getUserId()));
        intent.putExtra("userName", videoEntity.getUserName());
        currentContext.startActivity(intent);
    }

    @Override
    public void onClickZan(View view, VideoEntity.VideolistEntity videoEntity) {

    }

    @Override
    public void onClickPing(View view, VideoEntity.VideolistEntity videoEntity) {

    }

    @Override
    public void onClickFen(View view, VideoEntity.VideolistEntity videoEntity) {
        ShareModel model = new ShareModel();
        model.setTitle("互联视讯行车记录仪：" + videoEntity.getVideoTitle());
        model.setContent(videoEntity.getVideoCaption());
        model.setImageMedia(new UMImage(getMyActivity(), videoEntity.getAvatarLocation()));
//        model.setVideoMedia(new UMVideo( "http://192.168.1.124/demo/video/webView?videoId="+videoEntity.getVideoId()));
        model.setTagUrl("http://192.168.1.124/demo/video/webView?videoId="+videoEntity.getVideoId());
        su.share(model, new IShareCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getMyActivity(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaild() {
                Toast.makeText(getMyActivity(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getMyActivity(), "取消分享", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickLinks(View view, VideoEntity.VideolistEntity videolistEntity) {
        Intent intent = new Intent(getActivity(), TypeVideoList_Activity.class);
        intent.putExtra("type", videolistEntity.getVideoType());
        startActivity(intent);
    }

}
