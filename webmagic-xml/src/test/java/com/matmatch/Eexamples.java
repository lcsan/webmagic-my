package com.matmatch;

import com.alibaba.fastjson.JSON;
import com.matmatch.mater.ConsolePageModelPipeline3;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials/examples")
@ExtractBy(value = "json('$..firstLevelCategory[*]')", multi = true)
public class Eexamples implements AfterExtractor {

    @ExtractBy("json('$..count')")
    private int count;

    @ExtractBy("json('$..filter').replace('\"label.*?\\}','\"namespace\":\"FIRST_LEVEL_CATEGORY\"\\}')")
    private String filter;

    private String token = "a668feaa-1eeb-4089-a61e-b28ecedcf162";

    public int getCount() {
        return count;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public void afterProcess(Page page) {
        if (count > 10000) {
            System.out.println(JSON.toJSONString(this));
        }
        int pagenum = count / 100;
        pagenum = pagenum == 0 ? 1 : pagenum;
        int total = 100;
        total = total > pagenum ? pagenum : total;
        for (int i = 1; i <= total; i++) {
            Request req = new Request("https://matmatch.com/searchapi/materials");
            req.setMethod(Method.POST);
            req.setRequestBody(HttpRequestBody.json("{\"pageNumber\":\"" + i + "\",\"filters\":[" + filter
                    + "],\"groupingEnabled\":false,\"pageSize\":100}", "UTF-8"));
            req.addHeader("Referer", "https://matmatch.com/search");
            req.addHeader("X-XSRF-TOKEN", token);
            page.addTargetRequest(req);
        }
    }

    public static void main(String[] args) {
        Request req = new Request("https://matmatch.com/searchapi/materials/examples");
        req.setMethod(Method.POST);
        req.setRequestBody(HttpRequestBody.json(
                "{\"filters\":[],\"exampleNames\":[\"firstLevelCategory\",\"secondLevelCategory\",\"thirdLevelCategory\"],\"pageSize\":100}",
                "UTF-8"));
        req.addHeader("Referer", "https://matmatch.com/search");
        req.addHeader("X-XSRF-TOKEN", "a668feaa-1eeb-4089-a61e-b28ecedcf162");

        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(10000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline3(), Eexamples.class, Eexamples2.class, Eexamples3.class, Search.class)
                .thread(5).addRequest(req).run();
    }

}
