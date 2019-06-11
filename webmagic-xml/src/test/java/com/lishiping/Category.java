package com.lishiping;

import java.io.IOException;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://app.pearvideo.com/clt/jsp/v4/getCategorys.jsp")
public class Category {

    @ExtractBy(value = "//body/text()")
    private String body;

    public static void main(String[] args) throws IOException {
        OOSpider dp = (OOSpider) OOSpider
                .create(Site.me().setSleepTime(200).setRetryTimes(2)
                        .addCookie("PEAR_UUID", "841671c5-60c7-4a57-82f8-e31c1084de8e").setUserAgent(
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"),
                        new ConsolePageModelPipeline(), Category.class)
                .addUrl("http://app.pearvideo.com/clt/jsp/v4/getCategorys.jsp");
        System.out.println(dp.getSite());
    }

}
