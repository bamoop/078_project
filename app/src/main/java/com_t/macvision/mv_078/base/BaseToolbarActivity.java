package com_t.macvision.mv_078.base;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.macvision.mv_078.R;

import butterknife.Bind;

/**
 * Created by bzmoop on 2016/9/13 0013.
 */
public abstract class BaseToolbarActivity<P extends BasePresonter> extends BaseActivity<P> {
    private static final String TAG = "BaseToolbarActivity";
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public void initView(View view) {
        toolbar_title.setText(setTitle());
        toolbar.setTitle("");

    }
    public void goBackMenu() {
        toolbar.setNavigationIcon(R.mipmap.icon_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "tooar返回");
            }
        });
    }
    protected abstract String setTitle();

}
