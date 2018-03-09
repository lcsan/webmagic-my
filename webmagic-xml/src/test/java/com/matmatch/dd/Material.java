package com.matmatch.dd;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.matmatch.mater.ConsolePageModelPipeline2;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl(value = "https://matmatch.com/materials/*", sourceRegion = "xxx")
public class Material {

    @ExtractBy("//div[@class='breadcrumbs']/a[1]/text()")
    private String firstLevelCategory;

    @ExtractBy("//div[@class='breadcrumbs']/a[2]/text()")
    private String secondLevelCategory;

    @ExtractBy("//div[@class='breadcrumbs']/a[3]/text()")
    private String thirdLevelCategory;

    @ExtractBy("css('a.tag-standard').filter('primaryStandards').xpath('//a/text()')")
    private List<String> primaryStandards;

    @ExtractBy("css('a.tag-standard').filter('equivalentStandards').xpath('//a/text()')")
    private List<String> equivalentStandards;

    @ExtractBy("//div[@class='text-small text-hint margin-top-1']/text()")
    private String standardsText;

    @ExtractBy("css('table.material-table-technological').filter('Alternative and trade names').xpath('//span/text()')")
    private List<String> alias;

    @ExtractBy("//p[@class='page-description margin-top-2']/text()")
    private String description;

    @ExtractBy("xpath('//table[@class='width-100']').filter('Element')")
    private List<Composition> composition;

    @ExtractBy("//div[@class='scroll-container-card-horizontal']")
    private List<Propretys> propretys;

    @ExtractBy("//div[@class='text-hint margin-top-2 text-small']/text()")
    private String propretyText;

    public List<Composition> getComposition() {
        return composition;
    }

    public String getDescription() {
        return description;
    }

    public String getFirstLevelCategory() {
        return firstLevelCategory;
    }

    public String getSecondLevelCategory() {
        return secondLevelCategory;
    }

    public String getThirdLevelCategory() {
        return thirdLevelCategory;
    }

    public List<String> getPrimaryStandards() {
        return primaryStandards;
    }

    public List<String> getEquivalentStandards() {
        return equivalentStandards;
    }

    public String getStandardsText() {
        return standardsText;
    }

    public List<String> getAlias() {
        return alias;
    }

    public String getPropretyText() {
        return propretyText;
    }

    public List<Propretys> getPropretys() {
        return propretys;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static void main(String args[]) {
        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(20000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new ConsolePageModelPipeline2(), Material.class, Composition.class, Propretys.class, Propretys.class,
                Proprety.class).addUrl("https://matmatch.com/materials/vdmm022-vdm-metals-vdm-alloy-825").run();
    }
}
