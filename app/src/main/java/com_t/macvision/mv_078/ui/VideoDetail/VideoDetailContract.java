package com_t.macvision.mv_078.ui.VideoDetail;

import com_t.macvision.mv_078.base.BasePresenter;
import com_t.macvision.mv_078.base.BaseView;
import com_t.macvision.mv_078.model.entity.CommentEntity;
import com_t.macvision.mv_078.model.entity.VideoDetailEntity;
import com_t.macvision.mv_078.ui.VideoList.VideoContract;

/**
 * 作者：LiangXiong on 2016/8/11 0011 18:07
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public interface VideoDetailContract {
    interface View extends BaseView<VideoContract.Presenter> {
        /**
         * 填充数据
         */
        void fillData(VideoDetailEntity entity);

        /**
         * 加载评论数据
         **/
        void fillCommentData(CommentEntity entity);

        /**
         * 没有更多数据
         */
        void hasNoMoreData();

        /**
         * 添加更多数据
         */
        void appendMoreDataToView(CommentEntity entity);

        /**
         * 数据加载完成
         */
        void getDataFinish();
    }

    interface Presenter extends BasePresenter {
        void getData(int videoID);

        void getComment(int video, int page, Boolean isgetDataMore);

        void saveComment( String token, String cmContent, String userId, String cmVideoId, String beReplyUserId);

        void clickLike( String videoId, String userId);

        void shareVideo();
    }
}
