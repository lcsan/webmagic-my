package com;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.selenium.PhantomjsDownloader;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.selector.Type;

@TargetUrl("https://forum.uibot.com.cn/")
public class TestPhantomjs {

    @ExtractBy(value = ".subject.break-all a", type = Type.css)
    private List<String> title;

    public List<String> getTitle() {
        return title;
    }

    public static void main(String[] args) {
        // PhantomjsDownloader dw = new PhantomjsDownloader();
        // Page page = dw.download(new Request("https://forum.uibot.com.cn/"),
        // new Task() {
        //
        // @Override
        // public String getUUID() {
        // return null;
        // }
        //
        // @Override
        // public Site getSite() {
        // return Site.me();
        // }
        // });
        // System.out.println(page.getRawText());
        // dw.shutdown();
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), TestPhantomjs.class)
                .setDownloader(new PhantomjsDownloader()).addUrl("https://forum.uibot.com.cn/").start();
    }

}
