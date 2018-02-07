package com.matmatch;

import com.alibaba.fastjson.JSON;
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

@ExtractBy(value = "json('$..searchResults[*]')", multi = true)
@TargetUrl("https://matmatch.com/searchapi/materials")
public class Search implements AfterExtractor {

    @ExtractBy("json('$..urlParam').replace('^(.*?)$','https://matmatch.com/materials/$1')")
    private String url;

    @ExtractBy("replace('^(.*?)$','$1')")
    private String body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static void main(String[] args) {
        String token = "615bccc1-61a6-4185-a743-ce12d50db2ef";
        Request req = new Request("https://matmatch.com/searchapi/materials");
        req.setMethod(Method.POST);
        req.setRequestBody(
                HttpRequestBody.json("{\"pageNumber\":\"1\",\"filters\":[],\"groupingEnabled\":false}", "UTF-8"));
        req.addHeader("Referer", "https://matmatch.com/search");
        req.addHeader("X-XSRF-TOKEN", token);
        req.putExtra("token", token);

        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(10000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline2(), Search.class).addRequest(req).run();
    }

    @Override
    public void afterProcess(Page page) {
        JSONObject json = JSON.parseObject(body);
        page.addTargetRequest(new Request(url).putExtra("json", json));
    }
}
