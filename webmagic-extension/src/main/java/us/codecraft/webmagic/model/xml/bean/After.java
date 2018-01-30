package us.codecraft.webmagic.model.xml.bean;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.annotation.XmlValue;

public class After {

    private String source;

    private Invocable inv;

    public String getSource() {
        return source;
    }

    @XmlValue
    public void setSource(String source) {
        this.source = source;
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        try {
            engine.eval(source);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        inv = (Invocable) engine;
    }

    public Object invokeFunction(Object... params) throws NoSuchMethodException, ScriptException {
        if (null != inv) {
            return inv.invokeFunction("after", params);
        }
        return null;
    }

    @Override
    public String toString() {
        return "After [source=" + source + "]\r\n";
    }

}
