package us.codecraft.webmagic.model.xml.bean;

import java.util.List;
import java.util.regex.Pattern;

public class Tagurl extends UrlFilter {
    public Tagurl() {
        super();
    }

    public Tagurl(List<String> expression, String region) {
        super(expression, region);
    }

    @Override
    public String toString() {
        return "Tagurl " + super.toString() + "]";
    }

    @Override
    public void initUrlPatterns() {
        urlPatterns.add(Pattern.compile("(.*)"));
    }
}
