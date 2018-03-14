package com.matmatch.dd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl("https://matmatch.com/search")
@ExtractBy(value = "xpath('//script').filter('filterDefinitions').json('$..materialProperty')", multi = true)
public class Start implements AfterExtractor {

    @ExtractBy("json('$.*')")
    private List<String> list1;

    private Map<String, JSONObject> map;

    private List<JSONObject> list;

    public Map<String, JSONObject> getMap() {
        return map;
    }

    public List<JSONObject> getList() {
        return list;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public void afterProcess(Page page) {
        String token = page.getHeaders().get("Set-Cookie").get(0).replaceAll("^.*?XSRF-TOKEN=(.*?);.*?$", "$1");
        System.out.println(token);
        map = new HashMap<String, JSONObject>();
        list = new ArrayList<JSONObject>();
        for (String string : list1) {
            JSONObject json = JSON.parseObject(string);
            map.put(json.getString("symbol"), json);
            map.put(json.getString("label"), json);
            list.add(json);
        }
        Common.setSTART(this);
        Request req = new Request("https://matmatch.com/searchapi/materials/examples");
        req.setMethod(Method.POST);
        req.setRequestBody(HttpRequestBody.json(
                "{\"filters\":[],\"exampleNames\":[\"firstLevelCategory\",\"secondLevelCategory\",\"thirdLevelCategory\"],\"pageSize\":100}",
                "UTF-8"));
        req.addHeader("Referer", "https://matmatch.com/search");
        req.addHeader("X-XSRF-TOKEN", token);
        req.putExtra("token", token);
        page.addTargetRequest(req);

    }

    public static void main(String[] args) {
        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(20000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline(), Start.class, Eexample.class, Searchs.class, Material.class,
                Composition.class, Material.class, Proprety.class, Propretys.class).thread(15)
                .addUrl("https://matmatch.com/search").run();
    }

}
