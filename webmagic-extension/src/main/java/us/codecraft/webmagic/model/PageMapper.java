package us.codecraft.webmagic.model;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.pageextra.PageModelList;

/**
 * @author code4crafer@gmail.com
 * @since 0.5.2
 */
public class PageMapper<T> {

	private Class<T> clazz;

	private PageModelExtractor pageModelExtractor;

	public PageMapper(Class<T> clazz) {
		this.clazz = clazz;
		this.pageModelExtractor = PageModelList.create(clazz).getPageModelList().get(0);
	}

	public T get(Page page) {
		return (T) pageModelExtractor.process(page);
	}

	public List<T> getAll(Page page) {
		return (List<T>) pageModelExtractor.process(page);
	}
}
