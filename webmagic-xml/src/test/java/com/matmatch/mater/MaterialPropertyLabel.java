package com.matmatch.mater;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//tr", multi = true)
public class MaterialPropertyLabel {

    @ExtractBy(value = "//td/allText()", notNull = true)
    private String property;

    @ExtractBy("//span[@class='nowrap material-main-measurement']/allText()")
    private String label;

    @ExtractBy("//td[2]//span[@class='ellipsis comment-with-more text-40']/allText()")
    private String tip;

    @ExtractBy("//li/allText()")
    private List<String> moreLabel;

    public String getProperty() {
        return property;
    }

    public String getTip() {
        return tip;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getMoreLabel() {
        return moreLabel;
    }

    @Override
    public String toString() {
        return "MaterialPropertyLabel [property=" + property + ", label=" + label + "]";
    }

}
