package org.webmagic.spring;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Headers {

	private List<Header> headers = new ArrayList<Header>();

	public List<Header> getHeaders() {
		return headers;
	}

	@XmlElement(name = "header")
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return "Headers [headers=" + headers + "]";
	}

}
