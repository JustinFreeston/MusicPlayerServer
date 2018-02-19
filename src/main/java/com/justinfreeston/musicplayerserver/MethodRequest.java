package com.justinfreeston.musicplayerserver;

import java.util.Map;

public abstract class MethodRequest {
	
	private Map<String, String> headers;
	private Map<String, String> body;

	public void receiveRequest(Map<String, String> headers, Map<String, String> body) {
		this.headers = headers;
		this.body = body;
		processRequest();
	}
	
	public abstract void processRequest();
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public Map<String, String> getBody() {
		return body;
	}

}
