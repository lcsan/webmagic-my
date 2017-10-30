package us.codecraft.webmagic.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * The extension to Pipeline for page model extractor.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
class ModelPipeline implements Pipeline {

    private Map<String, PageModelPipeline> pageModelPipelines = new ConcurrentHashMap<String, PageModelPipeline>();

    public ModelPipeline() {
    }

    public ModelPipeline put(String clazz, PageModelPipeline pageModelPipeline) {
        pageModelPipelines.put(clazz, pageModelPipeline);
        return this;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, PageModelPipeline> classPageModelPipelineEntry : pageModelPipelines.entrySet()) {
            Object o = resultItems.get(classPageModelPipelineEntry.getKey());
            if (o != null) {
                if (o instanceof Collection<?>) {
                    List<Object> list = (List<Object>) o;
                    for (Object o1 : list) {
                        classPageModelPipelineEntry.getValue().process(o1, task);
                    }
                } else {
                    classPageModelPipelineEntry.getValue().process(o, task);
                }
            }
        }
    }
}
