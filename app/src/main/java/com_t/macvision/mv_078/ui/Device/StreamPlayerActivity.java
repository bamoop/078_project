package com_t.macvision.mv_078.ui.Device;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.macvision.mv_078.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.ui.adapter.FragmentTableAdapter;
import com_t.macvision.mv_078.ui.adapter.ViewPager_View_Adapter;
import com_t.macvision.mv_078.util.ScreenUtils;

import org.videolan.libvlc.EventHandler;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.vlc.Util;
import org.videolan.vlc.WeakHandler;

import java.util.ArrayList;
import java.util.List;


public class StreamPlayerActivity extends BaseActivity implements SurfaceHolder.Callback, IVideoPlayer {
    private final static String TAG = "[VlcVideoActivity]";

    //vlc相关
    private SurfaceView mSurfaceView;
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
    List<String> mTitle=new ArrayList<>();
    List<View> viewList = new ArrayList<>();
    ViewPager_View_Adapter mViewPagerAdapter;
    @Bind(R.id.vp_streamPlay)
    ViewPager mViewPager;
    @Bind(R.id.tab_selector)
    TabLayout mTableLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_player);
        ButterKnife.bind(this);
        mSurfaceView = (SurfaceView) findViewById(R.id.player_surface);

        initView();
        initVlc();


    }

    private void initView() {
        View view1= LayoutInflater.from(this).inflate(R.layout.psi_layout, null);
        View view2= LayoutInflater.from(this).inflate(R.layout.device_shoot, null);
        viewList.add(view1);
        viewList.add(view2);
        mTitle.add("状态");
        mTitle.add("小视频/抓拍");
        mViewPagerAdapter=new ViewPager_View_Adapter(viewList,mTitle);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(1);
        mTableLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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
        mLibVLC.playMRL("http://7xr5j6.com1.z0.glb.clouddn.com/hunantv0129.mp4?v=3");
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
    protected void onDestroy() {
        super.onDestroy();
        if (mLibVLC != null) {
            em.removeHandler(mVlcHandler);
        }
    }

    private final Handler eventHandler = new VideoPlayerEventHandler(StreamPlayerActivity.this);

    private static class VideoPlayerEventHandler extends WeakHandler<StreamPlayerActivity> {
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

    private void showLoading() {
    }

    private void hideLoading() {
    }
}
