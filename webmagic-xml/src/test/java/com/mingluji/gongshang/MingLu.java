package com.mingluji.gongshang;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://gongshang.mingluji.com/\\w+/node/\\d+")
@HelpUrl(value = { "https://gongshang.mingluji.com/", "https://gongshang.mingluji.com/\\w+",
        "https://gongshang.mingluji.com/\\w+/zhuceriqi/*" })
public class MingLu {

    @ExtractBy("//fieldset//ul")
    private List<MingLuContent> minglus;

    public List<MingLuContent> getMinglus() {
        return minglus;
    }

    @Override
    public String toString() {
        return "MingLu [minglus=" + minglus + "]";
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me().setTimeOut(20000).setRetryTimes(2).setCycleRetryTimes(2),
                new MingLuPageModelPipeline(), MingLu.class, MingLuContent.class).thread(20)
                .addUrl("https://gongshang.mingluji.com/").start();
    }
}
