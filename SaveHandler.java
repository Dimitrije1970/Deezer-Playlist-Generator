package com.mypackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHandler {

	private Playlist playlist;
	
	public SaveHandler(Playlist playlist) {
		this.playlist = playlist;
		
	}
	
	public void savePlaylist(String directory) {
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(directory));
			StringBuilder line = new StringBuilder();
			
			for(Track aTrack : playlist.getPlaylist()) {
				line.append(aTrack.getName() + " / " + aTrack.getArtist() + " / " + aTrack.getAlbum());
				fw.write(line.toString());
				fw.newLine();
				line.delete(0, line.length());
			}
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
