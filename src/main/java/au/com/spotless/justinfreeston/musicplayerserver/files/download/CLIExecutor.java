package au.com.spotless.justinfreeston.musicplayerserver.files.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;

public class CLIExecutor {
	
	private ProcessBuilder processBuilder;
	private List<String> log;
	private CLIDownloaderProcess runner;
	
	public CLIExecutor(List<String> command) {
		this.processBuilder = new ProcessBuilder(command);
//		processBuilder.redirectOutput(Redirect.INHERIT);
//		processBuilder.redirectError(Redirect.INHERIT);
		start();
	}
	
	private void start() {
		this.runner = new CLIDownloaderProcess();
		Thread t = new Thread(this.runner);
		t.start();
	}
	
	public List<String> getLog() {
		return this.log;
	}
	
	public boolean isRunning() {
		if (this.runner != null) {
			// Process is in the middle of being initialized
			if (this.runner.process == null) {
				return true;
			}
			return this.runner.process.isAlive();
		}
		return false;
	}
	
	private class CLIDownloaderProcess implements Runnable {
		private Process process;
		
		{
			log = new ArrayList<>();
		}
		
		@Override
		public void run() {
			try {
				this.process = processBuilder.start();
				log();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void log() throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				log.add(line);
			}
		}
	}

}
