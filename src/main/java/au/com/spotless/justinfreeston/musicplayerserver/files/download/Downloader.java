package au.com.spotless.justinfreeston.musicplayerserver.files.download;

import java.io.IOException;

public interface Downloader {
	
	public void download(String url) throws IOException, InterruptedException;
	public boolean isDownloading();
	public float getDownloadPercent();
	public String getDownloadSize();
	public String getDownloadSpeed();
	public int getETA();
	public String getFile();

}
