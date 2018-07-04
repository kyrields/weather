

package com.test.common;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tfkj.ibms.cms.pojo.WeatherPojo;

public class WeatherUtils {
	
	// 天气对应标志
	public static Map<String, String> dayMap;
	
	static {
		dayMap = new HashMap<>();
		
		dayMap.put("晴-日", "clear-day");
		dayMap.put("晴-夜", "clear-night");
		dayMap.put("多云-日", "partly-cloudy-day");
		dayMap.put("多云-夜", "partly-cloudy-night");
		dayMap.put("阴", "cloudy");
		dayMap.put("雨", "rain");
		dayMap.put("雪", "snow");
		
	}
	
	public  static String getWeatherData(String cityname) {
		String res = "";
		try {
			String city = java.net.URLEncoder.encode(cityname, "utf-8");
			
			//拼地址
			String weather_url = String.format("https://www.sojson.com/open/api/weather/json.shtml?city=%s",city);
			URL url = new URL(weather_url);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			res = org.apache.commons.io.IOUtils.toString(is,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static List<Map<String, String>> getWeather(String weatherInfo) throws Exception {
		// 格式化接口返回内容
		WeatherPojo weatherPojo = (WeatherPojo)JSON.parseObject(weatherInfo, WeatherPojo.class);
		List<Map<String, String>> list = new ArrayList<>();
		boolean flag = true;
		for(WeatherPojo.Forecast fe : weatherPojo.getData().getForecast()) {
			Map<String, String> map = new HashMap<>();
			// 取出关键字，匹配天气标志
			String high_tem = fe.getHigh().substring(3, fe.getHigh().indexOf("℃"));
			String low_tem = fe.getLow().substring(3, fe.getLow().indexOf("℃"));
			String w_temp = low_tem + " - " + high_tem;
			String w_date = fe.getDate().substring(3);
			// 天气图标使用唯一标示
			String w_id = fe.getDate().substring(0, 2);
			String w_type = "";
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			if(fe.getType().contains("晴")) {
				if(hour >= 20 && flag) {
					w_type = dayMap.get("晴-夜");
					flag = false;
				}else {
					w_type = dayMap.get("晴-日");
				}
			}else if(fe.getType().contains("阴")) {
				w_type = dayMap.get("阴");
			}else if(fe.getType().contains("雨")) {
				w_type = dayMap.get("雨");
			}else if(fe.getType().contains("雪")) {
				w_type = dayMap.get("雪");
			}else if(fe.getType().contains("多云")) {
				if(hour >= 20 && flag) {
					w_type = dayMap.get("多云-夜");
					flag = false;
				}else {
					w_type = dayMap.get("多云-日");
				}
			}
			map.put("w_temp", w_temp);
			map.put("w_date", w_date);
			map.put("w_type", w_type);
			map.put("w_id", w_id);
			list.add(map);
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getWeather(getWeatherData("天津")));
	}
	
}
