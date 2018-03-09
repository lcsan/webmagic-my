package com.matmatch.dd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@ExtractBy(value = "json('$.searchResults[*]')", multi = true)
@TargetUrl("https://matmatch.com/searchapi/materials")
public class Searchs implements AfterExtractor {

	@ExtractBy("replace('^(.*?)$','$1')")
	private String body;

	@ExtractBy("json('$..urlParam').replace('^(.*?)$','https://matmatch.com/materials/$1')")
	private String url;

	private JSONObject json;

	public String getUrl() {
		return url;
	}

	public JSONObject getJson() {
		return json;
	}

	@Override
	public void afterProcess(Page page) {
		json = JSON.parseObject(body);
		page.addTargetRequest(new Request(url).addHeader("X-XSRF-TOKEN", (String) page.getRequest().getExtra("token"))
				.putExtra("mmaterialProperty", page.getRequest().getExtra("mmaterialProperty")).putExtra("json", json)
				.setPriority(2));
	}

}
