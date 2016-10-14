package com_t.macvision.mv_078.model.CloudModel;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-24
 * Time: 09:38
 * QQ  : 294894105 ZH
 */

public class LocationModel extends CloudBaseModel {

    /**
     * lat : 22.535303115844727
     * lng : 114.05586242675781
     * speed : 2
     * bearing : 76
     * address : 广东省深圳市福田区福华三路116号
     * time : 1473164267
     */

    private double lat;
    private double lng;
    private double speed;
    private double bearing;
    private String address;
    private int time;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
