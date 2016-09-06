package com_t.macvision.mv_078.ui.adapter;/**
 * Created by bzmoop on 2016/8/4 0004.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com_t.macvision.mv_078.ui.VideoList.FragmentMenu1;
import com_t.macvision.mv_078.ui.VideoList.FragmentTab1;
import com_t.macvision.mv_078.util.CircleImageView;
import com_t.macvision.mv_078.util.ImageFromFileCache;
import com_t.macvision.mv_078.util.ScreenUtils;
import com_t.macvision.mv_078.util.TaskUtils;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.LruCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.util.link_builder.Link;
import com_t.macvision.mv_078.util.link_builder.LinkBuilder;

/**
 * 作者：LiangXiong on 2016/8/4 0004 21:49
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class MainVideoListAdapter extends RecyclerView.Adapter<MainVideoListAdapter.ViewHolderItem> {
    private List<VideoEntity.VideolistEntity> mVideoList;
    private Context mContext;
    private static IClickMainItem mIClickItem;

    public MainVideoListAdapter(Context mContext, List<VideoEntity.VideolistEntity> mVideoList) {
        this.mContext = mContext;
        mVideoList = new ArrayList<>();
        this.mVideoList = mVideoList;

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
        @Bind(R.id.image_head)
        CircleImageView image_head;
        @Bind(R.id.tv_type)
        TextView tv_type;
        @Bind(R.id.tv_ReleaseAddress)
        TextView tv_ReleaseAddress;
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
            tv_ReleaseAddress.setText(videoEntity.getVideoReleaseAddress());
            tv_type.setText("#" + FragmentMenu1.entity.getData().get(Integer.parseInt(videoEntity.getVideoType()) - 1).getVTypeName());

            Glide.with(mContext).load(ImageFromFileCache.base64ToBitmap(videoEntity.getAvatarLocation())).into(image_head);

            if (videoEntity.getCategory().equals("image"))
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl + videoEntity.getVideoLocation()).
                        override(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenHeight(mContext) / 3).centerCrop().into(image_thumb);
            else
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl + videoEntity.getFirstFrameLocation()).
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
            tv_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickLinks(v, videoEntity);
                }
            });
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
        void onClickLinks(View view,VideoEntity.VideolistEntity  videolistEntity);
    }
}
