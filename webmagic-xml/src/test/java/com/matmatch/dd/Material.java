package com.matmatch.dd;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.utils.HttpConstant.Method;

@TargetUrl(value = "https://matmatch.com/materials/*")
public class Material implements AfterExtractor {

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

    @ExtractBy("//a[@class='tag tag-float tag-application app-link']/text()")
    private List<String> applications;

    @ExtractBy(value = "css('a.add-to-project-button','data-material-id')", notNull = true)
    private String id;

    @ExtractBy("css('a.add-to-project-button','data-material-name')")
    private String name;

    @ExtractBy("css('button.triggers-contact-supplier-popup','data-supplier-name') || xpath('span[@class='align-middle']/strong/text()')")
    private String supplierName;

    @ExtractBy("xpath('//a[@class='app-link link-to-enhance-with-search']/@href').replace('/suppliers/','')")
    private String supplierUrlParam;

    @ExtractBy("css('section.content-section').filter('^.*?(?:技术性|Technological properties).*?$')")
    private String technological;

    @ExtractByUrl("([^/]+)$")
    private String urlParam;

    private Boolean featured = true;
    private Boolean group = false;
    private Long groupSize = 0l;
    private Long supplierId;
    private Long matmatchRank;
    private List<String> extraColumns;

    @ExtractBy("css('a','href').filter('/search\\?')")
    private List<String> url;

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

    public List<String> getApplications() {
        return applications;
    }

    public String getTechnological() {
        return technological;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public Boolean getGroup() {
        return group;
    }

    public Long getGroupSize() {
        return groupSize;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public Long getMatmatchRank() {
        return matmatchRank;
    }

    public List<String> getExtraColumns() {
        return extraColumns;
    }

    public String getSupplierUrlParam() {
        return supplierUrlParam;
    }

    public String getUrlParam() {
        return urlParam;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public void afterProcess(Page page) {
        JSONObject json = (JSONObject) page.getRequest().getExtra("json");
        if (null != json) {
            id = json.getString("id");
            name = json.getString("name");
            featured = json.getBoolean("featured");
            group = json.getBoolean("group");
            groupSize = json.getLong("groupSize");
            supplierId = json.getLong("supplierId");
            supplierName = json.getString("supplierName");
            matmatchRank = json.getLong("matmatchRank");
            extraColumns = (List<String>) json.get("extraColumns");
            supplierUrlParam = json.getString("supplierUrlParam");
            urlParam = json.getString("urlParam");
        }
        for (String string : url) {
            try {
                string = URLDecoder.decode(string, "UTF-8");
                JSONObject js = Common.changeJSON(string);
                js.put("groupingEnabled", false);
                for (int i = 1; i <= 10; i++) {
                    Request req = new Request("https://matmatch.com/searchapi/materials");
                    req.setMethod(Method.POST);
                    js.put("pageNumber", "" + i);
                    req.setRequestBody(HttpRequestBody.json(js.toJSONString(), "UTF-8"));
                    req.addHeader("Referer", "https://matmatch.com/search");
                    req.addHeader("X-XSRF-TOKEN", Common.getToken());
                    page.addTargetRequest(req);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String args[]) {

        OOSpider.create(
                Site.me().setUseGzip(true).setTimeOut(20000).setRetryTimes(3).setUserAgent(
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
                new MatPageModelPipeline(), Start.class, Material.class, Composition.class, Propretys.class,
                Propretys.class, Proprety.class)
                .addUrl("https://matmatch.com/search",
                        "https://matmatch.com/materials/vdmm018-vdm-metals-vdm-alloy-718")
                .run();
    }
}
