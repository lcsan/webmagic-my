package us.codecraft.webmagic.model.xml.bean;

import java.util.List;

public class Helpurl extends UrlFilter {

    public Helpurl() {
        super();
    }

    public Helpurl(List<String> expression, String region) {
        super(expression, region);
    }

    @Override
    public String toString() {
        return "Helpurl " + super.toString() + "]";
    }

    @Override
    public void initUrlPatterns() {

    }

}
