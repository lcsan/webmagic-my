package org.webmagic.spring;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Cookies {

	private List<Cookie> cookies = new ArrayList<Cookie>();

	public List<Cookie> getCookies() {
		return cookies;
	}

	@XmlElement(name = "cookie")
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	@Override
	public String toString() {
		return "Cookies [cookies=" + cookies + "]";
	}

}
