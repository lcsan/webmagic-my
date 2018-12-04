package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;

/**
 * JsonPath selector.<br>
 * Used to extract content from JSON.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.1
 */
public class JsonPathSelector implements Selector {

    private String jsonPathStr;

    private JsonPath jsonPath;

    public JsonPathSelector(String jsonPathStr) {
        this.jsonPathStr = jsonPathStr;
        this.jsonPath = JsonPath.compile(this.jsonPathStr);
    }

    private String realJson(String text) {
        if (!text.startsWith("{") && !text.startsWith("[")) {
            int a = text.indexOf("{");
            int b = text.indexOf("[");
            int start = Math.min(a, b);
            // 解决-1
            start = start == -1 ? (a > -1 ? a : (b > -1 ? b : 0)) : start;
            int end = Math.max(text.lastIndexOf("}"), text.lastIndexOf("]")) + 1;
            text = text.substring(start, end);
        }
        return text;
    }

    @Override
    public String select(String text) {
        text = realJson(text);
        Object object = jsonPath.read(text);
        if (object == null) {
            return null;
        }
        if (object instanceof List) {
            List<?> list = (List<?>) object;
            if (list != null && list.size() > 0) {
                return toString(list.iterator().next());
            }
        }
        return object.toString();
    }

    private String toString(Object object) {
        if (object instanceof Map) {
            return JSON.toJSONString(object);
        } else {
            return String.valueOf(object);
        }
    }

    @Override
    public List<String> selectList(String text) {
        List<String> list = new ArrayList<String>();
        if (null == text) {
            return list;
        }
        text = realJson(text);
        if ("".equals(text)) {
            return list;
        }

        Object object = jsonPath.read(text);
        if (object == null) {
            return list;
        }
        if (object instanceof List) {
            List<?> items = (List<?>) object;
            for (Object item : items) {
                list.add(toString(item));
            }
        } else {
            list.add(toString(object));
        }
        return list;
    }
}
