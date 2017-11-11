package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
	private String host = "127.0.0.1";
	private int port = 6379;
	private String password;
	private int timeout;
	private int dbIndex;

	private JedisPool redisPool;

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

	public int getTimeout() {
		return timeout;
	}

	@XmlElement(name = "timeout")
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	@XmlElement(name = "dbIndex")
	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public JedisPool getRedisPool() {
		if (null == redisPool) {
			if (timeout > 0) {
				if (StringUtils.isNotBlank(password)) {
					if (dbIndex > 0) {
						redisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, dbIndex);
						return redisPool;
					}
					redisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
					return redisPool;
				}
				redisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
				return redisPool;
			}
			redisPool = new JedisPool(new JedisPoolConfig(), host, port);
			return redisPool;
		}
		return redisPool;
	}

	@Override
	public String toString() {
		return "Redis [host=" + host + ", port=" + port + ", password=" + password + ", timeOut=" + timeout
				+ ", dbIndex=" + dbIndex + "]";
	}

}
