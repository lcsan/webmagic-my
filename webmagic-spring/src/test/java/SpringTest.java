

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import us.codecraft.webmagic.Site;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:crawler_tudou2.xml" })
public class SpringTest {

    @Autowired
    private Site site;

    @Test
    public void test() throws IOException {
        System.out.println(site);
    }
}
