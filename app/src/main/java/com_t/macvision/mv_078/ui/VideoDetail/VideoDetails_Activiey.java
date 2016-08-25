package com_t.macvision.mv_078.ui.VideoDetail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com_t.macvision.mv_078.Constant;
import com.macvision.mv_078.R;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.model.entity.CommentEntity;
import com_t.macvision.mv_078.model.entity.VideoDetailEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.presenter.VideoDetailPresenter;
import com_t.macvision.mv_078.ui.adapter.VideoDetailAdapter;
import com_t.macvision.mv_078.util.CircleImageView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.com.video.venvy.param.JjVideoRelativeLayout;
import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.OnJjBufferCompleteListener;
import cn.com.video.venvy.param.OnJjBufferStartListener;
import cn.com.video.venvy.param.OnJjBufferingUpdateListener;
import cn.com.video.venvy.param.OnJjOpenStartListener;
import cn.com.video.venvy.param.OnJjOpenSuccessListener;
import cn.com.video.venvy.param.OnJjOutsideLinkClickListener;
import cn.com.video.venvy.widget.UsetMediaContoller;

public class VideoDetails_Activiey extends BaseActivity implements VideoDetailContract.View {
    String videoLocation;
    int videoID;
    private VideoDetailPresenter mPresenter;
    private String videoURL;
    JjVideoView PlayVideoView;
    View mLoadBufferView;
    View mLoadView;
    TextView mLoadBufferTextView;
    TextView mLoadText;
    RelativeLayout detail_handLayout;
    RecyclerView recyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    public int currentPage = 1;
    private boolean mHasMoreData = true;
    LinearLayoutManager layoutManager;
    VideoDetailAdapter mVideoDetailAdapter;
    List<CommentEntity.DataBean> mCommentEntity = new ArrayList<>();
    VideoEntity.VideolistEntity videolistEntity;

    TextView tv_username;
    TextView tv_count;
    CircleImageView image_head;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        ButterKnife.bind(this);
        initData();
        initView();
        initVideo();

    }

    public void initData() {
        Intent intent = this.getIntent();
        videolistEntity = (VideoEntity.VideolistEntity) intent.getSerializableExtra("VideoEntity");
        videoLocation = videolistEntity.getVideoLocation();
        videoID = Integer.parseInt(videolistEntity.getVideoId());
        videoURL = Constant.BaseVideoPlayUrl + videoLocation;

        mPresenter = new VideoDetailPresenter(this);
        mPresenter.getVideoDetail(videoID);
        mPresenter.getComment(videoID, currentPage, false);
        Logger.i("videoLocation: " + videoLocation);
    }

    public void initView() {
        PlayVideoView = (JjVideoView) findViewById(R.id.video);
        mLoadBufferView = findViewById(R.id.sdk_load_layout);
        mLoadView = findViewById(R.id.sdk_ijk_progress_bar_layout);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);
        mLoadText = (TextView) findViewById(R.id.sdk_ijk_progress_bar_text);
        mVideoDetailAdapter = new VideoDetailAdapter(this, mCommentEntity, videolistEntity);
        recyclerView = (RecyclerView) findViewById(R.id.rv_videoDetail);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        detail_handLayout = (RelativeLayout) findViewById(R.id.detail_handLayout);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mVideoDetailAdapter);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_count.setText(videolistEntity.getVideoViewNumber());
        tv_username.setText(videolistEntity.getUserId());
        initSwipeLayout();
        showRefresh();
        PlayVideoView.setMediaController(new UsetMediaContoller(this));

    }

    /**
     * 初始化播放器
     */
    public void initVideo() {

        PlayVideoView .setOnJjOutsideLinkClickListener(new OnJjOutsideLinkClickListener() {

                    @Override
                    public void onJjOutsideLinkClick(String arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onJjOutsideLinkClose() {
                        // TODO Auto-generated method stub

                    }
                });
        PlayVideoView.setMediaBufferingView(mLoadBufferView);
        PlayVideoView.setOnJjOpenStart(new OnJjOpenStartListener() {

            @Override
            public void onJjOpenStart(String arg0) {
                mLoadText.setText(arg0);
            }
        });
        /***
         * 视频开始播放
         */
        PlayVideoView.setOnJjOpenSuccess(new OnJjOpenSuccessListener() {
            @Override
            public void onJjOpenSuccess() {
                mLoadView.setVisibility(View.GONE);
            }
        });
        PlayVideoView.setOnJjBufferStart(new OnJjBufferStartListener() {
            @Override
            public void onJjBufferStartListener(int i) {
            }
        });
        PlayVideoView
                .setOnJjBufferingUpdateListener(new OnJjBufferingUpdateListener() {
                    @Override
                    public void onJjBufferingUpdate(int arg1) {
                        // TODO Auto-generated method stub
                        if (mLoadBufferView.getVisibility() == View.VISIBLE) {
                            mLoadBufferTextView.setText(String
                                    .valueOf(PlayVideoView.getBufferPercentage())
                                    + "%");
                        }
                    }
                });
        PlayVideoView.setOnJjBufferComplete(new OnJjBufferCompleteListener() {

            @Override
            public void onJjBufferCompleteListener(int arg0) {
                // TODO Auto-generated method stub
                Logger.i("onJjBufferCompleteListener:缓冲完成 " + arg0);
            }
        });

        /***
         * 注意VideoView 要调用下面方法 配置你用户信息
         */
        PlayVideoView.setVideoJjAppKey(Constant.JJAPPKEY);
        PlayVideoView.setVideoJjPageName(Constant.PAGENAME);
        PlayVideoView.setMediaCodecEnabled(true);// 是否开启 硬解 硬解对一些手机有限制
        // 判断是否源 0 代表 8大视频网站url 3代表自己服务器的视频源 2代表直播地址 1代表本地视频(手机上的视频源),4特殊需求
        PlayVideoView.setVideoJjType(3);
        // 参数代表是否记录视频播放位置 默认false不记录 true代表第二次或多次进入，直接跳转到上次退出的时间点开始播放
        PlayVideoView.setVideoJjSaveExitTime(false);

        RelativeLayout details_parent_layout = (RelativeLayout) findViewById(R.id.details_parent_layout);
        JjVideoRelativeLayout mJjVideoRelativeLayout = (JjVideoRelativeLayout) findViewById(R.id.jjlayout);
        mJjVideoRelativeLayout.setJjToFront(details_parent_layout);// 设置此方法
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (detail_handLayout.getVisibility()==View.GONE){
            detail_handLayout.setVisibility(View.VISIBLE);
        }else
            detail_handLayout.setVisibility(View.GONE);

        Log.i("moop", "onConfigurationChanged: ");
    }

    private void initSwipeLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                showRefresh();
                mHasMoreData = true;
                mPresenter.getComment(videoID, currentPage, false);
                Logger.i("onRefresh: 下拉刷新");
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mVideoDetailAdapter.getItemCount() - 1;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    Logger.i("onScrolled: 上滑" + "page=" + currentPage);
                    mPresenter.getComment(videoID, currentPage, true);
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

    @Override
    protected void onResume() {
        super.onResume();
        PlayVideoView.setResourceVideo(videoURL);
    }

    @Override
    public void fillData(VideoDetailEntity entity) {
        Logger.i("getVideoReleaseAddress=" + entity.getData().getVideoReleaseAddress());

    }

    @Override
    public void fillCommentData(CommentEntity entity) {
        currentPage++;
        if (currentPage == 1) {
            mCommentEntity.clear();
        }
        mVideoDetailAdapter.updateWithClear(entity.getData());

    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;

    }

    @Override
    public void appendMoreDataToView(CommentEntity entity) {
        currentPage++;
        mVideoDetailAdapter.update(entity.getData());
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PlayVideoView != null)
            PlayVideoView.onDestroy();
    }
}

