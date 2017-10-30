package us.codecraft.webmagic.model.pageextra;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.xml.bean.Extract;

public class ExtractParser {

    private Extract extract;

    private ExtractParser(Extract extract) {
        this.extract = extract;
    }

    private ExtractParser(ExtractBy extractBy) {
        this.extract = new Extract(extractBy);
    }

    public static ExtractParser create(Extract extract) {
        return new ExtractParser(extract);
    }

    public boolean isMulti() {
        return extract.isMulti();
    }

    /**
     * execut selector for source
     * 
     * @param page
     *            Page
     * @param html
     *            String
     * @param isRaw
     *            boolean
     * @return Object
     */
    public Object getSelectResult(Page page, String html, boolean isRaw) {
        Object value;
        switch (extract.getSource()) {
        case RawText:
        case RawHtml:
            value = getSelectResult(page);
            break;
        case Html:
            value = selectResult(page, html, isRaw);
            break;
        case Url:
            value = selectResult(page.getUrl().get());
            break;
        default:
            if (null != html) {
                value = selectResult(html);
            } else {
                value = getSelectResult(page);
            }
        }
        return value;
    }

    public Object getSelectResult(Page page) {
        return selectResult(page.getRawText());
    }

    private Object selectResult(Page page, String html, boolean isRaw) {
        return isRaw ? getSelectResult(page) : selectResult(html);
    }

    private Object selectResult(String content) {
        return extract.execute(content);
    }

}
