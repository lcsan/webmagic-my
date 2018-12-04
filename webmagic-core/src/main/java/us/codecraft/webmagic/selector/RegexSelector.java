package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

/**
 * Selector in regex.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class RegexSelector implements Selector {

    private String regexStr;

    private Pattern regex;

    private Integer[] group = { 1 };

    public RegexSelector(String regexStr, Integer... group) {
        this.compileRegex(regexStr);
        if (null != group) {
            this.group = group;
        }
    }

    private void compileRegex(String regexStr) {
        if (StringUtils.isBlank(regexStr)) {
            throw new IllegalArgumentException("regex must not be empty");
        }
        try {
            this.regex = Pattern.compile(regexStr, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            this.regexStr = regexStr;
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("invalid regex " + regexStr, e);
        }
    }

    /**
     * Create a RegexSelector. When there is no capture group, the value is set
     * to 0 else set to 1.
     * 
     * @param regexStr
     *            regexStr
     */
    public RegexSelector(String regexStr) {
        this.compileRegex(regexStr);
    }

    @Override
    public String select(String text) {
        return selectGroup(text).get(group[0]);
    }

    @Override
    public List<String> selectList(String text) {
        List<String> strings = new ArrayList<String>();
        List<RegexResult> results = selectGroupList(text);
        for (RegexResult result : results) {
            for (int gp : group) {
                strings.add(result.get(gp));
            }
        }
        return strings;
    }

    public RegexResult selectGroup(String text) {
        Matcher matcher = regex.matcher(text);
        if (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i++) {
                groups[i] = matcher.group(i);
            }
            return new RegexResult(groups);
        }
        return RegexResult.EMPTY_RESULT;
    }

    public List<RegexResult> selectGroupList(String text) {
        Matcher matcher = regex.matcher(text);
        List<RegexResult> resultList = new ArrayList<RegexResult>();
        while (matcher.find()) {
            String[] groups = new String[matcher.groupCount() + 1];
            for (int i = 0; i < groups.length; i++) {
                groups[i] = matcher.group(i);
            }
            resultList.add(new RegexResult(groups));
        }
        return resultList;
    }

    @Override
    public String toString() {
        return regexStr;
    }

}
