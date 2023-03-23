package com.mypackage;

public class Track {

	private int id, duration;
	private String name, album, artist;
	
	public Track(int id, String name, String album, String artist, int duration) {
		this.id = id;
		this.name = name;
		this.album = album;
		this.artist = artist;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public String getName() {
		return name;
	}

	public String getAlbum() {
		return album;
	}

	public String getArtist() {
		return artist;
	}
	
	
}
