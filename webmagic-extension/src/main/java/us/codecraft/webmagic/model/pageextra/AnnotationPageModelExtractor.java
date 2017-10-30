package us.codecraft.webmagic.model.pageextra;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.Leaf;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.model.xml.bean.Extract;
import us.codecraft.webmagic.model.xml.bean.Helpurl;
import us.codecraft.webmagic.model.xml.bean.Tagurl;
import us.codecraft.webmagic.utils.ClassUtils;

/**
 * The main internal logic of page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class AnnotationPageModelExtractor extends AbsPageModelExtractor {

    private Class<?> clazz;

    public static AnnotationPageModelExtractor create(Class<?> clazz, PageModelList pageModelList) {
        AnnotationPageModelExtractor pageModelExtractor = new AnnotationPageModelExtractor();
        pageModelExtractor.init(clazz, pageModelList);
        return pageModelExtractor;
    }

    public static AnnotationPageModelExtractor create(Class<?> clazz) {
        return create(clazz, null);
    }

    private void init(Class<?> clazz, PageModelList pageModelList) {
        this.clazz = clazz;
        initId(clazz.getCanonicalName());
        initLeaf();
        initPageModelList(pageModelList);
        initClassExtractors();
        initField();
    }

    private void initClassExtractors() {
        Annotation annotation = clazz.getAnnotation(TargetUrl.class);
        if (annotation == null || leaf) {
            targetUrlPatterns.add(Pattern.compile(".*"));
        } else {
            TargetUrl targetUrl = (TargetUrl) annotation;
            Tagurl tagurl = new Tagurl(Arrays.asList(targetUrl.value()), targetUrl.sourceRegion());
            initTargetUrl(tagurl);
        }
        annotation = clazz.getAnnotation(HelpUrl.class);
        if (annotation != null) {
            HelpUrl helpUrl = (HelpUrl) annotation;
            Helpurl helpurl = new Helpurl(Arrays.asList(helpUrl.value()), helpUrl.sourceRegion());
            initHelpUrl(helpurl);
        }
        annotation = clazz.getAnnotation(ExtractBy.class);
        if (annotation != null) {
            ExtractBy extractBy = (ExtractBy) annotation;
            initClassExtract(new Extract(extractBy));
        }

    }

    private void initLeaf() {
        // Leaf
        Annotation annotation = clazz.getAnnotation(Leaf.class);
        if (annotation != null) {
            Leaf extractBy = (Leaf) annotation;
            initLeaf(extractBy.isLeaf());
        }
    }

    private void initField() {
        List<us.codecraft.webmagic.model.xml.bean.Field> fields = new ArrayList<us.codecraft.webmagic.model.xml.bean.Field>();
        for (Field field : ClassUtils.getFieldsIncludeSuperClass(clazz)) {
            field.setAccessible(true);
            fields.add(new us.codecraft.webmagic.model.xml.bean.Field(field));
        }
        initField(fields);
    }

    @Override
    public Object initResultObj() throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    @Override
    public void handleField2ResultObj(Object obj, us.codecraft.webmagic.model.xml.bean.Field field)
            throws IllegalArgumentException, IllegalAccessException {
        field.getField().set(obj, field.getValue());
    }

    @Override
    public void handleAfter(Page page, Object obj) throws Exception {
        if (AfterExtractor.class.isAssignableFrom(clazz)) {
            ((AfterExtractor) obj).afterProcess(page);
        }
    }

}
