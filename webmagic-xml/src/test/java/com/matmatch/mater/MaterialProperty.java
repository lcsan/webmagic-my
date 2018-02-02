package com.matmatch.mater;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.Leaf;

@Leaf
@ExtractBy(value = "//tbody", multi = true)
public class MaterialProperty {

    @ExtractBy(value = "//th/text()", notNull = true)
    private String property;

    @ExtractBy("//table")
    private List<MaterialPropertyLabel> mterialPropertyLabels;

    public String getProperty() {
        return property;
    }

    public List<MaterialPropertyLabel> getMterialPropertyLabels() {
        return mterialPropertyLabels;
    }

    @Override
    public String toString() {
        return "MaterialProperty [property=" + property + ",mterialPropertyLabel=" + mterialPropertyLabels + "]";
    }

}
