package us.codecraft.webmagic.example;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.HasKey;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.Source;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.selector.Type;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.1
 */
public class GithubRepoApi implements HasKey {

	@ExtractBy(type = Type.json, value = "$.name", source = Source.RawText)
	private String name;

	@ExtractBy(type = Type.json, value = "$..owner.login", source = Source.RawText)
	private String author;

	@ExtractBy(type = Type.json, value = "$.language", multi = true, source = Source.RawText)
	private List<String> language;

	@ExtractBy(type = Type.json, value = "$.stargazers_count", source = Source.RawText)
	private int star;

	@ExtractBy(type = Type.json, value = "$.forks_count", source = Source.RawText)
	private int fork;

	@ExtractByUrl
	private String url;

	public static void main(String[] args) {
		OOSpider.create(Site.me().setSleepTime(100), new ConsolePageModelPipeline(), GithubRepoApi.class)
				.addUrl("https://api.github.com/repos/code4craft/webmagic").run();
	}

	@Override
	public String key() {
		return author + ":" + name;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public List<String> getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}

	public int getStar() {
		return star;
	}

	public int getFork() {
		return fork;
	}
}
