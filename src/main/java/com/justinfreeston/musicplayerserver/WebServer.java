package com.justinfreeston.musicplayerserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class WebServer extends NanoHTTPD {
	
	private Map<Method, MethodRequest> processes;
	
	{
		processes = new HashMap<Method, MethodRequest>();
	}
	
	public WebServer(int port) throws IOException {
		super(port);
		start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
	}
	
	@Override
	public Response serve(IHTTPSession session) {
		MethodRequest request = this.processes.get(session.getMethod());
		if (request != null) {
			Map<String, String> body = new HashMap<String, String>();
			try {
				session.parseBody(body);
			} catch (IOException | ResponseException e) {
				e.printStackTrace();
			}
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					request.receiveRequest(session.getHeaders(), body);
				}
			});
			t.start();
			return newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_HTML, "<html><body><h1>Success</h1></body></html>");
		}
		
		return newFixedLengthResponse(Status.METHOD_NOT_ALLOWED, NanoHTTPD.MIME_HTML, "<html><body><h1>Unsupported</h1></body></html>");
	}
	
	public void addProcess(Method method, MethodRequest request) {
		this.processes.put(method, request);
	}
	
	public void removeProcess(Method method) {
		this.processes.remove(method);
	}
	
	public MethodRequest getProcess(Method method) {
		return this.processes.get(method);
	}
	
}
