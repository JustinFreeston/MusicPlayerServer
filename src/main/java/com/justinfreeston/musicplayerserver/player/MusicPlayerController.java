package com.justinfreeston.musicplayerserver.player;

public class MusicPlayerController {
	
	private final MusicPlayerModel model;
	private final MusicPlayerView view;
	
	public MusicPlayerController(MusicPlayerModel model, MusicPlayerView view) {
		this.model = model;
		this.view = view;
	}

}
