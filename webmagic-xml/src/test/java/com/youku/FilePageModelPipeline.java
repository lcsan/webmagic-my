package com.youku;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class FilePageModelPipeline implements PageModelPipeline<YoukuList> {

    private static FileOutputStream fo = null;

    static {
        if (null == fo) {
            try {
                fo = new FileOutputStream("e:/youku.csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void process(YoukuList t, Task task) {
        try {
            fo.write(t.toString().getBytes());
            fo.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
