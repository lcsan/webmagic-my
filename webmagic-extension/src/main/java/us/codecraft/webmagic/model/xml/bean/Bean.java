package us.codecraft.webmagic.model.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Bean {

    private static final int HASH_CODE = 31;
    // bean的name，模板唯一标识。用于pipelie中bean.getBeanName()判断唯一性。
    private String name;

    // 是否子模板
    private boolean leaf = false;

    // Field列表
    private List<Field> fields;

    // After方法
    private After after;

    private String clazz;

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    @XmlAttribute(name = "clazz")
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<Field> getFields() {
        return fields;
    }

    @XmlElement(name = "field")
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public After getAfter() {
        return after;
    }

    @XmlElement(name = "after")
    public void setAfter(After after) {
        this.after = after;
    }

    public boolean isLeaf() {
        return leaf;
    }

    @XmlAttribute(name = "leaf")
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public BeanModel getBeanModel() {
        return new BeanModel(name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASH_CODE * result + (fields != null ? fields.hashCode() : 0);
        result = HASH_CODE * result + (after != null ? after.hashCode() : 0);
        result = HASH_CODE * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bean bean = (Bean) obj;
        if (name != null ? !name.equals(bean.getName()) : bean.getName() != null) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Bean [name=" + name + ", leaf=" + leaf + ", fields=" + fields + ", after=" + after + "]";
    }

}
