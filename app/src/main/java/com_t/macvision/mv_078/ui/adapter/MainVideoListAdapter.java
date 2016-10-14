package com_t.macvision.mv_078.ui.adapter;/**
 * Created by bzmoop on 2016/8/4 0004.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.macvision.mv_078.R;

import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.entity.VideoEntity;
import com_t.macvision.mv_078.ui.VideoList.FragmentMenu1;
import com_t.macvision.mv_078.util.CircleImageView;
import com_t.macvision.mv_078.util.ImageFromFileCache;

import com.orhanobut.logger.Logger;

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
    private List<VideoEntity> mVideoList;
    private Context mContext;
    private static IClickMainItem mIClickItem;

    public MainVideoListAdapter(Context mContext, List<VideoEntity> mVideoList) {
        this.mContext = mContext;
        mVideoList = new ArrayList<>();
        this.mVideoList = mVideoList;

    }

    public void update(List<VideoEntity> data) {
        mVideoList.addAll(data);
        Logger.i("列表现有: " + mVideoList.size() + "条");
        notifyDataSetChanged();
    }

    public void updateWithClear(List<VideoEntity> data) {
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

        abstract void bindItem(Context context, VideoEntity videolistEntity);
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
        void bindItem(Context context, VideoEntity videolistEntity) {

        }
    }

    class ViewHolderItemVideo extends ViewHolderItem {
        @Bind(R.id.tv_videoid)
        TextView tv_videoid;
        @Bind(R.id.btn_zan)
        CheckBox btn_zan;
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
        void bindItem(Context context, VideoEntity videoEntity) {
            tv_videoid.setText(videoEntity.getVideoCaption());
            tv_PlayCount.setText(videoEntity.getVideoViewNumber());
            tv_username.setText(videoEntity.getUserName());
            tv_pingCount.setText(videoEntity.getVideoCommentNumber());
            tv_zanCount.setText(videoEntity.getVideoLikesNumber());
            tv_ReleaseAddress.setText(videoEntity.getVideoReleaseAddress());
            Logger.i("type: " + FragmentMenu1.entity.getData().size() + "条");
           try{
               tv_type.setText("#" + FragmentMenu1.entity.getData().get(Integer.parseInt(videoEntity.getVideoType())-2).getVTypeName());

           }catch (Exception e){
               e.printStackTrace();
               tv_type.setText("type提示异常");
           }

            Glide.with(mContext).load(ImageFromFileCache.base64ToBitmap(videoEntity.getAvatarLocation())).into(image_head);

            if (videoEntity.getCategory().equals("image"))
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl + videoEntity.getVideoLocation()).centerCrop().into(image_thumb);
            else
                Glide.with(mContext).load(Constant.BaseVideoPlayUrl + videoEntity.getFirstFrameLocation()).centerCrop().into(image_thumb);

            btn_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIClickItem.onClickZan(btn_zan, videoEntity);
                }
            });
            btn_zan.setChecked(videoEntity.getIsLike());

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
        void onClickItemDetail(View view, VideoEntity videoEntity);

        void onClickTitle(View view, VideoEntity videoEntity);

        void onClickZan(CheckBox checkBox, VideoEntity videoEntity);

        void onClickPing(View view, VideoEntity videoEntity);

        void onClickFen(View view, VideoEntity videoEntity);
        void onClickLinks(View view,VideoEntity videolistEntity);
    }
}
