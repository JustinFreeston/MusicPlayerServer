package au.com.spotless.justinfreeston.musicplayerserver;

import org.json.JSONObject;

import au.com.spotless.justinfreeston.musicplayerserver.player.MusicPlayer;

public class ProcessPost extends MethodRequest {
	
	private JSONObject data;

	@Override
	public void processRequest() {
		this.data = new JSONObject(getBody().get("postData"));
		
		MusicPlayer player = MusicPlayer.getInstance();
		switch (this.data.getString("action")) {
		case "queue":
			player.queue(this.data.getString("url"));
			break;
		case "play":
			player.play();
			break;
		case "pause":
			player.pause();
			break;
		case "stop":
			player.stop();
			break;
		default:
		}
	}

}
