package us.codecraft.webmagic.model.xml.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.StringUtils;

import us.codecraft.webmagic.model.Source;
import us.codecraft.webmagic.model.annotation.ComboExtract;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.selector.AndSelector;
import us.codecraft.webmagic.selector.CssSelector;
import us.codecraft.webmagic.selector.FilterSelector;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.MixeSelector;
import us.codecraft.webmagic.selector.OrSelector;
import us.codecraft.webmagic.selector.RegexSelector;
import us.codecraft.webmagic.selector.Selector;
import us.codecraft.webmagic.selector.Type;
import us.codecraft.webmagic.selector.XpathSelector;

public class Extract {

    // 规则表达式
    private String expression;

    // 选择器类型
    private Type type = Type.mixe;

    // 是否可以为null
    private boolean notNull = false;

    // 选择对象类型
    private Source source = Source.SelectedHtml;

    // 是否列表抽取
    private boolean multi = false;

    // 选择器
    private Selector selector;

    public Extract() {
        super();
    }

    public Extract(ExtractBy ext) {
        this.setExpression(ext.value());
        this.setSelectorType(Type.valueOf(ext.type().name().toLowerCase()));
        this.setMulti(ext.multi());
        this.setSource(Source.valueOf(ext.source().name()));
        this.setNotNull(ext.notNull());
    }

    public Extract(ExtractByUrl ext) {
        String regexPattern = ext.value();
        if (regexPattern.trim().equals("")) {
            regexPattern = ".*";
        }
        this.setExpression(regexPattern);
        this.setSelectorType(Type.regex);
        this.setMulti(ext.multi());
        this.setSource(Source.Url);
        this.setNotNull(ext.notNull());
    }

    public Extract(ComboExtract ext) {
        ExtractBy[] extractBies = ext.value();
        switch (ext.op()) {
        case And:
            this.selector = new AndSelector(getSelectors(extractBies));
            break;
        case Or:
            this.selector = new OrSelector(getSelectors(extractBies));
            break;
        default:
            this.selector = new AndSelector(getSelectors(extractBies));
        }
        this.setSelectorType(Type.regex);
        this.setMulti(ext.multi());
        this.setSource(Source.Url);
        this.setNotNull(ext.notNull());
    }

    public String getExpression() {
        return expression;
    }

    @XmlAttribute(name = "expression")
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Type getSelectorType() {
        return type;
    }

    @XmlAttribute(name = "type")
    public void setSelectorType(Type type) {
        this.type = type;
    }

    public boolean isNotNull() {
        return notNull;
    }

    @XmlAttribute(name = "notnull")
    public void setNotNull(boolean notnull) {
        this.notNull = notnull;
    }

    public Source getSource() {
        if (source == Source.SelectedHtml) {
            source = Source.Html;
        }
        return source;
    }

    @XmlAttribute(name = "source")
    public void setSource(Source source) {
        this.source = source;
    }

    public boolean isMulti() {
        return multi;
    }

    @XmlAttribute(name = "multi")
    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public Selector getSelector() {
        if (null != selector) {
            return selector;
        }
        source = source == Source.SelectedHtml ? Source.Html : source;
        // 匹配&&构造AndSelector选择器
        List<Selector> selectors = getSelector("&&", expression);
        if (!selectors.isEmpty()) {
            selector = new AndSelector(selectors);
            return selector;
        }
        // 匹配||构造OrSelector选择器
        selectors = getSelector("\\|\\|", expression);
        if (!selectors.isEmpty()) {
            selector = new OrSelector(selectors);
            return selector;
        }
        // 非逻辑选择器
        selector = getSelector(source, type, expression);
        return selector;
    }

    /**
     * split expr to mutl selector
     * 
     * @param regex
     *            String
     * @param expr
     *            regex
     * @return List<Selector>
     */
    private List<Selector> getSelector(String regex, String expr) {
        List<Selector> selectors = new ArrayList<Selector>();
        if (expr.matches("^.*?\\s" + regex + "\\s.*?$")) {
            String[] strs = expr.split("\\s" + regex + "\\s");
            for (String str : strs) {
                selectors.add(getSelector(source, type, str.trim()));
            }
        }
        return selectors;
    }

    /**
     * get selector for source
     * 
     * @param origin
     *            Source
     * @param tmpSelectorType
     *            SelectorType
     * @param expr
     *            String
     * @return Selector
     */
    private Selector getSelector(Source origin, Type tmpSelectorType, String expr) {
        if (origin == Source.Url) {
            if (StringUtils.isBlank(expr)) {
                expr = ".*";
            }
            if (tmpSelectorType.equals(Type.mixe)
                    && !expr.toLowerCase(Locale.getDefault()).matches("^(?:xpath|css|json|regex|replace|filter).*$")) {
                return new RegexSelector(expr);
            }
        }
        return getSelector(tmpSelectorType, expr);
    }

    /**
     * get selector by SelectorType
     * 
     * @param tmpSelectorType
     *            SelectorType
     * @param value
     *            String
     * @return Selector
     */
    private Selector getSelector(Type tmpSelectorType, String value) {
        Selector select;
        switch (tmpSelectorType) {
        case css:
            select = new CssSelector(value);
            break;
        case regex:
            select = new RegexSelector(value);
            break;
        case xpath:
            select = new XpathSelector(value);
            break;
        case json:
            select = new JsonPathSelector(value);
            break;
        case filter:
            select = new FilterSelector(value);
            break;
        case mixe:
        default:
            select = new MixeSelector(value);
        }
        return select;
    }

    /**
     * get selector by ExtractBy[]
     * 
     * @param extractBies
     *            ExtractBy[]
     * @return List
     */
    public List<Selector> getSelectors(ExtractBy[] extractBies) {
        List<Selector> selectors = new ArrayList<Selector>();
        if (extractBies == null) {
            return selectors;
        }
        for (ExtractBy extractBy : extractBies) {
            selectors.add(new Extract(extractBy).getSelector());
        }
        return selectors;
    }

    /**
     * execute selector
     * 
     * @param content
     *            String
     * @return Object
     */
    public Object execute(String content) {
        if (null != content) {
            return isMulti() ? getSelector().selectList(content) : getSelector().select(content);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Extract [expression=" + expression + ", type=" + type + ", notNull=" + notNull + ", source=" + source
                + ", multi=" + multi + ", selector=" + selector + "]";
    }

}
