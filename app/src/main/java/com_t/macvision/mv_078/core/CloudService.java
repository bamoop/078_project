package com_t.macvision.mv_078.core;


import java.util.ArrayList;

import com_t.macvision.mv_078.model.CloudModel.AppTokenModel;
import com_t.macvision.mv_078.model.CloudModel.CloudBaseModel;
import com_t.macvision.mv_078.model.CloudModel.CloudNullEntity;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;
import com_t.macvision.mv_078.model.entity.NullEntity;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-22
 * Time: 15:25
 * QQ  : 294894105 ZH
 * 创建一个接口来管理云狗GET，POST请求的 URL
 */

public interface CloudService {
    /**
     * 获取ToKen
     **/
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<AppTokenModel>> getToken(
            @Field("appid") String appid,
            @Field("password") String password,
            @Field("time") String time);

    /**
     * 注册
     **/
    @POST("register.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("token") String token);

    /**
     * 登陆
     **/
    @POST("login.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<AppTokenModel>> login(
            @Field("username") String username,
            @Field("password") String password);

    /**
     * 解除绑定
     **/
    @POST("unbind.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> unbind(
            @Field("token") String token,
            @Field("imei") String imei);

    /**
     * 查询用户绑定的设备列表
     **/
    @POST("querybonds.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> queryBonds(
            @Field("token") String token);

    /**
     * 实时位置
     **/
    @POST("location.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<LocationModel>> location(
            @Field("token") String token,
            @Field("imei") String imei);

    /**
     * 历史轨迹
     **/
    @POST("history.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<ArrayList<LocationModel>>> history(
            @Field("token") String token,
            @Field("imei") String imei,
            @Field("begin") String begin,
            @Field("end") String end
    );

    /**
     * 查询云狗设置
     **/
    @POST("queryconf.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> queryconf(
            @Field("token") String token,
            @Field("imei") String imei);

    /**
     * 云狗设置
     **/
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> config(
            @Field("token") String token,
            @Field("imei") String imei,
            @Field("mspeed") String mspeed,
            @Field("ospeed") String ospeed,
            @Field("traff") String traff,
            @Field("mode") String mode,
            @Field("sensi") String sensi,
            @Field("vol") String vol,
            @Field("vmode") String vmode);

    /**
     * 远程拍照
     **/
    @POST("takephoto.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> takephoto(
            @Field("token") String token,
            @Field("imei") String imei);

    /**
     * 获取设备拍摄的最新照片列表
     **/
    @POST("getphotolist.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> getphotolist(
            @Field("token") String token,
            @Field("imei") String imei);

    /**
     * 获取设备报警列表
     **/
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<CloudBaseModel<CloudNullEntity>> getalarmlist(
            @Field("token") String token,
            @Field("imei") String imei);

}
