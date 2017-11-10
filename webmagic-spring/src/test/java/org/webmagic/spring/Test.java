package org.webmagic.spring;

import javax.xml.bind.annotation.XmlRootElement;

import xml.Xml2Models;

@XmlRootElement(name = "spider")
public class Test {

	public static void main(String[] args) {
		// List list = Xml2Models.create("crawler/test_model6.xml").getModes();
		// System.out.println(list);
		// Spider sp = OOSpider.create(Site.me(), new GroovyPageModelPipeline(),
		// "crawler/test_model6.xml")
		// .addUrl("http://www.iqiyi.com/lib/s_200002105.html").thread(20);
		// sp.run();
		Spider test = Xml2Models.parse("E:/workspace/webmagic-my/webmagic-spring/src/main/resources/input2.xml",
				Spider.class);
		System.out.println(test.getSite().getSite());
		// System.out.println(test.getAny().get(0));
	}

}
