package com.jrelax.kit;


import java.math.BigDecimal;

import java.math.RoundingMode;

/**
 * GIS地理位置工具类
 */
public class GISKit
{
	/**
	 * 获取以某个点为中心，范围@length内的四个坐标位置
	 * @param lon 经度
	 * @param lat 维度
	 * @param length 范围
	 * @return
	 */
	public static double[] calcOfRange(double lon, double lat, double length) {
		double range = 57.295779513082323D * length / 6372.7969999999996D;
		double ingR = range / Math.cos(lat * 3.141592653589793D / 180.0D);
		
		double maxLat = lat + range;
		double minLat = lat - range;
		double maxLon = lon + ingR;
		double minLon = lon - ingR;
		
		double[] ranges = { maxLat, minLat, maxLon, minLon };
		
		for (int i = 0; i < ranges.length; i++) {
			double v = ranges[i];
			BigDecimal bd = new BigDecimal(v);
			bd = bd.setScale(6, RoundingMode.HALF_UP);
			ranges[i] = bd.doubleValue();
		}
		
		return ranges;
	}


	/**
	 * 获取两个坐标之间的直线距离
	 * @param lon1 经度1
	 * @param lat1 维度1
	 * @param lon2 经度2
	 * @param lat2 维度2
	 * @return
	 */
	public static double calcOfDistance(double lon1, double lat1, double lon2, double lat2){
		double R = 6378137.0D;
		lat1 = lat1 * 3.141592653589793D / 180.0D;
		lat2 = lat2 * 3.141592653589793D / 180.0D;
		double a = lat1 - lat2;
		double b = (lon1 - lon2) * 3.141592653589793D / 180.0D;
		
		double sa2 = Math.sin(a / 2.0D);
		double sb2 = Math.sin(b / 2.0D);
		double d = 2.0D * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
}