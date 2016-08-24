package com_t.macvision.mv_078.ui.File;/**
 * Created by bzmoop on 2016/8/15 0015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.macvision.mv_078.R;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.presenter.FilePresenter;
import com_t.macvision.mv_078.ui.Upload.UploadActivity;
import com_t.macvision.mv_078.ui.adapter.FileListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：LiangXiong on 2016/8/15 0015 16:59
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileChildFragment extends BaseFragment implements FileContract.View, MenuClickListener, FileListAdapter.IClickMainItem {
    @Bind(R.id.rv_filelist)
    RecyclerView rv_filelist;
    private List<FileEntity> mFileList = new ArrayList<FileEntity>();
    private FilePresenter mPresenter;
    private FileListAdapter mFileAdapter;
    private LinearLayoutManager layoutManager;
    private List<FileEntity> selectList = new ArrayList<>();
    private Activity mActivity;

    public static FileChildFragment Tab1Instance(Bundle bundle) {
        FileChildFragment tab1Fragment = new FileChildFragment();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.filechild_fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new FilePresenter(this);
        mPresenter.start("", getActivity());
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
        mPresenter.start("", getActivity());
    }

    @Override
    protected void lazyLoad() {
        new FileFragment().setMenuClickListener(this);
    }

    @Override
    public void fillData(List<FileEntity> entity) {
        mFileList.addAll(entity);
        mFileAdapter.updateWithClear(entity);
    }

    @Override
    public void getDataFinish() {

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
        if (FileFragment.mIsEditState)
            mCheckBox.setChecked(!mCheckBox.isChecked());
        else {
            Log.i("moop2", "onClickItemVideo: 点击无效" + mActivity);
            Bundle bundle = new Bundle();
            bundle.putSerializable("FileEntity", entity);
            MainActivity.startActivity(getMyActivity(), bundle, UploadActivity.class);
        }
    }

}
