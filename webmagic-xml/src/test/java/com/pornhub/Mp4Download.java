package com.pornhub;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://cv.phncdn.com/videos/*")
public class Mp4Download implements AfterExtractor {

    @ExtractByUrl
    private String url;

    @Override
    public void afterProcess(Page page) {
        String title = (String) page.getRequest().getExtra("title");
        try {
            FileUtils.writeByteArrayToFile(new File("h:/tv/" + title.replaceAll("[^a-zA-Z0-9\\s]+", "") + ".mp4"),
                    page.getBytes(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
