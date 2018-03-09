package com;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
public class Home2 implements AfterExtractor {

    @ExtractBy("json('$..label')")
    private String label;

    @ExtractBy("replace('^(.*?)$','$1')")
    private String json;

    @ExtractBy("json('$..symbol')")
    private String symbol;

    public String getLabel() {
        return label;
    }

    public String getJson() {
        return json;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public void afterProcess(Page page) {
        // if (symbol.matches("^[a-zA-Z]+$")) {
        JSONObject js = JSON.parseObject(json);
        js.remove("units");
        js.remove("convertedMinMaxesForUnits");
        Common.MAP.put(symbol, js);
        // } else {
        Common.MAP.put(label, js);
        // }
    }

    public static void main(String[] args) {

        OOSpider.create(
                Site.me().setUseGzip(true).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline2(), Home2.class, Home3.class).test("https://matmatch.com/search");
        File fe = new File("H:/matmatch.com");
        if (fe.isDirectory()) {
            File[] fes = fe.listFiles();
            for (File file : fes) {
                String body = null;
                try {
                    body = FileUtils.readFileToString(file, "UTF-8");
                    JSONObject json = JSON.parseObject(body);
                    JSONObject result = new JSONObject();
                    result.put("id", json.get("materialId"));
                    String name = json.getString("materialName");
                    result.put("name", name);
                    name = name.toLowerCase();
                    List<String> shape = new ArrayList<String>();
                    for (String str : Common.SHAPE) {
                        if (name.contains(str)) {
                            shape.add(str);
                        }
                    }
                    result.put("shape", shape);
                    result.put("urlParam", json.get("path"));
                    result.put("supplierName", json.get("supplierName"));
                    result.put("primaryStandards", json.get("standards"));
                    result.put("equivalentStandards", json.get("standards"));
                    result.put("applications", json.get("applications"));
                    result.put("description", json.get("description"));

                    result.put("technological", json.get("technological"));

                    JSONArray jsary = json.getJSONArray("chemicals");
                    JSONArray rejsary = new JSONArray();
                    for (int i = 0, j = jsary.size(); i < j; i++) {
                        JSONObject js = jsary.getJSONObject(i);
                        JSONObject re = (JSONObject) Common.MAP.get(js.get("element")).clone();
                        re.put("min", js.get("minWeight"));
                        re.put("max", js.get("minWeight"));
                        re.put("unit", "%");
                        rejsary.add(re);
                    }
                    if (json.containsKey("materialPropertys")) {
                        jsary = json.getJSONArray("materialPropertys");
                        for (int i = 0, j = jsary.size(); i < j; i++) {
                            JSONObject js = jsary.getJSONObject(i);
                            JSONArray jsary1 = js.getJSONArray("mterialPropertyLabels");
                            for (int m = 0, n = jsary1.size(); m < n; m++) {
                                JSONObject js1 = jsary1.getJSONObject(m);
                                JSONArray jsary2 = js1.getJSONArray("moreLabel");
                                for (int k = 0, l = jsary2.size(); k < l; k++) {
                                    JSONObject js2 = jsary2.getJSONObject(k);
                                    JSONObject re = (JSONObject) Common.MAP.get(js1.get("property")).clone();
                                    re.put("unit", js1.get("valueUnit"));
                                    if (js2.containsKey("value")) {
                                        re.put("min", js2.get("value"));
                                        re.put("max", js2.get("value"));
                                    }
                                    if (js2.containsKey("minValue")) {
                                        re.put("min", js2.get("minValue"));
                                        re.put("max", js2.get("minValue"));
                                    }
                                    if (js2.containsKey("maxValue")) {
                                        re.put("max", js2.get("maxValue"));
                                    }
                                    if (js2.containsKey("tremValue")) {
                                        re.put("temperature", js2.get("tremValue"));

                                    }
                                    if (js2.containsKey("tremUnit")) {
                                        re.put("temperatureUnit", js2.get("tremUnit"));
                                    }
                                    rejsary.add(re);
                                }
                            }
                        }
                        result.put("materialProperty", rejsary);
                    }
                    if (json.containsKey("materials")) {
                        JSONArray materials = json.getJSONArray("materials");
                        for (int i = 0, j = materials.size(); i < j; i++) {
                            switch (i) {
                            case 0:
                                result.put("firstLevelCategory", materials.get(i));
                                break;
                            case 1:
                                result.put("secondLevelCategory", materials.get(i));
                                break;
                            case 2:
                                result.put("thirdLevelCategory", materials.get(i));
                                break;
                            default:
                                break;
                            }
                        }
                    }

                    FileUtils.write(new File("H:/matmatch/data/" + result.getString("urlParam") + ".txt"),
                            result.toJSONString());

                } catch (Exception e) {
                    System.out.println(body);
                    e.printStackTrace();
                }
            }
        }
    }
}
