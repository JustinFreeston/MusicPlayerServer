package com.justinfreeston.musicplayerserver.files.download;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeDL implements Downloader {
	
	private YouTubeDLConfig config;
	private CLIExecutor download;
	
	public YouTubeDL(YouTubeDLConfig config) {
		this.config = config;
	}
	
	public void setConfig(YouTubeDLConfig config) {
		this.config = config;
	}
	
	public YouTubeDLConfig getConfig() {
		return this.config;
	}
	
	public void download(String url) throws IOException, InterruptedException {
		if (this.download != null) {
			throw new IllegalStateException("This object cannot be reused.");
		}
		List<String> command = config.getCLICommand(url);
		System.out.println(String.join(" ", command)); // TODO Remove
		this.download = new CLIExecutor(command);
	}

	@Override
	public boolean isDownloading() {
		if (this.download == null) {
			return false;
		}
		return this.download.isRunning();
	}
	
	@Override
	public float getDownloadPercent() {
		if (this.download == null) {
			return -1;
		}
		
		List<String> log = this.download.getLog();
		for (int i = log.size()-1; i >= 0; i--) {
			String line = log.get(i);
			if (line.startsWith("[download] ")) {
				Pattern pattern = Pattern.compile("[\\d\\.].*?(?=\\%)");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return Float.parseFloat(matcher.group(0));
				}
			}
		}
		
		return 0;
	}

	@Override
	public String getDownloadSize() {
		if (this.download == null) {
			return null;
		}
		
		List<String> log = this.download.getLog();
		for (int i = log.size()-1; i >= 0; i--) {
			String line = log.get(i);
			if (line.startsWith("[download] ")) {
				Pattern pattern = Pattern.compile("(?<=of\\ )(.*?)(?=\\ at)");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return matcher.group(0).trim();
				}
			}
		}
		
		return null;
	}

	@Override
	public String getDownloadSpeed() {
		if (this.download == null) {
			return null;
		}
		
		List<String> log = this.download.getLog();
		for (int i = log.size()-1; i >= 0; i--) {
			String line = log.get(i);
			if (line.startsWith("[download] ")) {
				Pattern pattern = Pattern.compile("(?<=at\\ )(.*?)(?=\\ ETA)");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return matcher.group(0).trim();
				}
			}
		}
		
		return null;
	}

	@Override
	public int getETA() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFile() {
		if (this.download == null) {
			return null;
		}
		
		List<String> log = this.download.getLog();
		for (int i = log.size()-1; i >= 0; i--) {
			String line = log.get(i);
			if (line.startsWith("[ffmpeg] ")) {
				Pattern pattern = Pattern.compile("(?<=:\\ )(.*?)$");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					return matcher.group(0).trim();
				}
			}
		}
		
		return null;
	}
	
}

