package com_t.macvision.mv_078.core;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bzmoop on 2016/7/28 0028.
 * 这是一个服务类
 * 创建一个接口来管理GET，POST请求的 URL
 */
public interface VideoListService {

    /**
     * 用户注册
     **/
    @POST("user/register")
    @FormUrlEncoded
    Observable<String> register(
            @Field("userName") int userName,
            @Field("password") int passWord,
            @Field("phoneNumber") int phoneNumber);


    /**
     * 用户详情
     **/
    @POST("user/findUserById")
    @FormUrlEncoded
    Observable<String> userDetail(
            @Field("token") String token,
            @Field("userId") int userId);

    /**
     * 请求视频列表
     *
     * @param page,pagesize,type
     * @return Observable
     */
    @POST("video/list")
    @FormUrlEncoded
    Observable<String> getVideoListData(
            @Field("page") int page,
            @Field("pageSize") int pagesize,
            @Field("type") int type);

    /**
     * 首页TYPE
     *
     * @return Observable
     */
    @POST("video/findAllType")
    Observable<String> getAllType();

    /**
     * 视频详情
     *
     * @param videoId
     * @return Observable
     */
    @POST("video/findById")
    @FormUrlEncoded
    Observable<String> getVideoDetail(
            @Field("videoId") int videoId);

    /**
     * 点赞
     **/
    @POST("video/clickLike")
    @FormUrlEncoded
    Observable<String> clickLike(
            @Field("videoId") int videoId,
            @Field("userId") int userId);

    /**
     * 关注
     **/
    @POST("user/payAttention")
    @FormUrlEncoded
    Observable<String> payAttention(
            @Field("token") String token,
            @Field("myUserId") int myUserId,
            @Field("attUserID") int attUserID,
            @Field("operation") int operation);


    /**
     * 获取评论列表评论
     **/
    @POST("comment/findByVideoId")
    @FormUrlEncoded
    Observable<String> getCommentList(
            @Field("videoId") int videoId,
            @Field("page") int page,
            @Field("pageSize") int pageSize);

    /**
     * 评论
     **/
    @POST("comment/save")
    Observable<String> saveComment(
            @Field("token") String token,
            @Field("cmContent") String cmContent,
            @Field("cmUserId") int cmUserId,
            @Field("cmVideoId") String cmVideoId,
            @Field("beReplyUserId") String beReplyUserId);

    /**
     * 查询用户动态列表
     **/
    @POST("video/findReleaseByUId")
    @FormUrlEncoded
    Observable<String> getDynamicList(
            @Field("userId") int userId,
            @Field("page") int page,
            @Field("token") int pageSize
    );

}
