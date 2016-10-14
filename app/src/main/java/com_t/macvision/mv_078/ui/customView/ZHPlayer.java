package com_t.macvision.mv_078.ui.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import cn.com.video.venvy.param.JjVideoView;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-26
 * Time: 18:45
 * QQ  : 294894105 ZH
 * 封装一个云视链播放器，只是为了简化代码。。。。
 */

public class ZHPlayer extends RelativeLayout {
    private LayoutInflater mInflater;
    private boolean stretch = true;//是否拉伸

    JjVideoView PlayVideoView;


    public ZHPlayer(Context context) {
        super(context);
    }

    public ZHPlayer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    public ZHPlayer(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet,defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
