package com.pornhub;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://www.pornhub.com/view_video.php\\?viewkey=*")
@HelpUrl("https://www.pornhub.com/video*")
public class Pornhub implements AfterExtractor {

    @ExtractBy("//title/text()")
    private String title;

    @ExtractByUrl("([^=]+)^")
    private String id;

    @ExtractBy("xpath('//script').filter('var flashvars_').json('mediaDefinitions[1].videoUrl')")
    private String url;

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Pornhub [title=" + title + ", id=" + id + ", url=" + url + "]";
    }

    @Override
    public void afterProcess(Page page) {
        page.addTargetRequest(new Request(url).putExtra("title", title));
    }

    public static void main(String[] args) {
        OOSpider.create(
                Site.me()
                        .setUserAgent(
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.146 Safari/537.36")
                        .setRetryTimes(3).setTimeOut(10000),
                new ConsolePageModelPipeline(), Pornhub.class, Mp4Download.class)
                .addUrl("https://www.pornhub.com/video?page=2").thread(30).run();
    }

}
