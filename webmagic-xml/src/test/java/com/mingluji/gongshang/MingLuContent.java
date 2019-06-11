package com.mingluji.gongshang;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//li", multi = true)
public class MingLuContent {

    @ExtractBy(value = "xpath('//span[@class='field-label']/text()').filter('^(?!付费).*?$')", notNull = true)
    private String key;

    @ExtractBy("//span[@class='field-item']/allText()")
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MingLuContent [key=" + key + ", value=" + value + "]";
    }

}
