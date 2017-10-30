package us.codecraft.webmagic.example;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.selector.Type;
import us.codecraft.webmagic.utils.Experimental;

/**
 * @author code4crafter@gmail.com
 * @since 0.4.1
 */
@Experimental
public class AppStore {

	@ExtractBy(type = Type.json, value = "$..trackName")
	private String trackName;

	@ExtractBy(type = Type.json, value = "$..description")
	private String description;

	@ExtractBy(type = Type.json, value = "$..userRatingCount")
	private int userRatingCount;

	@ExtractBy(type = Type.json, value = "$..screenshotUrls")
	private List<String> screenshotUrls;

	@ExtractBy(type = Type.json, value = "$..supportedDevices")
	private List<String> supportedDevices;

	public static void main(String[] args) {
		AppStore appStore = OOSpider.create(Site.me(), AppStore.class)
				.<AppStore> get("http://itunes.apple.com/lookup?id=653350791&country=cn&entity=software");
		System.out.println(appStore.trackName);
		System.out.println(appStore.description);
		System.out.println(appStore.userRatingCount);
		System.out.println(appStore.screenshotUrls);
		System.out.println(appStore.supportedDevices);
	}
}
