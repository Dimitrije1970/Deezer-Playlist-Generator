package com.mypackage;

import java.util.ArrayList;

public class Playlist {
	
	private ArrayList<Track> playlist = new ArrayList<Track>();
	
	public void addTracks(ArrayList<Track> tracks) {
		playlist.addAll(tracks);
	}
	
	public void removeLastTrack() {
		playlist.remove(playlist.size());
	}
	
	public void removeAllTracks() {
		playlist.removeAll(playlist);
	}

	public ArrayList<Track> getPlaylist() {
		return playlist;
	}
	
	public int getSize() {
		return playlist.size();
	}
	
	public Track getTrack(int x) {
		return playlist.get(x);
	}
	
}
