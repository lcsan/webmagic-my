package us.codecraft.webmagic.model.xml.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.selector.AndSelector;
import us.codecraft.webmagic.selector.MixeSelector;
import us.codecraft.webmagic.selector.OrSelector;
import us.codecraft.webmagic.selector.Selector;

public abstract class UrlFilter {

    /**
     * 表达式转规则
     */
    protected List<Pattern> urlPatterns = new ArrayList<Pattern>();

    /**
     * 抽取区域选择器
     */
    protected Selector urlRegionSelector;

    // 表达式列表
    private List<String> expression = new ArrayList<String>();

    // 抽取区域
    private String region;

    public UrlFilter() {
        super();
    }

    public UrlFilter(List<String> expression, String region) {
        super();
        this.expression = expression;
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    @XmlAttribute(name = "region")
    public void setRegion(String region) {
        this.region = region;
        createUrlRegionSelector();
    }

    public List<String> getExpression() {
        return expression;
    }

    @XmlElement(name = "expression")
    public void setExpression(List<String> expression) {
        this.expression = expression;
        // getUrlPatterns();
    }

    private void createUrlPatterns() {
        for (String s : expression) {
            if (StringUtils.isBlank(s)) {
                urlPatterns.add(Pattern.compile("(.*)"));
            } else {
                urlPatterns.add(Pattern.compile("(" + s.replace(".", "\\.").replace("*", "[^\"'#]*").trim() + ")"));
            }
        }
    }

    public List<Pattern> getUrlPatterns() {
        if (urlPatterns.isEmpty()) {
            if (null != expression && !expression.isEmpty()) {
                createUrlPatterns();
            } else {
                initUrlPatterns();
            }
        }
        return urlPatterns;
    }

    private void createUrlRegionSelector() {
        if (StringUtils.isBlank(region)) {
            return;
        }
        // 匹配&&构造AndSelector选择器
        List<Selector> selectors = getSelector("&&", region);
        if (!selectors.isEmpty()) {
            urlRegionSelector = new AndSelector(selectors);
            return;
        }
        // 匹配||构造OrSelector选择器
        selectors = getSelector("\\|\\|", region);
        if (!selectors.isEmpty()) {
            urlRegionSelector = new OrSelector(selectors);
            return;
        }
        // 非逻辑选择器
        urlRegionSelector = new MixeSelector(region);
    }

    private List<Selector> getSelector(String regex, String expr) {
        List<Selector> selectors = new ArrayList<Selector>();
        if (expr.matches("^.*?\\s" + regex + "\\s.*?$")) {
            String[] strs = expr.split("\\s" + regex + "\\s");
            for (String str : strs) {
                selectors.add(new MixeSelector(str));
            }
        }
        return selectors;
    }

    public Selector getUrlRegionSelector() {
        return urlRegionSelector;
    }

    public abstract void initUrlPatterns();

    @Override
    public String toString() {
        return "UrlFilter [expression=" + expression + ", region=" + region + ", urlPatterns=" + urlPatterns
                + ", urlRegionSelector=" + urlRegionSelector + "]";
    }

}
