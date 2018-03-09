package com;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://matmatch.com/search")
public class Home3 implements AfterExtractor {

    @ExtractBy(value = "xpath('//script').filter('filterDefinitions').json('$..shape[*].filterValue')", multi = true)
    private List<String> shape;

    // @ExtractBy(value =
    // "xpath('//script').filter('filterDefinitions').json('$..shape[*].label')",
    // multi = true)
    // private List<String> shapelabel;

    public List<String> getShape() {
        return shape;
    }

    @Override
    public void afterProcess(Page page) {
        Common.SHAPE.addAll(shape);
        // Common.SHAPE.addAll(shapelabel);
    }

}
