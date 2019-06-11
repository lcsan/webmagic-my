package us.codecraft.webmagic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestPhantomJsDriver {

    public static PhantomJSDriver getPhantomJSDriver() {
        // 设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        // ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        // 截屏支持
        dcaps.setCapability("takesScreenshot", false);
        // css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        // js支持
        dcaps.setJavascriptEnabled(true);
        // 驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "D:/JAVA/elasticsearch-head-master/node_modules/phantomjs-prebuilt/lib/phantom/bin/phantomjs.exe");

        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return driver;
    }

    public static void main(String[] args) {
        WebDriver driver = getPhantomJSDriver();
        driver.get("http://www.baidu.com");
        System.out.println(driver.getCurrentUrl());
    }

}
