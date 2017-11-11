package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.xml.bean.Models;
import us.codecraft.webmagic.scheduler.PriorityScheduler;
import us.codecraft.webmagic.scheduler.RedisPriorityScheduler;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@XmlRootElement(name = "spider")
public class Spider {

	private Site site;
	private Redis redis;
	private Database database;
	private Pipeline pipeline;
	private Models models;
	private Task task;
	private us.codecraft.webmagic.Spider spider;

	public Site getSite() {
		return site;
	}

	@XmlElement(name = "site")
	public void setSite(Site site) {
		this.site = site;
	}

	public Redis getRedis() {
		return redis;
	}

	@XmlElement(name = "redis")
	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public Database getDatabase() {
		return database;
	}

	@XmlElement(name = "database")
	public void setDatabase(Database database) {
		this.database = database;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	@XmlElement(name = "pipeline")
	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public Models getModels() {
		return models;
	}

	@XmlElement(name = "models")
	public void setModels(Models models) {
		this.models = models;
	}

	public Task getTask() {
		return task;
	}

	@XmlElement(name = "task")
	public void setTask(Task task) {
		this.task = task;
	}

	public us.codecraft.webmagic.Spider getSpider() {
		if (null == spider) {
			spider = OOSpider.create(site.getSite(), pipeline.getPipeline(), models).addUrl(site.getStartUrls())
					.thread(site.getThreadSize());
			if (site.isUseRedis()) {
				if (site.isUsePriority()) {
					spider.setScheduler(new RedisPriorityScheduler(redis.getRedisPool()));
				} else {
					spider.setScheduler(new RedisScheduler(redis.getRedisPool()));
				}
			} else if (site.isUsePriority()) {
				spider.setScheduler(new PriorityScheduler());
			}
		}
		return spider;
	}

	public void run() {
		if (null != task) {
			task.runTask(getSpider(), site.isResetQueue());
		}
	}

	public void close() {
		if (null != task) {
			task.close();
		}
		getSpider().reset(false);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
