package com.matmatch.mater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class ConsolePageModelPipeline implements PageModelPipeline<Object> {

    @Override
    public void process(Object t, Task task) {
        if (t instanceof Materials) {
            String json = JSON.toJSONString(t);
            Materials materials = (Materials) t;
            try {
                FileUtils.writeStringToFile(new File("H:/matmatch.com/" + materials.getUrlParam() + ".txt"), json,
                        "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
