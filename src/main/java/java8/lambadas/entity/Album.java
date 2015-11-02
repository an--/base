package java8.lambadas.entity;

import java.util.List;


public class Album {
	
	private String name;
	
	private List<String> tracks;

	private List<String> musicans;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMusicans() {
		return musicans;
	}

	public void setMusicans(List<String> musicans) {
		this.musicans = musicans;
	}

	public List<String> getTracks() {
		return tracks;
	}

	public void setTracks(List<String> tracks) {
		this.tracks = tracks;
	}

	
}
