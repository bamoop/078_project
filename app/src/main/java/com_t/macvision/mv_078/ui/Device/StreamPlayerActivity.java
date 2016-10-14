package com_t.macvision.mv_078.ui.Device;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.Util;
import org.videolan.vlc.VLCApplication;
import org.videolan.vlc.WeakHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.ui.adapter.ViewPager_View_Adapter;
import com_t.macvision.mv_078.util.NetworkUtil;
import com_t.macvision.mv_078.util.ScreenUtils;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;
import com_t.macvision.mv_078.util.WIFIUtil;


public class StreamPlayerActivity extends BaseActivity<StreamPlayPresenter> implements SurfaceHolder.Callback, IVideoPlayer, StreamPlayerView {
    private final static String TAG = "StreamPlayerActivity";
    @Bind(R.id.btn_back)
    TextView btnBack;
    //vlc相关
    private LibVLC mLibVLC;
    private SurfaceHolder mSurfaceHolder;
    private int mVideoHeight;
    private int mVideoWidth;
    private int mVideoVisibleHeight;
    private int mVideoVisibleWidth;
    private int mSarNum;
    private int mSarDen;
    EventHandler em = EventHandler.getInstance();
    //viewpage相关
    List<String> mTitle = new ArrayList<>();
    List<View> viewList = new ArrayList<>();
    ViewPager_View_Adapter mViewPagerAdapter;
    @Bind(R.id.vp_streamPlay)
    ViewPager mViewPager;
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Bind(R.id.btn_video)
    RelativeLayout btn_video;
    @Bind(R.id.player_surface)
    SurfaceView mSurfaceView;
    String playUrl;
    View view_psi, viewshoot;
    Button btn_minVideo, btn_snapshot;
    SweetAlertDialog playWattingDialog;
    int instructCount = -1;
    WIFIUtil mWIFIUtil;

    @Override
    public int getLayout() {
        return R.layout.activity_stream_player;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        playUrl = intent.getStringExtra("playUrl");
        mWIFIUtil = new WIFIUtil(this);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new StreamPlayPresenter(this, this);
    }

    @Override
    public void initView(View view) {
        view_psi = LayoutInflater.from(this).inflate(R.layout.psi_layout, null);
        viewshoot = LayoutInflater.from(this).inflate(R.layout.device_shoot, null);
        playWattingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在努力加载视频");
        viewList.add(viewshoot);
        viewList.add(view_psi);
        mTitle.add("小视频/抓拍");
        mTitle.add("状态");
        mViewPagerAdapter = new ViewPager_View_Adapter(viewList, mTitle);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        btn_snapshot = (Button) viewshoot.findViewById(R.id.btn_snapshot);
        btn_minVideo = (Button) viewshoot.findViewById(R.id.btn_minVideo);

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StreamPlayerActivity.this, SDFile_Activity.class);
                startActivity(intent);
                Logger.i("点击SD文档");

            }
        });
        btn_snapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.snapshot();
            }
        });
        btn_minVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.minVideo();
                playWattingDialog.setTitleText("正在录制小视频，请稍等");
                playWattingDialog.show();
                new CountDownTimer(15000, 15000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        playWattingDialog.dismiss();
                        mPresenter.inspectShardVideo();
                    }
                }.start();
            }
        });
        initVlc();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLibVLC.playMRL(playUrl);
        playWattingDialog.setTitleText("正在努力加载视频");
//        playWattingDialog.show();
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                instructCount++;
                switch (instructCount) {
                    case 0:
                        mPresenter.setRecord(true);
                        break;
                    case 1:
                        mPresenter.inspectShardVideo();
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onFinish() {
                instructCount = -1;

            }
        }.start();
    }

    private void initVlc() {
        try {
            mLibVLC = Util.getLibVlcInstance();
        } catch (LibVlcException e) {
            return;
        }
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setFormat(PixelFormat.RGBX_8888);
        mSurfaceHolder.addCallback(this);

        em.addHandler(eventHandler);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mSurfaceView.setKeepScreenOn(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mLibVLC != null) {
            mLibVLC.stop();
            mSurfaceView.setKeepScreenOn(false);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mLibVLC != null) {
            em.removeHandler(mVlcHandler);
        }
    }

    private final Handler eventHandler = new VideoPlayerEventHandler(StreamPlayerActivity.this);

    @Override
    public void getDateError(String massage) {

    }

    @Override
    public void getDataSuccess(String playUrl) {

    }

    @Override
    public void getShareVideoListSuccess() {

    }

    @Override
    public void getShareVideoListError() {

    }

    @Override
    public void downloadShareVideoing() {
        if (mLibVLC != null) {
            mLibVLC.stop();
            mSurfaceView.setKeepScreenOn(false);
        }
    }

    @Override
    public void downloadShareVoideSuccess() {
        if (!mLibVLC.isPlaying())
            mLibVLC.playMRL(playUrl);
    }

    @Override
    public void commandSucceed() {

    }

    @Override
    public void commandFail(String massage) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        breakDevice();
    }

    private class VideoPlayerEventHandler extends WeakHandler<StreamPlayerActivity> {
        public VideoPlayerEventHandler(StreamPlayerActivity owner) {
            super(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            StreamPlayerActivity activity = getOwner();
            if (activity == null)
                return;

            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerPlaying:
                    Log.i(TAG, "MediaPlayerPlaying");
                    break;
                case EventHandler.MediaPlayerPaused:
                    Log.i(TAG, "MediaPlayerPaused");
                    break;
                case EventHandler.MediaPlayerStopped:
                    Log.i(TAG, "MediaPlayerStopped");
                    break;
                case EventHandler.MediaPlayerEndReached:
                    Log.i(TAG, "MediaPlayerEndReached");
                    break;
                case EventHandler.MediaPlayerVout:
                    Log.i(TAG, "handleVout");
                    playWattingDialog.dismiss();
                    break;
                case EventHandler.MediaPlayerPositionChanged:
// don't spam the logs
                    break;
                case EventHandler.MediaPlayerEncounteredError:
                    Log.i(TAG, "MediaPlayerEncounteredError");
                    break;
                default:
                    Log.e(TAG, String.format("Event not handled (0x%x)", msg.getData().getInt("event")));
                    break;
            }
        }
    }

    ;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setSurfaceSize(mVideoWidth, mVideoHeight, mVideoVisibleWidth, mVideoVisibleHeight, mSarNum, mSarDen);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mLibVLC != null) {
            mSurfaceHolder = holder;
            mLibVLC.attachSurface(holder.getSurface(), this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
        if (mLibVLC != null) {
            mLibVLC.attachSurface(holder.getSurface(), this);//, width, height
        }
        if (width > 0) {
            mVideoHeight = height;
            mVideoWidth = width;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mLibVLC != null) {
            mLibVLC.detachSurface();
        }
    }

    @Override
    public void setSurfaceSize(int width, int height, int visible_width, int visible_height, int sar_num, int sar_den) {
        mVideoHeight = height;
        mVideoWidth = width;
        mVideoVisibleHeight = visible_height;
        mVideoVisibleWidth = visible_width;
        mSarNum = sar_num;
        mSarDen = sar_den;
        mHandler.removeMessages(HANDLER_SURFACE_SIZE);
        mHandler.sendEmptyMessage(HANDLER_SURFACE_SIZE);
    }

    private static final int HANDLER_BUFFER_START = 1;
    private static final int HANDLER_BUFFER_END = 2;
    private static final int HANDLER_SURFACE_SIZE = 3;

    private static final int SURFACE_BEST_FIT = 0;
    private static final int SURFACE_FIT_HORIZONTAL = 1;
    private static final int SURFACE_FIT_VERTICAL = 2;
    private static final int SURFACE_FILL = 3;
    private static final int SURFACE_16_9 = 4;
    private static final int SURFACE_4_3 = 5;
    private static final int SURFACE_ORIGINAL = 6;
    private int mCurrentSize = SURFACE_FIT_VERTICAL;

    private Handler mVlcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null || msg.getData() == null)
                return;

            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerTimeChanged:
                    break;
                case EventHandler.MediaPlayerPositionChanged:
                    break;
                case EventHandler.MediaPlayerPlaying:
                    mHandler.removeMessages(HANDLER_BUFFER_END);
                    mHandler.sendEmptyMessage(HANDLER_BUFFER_END);
                    break;
                case EventHandler.MediaPlayerBuffering:
                    break;
                case EventHandler.MediaPlayerLengthChanged:
                    break;
                case EventHandler.MediaPlayerEndReached:
                    //播放完成
                    break;
            }

        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_BUFFER_START:
                    showLoading();
                    break;
                case HANDLER_BUFFER_END:
                    hideLoading();
                    break;
                case HANDLER_SURFACE_SIZE:
                    changeSurfaceSize();
                    break;
            }
        }
    };


    private void changeSurfaceSize() {
        // 获取屏幕大小
        int dw = ScreenUtils.getScreenWidth(this);
        int dh = ScreenUtils.getScreenHeight(this);
        Log.i(TAG, "changeSurfaceSize: dw=" + dw + "---" + "dh=" + dh);
        // 计算比例
        double ar = (double) mVideoWidth / (double) mVideoHeight;
        // 计算显示比例
        double dar = (double) dw / (double) dh;
        switch (mCurrentSize) {
            case SURFACE_BEST_FIT:
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_FIT_HORIZONTAL:
                dh = (int) (dw / ar);
                break;
            case SURFACE_FIT_VERTICAL:
                dw = (int) (dh * ar);
                break;
            case SURFACE_FILL:
                break;
            case SURFACE_16_9:
                ar = 16.0 / 9.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_4_3:
                ar = 4.0 / 3.0;
                if (dar < ar)
                    dh = (int) (dw / ar);
                else
                    dw = (int) (dh * ar);
                break;
            case SURFACE_ORIGINAL:
                dh = mVideoHeight;
                dw = mVideoWidth;
                break;
        }

        mSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
        lp.width = dw;
        lp.height = dh;
        mSurfaceView.setLayoutParams(lp);
        mSurfaceView.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void showLoading() {
    }

    private void hideLoading() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            breakDevice();
        }
        return true;
    }

    void breakDevice() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText("是否退出记录仪，并恢复之前的网络？")
                .setCancelText(" 不 ")
                .setConfirmText("确定")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (NetworkUtil.getIP().equals("192.72.1.1")) {
                    mWIFIUtil.closeWifi();
                } else {
                    mWIFIUtil.disconnectWifi(mWIFIUtil.getNetworkId());
                    int wifiNetId = (int) SharedPreferencesUtils.getParam(VLCApplication.getAppContext(), "wifiNetId", 1000);
                    mWIFIUtil.comment(wifiNetId);
                }
                finish();
            }
        }).show();
    }
}
