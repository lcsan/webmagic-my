package com.youku;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

@TargetUrl("http://v.youku.com/v_show/id_*.html")
@HelpUrl(value = { "http://list.youku.com/category/show/c_96*", "http://list.youku.com/category/show/c_97*" })
public class YoukuList implements AfterExtractor {

    @ExtractByUrl("id_(.*?).html")
    private String id;

    @ExtractBy(value = "regex('//movie.douban.com/subject/(\\d+)')", notNull = true)
    private String doubanid;

    @Override
    public String toString() {
        return "\"" + id + "\",\"" + doubanid + "\"\r\n";
    }

    @Override
    public void afterProcess(Page page) {

    }

    public static void main(String[] args) {
        OOSpider.create(
                Site.me().setRetryTimes(3).setTimeOut(5000).setUserAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"),
                new FilePageModelPipeline(), YoukuList.class).setScheduler(new PriorityScheduler())
                .addUrl("http://list.youku.com/category/show/c_96.html",
                        "http://list.youku.com/category/show/c_97.html")
                .thread(100).run();
    }
}
