package com.matmatch.dd;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class ConsolePageModelPipeline implements PageModelPipeline<Object> {

    @Override
    public void process(Object t, Task task) {
        if (t instanceof Material) {
            String json = JSON.toJSONString(t);
            System.out.println(json);
            Material materials = (Material) t;
            try {
                FileUtils.writeStringToFile(new File("H:/mt/" + materials.getUrlParam() + ".txt"), json, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
