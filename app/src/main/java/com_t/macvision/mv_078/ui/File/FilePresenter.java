package com_t.macvision.mv_078.ui.File;/**
 * Created by bzmoop on 2016/8/12 0012.
 */

import android.app.Activity;
import android.os.AsyncTask;

import com.orhanobut.logger.Logger;

import com_t.macvision.mv_078.core.MainActivity;
import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.base.BasePresonter;
import com_t.macvision.mv_078.ui.View.FileView;
import com_t.macvision.mv_078.util.DateUtil;
import com_t.macvision.mv_078.util.TaskUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 作者：LiangXiong on 2016/8/12 0012 13:50
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FilePresenter extends BasePresonter<FileView> {
    Activity context;
    ArrayList<FileEntity> mlist = new ArrayList<>();

    public FilePresenter(Activity context,FileView mFileView) {
        super(context, mFileView);
    }

    public void start(int type) {
        TaskUtils.executeAsyncTask(new AsyncTask<String, Void, File[]>() {
            @Override
            protected File[] doInBackground(String... params) {
                @SuppressWarnings("deprecation")
                File file = getAppDir(type);
                File[] files = file.listFiles();
//                Logger.i("传递过来的文件路径=" + type + "--目录下文件个数=" + files.length);
                return files;
            }

            @Override
            protected void onPostExecute(File[] files) {
                super.onPostExecute(files);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (files.length != 0)
                    for (int i = 0; i < files.length; i++) {
                        FileEntity file = new FileEntity();
                        file.setName(files[i].getName());
                        file.setPath(files[i].getPath());
                        Date d = new Date(files[i].lastModified());
                        file.setCreateTime(format.format(d));
//                        file.setSize(String.valueOf(files[i].length()/1048576));
                        file.setSize(DateUtil.FormetFileSize(files[i].length()));

                        mlist.add(file);
                        Logger.i("文件路径有=" + file.getPath());

                    }

                mView.getDataFinish();
                mView.fillData(mlist);
            }
        });


    }

    public void delete(File file) {

    }

    public void upload(File file) {

    }
}
