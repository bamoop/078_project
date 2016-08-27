package com_t.macvision.mv_078.ui.Device;/**
 * Created by bzmoop on 2016/8/3 0003.
 */

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.macvision.mv_078.R;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.core.MainActivity;

import butterknife.Bind;

/**
 * 作者：LiangXiong on 2016/8/3 0003 19:33
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FragmentMenu3 extends BaseFragment {
    @Bind(R.id.play_stream)
    Button play_stream;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getLayout() {
        return R.layout.fragment_menu3;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        toolbar.setTitle("连接记录仪");
        play_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MainActivity.startActivity(getMyActivity(), bundle, StreamPlayerActivity.class);

            }
        });
    }
}
