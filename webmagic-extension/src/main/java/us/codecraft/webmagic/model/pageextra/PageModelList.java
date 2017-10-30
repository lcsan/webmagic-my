package us.codecraft.webmagic.model.pageextra;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.model.PageModelExtractor;
import us.codecraft.webmagic.model.xml.bean.Model;

public class PageModelList {

    private List<PageModelExtractor> pageModelList = new ArrayList<PageModelExtractor>();

    private PageModelList(Class<?>... clazzs) {
        for (Class<?> clazz : clazzs) {
            pageModelList.add(AnnotationPageModelExtractor.create(clazz, this));
        }
    }

    private PageModelList(Model... models) {
        for (Model model : models) {
            pageModelList.add(XmlPageModelExtractor.create(model, this));
        }
    }

    public static PageModelList create(Model... models) {
        if (models == null || models.length == 0) {
            return null;
        }
        return new PageModelList(models);
    }

    public static PageModelList create(List<Model> models) {
        if (models == null || models.isEmpty()) {
            return null;
        }
        return new PageModelList(models.toArray(new Model[] {}));
    }

    public static PageModelList create(Class<?>... clazzs) {
        if (clazzs == null || clazzs.length == 0) {
            return null;
        }
        return new PageModelList(clazzs);
    }

    public PageModelList addPageModel(Class<?> clazz) {
        AnnotationPageModelExtractor pageModelExtractor = AnnotationPageModelExtractor.create(clazz, this);
        pageModelList.add(pageModelExtractor);
        return this;
    }

    public PageModelList addPageModel(Model model) {
        XmlPageModelExtractor pageModelExtractor = XmlPageModelExtractor.create(model, this);
        pageModelList.add(pageModelExtractor);
        return this;
    }

    public List<PageModelExtractor> getPageModelList() {
        return pageModelList;
    }

    public PageModelExtractor getPageModelExtractor(String name) {
        for (PageModelExtractor pageModelExtractor : pageModelList) {
            if (pageModelExtractor.id().equals(name)) {
                return pageModelExtractor;
            }
        }
        return null;
    }
}
