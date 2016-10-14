package com_t.macvision.mv_078.model.entity;/**
 * Created by bzmoop on 2016/8/12 0012.
 */

import java.io.Serializable;

/**
 * 作者：LiangXiong on 2016/8/12 0012 16:07
 * 邮箱：liangxiong.sz@foxmail.com
 * QQ  ：294894105
 */
public class FileEntity implements Serializable {
    String name;
    String path;
    String size;
    String format;
    String createTime;
    String attr;
    int position;

    private boolean isChecked;

    @Override
    public String toString() {
        return "FileEntity{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size='" + size + '\'' +
                ", format='" + format + '\'' +
                ", createTime='" + createTime + '\'' +
                ", attr='" + attr + '\'' +
                ", position=" + position +
                ", isChecked=" + isChecked +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }


}
