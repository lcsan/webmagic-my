package com.matmatch.dd;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//li", multi = true)
public class Proprety {

    @ExtractBy("xpath('//span[1]/text()').filter('^[^—]+$') || xpath('//span[1]/text()').filter('—').replace('^([\\d.]+)—[\\d.]+$','$1')")
    private Double min;

    @ExtractBy("xpath('//span[1]/text()').filter('^[^—]+$') || xpath('//span[1]/text()').filter('—').replace('^[\\d.]+—([\\d.]+)$','$1')")
    private Double max;

    @ExtractBy("//span[2]/text()")
    private String unit;

    @ExtractBy("//span[3]/text()")
    private Double temperature;

    @ExtractBy("//span[4]/text()")
    private String temperatureUnit;

    public Proprety() {
        super();
    }

    public Proprety(Double min, Double max, String unit, Double temperature, String temperatureUnit) {
        super();
        this.min = min;
        this.max = max;
        this.unit = unit;
        this.temperature = temperature;
        this.temperatureUnit = temperatureUnit;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public String getUnit() {
        return unit;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

}
