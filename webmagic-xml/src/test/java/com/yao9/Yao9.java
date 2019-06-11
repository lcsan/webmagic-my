package com.yao9;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.9yao.com/neihanduanzi/*")
@HelpUrl("http://www.9yao.com/neihanduanzi/index*")
public class Yao9 {

    @ExtractByUrl("(.*)")
    private String id;

    @ExtractBy("//h3[@class=\"boxHeader\"]/a/text()")
    private String title;

    @ExtractBy("//div[@style=\"position: relative;\"]/allText()")
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
                Site.me().setTimeOut(10000).setSleepTime(300).setRetryTimes(3).setUseGzip(true).setUserAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"),
                new CSVPageModelPipeline2(), Yao9.class)
                // .test("http://www.9yao.com/neihanduanzi/33775");
                .addUrl("http://www.9yao.com/neihanduanzi/index.html", "http://www.9yao.com/neihanduanzi/index_2.html")
                .thread(10).start();
    }

}
