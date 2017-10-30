
import java.io.IOException;

import org.jsoup.Jsoup;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.model.pageextra.PageModelList;
import us.codecraft.webmagic.model.xml.Xml2Models;
import us.codecraft.webmagic.model.xml.bean.Models;
import us.codecraft.webmagic.selector.PlainText;

/**
 * JXDocument Tester.
 *
 * @author <et.tw@163.com>
 * @version 1.0
 */
public class JXDocumentTest {

    @Test
    public void testXml() throws IOException, InstantiationException, IllegalAccessException {

        String url = "https://api.douban.com/v2/movie/subject/7054604?apikey=0b2bdeda43b5688921839c8ecb20399b";
        Models mds = Xml2Models.create("/test_model5.xml");
        url = "http://www.iqiyi.com/lib/s_200002105.html";
        mds = Xml2Models.create("/test_model6.xml");
        String body = Jsoup.connect(url)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like         Gecko) Chrome/54.0.2840.87 Safari/537.36")
                .timeout(10000).ignoreContentType(true).execute().body();
        // System.out.println(new
        // JsonPathSelector("$..languages[*]").selectList(body));
        Page page = new Page();
        page.setRawText(body);
        page.setRequest(new Request(url));
        page.setUrl(PlainText.create(url));

        PageModelList list = PageModelList.create(mds.getModes());
        Object obj = list.getPageModelExtractor("IqiyiPage").process(page);
        System.out.println(JSON.toJSONString(obj));
    }

}
