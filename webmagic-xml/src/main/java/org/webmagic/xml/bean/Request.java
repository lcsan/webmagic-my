package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlElement;

public class Request {

	private String url;

	private String method;

	private String requestBody;
	private long priority;

	private String cookie;

	private String header;
	private Cookies cookies;

	private Headers headers;

	public String getUrl() {
		return url;
	}

	@XmlElement(name = "url")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	@XmlElement(name = "method")
	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestBody() {
		return requestBody;
	}

	@XmlElement(name = "requestBody")
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public long getPriority() {
		return priority;
	}

	@XmlElement(name = "priority")
	public void setPriority(long priority) {
		this.priority = priority;
	}

	public String getCookie() {
		return cookie;
	}

	@XmlElement(name = "cookie")
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getHeader() {
		return header;
	}

	@XmlElement(name = "header")
	public void setHeader(String header) {
		this.header = header;
	}

	public Cookies getCookies() {
		return cookies;
	}

	@XmlElement(name = "cookies")
	public void setCookies(Cookies cookies) {
		this.cookies = cookies;
	}

	public Headers getHeaders() {
		return headers;
	}

	@XmlElement(name = "headers")
	public void setHeaders(Headers headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return "Request [url=" + url + ", method=" + method + ", requestBody=" + requestBody + ", priority=" + priority
				+ ", cookie=" + cookie + ", header=" + header + ", cookies=" + cookies + ", headers=" + headers + "]";
	}

}
