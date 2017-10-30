package us.codecraft.webmagic.model.xml.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "models")
public class Models {
    // 模板bean
    private List<Model> modes;

    public List<Model> getModes() {
        return modes;
    }

    @XmlElement(name = "model")
    public void setModes(List<Model> modes) {
        this.modes = modes;
    }

    public void addModel(Models models) {
        modes.addAll(models.getModes());
    }

    public void addModel(Model model) {
        modes.add(model);
    }

    @Override
    public String toString() {
        return "Models [modes=" + modes + "]";
    }

}
