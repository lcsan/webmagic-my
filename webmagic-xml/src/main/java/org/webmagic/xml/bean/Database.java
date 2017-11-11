package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlElement;

public class Database {
	private String driver;
	private String url;
	private String userName;
	private String password;
	private int initialSize;
	private int minIdle;
	private int maxActive;
	private int maxIdleTime;
	private int idleConnectionTestPeriod;
	private int maxPoolSize;
	private int minPoolSize;
	private int initialPoolSize;

	public String getDriver() {
		return driver;
	}

	@XmlElement(name = "driver")
	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	@XmlElement(name = "url")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	@XmlElement(name = "userName")
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	public int getInitialSize() {
		return initialSize;
	}

	@XmlElement(name = "initialSize")
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	@XmlElement(name = "minIdle")
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	@XmlElement(name = "maxActive")
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	@XmlElement(name = "maxIdleTime")
	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}

	@XmlElement(name = "idleConnectionTestPeriod")
	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	@XmlElement(name = "maxPoolSize")
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	@XmlElement(name = "minPoolSize")
	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	@XmlElement(name = "initialPoolSize")
	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	@Override
	public String toString() {
		return "Database [driver=" + driver + ", url=" + url + ", userName=" + userName + ", password=" + password
				+ ", initialSize=" + initialSize + ", minIdle=" + minIdle + ", maxActive=" + maxActive
				+ ", maxIdleTime=" + maxIdleTime + ", idleConnectionTestPeriod=" + idleConnectionTestPeriod
				+ ", maxPoolSize=" + maxPoolSize + ", minPoolSize=" + minPoolSize + ", initialPoolSize="
				+ initialPoolSize + "]";
	}

}
