package au.com.spotless.justinfreeston.musicplayerserver.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import au.com.spotless.justinfreeston.musicplayerserver.files.download.Downloader;

public class FileManager {
	
	private final Path directory;
	private final DownloadManager downloadManager;
	
	public FileManager(Path directory) {
		this.directory = directory;
		this.downloadManager = new DownloadManager(directory);
	}
	
	// TODO
	public boolean fileExists(String url) {
		return false;
	}
	
	// TODO
	public File getFile(String url) {
		return null;
	}
	
	public Downloader download(String url) throws IOException, InterruptedException {
		return downloadManager.download(url);
	}

}
