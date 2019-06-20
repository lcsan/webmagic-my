package us.codecraft.webmagic.selector;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

/**
 * XPath selector based on Xsoup.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.3.0
 */
public class XpathSelector extends BaseElementSelector {

    private String xpathStr;

    public XpathSelector(String xpathStr) {
        this.xpathStr = xpathStr;
    }

    @Override
    public String select(Element element) {
        return JXDocument.create((Document) element).selOne(xpathStr).toString();
    }

    @Override
    public List<String> selectList(Element element) {
        List<Object> list = JXDocument.create((Document) element).sel(xpathStr);
        List<String> re = new ArrayList<String>();
        for (Object obj : list) {
            re.add(obj.toString());
        }
        return re;
    }

    @Override
    public Element selectElement(Element element) {
        return JXDocument.create((Document) element).selNOne(xpathStr).asElement();
    }

    @Override
    public List<Element> selectElements(Element element) {
        List<JXNode> list = JXDocument.create((Document) element).selN(xpathStr);
        List<Element> re = new ArrayList<Element>();
        for (JXNode node : list) {
            re.add(node.asElement());
        }
        return re;
    }

    @Override
    public boolean hasAttribute() {
        return false;
    }

}
