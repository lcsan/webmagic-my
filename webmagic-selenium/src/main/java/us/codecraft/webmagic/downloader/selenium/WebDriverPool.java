package us.codecraft.webmagic.downloader.selenium;

import java.io.File;
import java.net.URL;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author taojw
 */
public class WebDriverPool {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private int CAPACITY = 5;
    private AtomicInteger refCount = new AtomicInteger(0);
    private static final String DRIVER_PHANTOMJS = "phantomjs";

    /**
     * store webDrivers available
     */
    private BlockingDeque<WebDriver> innerQueue = new LinkedBlockingDeque<WebDriver>(CAPACITY);

    private static String PHANTOMJS_PATH;
    private static DesiredCapabilities caps = DesiredCapabilities.phantomjs();
    static {
        if (System.getProperty("phantomjs_path") != null) {
            PHANTOMJS_PATH = WebDriverPool.class.getClassLoader().getResource(System.getProperty("phantomjs_path"))
                    .getPath();
        }
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_PATH);
        caps.setCapability("takesScreenshot", true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, "--load-images=no");

    }

    public WebDriverPool() {
        this("phantomjs.exe");
    }

    public WebDriverPool(String path) {
        File fe = new File(path);
        if (fe.exists()) {
            PHANTOMJS_PATH = fe.getPath();
        } else {
            URL url = WebDriverPool.class.getClassLoader().getResource(path);
            if (null == url) {
                // 从jar包里面读取模板文件
                PHANTOMJS_PATH = WebDriverPool.class.getResource(path).getPath();
            } else {
                PHANTOMJS_PATH = url.getPath();
            }
        }
        System.setProperty("phantomjs.binary.path", PHANTOMJS_PATH);
    }

    public WebDriverPool(int poolsize) {
        this("phantomjs.exe");
        this.CAPACITY = poolsize;
        innerQueue = new LinkedBlockingDeque<WebDriver>(poolsize);
    }

    public WebDriverPool(String path, int poolsize) {
        this(path);
        this.CAPACITY = poolsize;
        innerQueue = new LinkedBlockingDeque<WebDriver>(poolsize);
    }

    public WebDriver get() throws InterruptedException {
        WebDriver poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (refCount.get() < CAPACITY) {
            synchronized (innerQueue) {
                if (refCount.get() < CAPACITY) {

                    WebDriver mDriver = new PhantomJSDriver(caps);
                    // 尝试性解决：https://github.com/ariya/phantomjs/issues/11526问题
                    mDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
                    // mDriver.manage().window().setSize(new Dimension(1366,
                    // 768));
                    innerQueue.add(mDriver);
                    refCount.incrementAndGet();
                }
            }
        }
        return innerQueue.take();
    }

    public void returnToPool(WebDriver webDriver) {
        // webDriver.quit();
        // webDriver=null;
        innerQueue.add(webDriver);
    }

    public void close(WebDriver webDriver) {
        refCount.decrementAndGet();
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }

    public void shutdown() {
        try {
            for (WebDriver driver : innerQueue) {
                close(driver);
            }
            innerQueue.clear();
        } catch (Exception e) {
            // e.printStackTrace();
            logger.warn("webdriverpool关闭失败", e);
        }
    }
}
