package us.codecraft.webmagic.model.pageextra;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.xml.bean.After;
import us.codecraft.webmagic.model.xml.bean.BeanModel;
import us.codecraft.webmagic.model.xml.bean.Field;
import us.codecraft.webmagic.model.xml.bean.Model;

public class XmlPageModelExtractor extends AbsPageModelExtractor {

    /**
     * 持有的模板
     */
    private Model model;

    public static XmlPageModelExtractor create(Model model, PageModelList pglist) {
        XmlPageModelExtractor pageModelExtractor = new XmlPageModelExtractor();
        pageModelExtractor.init(model, pglist);
        return pageModelExtractor;
    }

    public static XmlPageModelExtractor create(Model model) {
        return create(model, null);
    }

    /**
     * init
     * 
     * @param tempModel
     *            Model
     * @param pageModelList
     *            PageModelList
     */
    private void init(Model tempModel, PageModelList pageModelList) {
        this.model = tempModel;
        initId(model.getBean().getName());
        initLeaf(model.getBean().isLeaf());
        initPageModelList(pageModelList);
        initTargetUrl(model.getTagurl());
        initHelpUrl(model.getHelpurl());
        initClassExtract(model.getExtract());
        initField(model.getBean().getFields());
    }

    @Override
    public Object initResultObj() {
        return model.getBean().getBeanModel();
    }

    @Override
    public void handleField2ResultObj(Object obj, Field field) {
        BeanModel beanModel = (BeanModel) obj;
        beanModel.put(field.getName(), field.getValue());
    }

    @Override
    public void handleAfter(Page page, Object obj) throws Exception {
        After after = model.getBean().getAfter();
        if (null != after) {
            after.invokeFunction(page, obj);
        }
    }

}
