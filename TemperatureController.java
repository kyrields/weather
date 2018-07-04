
package com.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("temperature")
public class TemperatureController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public String list(HttpServletRequest request, Map<String, Object> map) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("weather");
		List<Map<String,String>> weaList = new ArrayList<>();
		if(obj == null) {
			String weatherInfo = WeatherUtils.getWeatherData("天津");
			weaList = WeatherUtils.getWeather(weatherInfo);
			session.setAttribute("weather", weaList);
		}else {
			weaList = (List<Map<String,String>>)obj;
		}
		map.put("weaList", weaList);
		map.put("weaJSon", mapper.writeValueAsString(weaList));
		return "temperature/temperature_list";
	}
	
}
