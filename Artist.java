package com.mypackage;

import java.net.URL;

public class Artist {
	
	private int id;
	private String name;
	private URL tracklist;
	
	public Artist(int id, String name, URL tracklist) {
		this.id = id;
		this.name = name;
		this.tracklist = tracklist;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public URL getTracklist() {
		return tracklist;
	}

}
