package com_t.macvision.mv_078.ui.Upload;/**
 * Created by bzmoop on 2016/8/18 0018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com_t.macvision.mv_078.Constant;
import com.macvision.mv_078.R;
import com_t.macvision.mv_078.base.BaseActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.presenter.UploadPresenter;
import com_t.macvision.mv_078.util.LocationUtils;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import cn.com.video.venvy.param.JjVideoRelativeLayout;
import cn.com.video.venvy.param.JjVideoView;
import cn.com.video.venvy.param.OnJjBufferCompleteListener;
import cn.com.video.venvy.param.OnJjBufferStartListener;
import cn.com.video.venvy.param.OnJjBufferingUpdateListener;
import cn.com.video.venvy.param.OnJjOpenStartListener;
import cn.com.video.venvy.param.OnJjOpenSuccessListener;
import cn.com.video.venvy.param.OnJjOutsideLinkClickListener;
import cn.com.video.venvy.widget.UsetMediaContoller;

/**
 * 作者：LiangXiong on 2016/8/18 0018 21:32
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UploadActivity extends BaseActivity implements UploadContract.View {

    private FileEntity fileEntity;
    private UploadContract.Presenter mPresenter;
    private Map<String, String> upMap = new HashMap<>();

    private JjVideoView PlayVideoView;
    private View mLoadBufferView;
    private View mLoadView;
    private TextView mLoadBufferTextView;
    private TextView mLoadText;
    private LinearLayout btn_upload;

    private Button btn_againLocation;
    private TextView btn_type1;
    private EditText ed_state;

    @Override
    public void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_upload);

        initData();
        initView();
        initVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlayVideoView.setResourceVideo(fileEntity.getPath());
    }

    private void initData() {
        Intent intent = this.getIntent();
        fileEntity = (FileEntity) intent.getSerializableExtra("FileEntity");
        mPresenter = new UploadPresenter(this);

        upMap.put(Constant.userId, (String) SharedPreferencesUtils.getParam(this, Constant.userId,""));
        upMap.put("videoTitle","狂暴酷炫叼炸天" );
        upMap.put("videoReleaseAddress", (String)SharedPreferencesUtils.getParam(this,Constant.CITY,""));
        upMap.put("videoCaption","66666" );

    }

    private void initView() {
        PlayVideoView = (JjVideoView) findViewById(R.id.video);
        mLoadBufferView = findViewById(R.id.sdk_load_layout);
        mLoadView = findViewById(R.id.sdk_ijk_progress_bar_layout);
        mLoadBufferTextView = (TextView) findViewById(R.id.sdk_sdk_ijk_load_buffer_text);
        mLoadText = (TextView) findViewById(R.id.sdk_ijk_progress_bar_text);
        ed_state= (EditText) findViewById(R.id.ed_state);

        PlayVideoView.setMediaController(new UsetMediaContoller(this));

        btn_upload = (LinearLayout) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.upload(upMap,fileEntity);
            }
        });
        btn_againLocation = (Button) findViewById(R.id.btn_againLocation);
        btn_againLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationUtils.getCNBylocation(UploadActivity.this);

            }
        });
        btn_type1=(TextView)findViewById(R.id.btn_type1);
        btn_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upMap.put("videoType", "1");
            }
        });
    }


    public void initVideo() {

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
        PlayVideoView.setVideoJjType(1);
        // 参数代表是否记录视频播放位置 默认false不记录 true代表第二次或多次进入，直接跳转到上次退出的时间点开始播放
        PlayVideoView.setVideoJjSaveExitTime(false);

        RelativeLayout upload_parent_layout = (RelativeLayout) findViewById(R.id.upload_parent_layout);
        JjVideoRelativeLayout mJjVideoRelativeLayout = (JjVideoRelativeLayout) findViewById(R.id.jjlayout);
        mJjVideoRelativeLayout.setJjToFront(upload_parent_layout);// 设置此方法
    }


    @Override
    public void uploadDataFinish() {

    }

    @Override
    public void uploadDataFail() {

    }
}
