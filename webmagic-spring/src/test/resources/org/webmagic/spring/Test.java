package org.webmagic.spring;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;

public class Test {

    public static void main(String[] args) {
        // List list = Xml2Models.create("crawler/test_model6.xml").getModes();
        // System.out.println(list);
        Spider sp = OOSpider.create(Site.me(), new ConsolePageModelPipeline(), "crawler/test_model6.xml")
                .addUrl("http://www.iqiyi.com/lib/s_200002105.html");
        sp.run();
    }

}
