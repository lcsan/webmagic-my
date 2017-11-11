package us.codecraft.webmagic.model;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.xml.Xml2Models;
import us.codecraft.webmagic.model.xml.bean.Model;
import us.codecraft.webmagic.model.xml.bean.Models;
import us.codecraft.webmagic.pipeline.CollectorPipeline;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * The spider for page model extractor.<br>
 * In webmagic, we call a POJO containing extract result as "page model". <br>
 * You can customize a crawler by write a page model with annotations. <br>
 * Such as:
 * 
 * <pre>
 * {@literal @}TargetUrl("http://my.oschina.net/flashsword/blog/\\d+")
 *  public class OschinaBlog{
 *
 *      {@literal @}ExtractBy("//title")
 *      private String title;
 *
 *      {@literal @}ExtractBy(value = "div.BlogContent",type = ExtractBy.Type.Css)
 *      private String content;
 *
 *      {@literal @}ExtractBy(value = "//div[@class='BlogTags']/a/text()", multi = true)
 *      private List&lt;String&gt; tags;
 * }
 * </pre>
 * 
 * And start the spider by:
 * 
 * <pre>
 *   OOSpider.create(Site.me().addStartUrl("http://my.oschina.net/flashsword/blog")
 *        ,new JsonFilePageModelPipeline(), OschinaBlog.class).run();
 * }
 * </pre>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class OOSpider<T> extends Spider {

	private ModelPageProcessor modelPageProcessor;

	private ModelPipeline modelPipeline;

	// private PageModelPipeline pageModelPipeline;

	private List<String> pageModelClasses = new ArrayList<String>();

	protected OOSpider(ModelPageProcessor modelPageProcessor) {
		super(modelPageProcessor);
		this.modelPageProcessor = modelPageProcessor;
	}

	public OOSpider(PageProcessor pageProcessor) {
		super(pageProcessor);
	}

	public OOSpider(Site site, PageModelPipeline pageModelPipeline, Class... pageModels) {
		this(ModelPageProcessor.create(site, pageModels));
		this.modelPipeline = new ModelPipeline();
		super.addPipeline(modelPipeline);
		for (Class pageModel : pageModels) {
			String key = pageModel.getCanonicalName();
			if (pageModelPipeline != null) {
				this.modelPipeline.put(key, pageModelPipeline);
			}
			pageModelClasses.add(key);
		}
	}

	public static OOSpider create(Site site, Class... pageModels) {
		return new OOSpider(site, null, pageModels);
	}

	public static OOSpider create(Site site, PageModelPipeline pageModelPipeline, Class... pageModels) {
		return new OOSpider(site, pageModelPipeline, pageModels);
	}

	public OOSpider addPageModel(PageModelPipeline pageModelPipeline, Class... pageModels) {
		for (Class pageModel : pageModels) {
			modelPageProcessor.addPageModel(pageModel);
			modelPipeline.put(pageModel.getCanonicalName(), pageModelPipeline);
		}
		return this;
	}

	public OOSpider(Site site, PageModelPipeline pageModelPipeline, String... paths) {
		this(site, pageModelPipeline, Xml2Models.create(paths));
	}

	public OOSpider(Site site, PageModelPipeline pageModelPipeline, Models models) {
		this(ModelPageProcessor.create(site, models));
		this.modelPipeline = new ModelPipeline();
		super.addPipeline(modelPipeline);
		List<Model> list = models.getModes();
		for (Model pageModel : list) {
			if (!pageModel.getBean().isLeaf()) {
				String key = pageModel.getBean().getName();
				if (pageModelPipeline != null) {
					this.modelPipeline.put(key, pageModelPipeline);
				}
				pageModelClasses.add(key);
			}
		}
	}

	public static OOSpider create(Site site, String... paths) {
		return new OOSpider(site, null, paths);
	}

	public static OOSpider create(Site site, PageModelPipeline pageModelPipeline, String... paths) {
		return new OOSpider(site, pageModelPipeline, paths);
	}

	public static OOSpider create(Site site, PageModelPipeline pageModelPipeline, Models models) {
		return new OOSpider(site, pageModelPipeline, models);
	}

	public OOSpider addPageModel(PageModelPipeline pageModelPipeline, String... paths) {
		List<Model> list = Xml2Models.create(paths).getModes();
		for (Model pageModel : list) {
			modelPageProcessor.addPageModel(pageModel);
			modelPipeline.put(pageModel.getBean().getName(), pageModelPipeline);
		}
		return this;
	}

	@Override
	protected CollectorPipeline getCollectorPipeline() {
		return new PageModelCollectorPipeline<T>(pageModelClasses.get(0));
	}

	public OOSpider setIsExtractLinks(boolean isExtractLinks) {
		modelPageProcessor.setExtractLinks(isExtractLinks);
		return this;
	}

}
