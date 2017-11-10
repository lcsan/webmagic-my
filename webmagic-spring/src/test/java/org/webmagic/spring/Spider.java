package org.webmagic.spring;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.model.xml.bean.Models;

@XmlRootElement(name = "spider")
public class Spider {

	private Site site;
	private Redis redis;
	private Database database;
	private Pipeline pipeline;
	private Models models;
	private Task task;

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
