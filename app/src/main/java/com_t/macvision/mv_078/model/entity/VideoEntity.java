package com_t.macvision.mv_078.model.entity;

import com_t.macvision.mv_078.base.BaseModel;

/**
 * Created by bzmoop on 2016/9/9 0009.
 */
public class VideoEntity extends BaseModel {
    private String firstFrameBase64;
    private String firstFrameLocation;
    private String userId;
    private String videoCaption;
    private String videoCommentNumber;
    private String videoCreateTime;
    private String videoId;
    private String videoLikesNumber;
    private String videoLocation;
    private String videoReleaseAddress;
    private String videoTitle;
    private String videoType;
    private String videoViewNumber;
    private String videoWebViewAddress;
    private String userName;
    private String avatarLocation;
    private String userAutograph;
    private String category;
    private String isLike;



    @Override
    public String toString() {
        return "VideoEntity{" +
                "firstFrameBase64='" + firstFrameBase64 + '\'' +
                ", firstFrameLocation='" + firstFrameLocation + '\'' +
                ", userId='" + userId + '\'' +
                ", videoCaption='" + videoCaption + '\'' +
                ", videoCommentNumber='" + videoCommentNumber + '\'' +
                ", videoCreateTime='" + videoCreateTime + '\'' +
                ", videoId='" + videoId + '\'' +
                ", videoLikesNumber='" + videoLikesNumber + '\'' +
                ", videoLocation='" + videoLocation + '\'' +
                ", videoReleaseAddress='" + videoReleaseAddress + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoType='" + videoType + '\'' +
                ", videoViewNumber='" + videoViewNumber + '\'' +
                ", videoWebViewAddress='" + videoWebViewAddress + '\'' +
                ", userName='" + userName + '\'' +
//                ", avatarLocation='" + avatarLocation + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                ", category='" + category + '\'' +
                ", isLike='" + isLike + '\'' +
                '}';
    }
    public boolean getIsLike() {
        return isLike.equals("1");
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getFirstFrameBase64() {
        return firstFrameBase64;
    }

    public void setFirstFrameBase64(String firstFrameBase64) {
        this.firstFrameBase64 = firstFrameBase64;
    }

    public String getFirstFrameLocation() {
        return firstFrameLocation;
    }

    public void setFirstFrameLocation(String firstFrameLocation) {
        this.firstFrameLocation = firstFrameLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoCaption() {
        return videoCaption;
    }

    public void setVideoCaption(String videoCaption) {
        this.videoCaption = videoCaption;
    }

    public String getVideoCommentNumber() {
        return videoCommentNumber;
    }

    public void setVideoCommentNumber(String videoCommentNumber) {
        this.videoCommentNumber = videoCommentNumber;
    }

    public String getVideoCreateTime() {
        return videoCreateTime;
    }

    public void setVideoCreateTime(String videoCreateTime) {
        this.videoCreateTime = videoCreateTime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoLikesNumber() {
        return videoLikesNumber;
    }

    public void setVideoLikesNumber(String videoLikesNumber) {
        this.videoLikesNumber = videoLikesNumber;
    }

    public String getVideoLocation() {
        return videoLocation;
    }

    public void setVideoLocation(String videoLocation) {
        this.videoLocation = videoLocation;
    }

    public String getVideoReleaseAddress() {
        return videoReleaseAddress;
    }

    public void setVideoReleaseAddress(String videoReleaseAddress) {
        this.videoReleaseAddress = videoReleaseAddress;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoViewNumber() {
        return videoViewNumber;
    }

    public void setVideoViewNumber(String videoViewNumber) {
        this.videoViewNumber = videoViewNumber;
    }

    public String getVideoWebViewAddress() {
        return videoWebViewAddress;
    }

    public void setVideoWebViewAddress(String videoWebViewAddress) {
        this.videoWebViewAddress = videoWebViewAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarLocation() {
        return avatarLocation;
    }

    public void setAvatarLocation(String avatarLocation) {
        this.avatarLocation = avatarLocation;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
