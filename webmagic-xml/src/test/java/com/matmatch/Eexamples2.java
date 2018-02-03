package com.matmatch;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials/examples")
@ExtractBy(value = "json('$..secondLevelCategory[*]')", multi = true)
public class Eexamples2 implements AfterExtractor {

    @ExtractBy("json('$..count')")
    private int count;

    @ExtractBy("json('$..filter').replace('\"label.*?\\}','\"namespace\":\"SECOND_LEVEL_CATEGORY\"\\}')")
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
        for (int i = 2; i <= total; i++) {
            Request req = new Request("https://matmatch.com/searchapi/materials");
            req.setMethod(Method.POST);
            req.setRequestBody(HttpRequestBody.json("{\"pageNumber\":\"" + i + "\",\"filters\":[" + filter
                    + "],\"groupingEnabled\":false,\"pageSize\":100}", "UTF-8"));
            req.addHeader("Referer", "https://matmatch.com/search");
            req.addHeader("X-XSRF-TOKEN", token);
            page.addTargetRequest(req);
        }
    }

}
