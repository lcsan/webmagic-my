package org.webmagic.xml.bean;

import java.util.HashMap;
import java.util.Map;

public class Common {

    public final static Map<String, Integer> DM_MAP = new HashMap<String, Integer>();
    static {
        DM_MAP.put("mysql", 1);
        DM_MAP.put("oracle", 2);
        DM_MAP.put("pgsql", 3);
        DM_MAP.put("sqlserver", 4);
        DM_MAP.put("sqlite3", 5);
        DM_MAP.put("ansi", 6);
    }
}
