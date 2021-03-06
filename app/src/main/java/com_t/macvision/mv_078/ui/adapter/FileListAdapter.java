package com_t.macvision.mv_078.ui.adapter;/**
 * Created by bzmoop on 2016/8/14 0014.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import com_t.macvision.mv_078.model.entity.FileEntity;
import com_t.macvision.mv_078.util.ImageFromFileCache;
import com_t.macvision.mv_078.util.SlideRelativeLayout;
import com_t.macvision.mv_078.util.TaskUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：LiangXiong on 2016/8/14 0014 20:44
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolderItem> {
    public List<FileEntity> mFileList;
    private Context mContext;
    public static final int NORMAL = 1000;
    public static final int SLIDE = 2000;
    private int mState = NORMAL;
    private List<ViewHolderItemVideo> mViewHolderItemVideos = new ArrayList<>();
    private static IClickMainItem mIClickItem;
    public List<FileEntity> selectList = new ArrayList<>();
    boolean islocalfile=true;

    public FileListAdapter(Context context, List<FileEntity> mFileList,boolean islocalfile) {
        this.mContext = context;
        this.mFileList = mFileList;
        this.islocalfile= islocalfile;
    }

    public void update(List<FileEntity> data) {
        mFileList.addAll(data);
        Logger.i("列表现有: " + mFileList.size() + "条");
        notifyDataSetChanged();
    }

    public void updateWithClear(List<FileEntity> data) {
        mFileList.clear();
        update(data);
    }

    public void openItemAnimation() {
        mState = SLIDE;
        for (ViewHolderItemVideo holder : mViewHolderItemVideos) {
            holder.openItemAnimation();
        }
    }

    public void closeItemAnimation() {
        mState = NORMAL;
        for (ViewHolderItemVideo holder : mViewHolderItemVideos) {
            holder.closeItemAnimation();
        }
    }

    public void clickSelectAll() {
        if (!mFileList.isEmpty()) {
            for (FileEntity fileEntity : mFileList)
                fileEntity.setChecked(true);
        }
        selectList.addAll(mFileList);
        notifyDataSetChanged();
    }
    public void cleanSelectAdd(){
        if (!mFileList.isEmpty()) {
            for (FileEntity fileEntity : mFileList)
                fileEntity.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mFileList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public FileListAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.filelist_item, parent, false);

        mViewHolderItemVideos.add(new ViewHolderItemVideo(view));
        return new ViewHolderItemVideo(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        holder.bindItem(mContext, mFileList.get(position),position);
    }


    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    abstract class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }

        abstract void bindItem(Context context, FileEntity fileEntity,int position);
    }

    class ViewHolderItemVideo extends ViewHolderItem {
        @Bind(R.id.image_fileThumb)
        ImageView image_thumb;
        @Bind(R.id.tv_fileName)
        TextView tv_name;
        @Bind(R.id.item_root)
        SlideRelativeLayout slideRelativeLayout;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_size)
        TextView tv_size;
        private SlideRelativeLayout mSlideRelativeLayout;
        private CheckBox mCheckBox;


        public ViewHolderItemVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSlideRelativeLayout = (SlideRelativeLayout) itemView.findViewById(R.id.item_root);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
        }

        @Override
        void bindItem(Context context, FileEntity fileEntity,int position) {
            tv_name.setText(fileEntity.getName());
            tv_time.setText(fileEntity.getCreateTime());
            tv_size.setText(fileEntity.getSize());
            if (islocalfile)
                Glide.with(mContext).load(fileEntity.getPath()).centerCrop().into(image_thumb);
            else
                Glide.with(mContext).load(R.mipmap.video_default_logo).centerCrop().into(image_thumb);



            mCheckBox.setChecked(fileEntity.isChecked());
            slideRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickItemVideo(mCheckBox, fileEntity,position);
                    selectList.add(fileEntity);
                }
            });
            switch (mState) {
                case NORMAL:
                    mSlideRelativeLayout.close();
                    break;

                case SLIDE:
                    mSlideRelativeLayout.open();
                    break;
            }
        }

        public void openItemAnimation() {
            mSlideRelativeLayout.openAnimation();
        }

        public void closeItemAnimation() {
            mSlideRelativeLayout.closeAnimation();
            for (FileEntity fileEntity : selectList) {
                fileEntity.setChecked(false);
                mCheckBox.setChecked(fileEntity.isChecked());

            }
//            notifyDataSetChanged();

        }

        public void setCheckBox() {
            mCheckBox.setChecked(!mCheckBox.isChecked());

        }
    }

    private void cacheImage(final String url, ImageView image) {
        TaskUtils.executeAsyncTask(new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = ImageFromFileCache.createVideoThumbnail(url, 60, 60);
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (result != null) {
//                    image.setImageBitmap(result);
                    Glide.with(mContext).load(new File(ImageFromFileCache.getPath(url))).centerCrop().into(image);

                }
            }
        });
    }

    public void setClickItem(IClickMainItem IClickItem) {
        mIClickItem = IClickItem;
    }

    public interface IClickMainItem {
        void onClickItemVideo(CheckBox mCheckBox, FileEntity entity,int position);
    }
}
