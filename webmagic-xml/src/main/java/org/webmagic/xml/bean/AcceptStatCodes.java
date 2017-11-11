package org.webmagic.xml.bean;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class AcceptStatCodes {
	private Set<Integer> acceptStatCode;

	public Set<Integer> getAcceptStatCode() {
		return acceptStatCode;
	}

	@XmlElement(name = "acceptStatCode")
	public void setAcceptStatCode(Set<Integer> acceptStatCode) {
		this.acceptStatCode = acceptStatCode;
	}

	@Override
	public String toString() {
		return "AcceptStatCodes [acceptStatCode=" + acceptStatCode + "]";
	}

}
