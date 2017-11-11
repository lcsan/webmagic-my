package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Header {

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	@XmlAttribute(name = "key")
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Header [key=" + key + ", value=" + value + "]";
	}

}
