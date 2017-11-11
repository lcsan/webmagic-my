package org.webmagic.xml.bean;

import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task {
	private long delay;
	private long period;
	private boolean atFixedRate;
	private String cron;
	private Timer tm;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public long getDelay() {
		return delay;
	}

	@XmlElement(name = "delay")
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public long getPeriod() {
		return period;
	}

	@XmlElement(name = "period")
	public void setPeriod(long period) {
		this.period = period;
	}

	public boolean isAtFixedRate() {
		return atFixedRate;
	}

	@XmlElement(name = "atFixedRate")
	public void setAtFixedRate(boolean atFixedRate) {
		this.atFixedRate = atFixedRate;
	}

	public String getCron() {
		return cron;
	}

	@XmlElement(name = "cron")
	public void setCron(String cron) {
		this.cron = cron;
	}

	class SpiderTask extends TimerTask {
		private us.codecraft.webmagic.Spider spider;
		private boolean reset;

		public SpiderTask(us.codecraft.webmagic.Spider spider, boolean reset) {
			super();
			this.spider = spider;
			this.reset = reset;
		}

		@Override
		public void run() {
			logger.info("spider Task run!");
			spider.run();
			if (reset) {
				spider.reset(false);
			}
		}
	}

	public void runTask(us.codecraft.webmagic.Spider spider, boolean reset) {
		if (StringUtils.isNotBlank(cron)) {

		} else {
			tm = new Timer();
			if (atFixedRate) {
				tm.scheduleAtFixedRate(new SpiderTask(spider, reset), delay, period);
			} else {
				tm.schedule(new SpiderTask(spider, reset), delay, period);
			}
		}
	}

	public void close() {
		if (null != tm) {
			tm.cancel();
		}
	}

	@Override
	public String toString() {
		return "Task [delay=" + delay + ", period=" + period + ", atFixedRate=" + atFixedRate + ", cron=" + cron + "]";
	}

}
