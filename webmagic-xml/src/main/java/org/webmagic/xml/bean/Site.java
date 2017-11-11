package org.webmagic.xml.bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class Site {

	private us.codecraft.webmagic.Site site = us.codecraft.webmagic.Site.me();

	private String domain;

	private String userAgent;

	private String cookie;

	private String header;

	private String acceptStatCode;

	private String url;

	private Urls urls;

	private Cookies cookies;

	private Headers headers;

	private AcceptStatCodes acceptStatCodes;

	private String charset = "UTF-8";

	private int sleepTime = 500;

	private int retryTimes = 0;

	private int cycleRetryTimes = 0;

	private int retrySleepTime = 500;

	private int timeout = 5000;

	private boolean useGzip = true;

	private boolean disableCookieManagement = false;

	private int threadSize = 1;

	private boolean resetQueue = false;

	private boolean useRedis = false;

	private boolean usePriority = false;

	private boolean useDb = false;

	private Requests requests;

	private String[] startUrls;

	public String getDomain() {
		return domain;
	}

	@XmlElement(name = "domain")
	public void setDomain(String domain) {
		if (StringUtils.isNotBlank(domain)) {
			this.domain = domain.trim();
			site.setDomain(this.domain);
		}
	}

	public String getUserAgent() {
		return userAgent;
	}

	@XmlElement(name = "userAgent")
	public void setUserAgent(String userAgent) {
		if (StringUtils.isNotBlank(userAgent)) {
			this.userAgent = userAgent.trim();
			site.setUserAgent(this.userAgent);
		}
	}

	public String getCharset() {
		return charset;
	}

	@XmlElement(name = "charset")
	public void setCharset(String charset) {
		if (StringUtils.isNotBlank(charset)) {
			this.charset = charset.trim();
			site.setCharset(this.charset);
		}
	}

	public int getSleepTime() {
		return sleepTime;
	}

	@XmlElement(name = "sleepTime")
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
		site.setSleepTime(sleepTime);
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	@XmlElement(name = "retryTimes")
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
		site.setRetryTimes(retryTimes);
	}

	public int getCycleRetryTimes() {
		return cycleRetryTimes;
	}

	@XmlElement(name = "cycleRetryTimes")
	public void setCycleRetryTimes(int cycleRetryTimes) {
		this.cycleRetryTimes = cycleRetryTimes;
		site.setCycleRetryTimes(cycleRetryTimes);
	}

	public int getRetrySleepTime() {
		return retrySleepTime;
	}

	@XmlElement(name = "retrySleepTime")
	public void setRetrySleepTime(int retrySleepTime) {
		this.retrySleepTime = retrySleepTime;
		site.setRetrySleepTime(retrySleepTime);
	}

	public int getTimeout() {
		return timeout;
	}

	@XmlElement(name = "timeout")
	public void setTimeout(int timeout) {
		this.timeout = timeout;
		site.setTimeOut(timeout);
	}

	public boolean isUseGzip() {
		return useGzip;
	}

	@XmlElement(name = "useGzip")
	public void setUseGzip(boolean useGzip) {
		this.useGzip = useGzip;
		site.setUseGzip(useGzip);
	}

	public boolean isDisableCookieManagement() {
		return disableCookieManagement;
	}

	@XmlElement(name = "disableCookieManagement")
	public void setDisableCookieManagement(boolean disableCookieManagement) {
		this.disableCookieManagement = disableCookieManagement;
		site.setDisableCookieManagement(disableCookieManagement);
	}

	public int getThreadSize() {
		return threadSize;
	}

	@XmlElement(name = "threadSize")
	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public boolean isResetQueue() {
		return resetQueue;
	}

	@XmlElement(name = "resetQueue")
	public void setResetQueue(boolean resetQueue) {
		this.resetQueue = resetQueue;
	}

	public boolean isUseRedis() {
		return useRedis;
	}

	@XmlElement(name = "useRedis")
	public void setUseRedis(boolean useRedis) {
		this.useRedis = useRedis;
	}

	public boolean isUseDb() {
		return useDb;
	}

	@XmlElement(name = "useDb")
	public void setUseDb(boolean useDb) {
		this.useDb = useDb;
	}

	public String getCookie() {
		return cookie;
	}

	@XmlElement(name = "cookie")
	public void setCookie(String cookie) {
		if (StringUtils.isNotBlank(cookie)) {
			String[] item = cookie.split("\\s*;\\s*");
			for (String str : item) {
				if (StringUtils.isNotBlank(str)) {
					String[] kv = str.split("\\s*=\\s*");
					if (kv.length == 2) {
						site.addCookie(kv[0].trim(), kv[1].trim());
					}
				}
			}
		}
		this.cookie = cookie;
	}

	public String getAcceptStatCode() {
		return acceptStatCode;
	}

	@XmlElement(name = "acceptStatCode")
	public void setAcceptStatCode(String acceptStatCode) {
		if (StringUtils.isNotBlank(acceptStatCode)) {
			Set<Integer> code = new HashSet<Integer>();
			String[] item = acceptStatCode.trim().split("\\s*,\\s*");
			for (String str : item) {
				if (StringUtils.isNotBlank(acceptStatCode)) {
					code.add(Integer.parseInt(str));
				}
			}
			site.setAcceptStatCode(code);
		}
		this.acceptStatCode = acceptStatCode;
	}

	public String getHeader() {
		return header;
	}

	@XmlElement(name = "header")
	public void setHeader(String header) {
		if (StringUtils.isNotBlank(header)) {
			String[] item = header.split("\\s*\r\n\\s*");
			for (String str : item) {
				if (StringUtils.isNotBlank(str)) {
					String[] kv = str.split("\\s:\\s*");
					if (kv.length == 2) {
						site.addHeader(kv[0].trim(), kv[1].trim());
					}
				}
			}
		}
		this.header = header;
	}

	public Cookies getCookies() {
		return cookies;
	}

	@XmlElement(name = "cookies")
	public void setCookies(Cookies cookies) {
		List<Cookie> list = cookies.getCookies();
		for (Cookie cookie : list) {
			site.addCookie(cookie.getKey().trim(), cookie.getValue().trim());
		}
		this.cookies = cookies;
	}

	public Headers getHeaders() {
		return headers;
	}

	@XmlElement(name = "headers")
	public void setHeaders(Headers headers) {
		List<Header> list = headers.getHeaders();
		for (Header header : list) {
			site.addHeader(header.getKey().trim(), header.getValue().trim());
		}
		this.headers = headers;
	}

	public AcceptStatCodes getAcceptStatCodes() {
		return acceptStatCodes;
	}

	@XmlElement(name = "acceptStatCodes")
	public void setAcceptStatCodes(AcceptStatCodes acceptStatCodes) {
		site.setAcceptStatCode(acceptStatCodes.getAcceptStatCode());
		this.acceptStatCodes = acceptStatCodes;
	}

	public String getUrl() {
		return url;
	}

	@XmlElement(name = "url")
	public void setUrl(String url) {
		this.url = url;
	}

	public Urls getUrls() {
		return urls;
	}

	@XmlElement(name = "urls")
	public void setUrls(Urls urls) {
		this.urls = urls;
	}

	public Requests getRequests() {
		return requests;
	}

	@XmlElement(name = "requests")
	public void setRequests(Requests requests) {
		this.requests = requests;
	}

	public boolean isUsePriority() {
		return usePriority;
	}

	@XmlElement(name = "usePriority")
	public void setUsePriority(boolean usePriority) {
		this.usePriority = usePriority;
	}

	public us.codecraft.webmagic.Site getSite() {
		return site;
	}

	public String[] getStartUrls() {
		if (null == startUrls) {
			if (StringUtils.isNotBlank(url)) {
				startUrls = url.trim().split("\\s*,\\s*");
				if (null != urls) {
					String[] us = urls.getUrl();
					String[] result = Arrays.copyOf(us, us.length + startUrls.length);
					System.arraycopy(startUrls, 0, result, us.length, startUrls.length);
				}
			}
		}
		return startUrls;
	}

	public void setStartUrls(String[] startUrls) {
		this.startUrls = startUrls;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
