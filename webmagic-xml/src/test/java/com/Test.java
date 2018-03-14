package com;

import java.io.IOException;

import com.matmatch.dd.MatPageModelPipeline;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials*")
public class Test implements AfterExtractor {

    @Override
    public void afterProcess(Page page) {
        System.out.println(page.getJson());
    }

    public static void main(String[] args) throws IOException {
        Request req = new Request("https://matmatch.com/searchapi/materials");
        req.setMethod(Method.POST);
        req.setRequestBody(HttpRequestBody
                .json("{\"pageNumber\":\"10\",\"filters\":[],\"groupingEnabled\":false,\"pageSize\":1000}", "UTF-8"));
        req.addHeader("Referer", "https://matmatch.com/search");
        req.addHeader("X-XSRF-TOKEN", "75b714d8-b2ba-46b4-9ece-0b76f285859e");
        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(20000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new MatPageModelPipeline(), Test.class).addRequest(req).run();
    }

}
