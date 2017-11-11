package org.webmagic.xml.bean;

import javax.xml.bind.annotation.XmlValue;

import org.webmagic.xml.GroovyPageModelPipeline;

public class Pipeline {

	private String code;

	private GroovyPageModelPipeline pipeline;

	public String getCode() {
		return code;
	}

	@XmlValue
	public void setCode(String code) {
		this.code = code;
	}

	public GroovyPageModelPipeline getPipeline() {
		if (null == pipeline) {
			pipeline = new GroovyPageModelPipeline(code);
		}
		return pipeline;
	}

	@Override
	public String toString() {
		return "Pipeline [code=" + code + "]";
	}

}
