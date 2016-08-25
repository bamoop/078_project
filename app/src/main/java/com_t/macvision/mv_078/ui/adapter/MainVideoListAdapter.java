package com_t.macvision.mv_078.ui.adapter;/**
 * Created by bzmoop on 2016/8/4 0004.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import com_t.macvision.mv_078.Constant;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.util.ImageFromFileCache;
import com_t.macvision.mv_078.util.ScreenUtils;
import com_t.macvision.mv_078.util.TaskUtils;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.LruCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：LiangXiong on 2016/8/4 0004 21:49
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class MainVideoListAdapter extends RecyclerView.Adapter<MainVideoListAdapter.ViewHolderItem> {
    private List<VideoEntity.VideolistEntity> mVideoList;
    private Context mContext;
    private static IClickMainItem mIClickItem;
    private String imageCache = null;
    private LruCache lruCache;
    int viewType;

    public MainVideoListAdapter(Context mContext, List<VideoEntity.VideolistEntity> mVideoList) {
        this.mContext = mContext;
        mVideoList = new ArrayList<>();
        this.mVideoList = mVideoList;
        lruCache = new LruCache(mContext);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
    }

    public void update(List<VideoEntity.VideolistEntity> data) {
        mVideoList.addAll(data);
        Logger.i("列表现有: " + mVideoList.size() + "条");
        notifyDataSetChanged();
    }

    public void updateWithClear(List<VideoEntity.VideolistEntity> data) {
        mVideoList.clear();
        update(data);
    }

//    @Override
//    public int getItemViewType(int position) {
//        viewType = position;
//        if (mVideoList.get(position).getCategory().equals("image"))
//            return 0;
//        else
//            return 1;
//
//    }

    @Override
    public MainVideoListAdapter.ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
//        if (viewType == 1) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.mainlist_image_item, parent, false);
//            return new ViewHolderItemVideo(view);
//        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.mainlist_video_item, parent, false);
            return new ViewHolderItemVideo(view);
//        }
    }

    @Override
    public void onBindViewHolder(MainVideoListAdapter.ViewHolderItem holder, int position) {
//        if (viewType == 1)
//            holder.bindItem(mContext, mVideoList.get(position));
//        else
            holder.bindItem(mContext, mVideoList.get(position));

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    abstract class ViewHolderItem extends RecyclerView.ViewHolder {
        public ViewHolderItem(View itemView) {
            super(itemView);
        }

        abstract void bindItem(Context context, VideoEntity.VideolistEntity videolistEntity);
    }

    class ViewHoderItemImage extends ViewHolderItem {
        @Bind(R.id.tv_videoid)
        TextView tv_videoid;
        @Bind(R.id.btn_zan)
        TextView btn_zan;
        @Bind(R.id.linear_parent)
        LinearLayout FrameLayout;
        @Bind(R.id.image_thumb)
        ImageView image_thumb;

        public ViewHoderItemImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        void bindItem(Context context, VideoEntity.VideolistEntity videolistEntity) {

        }
    }

    class ViewHolderItemVideo extends ViewHolderItem {
        @Bind(R.id.tv_videoid)
        TextView tv_videoid;
        @Bind(R.id.btn_zan)
        TextView btn_zan;
        @Bind(R.id.linear_parent)
        LinearLayout FrameLayout;
        @Bind(R.id.image_thumb)
        ImageView image_thumb;
        @Bind(R.id.tv_PlayCount)
        TextView tv_PlayCount;
        @Bind(R.id.tv_username)
        TextView tv_username;
        @Bind(R.id.tv_pingCount)
        TextView tv_pingCount;
        @Bind(R.id.tv_zanCount)
        TextView tv_zanCount;
        @Bind(R.id.title_layout)
        RelativeLayout title_layout;
        @Bind(R.id.details_layout)
        LinearLayout details_layout;
        @Bind(R.id.btn_ping)
        Button btn_ping;
        @Bind(R.id.btn_fen)
        Button btn_fen;
        public ViewHolderItemVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        void bindItem(Context context, VideoEntity.VideolistEntity videoEntity) {
            tv_videoid.setText(videoEntity.getVideoId());
            tv_PlayCount.setText(videoEntity.getVideoViewNumber());
            tv_username.setText(videoEntity.getUserName());
            tv_pingCount.setText(videoEntity.getVideoCommentNumber());
            tv_zanCount.setText(videoEntity.getVideoLikesNumber());
            if(videoEntity.getCategory().equals("image"))
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl+videoEntity.getVideoLocation()).
                        override(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext) / 3).centerCrop().into(image_thumb);
            else
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl+videoEntity.getFirstFrameLocation()).
                        override(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext) / 3).centerCrop().into(image_thumb);

            btn_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickZan(v, videoEntity);

                }
            });
            details_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickItemDetail(v, videoEntity);
                }
            });
            title_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickTitle(v, videoEntity);
                }
            });

            btn_ping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickPing(v, videoEntity);
                }
            });


            btn_fen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickFen(v, videoEntity);
                }
            });

//            Bitmap bitmap=null;
//            Picasso.with(mContext)
//                    .load("http://192.168.1.124/default.jpg")
////                    .placeholder(R.mipmap.zan_normal)
//                    .resize(ScreenUtils.getScreenWidth(mContext),ScreenUtils.getScreenHeight(mContext)/3)
//                    .error(R.mipmap.zan_pressed)
//                    .into(image_thumb);
//            //显示图片来源标记

//            Picasso.with(mContext).load("http://192.168.1.124/aaaa/zm.mp4").into(image_thumb);
//            Picasso.with(mContext).load(String.valueOf(createVideoThumbnail("http://192.168.1.124/aaaa/zm.mp4",60,60))).into(image_thumb);
//            cacheImage(Constant.BaseVideoListUrl+videoEntity.getVideoLocation(),image_thumb);
        }

    }

    public void setClickItem(IClickMainItem IClickItem) {
        mIClickItem = IClickItem;
    }

    public interface IClickMainItem {
        void onClickItemDetail(View view, VideoEntity.VideolistEntity videoEntity);
        void onClickTitle(View view, VideoEntity.VideolistEntity videoEntity);
        void onClickZan(View view, VideoEntity.VideolistEntity videoEntity);
        void onClickPing(View view, VideoEntity.VideolistEntity videoEntity);
        void onClickFen(View view, VideoEntity.VideolistEntity videoEntity);
    }

    private void cacheImage(final String url, ImageView image) {
        TaskUtils.executeAsyncTask(new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = ImageFromFileCache.getImage(url);
                if (bmp == null) {
                    ImageFromFileCache.createVideoThumbnail(url, 80, 80);
                }
                if (bmp == null) {
                    return bmp;
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (result != null) {
                    image.setImageBitmap(result);
                }
            }
        });
    }


}
