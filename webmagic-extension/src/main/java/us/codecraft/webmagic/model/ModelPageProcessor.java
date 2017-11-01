package us.codecraft.webmagic.model;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.pageextra.PageModelList;
import us.codecraft.webmagic.model.xml.bean.Model;
import us.codecraft.webmagic.model.xml.bean.Models;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * The extension to PageProcessor for page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class ModelPageProcessor implements PageProcessor {

    private Site site;

    private boolean extractLinks = true;

    private PageModelList pageModelList;

    private ModelPageProcessor(Site site, Class... clazzs) {
        this.site = site;
        this.pageModelList = PageModelList.create(clazzs);
    }

    private ModelPageProcessor(Site site, Model... models) {
        this.site = site;
        this.pageModelList = PageModelList.create(models);
    }

    public static ModelPageProcessor create(Site site, Class... clazzs) {
        return new ModelPageProcessor(site, clazzs);
    }

    public static ModelPageProcessor create(Site site, Models models) {
        return new ModelPageProcessor(site, models.getModes().toArray(new Model[] {}));
    }

    public ModelPageProcessor addPageModel(Class clazz) {
        pageModelList.addPageModel(clazz);
        return this;
    }

    public static ModelPageProcessor create(Site site, Model... models) {
        return new ModelPageProcessor(site, models);
    }

    public static ModelPageProcessor create(Site site, List<Model> models) {
        return new ModelPageProcessor(site, models.toArray(new Model[] {}));
    }

    public ModelPageProcessor addPageModel(Model model) {
        pageModelList.addPageModel(model);
        return this;
    }

    @Override
    public void process(Page page) {
        List<PageModelExtractor> pageModelExtractorList = pageModelList.getPageModelList();
        for (PageModelExtractor pageModelExtractor : pageModelExtractorList) {
            // 子模板不参与此处解析
            if (pageModelExtractor.isLeaf()) {
                continue;
            }
            pageModelExtractor.parsePage(page);
        }
        if (page.getResultItems().getAll().size() == 0) {
            page.getResultItems().setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public boolean isExtractLinks() {
        return extractLinks;
    }

    public void setExtractLinks(boolean extractLinks) {
        this.extractLinks = extractLinks;
    }
}
