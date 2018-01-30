package us.codecraft.webmagic.model.pageextra;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.PageModelExtractor;
import us.codecraft.webmagic.model.xml.bean.Extract;
import us.codecraft.webmagic.model.xml.bean.Field;
import us.codecraft.webmagic.model.xml.bean.Helpurl;
import us.codecraft.webmagic.model.xml.bean.Tagurl;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selector;

public abstract class AbsPageModelExtractor implements PageModelExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbsPageModelExtractor.class);
    /**
     * 目标URL正则列表
     */
    List<Pattern> targetUrlPatterns = new ArrayList<Pattern>();

    /**
     * 目标URL正则选择器
     */
    Selector targetUrlRegionSelector;

    /**
     * 自动发现URL正则列表
     */
    List<Pattern> helpUrlPatterns = new ArrayList<Pattern>();

    /**
     * 自动发现URL正则选择器
     */
    Selector helpUrlRegionSelector;

    /**
     * 字段解析器
     */
    List<FieldParser> fieldExtra;

    /**
     * 类上Extract提取
     */
    Extract classExtract;

    /**
     * 模板id，bean id
     */
    String id;

    /**
     * 是否子模板
     */
    boolean leaf = false;

    /**
     * 模板列表持有
     */
    PageModelList pageModelList;

    /**
     * init a Object to save result
     * 
     * @return Object Object
     * @throws Exception
     *             Exception
     */
    public abstract Object initResultObj() throws Exception;

    /**
     * handle field to obj
     * 
     * @param obj
     *            result Object
     * @param field
     *            field
     * @throws Exception
     *             Exception
     */
    public abstract void handleField2ResultObj(Object obj, Field field) throws Exception;

    /**
     * handle page after process page
     * 
     * @param page
     *            page
     * @param obj
     *            obj
     * @throws Exception
     *             Exception
     */
    public abstract void handleAfter(Page page, Object obj) throws Exception;

    public AbsPageModelExtractor initTargetUrl(Tagurl tagurl) {
        // 发现和匹配标签
        if (tagurl == null || leaf) {
            targetUrlPatterns.add(Pattern.compile("(.*)"));
        } else {
            targetUrlPatterns = tagurl.getUrlPatterns();
            targetUrlRegionSelector = tagurl.getUrlRegionSelector();
        }
        return this;
    }

    public AbsPageModelExtractor initHelpUrl(Helpurl helpUrl) {
        // 辅助发现url
        if (helpUrl != null && !leaf) {
            helpUrlPatterns = helpUrl.getUrlPatterns();
            helpUrlRegionSelector = helpUrl.getUrlRegionSelector();
        }
        return this;
    }

    public AbsPageModelExtractor initClassExtract(Extract extract) {
        // 类上提取
        this.classExtract = extract;
        return this;
    }

    public AbsPageModelExtractor initField(List<Field> fields) {
        // 属性封装
        fieldExtra = new ArrayList<FieldParser>();
        for (Field field : fields) {
            fieldExtra.add(FieldParser.create(field, pageModelList));
        }
        return this;
    }

    public AbsPageModelExtractor initLeaf(boolean leaf) {
        // 类上提取
        this.leaf = leaf;
        return this;
    }

    public AbsPageModelExtractor initId(String id) {
        // 类上提取
        this.id = id;
        return this;
    }

    public AbsPageModelExtractor initPageModelList(PageModelList pageModelList) {
        this.pageModelList = pageModelList;
        return this;
    }

    @Override
    public void extractLinks(Page page) {
        extractLinks(page, helpUrlRegionSelector, helpUrlPatterns);
        extractLinks(page, targetUrlRegionSelector, targetUrlPatterns);
    }

    /**
     * tagurl\helpurl handle
     * 
     * @param page
     *            Page
     * @param urlRegionSelector
     *            Selector
     * @param urlPatterns
     *            List<Pattern>
     */
    private void extractLinks(Page page, Selector urlRegionSelector, List<Pattern> urlPatterns) {
        List<String> links;
        if (urlRegionSelector == null) {
            links = page.getHtml().links().all();
        } else {
            Selectable sel = page.getHtml().selectList(urlRegionSelector);
            if (null == sel) {
                links = new ArrayList<String>();
            } else {
                links = new Html(sel.toString()).links().all();
            }
        }
        for (String link : links) {
            for (Pattern targetUrlPattern : urlPatterns) {
                Matcher matcher = targetUrlPattern.matcher(link);
                if (matcher.find()) {
                    page.addTargetRequest(new Request(matcher.group(0)));
                }
            }
        }
    }

    @Override
    public Object process(Page page) {
        boolean mth = false;
        for (Pattern target : targetUrlPatterns) {
            if (target.matcher(page.getRequest().getUrl().trim()).matches()) {
                mth = true;
            }
        }
        if (!mth) {
            return null;
        }
        if (classExtract == null) {
            return processSingle(page, null, true);
        } else {
            Object obj = ExtractParser.create(classExtract).getSelectResult(page);
            if (obj instanceof List) {
                List<Object> os = new ArrayList<Object>();

                List<String> list = (List<String>) obj;
                for (String s : list) {
                    Object o = processSingle(page, s, false);
                    if (o != null) {
                        os.add(o);
                    }
                }
                return os;
            } else {
                return processSingle(page, (String) obj, false);
            }
        }
    }

    /**
     * process single model
     * 
     * @param page
     *            Page
     * @param html
     *            String
     * @param isRaw
     *            boolean
     * @return Object Object
     */
    private Object processSingle(Page page, String html, boolean isRaw) {
        Object obj = null;
        try {
            obj = initResultObj();
            Map<String, Object> extras = new HashMap<String, Object>();
            List<Request> requests = new ArrayList<Request>();

            for (FieldParser fieldExtractor : fieldExtra) {
                if (fieldExtractor.selectSource(obj, page, html, isRaw)) {
                    Field field = fieldExtractor.getField();
                    Object value = field.getValue();
                    // 填充结果值
                    if (field.isSaveflag()) {
                        handleField2ResultObj(obj, field);
                    }
                    // 处理url发现结果
                    if (field.isFoundflag()) {
                        if (value instanceof List) {
                            List<String> lt = (List<String>) value;
                            for (String url : lt) {

                                if (StringUtils.isNotBlank(url)) {
                                    requests.add(new Request(url));
                                }
                            }
                        } else {
                            String url = String.valueOf(value);
                            if (StringUtils.isNotBlank(url)) {
                                requests.add(new Request(url));
                            }
                        }
                    }
                    // 处理参数传递结果
                    if (field.isTransmitflag()) {
                        extras.put(field.getName(), value);
                    }
                } else {
                    return null;
                }
            }
            // 页面自动规则发现的url传递
            if (!requests.isEmpty()) {
                for (Request request : requests) {
                    if (!extras.isEmpty()) {
                        request.setExtras(extras);
                    }
                    page.addTargetRequest(request);
                }
            }
            handleAfter(page, obj);
        } catch (IllegalAccessException e) {
            LOGGER.error("process single page error!", e.getMessage());
        } catch (InvocationTargetException e) {
            LOGGER.error("process single page error!", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("process single page error!", e.getMessage());
        }
        return obj;
    }

    @Override
    public void parsePage(Page page) {
        // url发现
        extractLinks(page);
        // 模板解析
        Object process = process(page);
        if (process == null || (process instanceof List && ((List) process).size() == 0)) {
            return;
        }
        page.putField(id, process);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean isLeaf() {
        return leaf;
    }
}
