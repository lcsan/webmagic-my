package com.matmatch;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://matmatch.com/searchapi/materials")
public class Search {

	@ExtractBy("json('$..searchResults[*].urlParam').replace('^(.*?)$','https://matmatch.com/materials/$1')")
	private List<String> urls;

	public List<String> getUrls() {
		return urls;
	}

	// @Override
	// public void afterProcess(Page page) {
	// String token = (String) page.getRequest().getExtra("token");
	// if (null != token) {
	// int total = 100;
	// total = total > count ? count : total;
	// for (int i = 2; i <= total; i++) {
	// Request req = new Request("https://matmatch.com/searchapi/materials");
	// req.setMethod(Method.POST);
	// req.setRequestBody(HttpRequestBody.json(
	// "{\"pageNumber\":\"" + i +
	// "\",\"filters\":[],\"groupingEnabled\":false,\"pageSize\":100}",
	// "UTF-8"));
	// req.addHeader("Referer", "https://matmatch.com/search");
	// req.addHeader("X-XSRF-TOKEN", token);
	// page.addTargetRequest(req);
	// }
	// }
	// }

}
