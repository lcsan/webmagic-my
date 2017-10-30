package us.codecraft.webmagic.model.xml.bean;

import javax.xml.bind.annotation.XmlElement;

public class Model implements Cloneable {
    private static final int HASH_CODE = 31;
    // 模板bean
    private Bean bean;

    // 抽取规则
    private Extract extract;

    // url 匹配和发现规则
    private Tagurl tagurl;

    // url 辅助发现规则
    private Helpurl helpurl;

    public Bean getBean() {
        return bean;
    }

    @XmlElement(name = "bean")
    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public Extract getExtract() {
        return extract;
    }

    @XmlElement(name = "extract")
    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public Tagurl getTagurl() {
        return tagurl;
    }

    @XmlElement(name = "tagurl")
    public void setTagurl(Tagurl tagurl) {
        this.tagurl = tagurl;
    }

    public Helpurl getHelpurl() {
        return helpurl;
    }

    @XmlElement(name = "helpurl")
    public void setHelpurl(Helpurl helpurl) {
        this.helpurl = helpurl;
    }

    @Override
    public int hashCode() {
        int result = bean != null ? bean.hashCode() : 0;
        result = HASH_CODE * result + (extract != null ? extract.hashCode() : 0);
        result = HASH_CODE * result + (helpurl != null ? helpurl.hashCode() : 0);
        result = HASH_CODE * result + (tagurl != null ? tagurl.hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Model model = (Model) obj;
        if (bean != null ? !bean.equals(model.getBean()) : model.getBean() != null) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Model [bean=" + bean + ", extract=" + extract + ", tagurl=" + tagurl + ", helpurl=" + helpurl + "]";
    }

}
