package org.webmagic.xml;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class GroovyPageModelPipeline implements PageModelPipeline<Object> {

    private Invocable inv;

    public GroovyPageModelPipeline() {
        super();
    }

    public GroovyPageModelPipeline(String source) {
        super();
        if (StringUtils.isNotBlank(source)) {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            try {
                engine.eval(source);
            } catch (ScriptException e) {
                e.printStackTrace();
            }
            inv = (Invocable) engine;
        }
    }

    @Override
    public void process(Object t, Task task) {
        if (null != inv) {
            try {
                inv.invokeFunction("pipeline", t, task);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(JSON.toJSONString(t));
        }
    }

}
