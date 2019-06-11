package com.yao9;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://www.chanduan.com/article/*")
// @HelpUrl({ "http://www.chanduan.com/a/*", "http://www.chanduan.com/tags/*" })
public class Chanduan {

    @ExtractByUrl("(.*)")
    private String id;

    @ExtractBy(value = "//div[@class='title']/h2/text()", notNull = true)
    private String title;

    @ExtractBy("//div[@class='info']/regex('[\\d-]+')")
    private String time;

    @ExtractBy("xpath('//div[@class='content']/allText()').replace('^.*?相关搜索：\\s*','').split('\\s+')")
    private List<String> tags;

    @ExtractBy("xpath('//div[@class='content']/allText()').replace('相关搜索：.*?$','')")
    private String content;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    public static void main(String[] args) throws IOException {
        List<String> list = FileUtils.readLines(new File("C:\\Users\\Administrator\\Desktop\\chanduan.txt"));

        OOSpider.create(
                Site.me().setTimeOut(10000).setSleepTime(300).setRetryTimes(3).setUseGzip(true).setUserAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36"),
                new CSVPageModelPipeline3(), Chanduan.class)
                // .test("http://www.chanduan.com/article/5270.html");
                .addUrl(list.toArray(new String[] {})).thread(20).start();
    }

}
