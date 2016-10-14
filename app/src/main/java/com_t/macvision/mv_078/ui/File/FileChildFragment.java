package com_t.macvision.mv_078.ui.File;/**
 * Created by bzmoop on 2016/8/15 0015.
 */

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.ui.Upload.UploadActivity;
import com_t.macvision.mv_078.ui.VideoList.FragmentMenu1;
import com_t.macvision.mv_078.ui.View.FileView;
import com_t.macvision.mv_078.ui.adapter.FileListAdapter;
import com_t.macvision.mv_078.ui.adapter.RecycleViewDivider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者：LiangXiong on 2016/8/15 0015 16:59
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileChildFragment extends BaseFragment<FilePresenter> implements FileView, MenuClickListener, FileListAdapter.IClickMainItem {
    @Bind(R.id.rv_filelist)
    RecyclerView rv_filelist;
    @Bind(R.id.file_menu_layout)
    LinearLayout file_menu_layout;
    @Bind(R.id.image_download)
    ImageView image_selectAll;
    @Bind(R.id.play_layout)
    LinearLayout layout_play;
    @Bind(R.id.tv_download)
    TextView tv_download;

    private int type;
    private List<FileEntity> mFileList = new ArrayList<FileEntity>();

    private FilePresenter mPresenter;
    private FileListAdapter mFileAdapter;
    private LinearLayoutManager layoutManager;

    public static FileChildFragment Tab1Instance(Bundle bundle) {
        FileChildFragment tab1Fragment = new FileChildFragment();
        tab1Fragment.setArguments(bundle);
        return tab1Fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.filechild_fragment;
    }

    @Override
    public void initView(View view) {
        mFileAdapter = new FileListAdapter(getActivity(), mFileList, true);
        mFileAdapter.setClickItem(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_filelist.setLayoutManager(layoutManager);
        rv_filelist.setAdapter(mFileAdapter);
        rv_filelist.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));
        image_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFileAdapter.clickSelectAll();
            }
        });
        layout_play.setVisibility(View.GONE);
        Glide.with(this).load(R.mipmap.local_file_save).into(image_selectAll);
        tv_download.setText("全选");
    }

    @Override
    public void initPresenter() {
        mPresenter = new FilePresenter(currentContext, this);
    }


    @OnClick(R.id.image_delete)
    void delete() {
        int size = mFileList.size() - 1;
        for (int i = size; i > -1; i--) {
            if (mFileList.get(i).isChecked()) {
                File file = new File(mFileList.get(i).getPath());
                if (file.exists()) {
                    mFileAdapter.removeData(i);
                    Logger.i("删除=" + file.getPath() + "删除结果=" + file.delete() + "数组长度=" + mFileList.size());
                } else Logger.i("文件不存在");

            }
        }
    }

    @Override
    public void loadData() {
        super.loadData();
        type = getArguments().getInt("type");
        new FileFragment().setMenuClickListener(this);
        mPresenter.start(type);
    }



    @Override
    public void fillData(ArrayList<FileEntity> data) {
        mFileList.addAll(data);
        mFileAdapter.updateWithClear(data);
    }

    @Override
    public void showEmptyView() {

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
        file_menu_layout.setVisibility(View.VISIBLE);
        Logger.i("updateMenuTitleON1");

    }

    @Override
    public void onClickEdit() {
        mFileAdapter.closeItemAnimation();
        file_menu_layout.setVisibility(View.GONE);
        Logger.i("updateMenuTitleOff1");

    }

    @Override
    public void onClickDelete() {

    }

    @Override
    public void selectAll() {

    }


    @Override
    public void onClickItemVideo(CheckBox mCheckBox, FileEntity entity, int position) {

            if (FileFragment.mIsEditState) {
                entity.setChecked(!mCheckBox.isChecked());
                mCheckBox.setChecked(!mCheckBox.isChecked());
            } else {
                if (FragmentMenu1.entity == null) {
                    new SweetAlertDialog(currentContext, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误！")
                            .setContentText("请确定网络连接后再试")
                            .show();
                    new FragmentMenu1().getType();
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FileEntity", entity);
                    MainActivity.startActivity(getMyActivity(), bundle, UploadActivity.class);
                }
        }
    }
}
