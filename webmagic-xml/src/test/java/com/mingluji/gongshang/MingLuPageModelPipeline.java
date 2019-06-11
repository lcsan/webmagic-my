package com.mingluji.gongshang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class MingLuPageModelPipeline implements PageModelPipeline<Object> {

    @Override
    public void process(Object t, Task task) {
        if (t instanceof MingLu) {
            List<String> list = new ArrayList<String>();
            list.add(JSON.toJSONString(t));
            try {
                FileUtils.writeLines(new File("./minglu.txt"), list, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
