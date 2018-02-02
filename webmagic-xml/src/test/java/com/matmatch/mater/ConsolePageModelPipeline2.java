package com.matmatch.mater;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class ConsolePageModelPipeline2 implements PageModelPipeline<Object> {

    @Override
    public void process(Object t, Task task) {
        System.out.println(JSON.toJSONString(t));
    }

}
