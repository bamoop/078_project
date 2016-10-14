package com_t.macvision.mv_078.core;/**
 * Created by bzmoop on 2016/8/11 0011.
 */

import com_t.macvision.mv_078.model.entity.FileEntity;

/**
 * 作者：LiangXiong on 2016/8/11 0011 11:42
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class Constant {
    public static String BaseIP = "120.77.13.85";
    //    public static String BaseIP = "192.168.1.124";
    public static String BaseVideoListUrl = "http://" + BaseIP + "/shixun/";//服务器主机地址
    public static String BaseVideoPlayUrl = "http://" + BaseIP + "/upload/";//视频播放主机地址
    public static String BaseCloudUrl = "http://120.25.90.253/dog/api/";//云狗主机地址
    public static String UploadUrl = "http://120.77.13.85/shixun/upload/medias";
    public static final String JJAPPKEY = "VJX88vHt-";
    public static final String PAGENAME = "com.macvision.mv_078";

    public static final String userId = "userId";
    public static final String videoTitle = "videoTitle";
    public static final String videoReleaseAddress = "videoReleaseAddress";
    public static final String videoCaption = "videoCaption";
    public static final String videoType = "videoType";
    public static final String token = "token";
    public static final String CITY = "CITY";
    public static final int TEST_USERID = 7000018;//测试用户ID

    /**
     * 以下是行车记录仪相关
     **/
    public static String IP = "192.72.1.1";
    public static final String CGI_PATH = "/cgi-bin/Config.cgi?";
    public static final String DEFAULT_DIR = "DCIM";
    private static String COMMAND_VIDEOCAPTURE = "capture";
    public static String PROPERTY_VIDEORECORD = "Video";


    public static String DeviceBaseUrl = "http://" + IP;
    private String m_dir = "dir";
    private String m_reardir = "reardir";//后路
    public final static String PROPERTY_DCIM = "m_DCIM";
    private String m_Event = "Event";
    private String m_Photo = "Photo";

    /**
     * 云狗相关
     **/
    public static final String IMEI = "502160460206296";

}
