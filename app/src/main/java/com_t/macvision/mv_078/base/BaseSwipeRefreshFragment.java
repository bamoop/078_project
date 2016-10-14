package com_t.macvision.mv_078.base;

/**
 * Created by bzmoop on 2016/9/9 0009.
 */

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.macvision.mv_078.R;

import butterknife.Bind;
import com_t.macvision.mv_078.ui.View.ISwipeRefreshView;

public abstract class BaseSwipeRefreshFragment<P extends BasePresonter> extends BaseFragment<P> implements ISwipeRefreshView {
    private static final String TAG = "BaseSwipeRefreshFragment";

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void initView(View view) {
        Log.i(TAG, "initView");
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prepareRefresh()) {
                    onRefreshStarted();
                } else {
                    //产生一个加载数据的假象
                    hideRefresh();
                }
            }
        });
    }

    /**
     * 检查数据状态
     *
     * @return 返回true表示应该加载数据而不是假象
     */
    protected boolean prepareRefresh() {
        return true;
    }

    /**
     * 加载数据的方法
     */
    protected abstract void onRefreshStarted();

    /**
     * 显示刷新view
     **/
    @Override
    public void showRefresh() {
        Log.i(TAG, "showRefresh");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showEmptyView() {
        Log.i(TAG, "showEmptyView");

    }

    /**
     * 隐藏刷新view
     **/
    @Override
    public void hideRefresh() {
        Log.i(TAG, "hideRefresh");
        // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);
    }
}
