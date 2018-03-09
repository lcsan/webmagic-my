package com.matmatch.dd;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//tr", multi = true)
public class Propretys implements AfterExtractor {

    @ExtractBy(value = "//td[1]/span/text()", notNull = true)
    private String label;

    @ExtractBy("//span[@class='ellipsis comment-with-more text-40']/allText()")
    private String tip;

    @ExtractBy("//ul[@class='list-unstyled expandable margin-bottom-0']")
    private List<Proprety> propretys;

    @ExtractBy("xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('^[^—]+$') || xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('—').replace('^([\\d.E+-]+)—[\\d.E+-]+$','$1')")
    private Double min;

    @ExtractBy("xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('^[^—]+$') || xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('—').replace('^[\\d.E+-]+—([\\d.E+-]+)$','$1')")
    private Double max;

    @ExtractBy("//span[@class='nowrap material-main-measurement']//span[2]/text()")
    private String unit;

    @ExtractBy("//span[@class='link expandable-toggle expandable-link-toggle']/span/text()")
    private Double temperature;

    @ExtractBy("//span[@class='link expandable-toggle expandable-link-toggle']/span[2]/text()")
    private String temperatureUnit;

    private String category;
    private String symbol;
    private String name;
    private String type;
    private Boolean temperatureDependent;

    private Double tmin;
    private Double tmax;

    public String getLabel() {
        return label;
    }

    public String getTip() {
        return tip;
    }

    public List<Proprety> getPropretys() {
        return propretys;
    }

    public String getUnit() {
        return unit;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public String getCategory() {
        return category;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Boolean getTemperatureDependent() {
        return temperatureDependent;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getTmin() {
        return tmin;
    }

    public Double getTmax() {
        return tmax;
    }

    @Override
    public void afterProcess(Page page) {
        tmin = temperature;
        tmax = temperature;
        Start start = Common.getSTART();
        if (null != start) {
            JSONObject json = start.getMap().get(label);
            category = json.getString("category");
            name = json.getString("name");
            symbol = json.getString("symbol");
            type = json.getString("type");
            temperatureDependent = json.getBoolean("temperatureDependent");
        }
        if (propretys.isEmpty()) {
            propretys.add(new Proprety(min, max, unit, temperature, temperatureUnit));
        } else {
            for (Proprety proprety : propretys) {
                if (null != proprety.getMin()) {
                    min = Math.min(min, proprety.getMin());
                }
                if (null != proprety.getMax()) {
                    max = Math.max(max, proprety.getMax());
                }
                if (null != proprety.getTemperature()) {
                    tmin = Math.min(tmin, proprety.getTemperature());
                    tmax = Math.max(tmax, proprety.getTemperature());
                }
            }
        }
    }

}
