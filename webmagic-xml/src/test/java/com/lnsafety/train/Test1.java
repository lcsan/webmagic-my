package com.lnsafety.train;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("http://train.lnsafety.com/mnzxks/zxks.asp")
@ExtractBy(value = "//div[@id='RightExam']//li", multi = true)
public class Test1 {

    @ExtractBy("xpath('//a/text()').filter('\\S+').replace('^[\\s\\d、]+(.*?)[\\sABCD]+$','$1')")
    private String title;

    @ExtractBy("xpath('//div/text()').split('\\s+')")
    private List<String> answer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("ASPSESSIONIDQSCSTSRB", "BICAAOLDIGMLKAMHMJICPNNB");
        // cookies.put("ASPSESSIONIDQQASTQSA", "CNMBBKMDLHPNPGPEAGMEFDDF");
        cookies.put("ajj", "lx=2&zx=2");
        cookies.put("zx", "jssj=2018%2F12%2F5+19%3A23%3A36&kssj=2018%2F12%2F5+17%3A23%3A36&zkzh=441522199611208230");

        System.out.println(Jsoup.connect("http://train.lnsafety.com/mnzxks/zxks.asp").cookies(cookies).get().body());

        // OOSpider.create(Site.me()
        // .setUserAgent(
        // "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML,
        // like Gecko) Chrome/69.0.3497.100 Safari/537.36")
        // .addCookie("ASPSESSIONIDQSCSTSRB",
        // "GPLFDNDDEDPFHDMBMAPIBGJN").addCookie("ajj", "lx=2&zx=2")
        // .addCookie("zx",
        // "jssj=2018%2F12%2F5+1%3A19%3A54&kssj=2018%2F12%2F4+23%3A19%3A54&zkzh=441522199611208230")
        // .addHeader("Referer", "http://train.lnsafety.com/mnzxks/index1.html")
        // .addHeader("Upgrade-Insecure-Requests", "1"), new
        // ConsolePageModelPipeline(), Test1.class)
        // .addUrl("http://train.lnsafety.com/mnzxks/zxks.asp").start();
        //
        // System.out.println(URLEncoder.encode("氯化工艺作业", "GBK"));
    }

}
