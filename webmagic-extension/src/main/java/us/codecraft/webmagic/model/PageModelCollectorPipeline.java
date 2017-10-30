package us.codecraft.webmagic.model;

import java.util.Collection;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.CollectorPageModelPipeline;
import us.codecraft.webmagic.pipeline.CollectorPipeline;

/**
 * @author code4crafter@gmail.com
 * @since 0.4.0
 */
class PageModelCollectorPipeline<T> implements CollectorPipeline<T> {

    private final CollectorPageModelPipeline<T> classPipeline = new CollectorPageModelPipeline<T>();

    private final String key;

    PageModelCollectorPipeline(String key) {
        this.key = key;
    }

    @Override
    public List<T> getCollected() {
        return classPipeline.getCollected();
    }

    @Override
    public synchronized void process(ResultItems resultItems, Task task) {
        Object o = resultItems.get(key);
        if (o != null) {
            if (o instanceof Collection<?>) {
                List<Object> list = (List<Object>) o;
                for (Object o1 : list) {
                    classPipeline.process((T) o1, task);
                }
            } else {
                classPipeline.process((T) o, task);
            }
        }
    }
}
