package org.webmagic.spring;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Requests {
	private List<Request> requests;

	public List<Request> getRequests() {
		return requests;
	}

	@XmlElement(name = "request")
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	@Override
	public String toString() {
		return "Requests [requests=" + requests + "]";
	}

}
