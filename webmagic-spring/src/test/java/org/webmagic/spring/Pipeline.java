package org.webmagic.spring;

import javax.xml.bind.annotation.XmlValue;

public class Pipeline {
	private String code;

	public String getCode() {
		return code;
	}

	@XmlValue
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Pipeline [code=" + code + "]";
	}

}
