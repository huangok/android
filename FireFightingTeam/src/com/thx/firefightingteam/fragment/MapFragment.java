package com.thx.firefightingteam.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.thx.firefightingteam.R;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MapFragment extends Fragment {
	BaiduMapOptions mapOptions;
	private Handler handler;
	private BaiduMap baiduMap;
	private LocationClient mLocClient;
	private LocationListern locationListern;
	final int SHOW_STATELLITE = 1;
	private MapView mapView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.map_layout, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		MapStatus mapStatus = new MapStatus.Builder().target(new LatLng(37.863161,112.563512)).overlook(-20).zoom(20)
				.build();
		mapOptions = new BaiduMapOptions().mapStatus(mapStatus)
				.mapType(currentMapType).compassEnabled(false)
				.zoomControlsEnabled(false);
		 mapView = new MapView(this.getActivity(), mapOptions);
		RelativeLayout mapLayout = (RelativeLayout) view
				.findViewById(R.id.mapLayout);

		mapLayout.addView(mapView, -1, -1);
		baiduMap = mapView.getMap();
		baiduMap.setTrafficEnabled(true);
		baiduMap.setMyLocationEnabled(true);
		baiduMap.setOnMapLoadedCallback(new MapLoadedCallback());
		/*UiSettings uiSettings = baiduMap.getUiSettings();
		uiSettings.setZoomGesturesEnabled(true);
		uiSettings.setCompassEnabled(true);*/
		handler = new Handler(new HandlerCallBack());
		Work work = new Work();
		work.start();
		drawCircle();
		initLocation();
		mapView.invalidate();
	}

	private void initLocation() {
		baiduMap
		.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.NORMAL, true, null));
		baiduMap.setMyLocationEnabled(true);
		mLocClient = new LocationClient(this.getActivity());
		if(null==locationListern)locationListern=new LocationListern();
		mLocClient.registerLocationListener(locationListern);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	boolean isFirstLoc=true;
	class LocationListern implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || baiduMap == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				p("经度："+location.getLatitude());
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				baiduMap.animateMapStatus(u);
			}
		}
		
	}
	private void drawCircle(){
		CircleOptions overlayOptions=new CircleOptions();
		overlayOptions.center(new LatLng(37.863161,112.563512) )
					.fillColor(0x000000FF)
					.radius(1400)
					.stroke(new Stroke(5, 0xAA00FF00))
					.visible(true);
		baiduMap.addOverlay(overlayOptions);
	}
	//驾车线路索引
	private void driverPlanSearch(){
		RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(new GetRoutePlanResultListener() );
		PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", "龙泽");  
		PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", "西单");
		mSearch.drivingSearch((new DrivingRoutePlanOption())  
			    .from(stNode)  
			    .to(enNode));
	}
	//地理编码 地理信息查询
	private void searchGeoCoder(){
		GeoCoder mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new GetGeoCoderResultListener());
		mSearch.geocode(new GeoCodeOption()  
	    .city("太原")  
	    .address(""));
	}
	class MapLoadedCallback implements OnMapLoadedCallback{

		@Override
		public void onMapLoaded() {
			p("地图加载完毕");
		}
		
	}
	//地理信息索引回调
	class GetGeoCoderResultListener implements OnGetGeoCoderResultListener{

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
		  if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
	            //没有检索到结果  
	       }  
		        //获取地理编码结果 
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		   if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
	            //没有找到检索结果  
	        }  
	        //获取反向地理编码结果 
		}
		
	}
	class Work extends Thread {
		@Override
		public void run() {
			//handler.sendEmptyMessageDelayed(SHOW_STATELLITE, 10 * 1000);
		}
	}

	//驾车线路索引回调
	class GetRoutePlanResultListener implements OnGetRoutePlanResultListener{

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			//获取驾车线路规划结果  
		}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult arg0) {
			
		}

		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
			
		}
		
	}
	class HandlerCallBack implements Handler.Callback {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_STATELLITE: {
				t("更换开始");
				
				/*MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
						.newLatLng(new LatLng(112.563512, 37.863161));
				baiduMap.setMapStatus(mapStatusUpdate);*/
			}
				break;
			}
			return false;
		}

	}

	private void t(Object o) {
		Toast.makeText(this.getActivity(), "" + o, Toast.LENGTH_SHORT).show();
	}

	private void p(Object o) {
		Log.i("message", o + "");
	}
	
	public void loadRangeDB(LatLng center){
		
	}
	@Override
	public void onDestroy() {
		// 退出时销毁定位
		if(null!=mLocClient){
			mLocClient.stop();
		}
		// 关闭定位图层
		baiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		mapView = null;
		super.onDestroy();
	}

	private int currentMapType = BaiduMap.MAP_TYPE_NORMAL;
	public void changMapMode() {
		if(currentMapType==BaiduMap.MAP_TYPE_NORMAL){
			currentMapType=BaiduMap.MAP_TYPE_SATELLITE;
		}else{
			currentMapType=BaiduMap.MAP_TYPE_NORMAL;
		}
		baiduMap.setMapType(currentMapType);
	}
}
