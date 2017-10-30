package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MixeSelector implements Selector {

    private static final String METHOD_REGEX = "((?:xpath|regex|css|json|replace|filter)\\(['\"].*?['\"\\d]\\))\\.";

    private static final String HEADER_REGEX = "\\(.*?$";

    private static final String CONTENT_REGEX = "^\\w+\\(\\s*['\"](.*?)['\"]\\s*\\)$";

    private static final String CSS_REGEX = "^\\w+\\(\\s*['\"](.*?)['\"]\\s*,\\s*['\"](.*?)['\"]\\s*\\)$";

    private static final String REGEX_REGEX = "^\\w+\\(\\s*['\"](.*?)['\"]\\s*,(\\s*[\\d,\\s]+)\\)$";

    private static final String REGEX_ARGS_REGEX = "(\\d+)";

    private String expression;

    private List<Selector> selectors = new ArrayList<Selector>();

    public MixeSelector(String expression) {
        super();
        this.expression = expression;
        init();
    }

    public MixeSelector(Selector... selectors) {
        for (Selector selector : selectors) {
            this.selectors.add(selector);
        }
    }

    public MixeSelector(List<Selector> selectors) {
        this.selectors = selectors;
    }

    private void init() {
        if (expression.toLowerCase(Locale.getDefault()).matches("^(?:xpath|css|json|regex|replace|filter).*$")) {
            List<String> list = macher(METHOD_REGEX, expression + ".");
            for (String str : list) {
                selectors.add(split(str));
            }
        } else {
            selectors.add(new XpathSelector(expression));
        }
    }

    private Selector split(String expr) {
        String head = expr.replaceAll(HEADER_REGEX, "").toLowerCase(Locale.getDefault());
        Selector selector = null;
        switch (Type.valueOf(head).getCode()) {
        case 1:
            expr = expr.replaceAll(CONTENT_REGEX, "$1");
            selector = new XpathSelector(expr);
            break;
        case 2:
            List<String> list = macherStrs(CSS_REGEX, expr);
            if (list.size() > 1) {
                selector = new CssSelector(list.get(0), list.get(1));
            } else {
                expr = expr.replaceAll(CONTENT_REGEX, "$1");
                selector = new CssSelector(expr);
            }
            break;
        case 3:
            expr = expr.replaceAll(CONTENT_REGEX, "$1");
            selector = new JsonPathSelector(expr);
            break;
        case 4:
            List<String> lt = macherStrs(REGEX_REGEX, expr);
            if (lt.size() > 1) {
                selector = new RegexSelector(lt.get(0), macherInts(REGEX_ARGS_REGEX, lt.get(1))[0]);
            } else {
                expr = expr.replaceAll(CONTENT_REGEX, "$1");
                selector = new RegexSelector(expr);
            }
            break;
        case 5:
            List<String> ltr = macherStrs(CSS_REGEX, expr);
            if (ltr.size() > 1) {
                selector = new ReplaceSelector(ltr.get(0), ltr.get(1));
            }
            break;
        case 6:
            expr = expr.replaceAll(CONTENT_REGEX, "$1");
            selector = new FilterSelector(expr);
            break;
        default:
            selector = new XpathSelector(expr);
            break;
        }
        return selector;
    }

    private List<String> macher(String reg, String data) {
        List<String> list = new ArrayList<String>();
        if (null != data) {
            // 不区分大小写
            Matcher m = Pattern.compile(reg, Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(data);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }

    private List<String> macherStrs(String reg, String data) {
        List<String> list = new ArrayList<String>();
        if (null != data) {
            Matcher m = Pattern.compile(reg).matcher(data);
            if (m.find()) {
                for (int i = 1, j = m.groupCount(); i <= j; i++) {
                    list.add(m.group(i));
                }
            }
        }
        return list;
    }

    private Integer[] macherInts(String reg, String data) {
        List<String> list = macher(reg, data);
        List<Integer> re = new ArrayList<Integer>();
        for (String string : list) {
            re.add(Integer.valueOf(string));
        }
        return re.toArray(new Integer[] {});
    }

    @Override
    public String select(String text) {
        // 这里需要特殊处理，因为复合选择在未获取结果之前，应该完全按照所有表达式获取结果。不能先进行过滤。
        List<String> list = selectList(text);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> selectList(String text) {
        List<String> list = new ArrayList<String>();
        for (int i = 0, j = selectors.size(); i < j; i++) {
            Selector selector = selectors.get(i);
            if (i == 0) {
                list = selector.selectList(text);
            } else {
                List<String> re = new ArrayList<String>();
                for (String string : list) {
                    re.addAll(selector.selectList(string));
                }
                list = re;
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "MixeSelector [expression=" + expression + ", selectors=" + selectors + "]";
    }

}
