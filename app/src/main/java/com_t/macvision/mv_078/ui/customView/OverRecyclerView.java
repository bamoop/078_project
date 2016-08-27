package com_t.macvision.mv_078.ui.customView;/**
 * Created by bzmoop on 2016/8/27 0027.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 作者：LiangXiong on 2016/8/27 0027 12:15
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class OverRecyclerView extends RecyclerView {
    public OverRecyclerView(Context context) {
        super(context);
    }

    public OverRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}
