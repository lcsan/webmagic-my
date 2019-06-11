package com.kuaidi;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://m.kuaidi100.com/index_all.html*")
@ExtractBy(value = "//li[@class='smart-select-li']", multi = true)
public class KuaiDiH5 {

    @ExtractBy(value = "//li/text()", notNull = true)
    private String name;

    @ExtractBy("//li/regex('^.*?'(.*?)'.*?$',1)")
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "KuaiDiH5 [name=" + name + ", code=" + code + "]";
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("https://m.kuaidi100.com/index_all.html?type=顺丰速运&postid=821545765258");
        List<KuaiDiH5> re = OOSpider.create(Site.me(), KuaiDiH5.class).getAll(list);
        for (KuaiDiH5 kuaiDi : re) {
            System.out.println(kuaiDi);
        }
    }

}
