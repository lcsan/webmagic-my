package org.webmagic.xml.bean;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;

public class Urls {
	private String[] url;

	public String[] getUrl() {
		return url;
	}

	@XmlElement(name = "url")
	public void setUrl(String[] url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Urls [url=" + Arrays.toString(url) + "]";
	}

}
