package com.matmatch.mater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.matmatch.Search;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class ConsolePageModelPipeline3 implements PageModelPipeline<Object> {

	@Override
	public void process(Object t, Task task) {
		if (t instanceof Search) {
			String json = JSON.toJSONString(t);
			System.out.println(json);
			Search search = (Search) t;
			try {
				FileUtils.writeLines(new File("e:/matmatch12.txt"), search.getUrls(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
