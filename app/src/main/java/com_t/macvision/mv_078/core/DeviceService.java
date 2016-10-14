package com_t.macvision.mv_078.core;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by bzmoop on 2016/9/19 0019.
 */
public interface DeviceService {
    /**
     * 记录仪获取API
     **/
    @GET("/cgi-bin/Config.cgi?action=get" )
    Observable<String> getInstruct(
            @Query("property") String property
//            @Path("arg1") String action,
//            @Path("arg2") String property
    );
    /**记录仪设置API**/
    @GET("/cgi-bin/Config.cgi?action=set" )
    Observable<String> setInstruct(
            @Query("property") String property,
            @Query("value") String value);
    @GET("/cgi-bin/Config.cgi?action=del")
    /**删除记录仪文件**/
    Observable<String> deleteFile(
            @Query("property") String property);
}
