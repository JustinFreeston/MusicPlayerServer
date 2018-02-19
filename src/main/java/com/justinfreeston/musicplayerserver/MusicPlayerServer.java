package com.justinfreeston.musicplayerserver;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD.Method;
import kuusisto.tinysound.TinySound;

public class MusicPlayerServer {
	
	public static void main(String[] args) throws IOException {
		TinySound.init();
		
		try {
			WebServer webServer = new WebServer(9876);
			webServer.addProcess(Method.POST, new ProcessPost());
		} catch (IOException e) {
			System.err.println("Could not start web server:\n" + e);
		}
	}

}
