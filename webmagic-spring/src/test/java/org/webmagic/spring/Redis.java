package org.webmagic.spring;

import javax.xml.bind.annotation.XmlElement;

public class Redis {
	private String host;
	private int port;
	private String password;
	private int timeOut;
	private int dbIndex;

	public String getHost() {
		return host;
	}

	@XmlElement(name = "host")
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	@XmlElement(name = "port")
	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeOut() {
		return timeOut;
	}

	@XmlElement(name = "timeOut")
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	@XmlElement(name = "dbIndex")
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	@Override
	public String toString() {
		return "Redis [host=" + host + ", port=" + port + ", password=" + password + ", timeOut=" + timeOut
				+ ", dbIndex=" + dbIndex + "]";
	}

}
