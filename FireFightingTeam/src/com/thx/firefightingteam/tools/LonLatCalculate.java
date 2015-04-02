package com.thx.firefightingteam.tools;

import java.math.BigDecimal;

public class LonLatCalculate {

	private static double DEF_PI = 3.14159265359; // PI	
	private static double DEF_2PI = 6.28318530712; // 2*PI
	private static double DEF_PI180 = 0.01745329252; // PI/180
	private static double DEF_R = 6370693.5; // radius of earth
	private final static int SCALE = 7;
	//1000表示1公里，111表示同经度时，纬度相差一度，距离就相差111公里
	//单位纬度
	private static final BigDecimal LATITUDE_UNIT = new BigDecimal(1).divide(
			new BigDecimal(1000 * 111), SCALE, BigDecimal.ROUND_HALF_EVEN);
	/**
	 * 
	 * @param longitude 经度
	 * @param latitude 维度
	 * @param radius 半径
	 */
	public  Bounds calculate(Double longitude, Double latitude, int radius) {
		String latx=new BigDecimal(radius).multiply(LATITUDE_UNIT).toString();
		String lagx = new BigDecimal(latx).divide(new BigDecimal(String.valueOf(Math.cos(latitude))),SCALE, BigDecimal.ROUND_HALF_EVEN).toString();
		Bounds bounds=new Bounds();
		bounds.latN=latitude + Math.abs(Double.valueOf(latx));
		bounds.latS = latitude - Math.abs(Double.valueOf(latx));
		bounds.lonE = longitude + Math.abs(Double.valueOf(lagx));
		bounds.lonW = longitude - Math.abs(Double.valueOf(lagx));
		return bounds;
	}
	public static double GetShortDistance(double lon1, double lat1, double lon2,double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public static double GetLongDistance(double lon1, double lat1, double lon2,double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
				* Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return distance;
	}

	 class Bounds {
		public Double lonW;
		public Double lonE;
		public Double latN;
		public Double latS;
	}
}
