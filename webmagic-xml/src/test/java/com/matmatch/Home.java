package com.matmatch;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matmatch.mater.ConsolePageModelPipeline2;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://matmatch.com/search")
@ExtractBy(value = "xpath('//script').filter('filterDefinitions').json('$..materialProperty.*')", multi = true)
public class Home implements AfterExtractor {

    @ExtractBy("json('$..name')")
    private String name;

    @ExtractBy("json('$..type')")
    private String type;

    @ExtractBy("json('$..min')")
    private int min;

    @ExtractBy("json('$..max')")
    private int max;

    @ExtractBy("json('$..units')")
    private List<String> utils;

    @ExtractBy("json('$..defaultUnit')")
    private String unit;

    @ExtractBy("json('$..convertedMinMaxesForUnits')")
    private String convertedMinMaxesForUnits;

    @Override
    public void afterProcess(Page page) {
        String[] args = new String[] {
                "{\"type\":\"materialProperty\",\"filterValue\":\"Wrought Aluminium\",\"name\":\"thirdLevelCategory\",\"namespace\":\"THIRD_LEVEL_CATEGORY\"}",
                "{\"type\":\"materialProperty\",\"filterValue\":\"Steel\",\"name\":\"thirdLevelCategory\",\"namespace\":\"THIRD_LEVEL_CATEGORY\"}" };
        if (!utils.isEmpty()) {
            JSONObject json = JSON.parseObject(convertedMinMaxesForUnits);
            for (String string : utils) {
                JSONObject jb = json.getJSONObject(string);
                int min = jb.getIntValue("min");
                int max = jb.getIntValue("max");
                for (String string2 : args) {
                    JSONObject js = new JSONObject();
                    js.put("name", name);
                    js.put("type", type);
                    js.put("unit", string);
                    js.put("max", max);
                    js.put("min", min);
                }
            }
        } else {
            for (String string : args) {
                JSONObject js = new JSONObject();
                js.put("name", name);
                js.put("type", type);
                js.put("unit", unit);
                js.put("max", max);
                js.put("min", min);
            }
        }
    }

    public static void main(String[] args) {
        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(10000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline2(), Home.class).test("https://matmatch.com/search");
    }

}
