package com.matmatch.dd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

/**
 * 化学成分
 * 
 * @author Administrator
 *
 */
@Leaf
@ExtractBy(value = "//table[@class='width-100']//tr", multi = true)
public class Composition implements AfterExtractor {

    @ExtractBy(value = "//td/span[1]/text()", notNull = true)
    private String symbol;

    @ExtractBy("//td[2]/span/text()")
    private Double min;

    @ExtractBy("//td[2]/span[2]/text()")
    private Double max;

    @ExtractBy("//td[3]//span/text()")
    private String more;

    private String category;
    private String name;
    private String label;
    private String type;
    private Boolean temperatureDependent;
    private String unit = "%";

    public String getSymbol() {
        return symbol;
    }

    public String getMore() {
        return more;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public Boolean getTemperatureDependent() {
        return temperatureDependent;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public void afterProcess(Page page) {
        Start start = Common.getSTART();
        if (null != start) {
            JSONObject json = start.getMap().get(symbol);
            category = json.getString("category");
            name = json.getString("name");
            label = json.getString("label");
            type = json.getString("type");
            temperatureDependent = json.getBoolean("temperatureDependent");
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
