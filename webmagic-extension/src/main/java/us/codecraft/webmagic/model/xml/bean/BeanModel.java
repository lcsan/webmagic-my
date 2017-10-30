package us.codecraft.webmagic.model.xml.bean;

import java.util.HashMap;
import java.util.Map;

public class BeanModel extends HashMap<String, Object> {

    private static final long serialVersionUID = 3913548290141230494L;

    private Map<String, Object> findUrlFieldMap = new HashMap<String, Object>();

    private Map<String, Object> resultFieldMap = new HashMap<String, Object>();

    public BeanModel(String beanName) {
        super();
        this.put("beanName", beanName);
    }

    public void setFindUrlFieldMap(Map<String, Object> findUrlFieldMap) {
        this.findUrlFieldMap = findUrlFieldMap;
    }

    public void setResultFieldMap(Map<String, Object> resultFieldMap) {
        this.resultFieldMap = resultFieldMap;
    }

    /**
     * add addTagRequest url
     * 
     * @param key
     *            String
     * @param value
     *            Object
     */
    public void putFindUrlField(String key, Object value) {
        findUrlFieldMap.put(key, value);
    }

    /**
     * add putExtra field
     * 
     * @param key
     *            String
     * @param value
     *            Object
     */
    public void putResultField(String key, Object value) {
        resultFieldMap.put(key, value);
    }

    public Map<String, Object> getFindUrlFieldMap() {
        return findUrlFieldMap;
    }

    public Map<String, Object> getResultFieldMap() {
        return resultFieldMap;
    }

    public Object getResultValue(String resultKey) {
        return resultFieldMap.get(resultKey);
    }

    public Object getUrlValue(String urlKey) {
        return findUrlFieldMap.get(urlKey);
    }
}
