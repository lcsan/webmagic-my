package com.matmatch;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://matmatch.com/search")
public class Home implements AfterExtractor {
	public static void main(String[] args) {
		OOSpider.create(
				Site.me().setUseGzip(true).setUserAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"),
				new ConsolePageModelPipeline(), Home.class).addUrl("https://matmatch.com/search").run();
	}

	@Override
	public void afterProcess(Page page) {
		System.out.println(page.getHeaders().get("Set-Cookie"));
	}

}
