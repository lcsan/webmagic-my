package com.matmatch.dd;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/searchapi/materials/examples")
@ExtractBy(value = "json('$.examples.*.[*]')", multi = true)
public class Eexample implements AfterExtractor {

    @ExtractBy("json('$..count')")
    private int count;

    @ExtractBy("json('$..filter').replace('\"label.*?\\}','\"namespace\":\"FIRST_LEVEL_CATEGORY\"\\}')")
    private String filter;

    public int getCount() {
        return count;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public void afterProcess(Page page) {
        String token = (String) page.getRequest().getExtra("token");
        Start start = Common.getSTART();

        Object obj = page.getRequest().getExtra("json");
        JSONObject prajson = (null == obj ? null : (JSONObject) obj);
        if (count > 10000 && null != prajson) {
            List<JSONObject> list = start.getList();
            for (JSONObject json : list) {
                JSONArray jsary = json.getJSONArray("units");
                if (jsary.isEmpty()) {
                    JSONObject js = (JSONObject) json.clone();
                    js.put("unit", js.get("defaultUnit"));

                    Request req = new Request("https://matmatch.com/searchapi/materials/examples");
                    req.setMethod(Method.POST);
                    req.setRequestBody(HttpRequestBody.json(
                            "{\"filters\":[" + js.toJSONString()
                                    + "],\"exampleNames\":[\"firstLevelCategory\",\"secondLevelCategory\",\"thirdLevelCategory\"],\"pageSize\":100}",
                            "UTF-8"));
                    req.addHeader("Referer", "https://matmatch.com/search");
                    req.addHeader("X-XSRF-TOKEN", token);
                    req.putExtra("token", token);
                    req.putExtra("json", js);
                    page.addTargetRequest(req);
                } else {
                    JSONObject con = json.getJSONObject("convertedMinMaxesForUnits");
                    for (Object object : jsary) {
                        String unit = (String) object;
                        JSONObject jb = con.getJSONObject(unit);

                        JSONObject js = (JSONObject) json.clone();
                        js.put("unit", unit);
                        js.put("max", jb.get("max"));
                        js.put("min", jb.get("min"));

                        Request req = new Request("https://matmatch.com/searchapi/materials/examples");
                        req.setMethod(Method.POST);
                        req.setRequestBody(HttpRequestBody.json(
                                "{\"filters\":[" + js.toJSONString()
                                        + "],\"exampleNames\":[\"firstLevelCategory\",\"secondLevelCategory\",\"thirdLevelCategory\"],\"pageSize\":100}",
                                "UTF-8"));
                        req.addHeader("Referer", "https://matmatch.com/search");
                        req.addHeader("X-XSRF-TOKEN", token);
                        req.putExtra("token", token);
                        req.putExtra("json", js);
                        page.addTargetRequest(req);
                    }
                }
            }

        }
        int pagenum = count / 100;
        pagenum = pagenum == 0 ? 1 : pagenum;
        int total = 100;
        total = total > pagenum ? pagenum : total;
        for (int i = 1; i <= total; i++) {
            Request req = new Request("https://matmatch.com/searchapi/materials");
            req.setMethod(Method.POST);
            req.setRequestBody(HttpRequestBody.json("{\"pageNumber\":\"" + i + "\",\"filters\":[" + filter
                    + (null == prajson ? "" : "," + prajson.toJSONString())
                    + "],\"groupingEnabled\":false,\"pageSize\":100}", "UTF-8"));
            req.addHeader("Referer", "https://matmatch.com/search");
            req.addHeader("X-XSRF-TOKEN", token);
            req.putExtra("token", token);
            page.addTargetRequest(req);
        }
    }

}
