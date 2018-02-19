package com.justinfreeston.musicplayerserver.files.download;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class YouTubeDLConfig {
	private File exeFile;
	private Path outputDirectory;
	private String nameFormat;
	private boolean extractAudio;
	private String audioFormat;
	private Path ffmpegLocation;
	private boolean restrictFilenames;

	{
		this.exeFile = Paths.get("./MusicPlayerServer/youtube-dl.exe").toFile();
		this.outputDirectory = null;
		this.nameFormat = "%(title)s-%(id)s.%(ext)s";
		this.extractAudio = true;
		this.audioFormat = "wav";
		this.ffmpegLocation = null;
		this.restrictFilenames = true;
	}

	// Assumed file is ./MusicPlayerServer/youtube-dl.exe and is already set above
	public YouTubeDLConfig() {
	}

	public YouTubeDLConfig(File exeFile) {
		this.exeFile = exeFile;
	}

	public void setExeFile(File exeFile) {
		this.exeFile = exeFile;
	}

	public File getExeFile() {
		return this.exeFile;
	}

	public void setOutputDirectory(Path outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Path getOutputDirectory() {
		return this.outputDirectory;
	}

	public void setNameFormat(String nameFormat) {
		this.nameFormat = nameFormat;
	}

	public String getNameFormat() {
		return this.nameFormat;
	}

	public void setExtractAudio(boolean extractAudio) {
		this.extractAudio = extractAudio;
	}

	public boolean isExtractAudio() {
		return this.extractAudio;
	}

	public void setAudioFormat(String audioFormat) {
		this.audioFormat = audioFormat;
	}

	public String getAudioFormat() {
		return this.audioFormat;
	}

	public void setFfmpegLocation(Path ffmpegLocation) {
		this.ffmpegLocation = ffmpegLocation;
	}

	public Path getFfmpegLocation() {
		return this.ffmpegLocation;
	}

	public void setRestrictFilenames(boolean restrictFilenames) {
		this.restrictFilenames = restrictFilenames;
	}

	public boolean isRestrictFilenames() {
		return this.restrictFilenames;
	}

	public List<String> getCLICommand(String url) throws IOException {
		List<String> call = new ArrayList<>();
		call.add(exeFile.getCanonicalPath());

		call.add("--output");
		if (outputDirectory != null && outputDirectory.toFile().isDirectory()) {
			call.add(outputDirectory.toFile().getCanonicalPath() + "\\" + nameFormat);
		} else {
			call.add(nameFormat);
		}

		if (extractAudio) {
			call.add("--extract-audio");
			call.add("--audio-format");
			call.add(audioFormat);
		}
		if (ffmpegLocation != null) {
			call.add("--ffmpeg-location");
			call.add(ffmpegLocation.toFile().getCanonicalPath());
		}
		if (restrictFilenames) {
			call.add("--restrict-filenames");
		}

		call.add(url);

		return call;
	}

}

