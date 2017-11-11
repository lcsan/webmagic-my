
import javax.xml.bind.annotation.XmlRootElement;

import org.webmagic.spring.GroovyPageModelPipeline;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

@XmlRootElement(name = "spider")
public class Test {

	public static void main(String[] args) {
		// List list = Xml2Models.create("crawler/test_model6.xml").getModes();
		// System.out.println(list);
		Spider sp = OOSpider.create(Site.me(), new GroovyPageModelPipeline(), "crawler/test_model6.xml")
				.addUrl("http://www.iqiyi.com/lib/s_200002105.html").thread(20);
		sp.run();
	}

}
