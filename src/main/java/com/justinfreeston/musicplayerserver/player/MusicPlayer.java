package com.justinfreeston.musicplayerserver.player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.justinfreeston.musicplayerserver.files.FileManager;
import com.justinfreeston.musicplayerserver.files.download.Downloader;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

public class MusicPlayer {
	
	private static MusicPlayer instance;
	private final FileManager fileManager;
	private MusicPlayerController controller;
	
	private MusicPlayer() {
		instance = this;
		// Music directory
		this.fileManager = new FileManager(Paths.get("./MusicPlayerServer/music/"));
		MusicPlayerModel model = new MusicPlayerModel();
		MusicPlayerView view = new MusicPlayerView();
		this.controller = new MusicPlayerController(model, view);
	}
	
	public static MusicPlayer getInstance() {
		if (instance == null) {
			new MusicPlayer();
		}
		return instance;
	}
	
	public void queue(String url) {
		File localFile = fileManager.getFile(url);
		if (localFile == null) {
			try {
				Downloader download = fileManager.download(url);
				while (download.isDownloading()) {
					// TODO Remove
					System.out.println(download.getDownloadPercent());
					System.out.println(download.getDownloadSize());
					System.out.println(download.getDownloadSpeed());
					Thread.sleep(500);
				}
				System.out.println(download.getFile()); // TODO Remove
				File file = Paths.get(download.getFile()).toFile(); // TODO Remove
				Music song = TinySound.loadMusic(file); // TODO Remove
				song.play(true); // TODO Remove
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		controller.queue();
	}
	
	public void play() {
		
	}
	
	public void pause() {
		
	}
	
	public void stop() {
		
	}
	
}
