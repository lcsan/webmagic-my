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
	private String minWeight;

	@ExtractBy("//td[2]/span[2]/text()")
	private String maxWeight;

	public String getElement() {
		return element;
	}

	public String getMinWeight() {
		return minWeight;
	}

	public String getMaxWeight() {
		return maxWeight;
	}

	@Override
	public String toString() {
		return "Chemical [element=" + element + ", minWeight=" + minWeight + ", maxWeight=" + maxWeight + "]";
	}

}
