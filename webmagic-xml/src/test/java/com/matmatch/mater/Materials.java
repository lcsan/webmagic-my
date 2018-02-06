package com.matmatch.mater;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://matmatch.com/materials/*")
public class Materials {

    @ExtractByUrl("([^/]+)$")
    private String path;

    @ExtractBy("css('a.add-to-project-button','data-material-id')")
    private String materialId;

    @ExtractBy("css('button.triggers-contact-supplier-popup','data-supplier-name') || xpath('//span[@class='align-middle']/strong/text()')")
    private String supplierName;

    @ExtractBy(value = "css('a.add-to-project-button','data-material-name')", notNull = true)
    private String materialName;

    @ExtractBy("//p[@class='page-description margin-top-2']/text()")
    private String description;

    @ExtractBy("//div[@class='tags material-standards spaced-blocks-extra-compact']//a/text()")
    private List<String> standards;

    @ExtractBy("//div[@class='spaced-container breadcrumbs']/a/text()")
    private List<String> materials;

    @ExtractBy("//a[@class='tag tag-float tag-application app-link']/text()")
    private List<String> applications;

    @ExtractBy("//table[@class='width-100']")
    private List<Chemical> chemicals;

    @ExtractBy("//div[@class='scroll-container-card-horizontal']/table[@class='material-table']")
    private List<MaterialProperty> materialPropertys;

    @ExtractBy("//table[@class='material-table material-table-technological']/tidyText()")
    private String technological;

    @ExtractBy("//a[@class='app-link link-to-enhance-with-search list-item list-item-link card-inner-full-width']/text()")
    private String suppliers;

    public String getPath() {
        return path;
    }

    public String getMaterialId() {
        return materialId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getStandards() {
        return standards;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public List<String> getApplications() {
        return applications;
    }

    public List<Chemical> getChemicals() {
        return chemicals;
    }

    public List<MaterialProperty> getMaterialPropertys() {
        return materialPropertys;
    }

    public String getTechnological() {
        return technological;
    }

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("C:/Users/longchensan/Desktop/aa-1.txt");
        BufferedReader buf = new BufferedReader(fr);
        String line = null;
        List<String> list = new ArrayList<String>();
        while (null != (line = buf.readLine())) {
            if (!"".equals(line)) {
                list.add(line.trim());
            }
        }
        buf.close();

        // OOSpider.create(
        // Site.me().setTimeOut(10000).setSleepTime(1000).setRetryTimes(3).setUseGzip(true).setUserAgent(
        // "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like
        // Gecko) Chrome/55.0.2883.87 Safari/537.36"),
        // new ConsolePageModelPipeline(), Materials.class, Chemical.class,
        // MaterialProperty.class,
        // MaterialPropertyLabel.class).setScheduler(new
        // RedisScheduler("127.0.0.1")).thread(20)
        // .addUrl(list.toArray(new String[] {})).run();

        OOSpider.create(
                Site.me().setUseGzip(true).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline2(), Materials.class, Chemical.class, MaterialProperty.class,
                MaterialPropertyLabel.class, MoreLabel.class)
                .test("https://matmatch.com/materials/aasx002-advanced-alloy-services-rene-80",
                        "https://matmatch.com/materials/plana109-plansee-densimet-185-d185");
    }

}
