package com.yao9;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.csvreader.CsvWriter;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class CSVPageModelPipeline implements PageModelPipeline<Haha56> {

    private final static CsvWriter csvWriter;

    static {
        csvWriter = new CsvWriter("d:/笑话hh56.csv", ',', Charset.forName("UTF-8"));
        String[] headers = { "url", "标题", "子标题", "内容" };
        try {
            csvWriter.writeRecord(headers);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(Haha56 t, Task task) {
        try {
            List<String> list = t.getContent();
            if (list != null && list.size() > 0) {
                for (String item : list) {
                    int len = item.length();
                    if (StringUtils.isNotBlank(item) && len <= 200 && len >= 20) {
                        String[] content = { t.getId(), t.getTitle(), item.substring(0, 6).trim(), item };
                        System.out.println(ToStringBuilder.reflectionToString(content));
                        csvWriter.writeRecord(content);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
