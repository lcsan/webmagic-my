package com.matmatch.mater;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//tr", multi = true)
public class MaterialPropertyLabel implements AfterExtractor {

    @ExtractBy(value = "//td[1]/span/text()", notNull = true)
    private String property;

    @ExtractBy(value = "//td[1]/span[@class='text-40']/text()")
    private String propertyUnit;

    @ExtractBy(value = "//td[1]/span[@class='text-40']/sub/text()")
    private String propertyUnitSub;

    @ExtractBy("//span[@class='ellipsis comment-with-more text-40']/allText()")
    private String tip;

    @ExtractBy("//ul[@class='list-unstyled expandable margin-bottom-0']")
    private List<MoreLabel> moreLabel;

    @ExtractBy("xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('—').replace('^([\\d.]+)—[\\d.]+$','$1')")
    private Double minValue;

    @ExtractBy("xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('—').replace('^[\\d.]+—([\\d.]+)$','$1')")
    private Double maxValue;

    @ExtractBy("xpath('//span[@class='nowrap material-main-measurement']//span[@class='property-value']/text()').filter('^[^—]+$')")
    private Double value;

    @ExtractBy("//span[@class='nowrap material-main-measurement']//span[2]/text()")
    private String valueUnit;

    @ExtractBy("//span[@class='link expandable-toggle expandable-link-toggle']/span/text()")
    private Double tremValue;

    @ExtractBy("//span[@class='link expandable-toggle expandable-link-toggle']/span[2]/text()")
    private String tremUnit;

    public String getProperty() {
        return property;
    }

    public String getPropertyUnit() {
        return propertyUnit;
    }

    public String getPropertyUnitSub() {
        return propertyUnitSub;
    }

    public String getTip() {
        return tip;
    }

    public List<MoreLabel> getMoreLabel() {
        return moreLabel;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getValue() {
        return value;
    }

    public String getValueUnit() {
        return valueUnit;
    }

    public Double getTremValue() {
        return tremValue;
    }

    public String getTremUnit() {
        return tremUnit;
    }

    @Override
    public void afterProcess(Page page) {
        if (moreLabel.isEmpty()) {
            moreLabel.add(new MoreLabel(minValue, maxValue, value, valueUnit, tremValue, tremUnit));
        }
    }

}
