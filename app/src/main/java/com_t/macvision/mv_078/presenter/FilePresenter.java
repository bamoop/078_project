package com_t.macvision.mv_078.presenter;/**
 * Created by bzmoop on 2016/8/12 0012.
 */

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.ui.File.FileContract;
import com_t.macvision.mv_078.util.TaskUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 作者：LiangXiong on 2016/8/12 0012 13:50
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FilePresenter implements FileContract.Presenter {
    private FileContract.View mFileView;
    Activity context;
    ArrayList<FileEntity> mlist = new ArrayList<>();

    public FilePresenter(FileContract.View mFileView) {
        this.mFileView = mFileView;
    }

    @Override
    public void start(String dir, Activity context) {
        TaskUtils.executeAsyncTask(new AsyncTask<String, Void, File[]>() {
            @Override
            protected File[] doInBackground(String... params) {
                @SuppressWarnings("deprecation")
                File file = MainActivity.getAppDir();
                File[] files = file.listFiles();

                Logger.i("传递过来的文件路径=" + dir + "目录下文件个数=" + files.length);


                return files;
            }

            @Override
            protected void onPostExecute(File[] files) {
                super.onPostExecute(files);
                if (files.length != 0)
                    for (int i = 0; i < files.length; i++) {
                        FileEntity file = new FileEntity();
                        file.setNaame(files[i].getName());
                        file.setPath(files[i].getPath());
                        file.setSize(String.valueOf(files[i].length()));
                        mlist.add(file);
                        Logger.i("文件路径有=" + file.getPath());

                    }

//                if (mCursor.moveToFirst()) {
//                    do {
//                        FileEntity file = new FileEntity();
//                        file.setNaame(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
//                        file.setPath(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
////                        file.setSize(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
//                        mlist.add(file);
//                    } while (mCursor.moveToNext());
//                }
//                if(Integer.parseInt(Build.VERSION.SDK) < 11)
//                {
//                    mCursor.close();
//
//                }
                mFileView.getDataFinish();
                mFileView.fillData(mlist);
            }
        });


    }

    @Override
    public void delete(File file) {

    }

    @Override
    public void upload(File file) {

    }
}
