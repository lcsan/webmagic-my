package com.kuaidi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://www.kuaidi100.com/all/")
@ExtractBy(value = "//div[@class='column-list']//a", multi = true)
public class KuaiDi {

    @ExtractBy(value = "//a/text()", notNull = true)
    private String name;

    @ExtractBy("//a/regex('^.*?([^/]+)\\.shtml.*?$',1)")
    private String code;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static void main(String[] args) {
        List<String> ls = new ArrayList<String>();
        ls.add("https://m.kuaidi100.com/index_all.html?type=顺丰速运&postid=821545765258");
        OOSpider sp = OOSpider.create(Site.me(), KuaiDiH5.class);
        List<KuaiDiH5> r = sp.getAll(ls);
        Map<String, String> map = new HashMap<String, String>();
        for (KuaiDiH5 kuaiDi : r) {
            map.put(kuaiDi.getName(), kuaiDi.getCode());
        }
        sp.stop();
        sp.close();

        List<String> list = new ArrayList<String>();
        list.add("https://www.kuaidi100.com/all/");
        sp = OOSpider.create(Site.me(), KuaiDi.class);
        List<KuaiDi> re = sp.getAll(list);
        for (KuaiDi kuaiDi : re) {
            String name = kuaiDi.getName().replaceAll("-", "").replaceAll("&", "与").replaceAll("[(（]", "/")
                    .replaceAll("[)）]", "");
            String code = kuaiDi.getCode();
            String cd = map.get(name);
            if (null != cd) {
                code = cd;
            }
            if (name.matches("^.*?(?:快递|速递|物流|配送|速运|快运)$")) {
                System.out.println(name.replaceAll("(?:快递|速递|物流|配送|速运|快运)", "") + "\t" + code);
            }
            if (name.contains("/")) {
                String[] str = name.split("/");
                for (String string : str) {
                    if (name.matches("^.*?(?:快递|速递|物流|配送|速运|快运)$")) {
                        System.out.println(string.replaceAll("(?:快递|速递|物流|配送|速运|快运)", "") + "\t" + code);
                    } else {
                        System.out.println(string + "\t" + code);
                    }
                }
            } else {
                System.out.println(name + "\t" + code);
            }
        }

        sp.stop();
        sp.close();
    }

}
