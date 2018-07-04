<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>温湿度监控</title>
	<script src="js/skycons.js"></script>
	<script src="js/jquery.min.js"></script>
	<style type="text/css">
		.weather_w3_inner_info ul li {
		    display: inline-block;
		    text-align: center;
		    width: 16%;
		    margin-top: 2em;
		}
		.over_lay_agile {
		    background: url(img/overlay.png)repeat;
		    padding: 3em 3em 7.5em 3em;
		    height:100%;
		}
		.weather_w3_inner_info ul {
		    margin: 0 auto;
		    text-align: center;
		}
		.weather_w3_inner_info {
		    background: url(img/weather_bg.jpg)no-repeat center;
		    background-attachment: fixed;
		}
		figure.icons, .weather-text {
		    float: left;
		    width: 50%;
		    color: white;
		}
	</style>
</head>
<body style="min-height:700px;">
	<div class="row" style="margin: 15px;height: 180px;background-color: white;">

		<div class="weather_w3_agile_info agile_info_shadow">
		  <div class="weather_w3_inner_info">
		      
			     <div class="over_lay_agile">
		       	  <ul>

		       	  	<c:forEach items="${weaList }" var="wea">
		       	  		<li>
							<figure class="icons">
								<canvas id="${wea.w_id }" width="60" height="60"></canvas>
							</figure>
							<div class="weather-text">
								<h4>${wea.w_date }</h4>
								<h5>${wea.w_temp } °C</h5>
							</div>
							<div class="clearfix"></div>
						</li>
		       	  	</c:forEach> 
				</ul>
				</div>
			</div>	
		</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		var listStr  = '${weaJSon }';
		var weaList = JSON.parse(listStr);
		var icons = new Skycons({"color": "#fff"});
	  	$.each(weaList, function(idx, obj){
	  		icons.set(obj.w_id, obj.w_type);
	  	});
	 	icons.play();
	});
</script>
</html>
