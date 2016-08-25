package com_t.macvision.mv_078.ui.person_main;/**
 * Created by bzmoop on 2016/8/25 0025.
 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.macvision.mv_078.R;

import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.model.entity.VideoEntity;

/**
 * 作者：LiangXiong on 2016/8/25 0025 15:32
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class Fragment_Dynamic extends BaseFragment implements DynamicContract.View {
    @Bind(R.id.rv_dynamic)
    RecyclerView rv_dynamic;
    LinearLayoutManager layoutManager;

    /**
     * 是否有更多数据
     **/
    private boolean mHasMoreData = true;

    public  int currentPage = 1;

    private List<VideoEntity.VideolistEntity> mDataList;


    @Override
    protected int getLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void fillData(VideoEntity entity) {

    }

    @Override
    public void hasNoMoreData() {

    }

    @Override
    public void appendMoreDataToView(VideoEntity entity) {

    }

    @Override
    public void getDataFinish() {

    }

    @Override
    public void getDataFail() {

    }
}
