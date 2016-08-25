package com_t.macvision.mv_078.ui.Device;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.macvision.mv_078.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.DeviceModle.FileNode;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.presenter.FilePresenter;
import com_t.macvision.mv_078.presenter.SDFilePresenter;
import com_t.macvision.mv_078.ui.File.FileContract;
import com_t.macvision.mv_078.ui.File.FileFragment;
import com_t.macvision.mv_078.ui.File.MenuClickListener;
import com_t.macvision.mv_078.ui.adapter.FileListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

/**
 * 作者：LiangXiong on 2016/8/15 0015 16:59
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class SDFileChildFragment extends BaseFragment implements SDFileContract.View, MenuClickListener, FileListAdapter.IClickMainItem {
    @Bind(R.id.rv_filelist)
    RecyclerView rv_filelist;
    private List<FileEntity> mFileList = new ArrayList<FileEntity>();
    private SDFilePresenter mPresenter;
    private FileListAdapter mFileAdapter;
    private LinearLayoutManager layoutManager;
    private List<FileEntity> selectList = new ArrayList<>();
    private Activity mActivity;

    public static SDFileChildFragment Tab1Instance(Bundle bundle) {
        SDFileChildFragment tab1Fragment = new SDFileChildFragment();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.filechild_fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new SDFilePresenter(this);
        mPresenter.getData("", currentContext,0);
        mFileAdapter = new FileListAdapter(getActivity(), mFileList);
        mFileAdapter.setClickItem(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_filelist.setLayoutManager(layoutManager);
        rv_filelist.setAdapter(mFileAdapter);
        rv_filelist.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));
        initData();

    }

    private void initData() {
        mPresenter.getData("", currentContext,0);

    }

    @Override
    protected void lazyLoad() {
        new FileFragment().setMenuClickListener(this);
    }



    @Override
    public void fillData(List<FileNode> nodeList) {

    }

    @Override
    public void hasNoMoreData() {

    }

    @Override
    public void appendMoreDataToView(List<FileNode> nodeList) {

    }

    @Override
    public void getDataFinish() {

    }

    @Override
    public void getDatafail() {

    }


    @Override
    public boolean menuEditEnable() {
        return false;
    }

    @Override
    public void onClickCancel() {
        mFileAdapter.openItemAnimation();

    }

    @Override
    public void onClickEdit() {
        mFileAdapter.closeItemAnimation();

    }

    @Override
    public void onClickDelete() {

    }

    @Override
    public void selectAll() {

    }


    @Override
    public void onClickItemVideo(CheckBox mCheckBox, FileEntity entity) {
        if (Fragment_SDFile.mIsEditState)
            mCheckBox.setChecked(!mCheckBox.isChecked());
        else {
            Log.i("moop3", "onClickItemDetail: 点击无效" + mActivity);
            Bundle bundle = new Bundle();
            bundle.putSerializable("FileEntity", entity);
        }
    }

}
