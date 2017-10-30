package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterSelector implements Selector {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterSelector.class);

    private Pattern regex;

    public FilterSelector(String regexStr) {
        if (StringUtils.isBlank(regexStr)) {
            LOGGER.error("Regex can not be null or empty.");
            throw new IllegalArgumentException("Regex can not be null or empty.");
        }
        String str = "";
        if (!regexStr.startsWith("^")) {
            str += "^.*?";
        }
        str += regexStr;
        if (!regexStr.endsWith("$")) {
            str += ".*?$";
        }
        try {
            // 多行、大小写
            regex = Pattern.compile(str, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            LOGGER.error("Regex is illegal, [regex:{}, msg:{}]", regexStr, e.getMessage());
            throw new IllegalArgumentException("invalid regex", e);
        }
    }

    @Override
    public String select(String text) {
        if (regex.matcher(text).matches()) {
            return text;
        }
        return null;
    }

    @Override
    public List<String> selectList(String text) {
        List<String> list = new ArrayList<String>();
        if (regex.matcher(text).matches()) {
            list.add(text);
        }
        return list;
    }

    @Override
    public String toString() {
        return "FilterSelector [regex=" + regex + "]";
    }

}
