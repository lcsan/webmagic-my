package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author code4crafter@gmail.com
 * @since 0.3.0
 */
public abstract class BaseElementSelector implements Selector, ElementSelector {

	@Override
	public String select(String text) {
		if (text != null) {
			return select(parse(text));
		}
		return null;
	}

	@Override
	public List<String> selectList(String text) {
		if (text != null) {
			return selectList(parse(text));
		} else {
			return new ArrayList<String>();
		}
	}

	public Element selectElement(String text) {
		if (text != null) {
			return selectElement(parse(text));
		}
		return null;
	}

	public List<Element> selectElements(String text) {
		if (text != null) {
			return selectElements(parse(text));
		} else {
			return new ArrayList<Element>();
		}
	}

	/**
	 * 解决jsoup无table下tr\td\th\tbody无意义丢弃问题
	 * 
	 * @param html
	 * @return
	 */
	private Document parse(String html) {
		if (StringUtils.isNotBlank(html) && (html.startsWith("<tbody>") || html.startsWith("<tr>")
				|| html.startsWith("<th>") || html.startsWith("<td>"))) {
			html = "<table>" + html + "</table>";
		}
		return Jsoup.parse(html);
	}

	public abstract Element selectElement(Element element);

	public abstract List<Element> selectElements(Element element);

	public abstract boolean hasAttribute();

}
