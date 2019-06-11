package com.yao9;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.csvreader.CsvWriter;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class CSVPageModelPipeline3 implements PageModelPipeline<Chanduan> {

    private final static CsvWriter csvWriter;

    static {
        csvWriter = new CsvWriter("d:/chanduans1.csv", ',', Charset.forName("UTF-8"));
        String[] headers = { "url", "标题", "时间", "标签", "内容" };
        try {
            csvWriter.writeRecord(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(Chanduan t, Task task) {
        try {
            String item = t.getContent();
            int len = item.length();
            if (StringUtils.isNotBlank(item) && len >= 20) {
                String[] content = { t.getId(), t.getTitle(), t.getTime(), String.join("|", t.getTags()), item };
                System.out.println(ToStringBuilder.reflectionToString(content));
                csvWriter.writeRecord(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
