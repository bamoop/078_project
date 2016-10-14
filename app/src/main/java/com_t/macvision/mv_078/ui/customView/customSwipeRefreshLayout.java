package com_t.macvision.mv_078.ui.customView;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by bzmoop on 2016/9/12 0012.
 */
public class customSwipeRefreshLayout extends SwipeRefreshLayout implements NestedScrollingChild{


    public customSwipeRefreshLayout(Context context) {
        super(context);
    }
    public customSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
