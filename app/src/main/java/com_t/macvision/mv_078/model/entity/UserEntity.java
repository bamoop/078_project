package com_t.macvision.mv_078.model.entity;/**
 * Created by bzmoop on 2016/8/26 0026.
 */

import com_t.macvision.mv_078.base.BaseModel;

/**
 * 作者：LiangXiong on 2016/8/26 0026 14:17
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class UserEntity extends BaseModel {

    /**
     * userId : 7000001
     * userName : user_01
     * phone : 13111111111
     * fansNumber : 0
     * followNumber : 0
     * vReleaseNumber : 1
     * avatarLocation : 6464646464646
     * userAutograph : 个性签名撒币
     * userCreateTime : 1473164350000
     * userToken : abcdefghijklmn
     */

        private String userId;
        private String userName;
        private String phone;
        private String fansNumber;
        private String followNumber;
        private String vReleaseNumber;
        private String avatarLocation;
        private String userAutograph;
        private String userCreateTime;
        private String userToken;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFansNumber() {
            return fansNumber;
        }

        public void setFansNumber(String fansNumber) {
            this.fansNumber = fansNumber;
        }

        public String getFollowNumber() {
            return followNumber;
        }

        public void setFollowNumber(String followNumber) {
            this.followNumber = followNumber;
        }

        public String getVReleaseNumber() {
            return vReleaseNumber;
        }

        public void setVReleaseNumber(String vReleaseNumber) {
            this.vReleaseNumber = vReleaseNumber;
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

        public String getUserCreateTime() {
            return userCreateTime;
        }

        public void setUserCreateTime(String userCreateTime) {
            this.userCreateTime = userCreateTime;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }
}
