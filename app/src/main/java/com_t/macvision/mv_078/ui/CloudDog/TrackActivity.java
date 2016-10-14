package com_t.macvision.mv_078.ui.CloudDog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.macvision.mv_078.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com_t.macvision.mv_078.base.BaseToolbarActivity;
import com_t.macvision.mv_078.core.Constant;
import com_t.macvision.mv_078.model.CloudModel.LocationModel;
import com_t.macvision.mv_078.util.SharedPreferencesUtils;

/**
 * User: LiangXiong(LiangXiong.sz@faxmail.com)
 * Date: 2016-09-24
 * Time: 11:24
 * QQ  : 294894105 ZH
 */

public class TrackActivity extends BaseToolbarActivity<TrackPresent> implements LocationSource, AMapLocationListener,TreckView<LocationModel>{

    @Bind(R.id.mapView)
    MapView mapView;
    @Bind(R.id.tv_carlocation_time)
    TextView tvCarlocationTime;
    CoordinateConverter converter;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    SimpleCalendarDialogFragment simpleCalen;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @Override
    protected String setTitle() {
        return "历史轨迹";
    }

    @Override
    public int getLayout() {
        return R.layout.activty_track;
    }


    @OnClick(R.id.tv_carlocation_time)
    public void onClick() {
        simpleCalen.show(this.getFragmentManager(), "选择查询日期");
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
        converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        simpleCalen = new SimpleCalendarDialogFragment();
        simpleCalen.show(this.getFragmentManager(), "选择查询日期");

    }

    @Override
    public void initPresenter() {
        mPresenter = new TrackPresent(this, this);
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
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {

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

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.moveCamera(update);
    }

    @Override
    public void getTrackSuccess(ArrayList<LocationModel> locationModel) {
        List<LatLng> latlnglist = new ArrayList<LatLng>();
        for (LocationModel location : locationModel) {
            converter.coord(new LatLng(location.getLat(),location.getLng()));
            LatLng desLatLng = converter.convert();
            latlnglist.add(desLatLng);
        }
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(latlnglist.get(1).latitude,latlnglist.get(1).longitude), 18, 30, 0)), null);
        aMap.addPolyline((new PolylineOptions())
                .addAll(latlnglist)
                .color(Color.BLUE));
    }



    @Override
    public void getTrackError(String massage) {

    }

    @Override
    public void getTrackEmpty() {
        Toast.makeText(this,"今天没有记录的行车轨迹",Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ValidFragment")
    class SimpleCalendarDialogFragment extends DialogFragment implements OnDateSelectedListener {
        TextView textView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            return inflater.inflate(R.layout.dialog_calendar, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            textView = (TextView) view.findViewById(R.id.textView);

            MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
            widget.setTileHeight(0);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String today = sdf.format(date);
            Log.i("moop","今天的日期是"+today);
            int year,month,day;
            year= Integer.parseInt(today.substring(0,4));
            month= Integer.parseInt(today.substring(4,6));
            day= Integer.parseInt(today.substring(6,8));

            Calendar calendar = Calendar.getInstance();
            widget.setSelectedDate(calendar.getTime());

            Calendar instance2 = Calendar.getInstance();
            instance2.set(year,month-1,day);

            widget.state().edit()
                    .setMaximumDate(instance2.getTime())
                    .commit();

            widget.setOnDateChangedListener(this);
        }

        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            tvCarlocationTime.setText(FORMATTER.format(date.getDate()));

            Date date1 = null,date2=null;
            String[] day=null;

            day = FORMATTER.format(date.getDate()).split("年|月|日");

            String satrt=day[0]+"-"+day[1]+"-"+day[2]+" 00:00:00";
            String end  =day[0]+"-"+day[1]+"-"+day[2]+" 23:59:59";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date1=format.parse(satrt);
                date2=format.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String trackTime, beginTime, endTime;
            trackTime=day[0]+"-"+day[1]+"-"+day[2];
            Log.i("moop", "year" + day[0] + "----month=" + day[1] + "---" + day[2]);
            beginTime= String.valueOf(date1.getTime()).substring(0,10);
            endTime= String.valueOf(date2.getTime()).substring(0,10);
            simpleCalen.dismiss();
            String usetToken = (String) SharedPreferencesUtils.getParam(TrackActivity.this, "CloudUserToken", "");
            Log.i("token01", "请求轨迹token"+usetToken);

            mPresenter.getLocation(usetToken, Constant.IMEI,beginTime,endTime);
        }
    }
}
