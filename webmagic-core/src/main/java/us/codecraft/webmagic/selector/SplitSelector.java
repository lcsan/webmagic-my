package us.codecraft.webmagic.selector;

import java.util.Arrays;
import java.util.List;

/**
 * Replace selector.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class SplitSelector implements Selector {

    private String regexStr;

    public SplitSelector(String regexStr) {
        this.regexStr = regexStr;
    }

    @Override
    public String select(String text) {
        List<String> list = selectList(text);
        if (list.isEmpty()) {
            return text;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<String> selectList(String text) {
        return Arrays.asList(text.trim().split(regexStr));
    }

    @Override
    public String toString() {
        return regexStr;
    }

}
