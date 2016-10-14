package com_t.macvision.mv_078.ui.VideoDetail;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.com.video.venvy.param.JjVideoRelativeLayout;
import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.OnJjBufferCompleteListener;
import cn.com.video.venvy.param.OnJjBufferStartListener;
import cn.com.video.venvy.param.OnJjBufferingUpdateListener;
import cn.com.video.venvy.param.OnJjOpenStartListener;
import cn.com.video.venvy.param.OnJjOpenSuccessListener;
import cn.com.video.venvy.param.OnJjOutsideLinkClickListener;
import cn.com.video.venvy.widget.UsetMediaContoller;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.base.BaseSwipeRefreshActivity;
import com_t.macvision.mv_078.model.entity.CommentEntity;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.ui.View.CommentView;
import com_t.macvision.mv_078.ui.adapter.VideoDetailAdapter;
import com_t.macvision.mv_078.ui.person_main.PersionHome_Activity;
import com_t.macvision.mv_078.util.CircleImageView;
import com_t.macvision.mv_078.util.ImageFromFileCache;
import com_t.macvision.mv_078.util.KeyBoardUtils;
import com_t.macvision.mv_078.util.ScreenUtils;

public class VideoDetails_Activiey extends BaseSwipeRefreshActivity<VideoDetailPresenter> implements CommentView<CommentEntity>, VideoDetailAdapter.ItemOnclick {
    String videoLocation;
    int videoID;
    private VideoDetailPresenter mPresenter;
    private String videoURL;
    EditText edit_ping;
    Button btn_send;
    private boolean mHasMoreData = true;
    LinearLayoutManager layoutManager;
    VideoDetailAdapter mVideoDetailAdapter;
    List<CommentEntity> mCommentEntity = new ArrayList<>();
    VideoEntity videolistEntity;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.tv_PlayCount)
    TextView tv_count;
    @Bind(R.id.toolbar)
    Toolbar toobar;
    @Bind(R.id.video)
    JjVideoView PlayVideoView;
    @Bind(R.id.sdk_load_layout)
    View mLoadBufferView;
    @Bind(R.id.sdk_ijk_progress_bar_layout)
    View mLoadView;
    @Bind(R.id.sdk_sdk_ijk_load_buffer_text)
    TextView mLoadBufferTextView;
    @Bind(R.id.sdk_ijk_progress_bar_text)
    TextView mLoadText;
    @Bind(R.id.jjlayout)
    JjVideoRelativeLayout mJjVideoRelativeLayout;
    @Bind(R.id.details_parent_layout)
    RelativeLayout details_parent_layout;
    @Bind(R.id.rv_videoDetail)
    RecyclerView recyclerView;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.detail_handLayout)
    RelativeLayout detail_handLayout;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.Screen)
    LinearLayout Screen;
    @Bind(R.id.image_head)
    CircleImageView image_head;
    @Bind(R.id.video_view_layout)
    RelativeLayout video_view_layout;
    @Bind(R.id.comment_layout)
    LinearLayout comment_layout;

    String comment;//评论内容
    String beReplyUserId = "";
    String beReplyUserName = "";
    RelativeLayout.LayoutParams layoutParams ;

    @Override
    public int getLayout() {
        return R.layout.activity_video_details;
    }

    @Override
    public void initData() {
        Intent intent = this.getIntent();
        videolistEntity = (VideoEntity) intent.getSerializableExtra("VideoEntity");
        videoLocation = videolistEntity.getVideoLocation();
        videoID = Integer.parseInt(videolistEntity.getVideoId());
        videoURL = Constant.BaseVideoPlayUrl + videoLocation;
//        mPresenter.getVideoDetail(videoID);
        mPresenter.getCommentList(videoID, false);

        Logger.i("videoLocation: " + videoLocation);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new VideoDetailPresenter(this, this);

    }

    @Override
    public void initView(View view) {
        super.initView(view);
        if (videolistEntity.getCategory().equals("image")) {
            image.setVisibility(View.VISIBLE);
            Glide.with(this).load(Constant.BaseVideoPlayUrl + videolistEntity.getVideoLocation()).centerCrop().into(image);
//            Glide.with(this).load( ImageFromFileCache.base64ToBitmap(videolistEntity.getAvatarLocation())).into(image);

            mJjVideoRelativeLayout.setVisibility(View.GONE);
        } else {
            initVideo();
            mJjVideoRelativeLayout.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            PlayVideoView.setResourceVideo(videoURL);
        }
        Glide.with(this).load(ImageFromFileCache.base64ToBitmap(videolistEntity.getAvatarLocation())).centerCrop().into(image_head);
        toolbar_title.setText(videolistEntity.getVideoTitle());
        tv_count.setText(videolistEntity.getVideoViewNumber());
        tv_username.setText(videolistEntity.getUserName());

        mVideoDetailAdapter = new VideoDetailAdapter(this, mCommentEntity, videolistEntity);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mVideoDetailAdapter);
        btn_send = (Button) findViewById(R.id.btn_send);
        edit_ping = (EditText) findViewById(R.id.edit_ping);
        mVideoDetailAdapter.setClickItem(this);

        initSwipeLayout();
        lostEditFocus();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = edit_ping.getText().toString();
                if (edit_ping.getText().toString().length() > 3) {
                    mPresenter.saveComment("abcdefghijklmn", comment, "7000001", String.valueOf(videoID), beReplyUserId, beReplyUserName);
                    Logger.i("beReplyUserId=" + beReplyUserId);
                }
                lostEditFocus();
            }
        });
        edit_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditTextFocus();
            }
        });
        Screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lostEditFocus();
            }
        });
    }

    void getEditTextFocus() {
        Screen.setVisibility(View.VISIBLE);
        KeyBoardUtils.openKeybord(edit_ping, VideoDetails_Activiey.this);
        edit_ping.setFocusable(true);
        edit_ping.setFocusableInTouchMode(true);
        edit_ping.requestFocus();
    }

    void lostEditFocus() {
        Screen.setVisibility(View.GONE);
        edit_ping.clearFocus();
        edit_ping.setFocusable(false);
        edit_ping.setHint("说点什么吧....");
        beReplyUserId = "";
        beReplyUserName = "";
        KeyBoardUtils.closeKeybord(edit_ping, VideoDetails_Activiey.this);
    }

    /**
     * 初始化播放器
     */
    public void initVideo() {
        layoutParams = (RelativeLayout.LayoutParams) video_view_layout.getLayoutParams();
        layoutParams.height = (int) (ScreenUtils.getScreenWidth(this) * 0.5625);
        video_view_layout.setLayoutParams(layoutParams);

        PlayVideoView.setOnJjOutsideLinkClickListener(new OnJjOutsideLinkClickListener() {

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

        mJjVideoRelativeLayout.setJjToFront(details_parent_layout);// 设置此方法
        PlayVideoView.setMediaController(new UsetMediaContoller(this));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (detail_handLayout.getVisibility() == View.GONE) {
            detail_handLayout.setVisibility(View.VISIBLE);
            comment_layout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            toobar.setVisibility(View.VISIBLE);
            layoutParams.height = (int) (ScreenUtils.getScreenWidth(this) * 0.5625);
        } else {
            detail_handLayout.setVisibility(View.GONE);
            comment_layout.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            toobar.setVisibility(View.GONE);
            layoutParams.height = (ScreenUtils.getScreenHeight(this));
        }
        video_view_layout.setLayoutParams(layoutParams);

    }

    private void initSwipeLayout() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mVideoDetailAdapter.getItemCount() - 1;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    mPresenter.getCommentList(videoID, true);
                    showRefresh();
                }
            }
        });
    }


    @Override
    public void fillData(ArrayList<CommentEntity> data) {
        mCommentEntity.clear();
        mVideoDetailAdapter.updateWithClear(data);

    }

    @Override
    public void appendMoreDataToView(ArrayList<CommentEntity> data) {
        mVideoDetailAdapter.update(data);
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
    protected void onRefreshStarted() {
        Logger.i("刷新请求数据");
        mPresenter.resetCurrentPage();
        mHasMoreData = true;
        mPresenter.getCommentList(videoID, false);

    }

    @Override
    public void saveCommentSucceed() {
        showRefresh();
        mPresenter.resetCurrentPage();
        mHasMoreData = true;
        mPresenter.getCommentList(videoID, false);
        edit_ping.setText(null);
        edit_ping.clearFocus();
        edit_ping.setHint(getString(R.string.comment_hint));
    }

    @Override
    public void saveCommentFail() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void destroy() {
        super.destroy();
        if (PlayVideoView != null)
            PlayVideoView.onDestroy();
    }


    @Override
    public void onClick_comment(CommentEntity commentEntity) {
        edit_ping.setHint(getString(R.string.reply) + commentEntity.getUserName());
        beReplyUserId = String.valueOf(commentEntity.getCmUserId());
        beReplyUserName = String.valueOf(commentEntity.getUserName());
        Logger.i("beReplyUserId99=" + beReplyUserId);
        getEditTextFocus();
    }

    @Override
    public void onClick_header(CommentEntity commentEntity) {
        Intent intent = new Intent(VideoDetails_Activiey.this, PersionHome_Activity.class);
        Logger.i("getCmUserId" + commentEntity.getCmUserId());

        intent.putExtra("userID", commentEntity.getCmUserId());
        intent.putExtra("userName", commentEntity.getUserName());
        startActivity(intent);
    }

    @Override
    public void onClick_CommentLink(CommentEntity commentEntity) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                finish();
            } else {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 设置为竖屏
            }
        }
        return true;
    }

}

