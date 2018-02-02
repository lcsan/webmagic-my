package com.matmatch;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials/examples")
public class Eexamples {

	@ExtractBy("*")
	private String body;

	public static void main(String[] args) {
		Request req = new Request("https://matmatch.com/searchapi/materials/examples");
		req.setMethod(Method.POST);
		req.setRequestBody(HttpRequestBody
				.json("{\"filters\":[],\"exampleNames\":[\"shape\"],\"pageSize\":100,\"pageNumber\":1}", "UTF-8"));
		req.addHeader("Referer", "https://matmatch.com/search");
		req.addHeader("X-XSRF-TOKEN", "f20d4210-012a-42fd-9899-cc6df30e8ccf");

		OOSpider.create(
				Site.me().setUseGzip(true).setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
				new ConsolePageModelPipeline(), Eexamples.class).addRequest(req).run();
	}

}
