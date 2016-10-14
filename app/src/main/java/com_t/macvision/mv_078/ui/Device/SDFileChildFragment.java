package com_t.macvision.mv_078.ui.Device;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.base.BaseSwipeRefreshFragment;
import com_t.macvision.mv_078.ui.File.FileFragment;
import com_t.macvision.mv_078.ui.File.MenuClickListener;
import com_t.macvision.mv_078.ui.adapter.FileListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

/**
 * 作者：LiangXiong on 2016/8/15 0015 16:59
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class SDFileChildFragment extends BaseSwipeRefreshFragment<SDFilePresenter> implements SDFileView, MenuClickListener, FileListAdapter.IClickMainItem {
    @Bind(R.id.rv_filelist)
    RecyclerView rv_filelist;
    @Bind(R.id.image_download)
    ImageView image_download;
    @Bind(R.id.image_play)
    ImageView image_play;
    @Bind(R.id.image_delete)
    ImageView image_delete;
    @Bind(R.id.file_menu_layout)
    LinearLayout file_menu_layout;
    private ArrayList<FileEntity> mFileList = new ArrayList<FileEntity>();

    private SDFilePresenter mPresenter;
    private FileListAdapter mFileAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<FileEntity> selectFileList = new ArrayList<>();
    private int type;
    /**
     * 有更多的数据
     */
    private boolean mHasMoreData = true;

    public static SDFileChildFragment Tab1Instance(Bundle bundle) {
        SDFileChildFragment tab1Fragment = new SDFileChildFragment();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.filechild_fragment;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mFileAdapter = new FileListAdapter(getActivity(), mFileList, false);
        mFileAdapter.setClickItem(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_filelist.setLayoutManager(layoutManager);
        rv_filelist.setAdapter(mFileAdapter);
        rv_filelist.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));

        rv_filelist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mFileAdapter.getItemCount() - 2;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData) {
                    mPresenter.getdata(true, type);
                    Logger.i("onScrolled: onScrolled" + "type");
                    showRefresh();
                }
            }
        });
    }

    @Override
    protected void onRefreshStarted() {
        Logger.i("刷新请求数据");
        mPresenter.resetCurrentPage();
        mHasMoreData = true;
        mPresenter.getdata(false, type);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        mPresenter = new SDFilePresenter(currentContext, this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {
        super.loadData();
        type = getArguments().getInt("type");
        new Fragment_SDFile().setMenuClickListener(this);
        mPresenter.getdata(false, type);
    }

    @Override
    public void getDataFinish() {
        hideRefresh();
    }

    @Override
    public void showErrorView(String massage) {
        hideRefresh();
    }

    @Override
    public void fillData(ArrayList<FileEntity> data) {
        mFileList.clear();
        mFileAdapter.updateWithClear(data);
    }

    @Override
    public void appendMoreDataToView(ArrayList<FileEntity> data) {
        mFileAdapter.update(data);
    }

    @Override
    public void hasNoMoreData() {
        mHasMoreData = false;
    }

    @Override
    public boolean menuEditEnable() {
        return false;
    }

    @Override
    public void onClickCancel() {
        mFileAdapter.openItemAnimation();
        if (file_menu_layout!=null)
            file_menu_layout.setVisibility(View.VISIBLE);


    }

    @Override
    public void onClickEdit() {
        mFileAdapter.closeItemAnimation();
        if (file_menu_layout!=null)
            file_menu_layout.setVisibility(View.GONE);

    }

    @Override
    public void onClickDelete() {

    }

    @Override
    public void selectAll() {

    }


    @Override
    public void onClickItemVideo(CheckBox mCheckBox, FileEntity entity, int position) {
        if (Fragment_SDFile.mIsEditState) {
            entity.setChecked(!mCheckBox.isChecked());
            mCheckBox.setChecked(!mCheckBox.isChecked());
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("http://" + Constant.IP + entity.getPath()), "video/3gp");
            Log.i("moop", "onClickItemVideo: " + "http://" + Constant.IP + entity.getPath());
            startActivity(intent);
        }
    }

    @OnClick(R.id.image_delete)
    void deleteOnclick() {
        int size = mFileList.size() - 1;
        for (int i = size; i > -1; i--) {
            if (mFileList.get(i).isChecked()) {
                selectFileList.add(mFileList.get(i));
                mFileAdapter.removeData(i);
            }
        }
        mPresenter.download(selectFileList);
    }

    @OnClick(R.id.image_play)
    void playOnclick() {

//        int size = mFileList.size() - 1;
//        for (int i = size; i > -1; i--) {
//
//        }
    }

    @OnClick(R.id.image_download)
    void downloadOnclick() {
        int size = mFileList.size() - 1;
        for (int i = size; i > -1; i--) {
            if (mFileList.get(i).isChecked()) {
                selectFileList.add(mFileList.get(i));
            }
        }
        mPresenter.download(selectFileList);


    }

    @Override
    public void commandSucceed() {

    }

    @Override
    public void commandFail(String massage) {

    }
}
