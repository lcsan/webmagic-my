package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.plugin.cron4j.Cron4jPlugin;

public class Task {

    private String cron;

    private Cron4jPlugin cp;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String getCron() {
        return cron;
    }

    @XmlValue
    public void setCron(String cron) {
        this.cron = cron;
    }

    public void start(Runnable spider) {
        if (cp == null) {
            cp = new Cron4jPlugin();
            cp.addTask(cron, spider);
        }
        cp.start();
    }

    public void stop() {
        if (cp != null) {
            cp.stop();
        }
    }

    @Override
    public String toString() {
        return "Task [cron=" + cron + "]";
    }

}
