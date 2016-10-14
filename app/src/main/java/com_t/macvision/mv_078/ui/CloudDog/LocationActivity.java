package com_t.macvision.mv_078.ui.CloudDog;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.macvision.mv_078.R;

import org.videolan.vlc.VLCApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import com_t.macvision.mv_078.base.BaseToolbarActivity;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-23
 * Time: 19:14
 * QQ  : 294894105 ZH
 */

public class LocationActivity extends BaseToolbarActivity<LocationPresent> implements LocationSource, AMapLocationListener, LocationView {
    @Bind(R.id.mapView)
    MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    CoordinateConverter converter;
    Marker loactionMark;

    @Override
    protected String setTitle() {
        return "定位";
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_carloaction__position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    @Override
    public void initData() {
        super.initData();
        String usetToken = (String) SharedPreferencesUtils.getParam(this, "CloudUserToken", "");
        mPresenter.getLocation(usetToken, Constant.IMEI);
        converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);

    }

    @Override
    public void initPresenter() {
        mPresenter = new LocationPresent(this, this);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.ic_logo));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void destroy() {
        super.destroy();
        mapView.onDestroy();
    }

    @Override
    public void getLocationSuccess(LocationModel locationModel) {
        LatLng mloaction = new LatLng(locationModel.getLat(), locationModel.getLng());
        converter.coord(mloaction);
        LatLng desLatLng = converter.convert();
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(desLatLng
                , 18, 30, 0
        )), null);
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(desLatLng);
        markerOptions.title("当前地址").snippet(locationModel.getAddress());
        markerOptions.draggable(true);
        markerOptions.icon(
                BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.loaction_icon)));

        loactionMark=aMap.addMarker(markerOptions);


    }
    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.moveCamera(update);
    }
    @Override
    public void getLocationError(String massage) {

    }
}
