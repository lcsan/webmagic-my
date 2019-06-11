package com.yao9;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.haha56.net/a/\\d+/\\d+/\\d+.html")
@HelpUrl("http://www.haha56.net/*")
public class Haha56 {

    @ExtractByUrl("(.*)")
    private String id;

    @ExtractBy("//div[@class='title']/text()")
    private String title;

    @ExtractBy("xpath('//div[@class='content']/html()').filter('<h3>').split('<h3>').replace('　*<.*?>　*','') || xpath('//div[@class='content']/allText()').split('【?\\d+】?[、.，,\\s　]+\\s*')")
    private List<String> content;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContent() {
        return content;
    }

    public static void main(String[] args) {
        OOSpider.create(
                Site.me().setTimeOut(10000).setSleepTime(200).setRetryTimes(3).setUseGzip(true).setUserAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"),
                new CSVPageModelPipeline(), Haha56.class)
                // .test("http://www.haha56.net/a/2012/11/10596.html",
                // "http://www.haha56.net/a/2018/03/17281.html");
                .addUrl("http://www.haha56.net/main/xy_joke/", "http://www.haha56.net/",
                        "http://www.haha56.net/joke_top.htm")
                .thread(10).start();
    }

}
