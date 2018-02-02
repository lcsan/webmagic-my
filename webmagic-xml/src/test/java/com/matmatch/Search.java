package com.matmatch;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials")
public class Search {

	@ExtractBy("*")
	private String body;

	public static void main(String[] args) {
		Request req = new Request("https://matmatch.com/searchapi/materials");
		req.setMethod(Method.POST);
		req.setRequestBody(HttpRequestBody
				.json("{\"pageNumber\":\"100\",\"filters\":[],\"groupingEnabled\":false,\"pageSize\":100}", "UTF-8"));
		req.addHeader("Referer", "https://matmatch.com/search");
		req.addHeader("X-XSRF-TOKEN", "0bcbf248-4c11-4082-ba65-68b3d2a0b9c7");
		req.putExtra("flag", true);

		OOSpider.create(
				Site.me().setUseGzip(true).setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
				new ConsolePageModelPipeline(), Search.class).addRequest(req).run();

	}

}
