package us.codecraft.webmagic.model.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Format {

    // 规则表达式
    private String expression;

    // 传参 #{..}标识object内部取field值，#{this}代表取当前field值
    private List<String> arges;

    public String getExpression() {
        return expression;
    }

    @XmlAttribute(name = "expression")
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getArges() {
        return arges;
    }

    @XmlElement(name = "param")
    public void setArges(List<String> arges) {
        this.arges = arges;
    }

    @Override
    public String toString() {
        return "Format [expression=" + expression + ", arges=" + arges + "]";
    }

}
