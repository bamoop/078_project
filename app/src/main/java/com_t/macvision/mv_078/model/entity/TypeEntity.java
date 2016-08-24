package com_t.macvision.mv_078.model.entity;/**
 * Created by bzmoop on 2016/8/19 0019.
 */

import java.util.List;

/**
 * 作者：LiangXiong on 2016/8/19 0019 14:50
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class TypeEntity {

    /**
     * errno : 0
     * msg : ok
     * data : [{"vTypeCreateTime":"2016-08-17 01:02:36","vTypeId":1,"vTypeName":"曝光台"}]
     */

    private String errno;
    private String msg;
    /**
     * vTypeCreateTime : 2016-08-17 01:02:36
     * vTypeId : 1
     * vTypeName : 曝光台
     */

    private List<DataBean> data;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String vTypeCreateTime;
        private String vTypeId;
        private String vTypeName;

        public String getVTypeCreateTime() {
            return vTypeCreateTime;
        }

        public void setVTypeCreateTime(String vTypeCreateTime) {
            this.vTypeCreateTime = vTypeCreateTime;
        }

        public String getVTypeId() {
            return vTypeId;
        }

        public void setVTypeId(String vTypeId) {
            this.vTypeId = vTypeId;
        }

        public String getVTypeName() {
            return vTypeName;
        }

        public void setVTypeName(String vTypeName) {
            this.vTypeName = vTypeName;
        }
    }
}
