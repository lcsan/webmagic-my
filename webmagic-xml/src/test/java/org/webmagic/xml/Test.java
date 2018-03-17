package org.webmagic.xml;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Test {

    public static void main(String[] args) {
        // List list = Xml2Models.create("crawler/test_model6.xml").getModes();
        // System.out.println(list);
        // Spider sp = OOSpider.create(Site.me(), new GroovyPageModelPipeline(),
        // "crawler/test_model6.xml")
        // .addUrl("http://www.iqiyi.com/lib/s_200002105.html").thread(20);
        // sp.run();
        // Spider test = Xml2Models.parse("test.xml", Spider.class);
        // test.run();
        // test.getSpider().run();
        // System.out.println(test.getSite().getSite());
        // System.out.println(test.getAny().get(0));
        final JedisPool jp = new JedisPool(new JedisPoolConfig(), "127.0.0.1");

        new Thread(new Runnable() {
            @Override
            public void run() {
                jp.getResource().subscribe(new NewsListener(), "aaa");
            }
        }).start();
        jp.getResource().publish("aaa", "hahaha是我");
        jp.getResource().publish("aaa", "hahaha是我1");
        jp.getResource().publish("aaa", "hahaha是我2");
        jp.getResource().publish("aaa", "hahaha是我3");
        jp.getResource().publish("aaa", "hahaha是我4");
    }

}
