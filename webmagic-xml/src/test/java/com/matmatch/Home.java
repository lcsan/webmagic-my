package com.matmatch;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matmatch.mater.ConsolePageModelPipeline2;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/search*")
@ExtractBy(value = "xpath('//script').filter('filterDefinitions').json('$..materialProperty.*')", multi = true)
public class Home implements AfterExtractor {

	@ExtractBy("json('$..name')")
	private String name;

	@ExtractBy("json('$..type')")
	private String type;

	@ExtractBy("json('$..min')")
	private String min;

	@ExtractBy("json('$..max')")
	private String max;

	@ExtractBy("json('$..units[*]')")
	private List<String> utils;

	@ExtractBy("json('$..defaultUnit')")
	private String unit;

	@ExtractBy("json('$..convertedMinMaxesForUnits')")
	private String convertedMinMaxesForUnits;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}

	public List<String> getUtils() {
		return utils;
	}

	public String getUnit() {
		return unit;
	}

	public String getConvertedMinMaxesForUnits() {
		return convertedMinMaxesForUnits;
	}

	@Override
	public void afterProcess(Page page) {

		String token = (String) page.getRequest().getExtra("token");
		String filter = (String) page.getRequest().getExtra("filter");

		if (!utils.isEmpty()) {
			JSONObject json = JSON.parseObject(convertedMinMaxesForUnits);
			for (String string : utils) {
				JSONObject jb = json.getJSONObject(string);
				JSONObject js = new JSONObject();
				js.put("name", name);
				js.put("type", type);
				js.put("unit", string);
				js.put("max", jb.get("max"));
				js.put("min", jb.get("min"));
				JSONArray jsary = new JSONArray();
				jsary.add(js);
				jsary.add(JSON.parseObject(filter));
				JSONObject re = new JSONObject();
				re.put("filters", jsary);
				re.put("pageSize", 0);

				Request req = new Request("https://matmatch.com/searchapi/materials/examples?1");
				req.setMethod(Method.POST);
				req.setRequestBody(HttpRequestBody.json(re.toJSONString(), "UTF-8"));
				req.addHeader("Referer", "https://matmatch.com/search");
				req.addHeader("X-XSRF-TOKEN", token);
				req.putExtra("token", token);
				req.putExtra("json", re);
				page.addTargetRequest(req);
			}
		} else {
			JSONObject js = new JSONObject();
			js.put("name", name);
			js.put("type", type);
			js.put("unit", unit);
			js.put("max", Float.valueOf(max));
			js.put("min", Float.valueOf(min));
			JSONArray jsary = new JSONArray();
			jsary.add(js);
			jsary.add(filter);
			JSONObject re = new JSONObject();
			re.put("filters", jsary);
			re.put("pageSize", 0);

			Request req = new Request("https://matmatch.com/searchapi/materials/examples?1");
			req.setMethod(Method.POST);
			req.setRequestBody(HttpRequestBody.json(re.toJSONString(), "UTF-8"));
			req.addHeader("Referer", "https://matmatch.com/search");
			req.addHeader("X-XSRF-TOKEN", token);
			req.putExtra("token", token);
			req.putExtra("json", re);
			page.addTargetRequest(req);
		}
	}

	public static void main(String[] args) {
		OOSpider.create(
				Site.me().setUseGzip(true).setTimeOut(10000).setRetryTimes(3).setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
				new ConsolePageModelPipeline2(), Home.class).test("https://matmatch.com/search");
	}

}
