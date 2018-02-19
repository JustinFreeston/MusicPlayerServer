package com.justinfreeston.musicplayerserver.files;

import java.io.IOException;
import java.nio.file.Path;

import com.justinfreeston.musicplayerserver.files.download.Downloader;
import com.justinfreeston.musicplayerserver.files.download.YouTubeDL;
import com.justinfreeston.musicplayerserver.files.download.YouTubeDLConfig;

public class DownloadManager {
	
	private Path directory;
	private YouTubeDLConfig ytdlConfig;
	
	public DownloadManager(Path directory) {
		this.directory = directory;
	}
	
	public Downloader download(String url) throws IOException, InterruptedException {
		if (this.ytdlConfig == null) {
			YouTubeDLConfig ytdlConfig = new YouTubeDLConfig(directory.resolve("../youtube-dl.exe").toFile());
			ytdlConfig.setFfmpegLocation(directory.resolve("../ffmpeg-3.4.1-win64-static/bin"));
			ytdlConfig.setOutputDirectory(directory);
			this.ytdlConfig = ytdlConfig;
		}
		Downloader ytdl = new YouTubeDL(this.ytdlConfig);
		ytdl.download(url);
		return ytdl;
	}

}
