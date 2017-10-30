package us.codecraft.webmagic.model;

import us.codecraft.webmagic.Page;

public interface PageModelExtractor {
    /**
     * parse model field
     * 
     * @param page
     *            Page
     * @return Object
     */
    public Object process(Page page);

    /**
     * parse model to page
     * 
     * @param page
     *            Page
     */
    public void parsePage(Page page);

    /**
     * url found
     * 
     * @param page
     *            Page
     */
    public void extractLinks(Page page);

    /**
     * model id
     * 
     * @return String
     */
    public String id();

    /**
     * is leaf
     * 
     * @return boolean
     */
    public boolean isLeaf();
}
