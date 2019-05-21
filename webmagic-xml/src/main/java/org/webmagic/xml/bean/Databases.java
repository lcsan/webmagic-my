package org.webmagic.xml.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Databases {

    private List<Database> database = new ArrayList<Database>();

    public List<Database> getDatabase() {
        return database;
    }

    @XmlElement(name = "database")
    public void setDatabase(List<Database> database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "Databases [database=" + database + "]";
    }

}
