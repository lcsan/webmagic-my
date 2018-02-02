package douban;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.selector.Type;

@TargetUrl("https://api.douban.com/v2/movie/subject/*")
@ExtractBy(value = "$..popular_comments[*]", multi = true, type = Type.json)
public class DouBan {

    @ExtractBy("*")
    private String aa;

    @ExtractBy(value = "*", type = Type.json)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static void main(String[] args) {
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), DouBan.class)
                .addUrl("https://api.douban.com/v2/movie/subject/7054604?apikey=0b2bdeda43b5688921839c8ecb20399b")
                .run();
    }

}
