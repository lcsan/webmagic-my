package com.matmatch.mater;

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
public class Chemical {

    @ExtractBy(value = "//td/span[1]/text()", notNull = true)
    private String element;

    @ExtractBy("//td[2]/span/text()")
    private Double minWeight;

    @ExtractBy("//td[2]/span[2]/text()")
    private Double maxWeight;

    public String getElement() {
        return element;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    @Override
    public String toString() {
        return "Chemical [element=" + element + ", minWeight=" + minWeight + ", maxWeight=" + maxWeight + "]";
    }

}
