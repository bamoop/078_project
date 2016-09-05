package com_t.macvision.mv_078.ui.File;/**
 * Created by bzmoop on 2016/8/15 0015.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.macvision.mv_078.R;
import com.orhanobut.logger.Logger;

import butterknife.OnClick;
import com_t.macvision.mv_078.base.BaseFragment;
import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.presenter.FilePresenter;
import com_t.macvision.mv_078.ui.Upload.UploadActivity;
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
public class FileChildFragment extends BaseFragment implements FileContract.View, MenuClickListener, FileListAdapter.IClickMainItem {
    @Bind(R.id.rv_filelist)
    RecyclerView rv_filelist;
    @Bind(R.id.file_menu_layout)
    LinearLayout file_menu_layout;
    @Bind(R.id.image_save)
    ImageView image_save;
    private List<FileEntity> mFileList = new ArrayList<FileEntity>();
    private FilePresenter mPresenter;
    private FileListAdapter mFileAdapter;
    private LinearLayoutManager layoutManager;
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
        mPresenter.start(MainActivity.sAppDir, getActivity());
        mFileAdapter = new FileListAdapter(getActivity(), mFileList);
        mFileAdapter.setClickItem(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_filelist.setLayoutManager(layoutManager);
        rv_filelist.setAdapter(mFileAdapter);
        rv_filelist.addItemDecoration(new RecycleViewDivider(
                currentContext, LinearLayoutManager.VERTICAL, 1,
                getResources().getColor(R.color.theme_fragment_bgColor)));
        initData();
        image_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFileAdapter.clickSelectAll();
            }
        });

    }

    @OnClick(R.id.image_delete)
    void delete() {
        int size = mFileList.size() - 1;
        int x = 0;
        for (int i = size; i > -1; i--) {
            if (mFileList.get(i).isChecked()) {
                File file = new File(mFileList.get(i).getPath());
                if (file.exists()) {
                    mFileAdapter.removeData(i);
                    Logger.i("删除=" + file.getPath() + "删除结果=" + file.delete() + "数组长度=" + mFileList.size());

                } else Logger.i("文件不存在");

            }
            x++;
//                i++;
//            }while (i<size);
        }
        Logger.i("循环了=" + x);
//        mFileAdapter.cleanSelectAdd();
    }

    private void initData() {
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
        file_menu_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickEdit() {
        mFileAdapter.closeItemAnimation();
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

        if (FileFragment.mIsEditState) {
            entity.setChecked(!mCheckBox.isChecked());
            mCheckBox.setChecked(!mCheckBox.isChecked());
            Log.i("moop2", "onClickItemDetail: 点击无效" + mActivity);
            Bundle bundle = new Bundle();
            bundle.putSerializable("FileEntity", entity);
//            MainActivity.startActivity(getMyActivity(), bundle, UploadActivity.class);
        }
    }
}
