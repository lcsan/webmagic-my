package com.matmatch;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials/examples\\?1")
public class Eexamples4 implements AfterExtractor {

	@ExtractBy("json('$..resultsCount')")
	private int count;

	public int getCount() {
		return count;
	}

	@Override
	public void afterProcess(Page page) {
		if (count > 0) {
			String token = (String) page.getRequest().getExtra("token");
			JSONObject json = (JSONObject) page.getRequest().getExtra("json");
			json.put("pageSize", 100);
			json.put("groupingEnabled", false);

			int pagenum = count / 100;
			pagenum = pagenum == 0 ? 1 : pagenum;
			int total = 100;
			total = total > pagenum ? pagenum : total;
			for (int i = 1; i <= total; i++) {
				json.put("pageNumber", i);
				Request req = new Request("https://matmatch.com/searchapi/materials");
				req.setMethod(Method.POST);
				req.setRequestBody(HttpRequestBody.json(json.toJSONString(), "UTF-8"));
				req.addHeader("Referer", "https://matmatch.com/search");
				req.addHeader("X-XSRF-TOKEN", token);
				page.addTargetRequest(req);
			}
		}

	}

}
