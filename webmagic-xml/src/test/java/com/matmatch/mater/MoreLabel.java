package com.matmatch.mater;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//li", multi = true)
public class MoreLabel {

    @ExtractBy("xpath('//span[1]/text()').filter('—').replace('^([\\d.]+)—[\\d.]+$','$1')")
    private Double minValue;

    @ExtractBy("xpath('//span[1]/text()').filter('—').replace('^[\\d.]+—([\\d.]+)$','$1')")
    private Double maxValue;

    @ExtractBy("xpath('//span[1]/text()').filter('^[^—]+$')")
    private Double value;

    @ExtractBy("//span[2]/text()")
    private String valueUnit;

    @ExtractBy("//span[3]/text()")
    private Double tremValue;

    @ExtractBy("//span[4]/text()")
    private String tremUnit;

    public MoreLabel() {
        super();
    }

    public MoreLabel(Double minValue, Double maxValue, Double value, String valueUnit, Double tremValue,
            String tremUnit) {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.valueUnit = valueUnit;
        this.tremValue = tremValue;
        this.tremUnit = tremUnit;
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

}
