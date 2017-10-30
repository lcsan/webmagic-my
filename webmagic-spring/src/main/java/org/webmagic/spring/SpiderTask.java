package org.webmagic.spring;

import us.codecraft.webmagic.Spider;

public class SpiderTask {

    private Spider spider;

    private String[] urls;

    private int threadNum = 10;

    public SpiderTask(Spider spider, String[] urls, int threadNum) {
        super();
        this.spider = spider;
        this.urls = urls;
        this.threadNum = threadNum;
    }

    public void resetRun() {
        spider.addUrl(urls).thread(threadNum).run();
        spider.reset(true);
    }

    public void run() {
        spider.addUrl(urls).thread(threadNum).run();
    }
}
