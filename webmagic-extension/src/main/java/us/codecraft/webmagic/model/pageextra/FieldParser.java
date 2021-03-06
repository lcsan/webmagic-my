package us.codecraft.webmagic.model.pageextra;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.PageModelExtractor;
import us.codecraft.webmagic.model.formatter.ObjectFormatter;
import us.codecraft.webmagic.model.xml.bean.Field;
import us.codecraft.webmagic.model.xml.bean.Format;

public class FieldParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldParser.class);

    private Field field;

    private ExtractParser xmlExtractParser;

    // 子模板解析工具类
    private PageModelList pageModelList;

    // private String url;

    private FieldParser(Field field, PageModelList pageModelList) {
        this.field = field;
        this.pageModelList = pageModelList;
        this.xmlExtractParser = ExtractParser.create(field.getExtract());
    }

    public static FieldParser create(Field field, PageModelList pageModelList) {
        return new FieldParser(field, pageModelList);
    }

    /**
     * set field value
     * 
     * @param value
     *            result
     * @throws IllegalAccessException
     *             IllegalAccessException
     * @throws InvocationTargetException
     *             InvocationTargetException
     */
    public Object setField(Page page, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value == null) {
            return null;
        }
        // 子模板抽取
        return executLeaf(page, value);
        // field.setValue(value);
    }

    /**
     * parse leaf model
     * 
     * @param value
     *            Object
     * @return Object
     */
    private Object executLeaf(Page page, Object value) {
        // 子模板抽取
        PageModelExtractor pageModelExtractor = null;

        if (null == field.getLeafid()) {
            return value;
        }
        if (null != pageModelList) {
            pageModelExtractor = pageModelList.getPageModelExtractor(field.getLeafid());
        }
        if (null != pageModelExtractor) {
            return pageModelExtractor.process(getPage(page, value.toString()));
        }
        return value;
    }

    /**
     * 挨个解析Field
     * 
     * @param o
     *            用于脚本对象内取值
     * @param page
     *            待解析页面
     * @param html
     *            直接解析的文本
     * @param isRaw
     *            是否直接解析的文本
     * @return [boolean error,Object value]
     * @throws Exception
     *             异常
     */
    public List<Object> selectSource(Object o, Page page, String html, boolean isRaw) throws Exception {

        List<Object> list = new ArrayList<Object>();
        if (null == field.getExtract()) {
            list.add(true);
            list.add(null);
            return list;
        }
        // 选择器结果解析
        Object value = xmlExtractParser.getSelectResult(page, html, isRaw);
        // 设置了为null的则直接退出解析
        if (value == null) {
            if (field.getExtract().isNotNull()) {
                LOGGER.error("Value of field cont not be null .[url:{} field:{}]", page.getUrl(), field.getName());
                list.add(false);
                list.add(null);
                return list;
            }
            list.add(true);
            list.add(null);
            return list;
        }
        Format format = field.getFormat();
        // xml全局format格式化
        if (null != format) {
            value = executeFormat(o, value, format);
        }
        // 格式化解析
        value = getFormatterResut(value);
        // 解析结果赋值
        value = setField(page, value);
        list.add(true);
        list.add(value);
        return list;
    }

    /**
     * parse format
     * 
     * @param beanModel
     *            result Object
     * @param value
     *            Object
     * @param format
     *            Format
     * @return Object
     */
    @SuppressWarnings("unchecked")
    private Object executeFormat(Object beanModel, Object value, Format format) {
        if (StringUtils.isBlank(format.getExpression()) || null == format.getArges()) {
            return value;
        }
        List<String> list = format.getArges();
        List<Object> res = new ArrayList<Object>();
        // this标识位
        int flag = -1;
        for (int i = 0, j = list.size(); i < j; i++) {
            String key = list.get(i);
            if ("#{this}".equals(key)) {
                flag = i;
            }
            res.add(getBeanValue(beanModel, key, value));
        }
        if (List.class.isAssignableFrom(value.getClass())) {
            List<String> result = new ArrayList<String>();
            List<String> objects = (List<String>) value;
            for (String string : objects) {
                if (flag > -1) {
                    res.set(flag, string);
                }
                result.add(MessageFormat.format(format.getExpression(), res.toArray(new Object[] {})));
            }
            return result;
        } else {
            return MessageFormat.format(format.getExpression(), res.toArray(new Object[] {}));
        }
    }

    /**
     * get value for Object
     * 
     * @param beanModel
     *            Object
     * @param key
     *            String
     * @param value
     *            Object
     * @return Object
     */
    private Object getBeanValue(Object beanModel, String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        key = key.trim();
        if (key.matches("^#\\{.*?\\}$")) {
            key = key.replaceAll("^#\\{(.*?)\\}$", "$1");
            if ("this".equalsIgnoreCase(key)) {
                return value;
            }
            return (null == beanModel) ? "" : beanModel;
        }
        return key;
    }

    /**
     * parse ObjectFormatter
     * 
     * @param value
     *            Object
     * @return Object
     * @throws Exception
     *             Exception
     */
    @SuppressWarnings("unchecked")
    private Object getFormatterResut(Object value) throws Exception {
        ObjectFormatter<?> objectFormatter = field.getObjectFormatter();
        if (objectFormatter != null) {
            if (field.isMulti()) {
                value = convert((List<String>) value, objectFormatter);
            } else {
                value = convert((String) value, objectFormatter);
            }
        }
        return value;
    }

    /**
     * parse ObjectFormatter
     * 
     * @param value
     *            String
     * @param objFormatter
     *            ObjectFormatter
     * @return Object
     * @throws Exception
     *             Exception
     */
    private Object convert(String value, ObjectFormatter<?> objFormatter) throws Exception {
        try {
            if (null != objFormatter && null != value && !"".equals(value)) {
                Object format = objFormatter.format(value);
                return format;
            }
        } catch (ClassCastException e) {
            LOGGER.error("Format Error, msg:{}", e.getMessage());
        }
        return null;
    }

    /**
     * parse ObjectFormatter by list value
     * 
     * @param values
     *            List<String>
     * @param objFormatter
     *            ObjectFormatter
     * @return Object
     * @throws Exception
     *             Exception
     */
    private Object convert(List<String> values, ObjectFormatter<?> objFormatter) throws Exception {
        if (null != values) {
            List<Object> objects = new ArrayList<Object>();
            for (String value : values) {
                Object converted = convert(value, objFormatter);
                if (converted != null) {
                    objects.add(converted);
                }
            }
            return objects;
        }
        return null;
    }

    /**
     * create new Page by value
     * 
     * @param value
     *            String
     * @return Page
     */
    private Page getPage(Page pg, String value) {
        // 理论上这些属性应该是只读的，所以这里弱引用，如果存在改值情况，这里则不能这么操作
        Page page = new Page();
        page.setRawText(value);
        page.setRequest(pg.getRequest());
        page.setUrl(pg.getUrl());
        return page;
    }

    public Field getField() {
        return field;
    }

}
