package com.mypackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestHandler {

	public Artist search_for_artist(String searchbar_content) {
		
		String query = null;
		String[] words = searchbar_content.split(" ");
		
		if(words.length > 1) {
			query = words[1];
		}else {
			query = searchbar_content;
		}
		
		try {
			URL url = new URL("https://api.deezer.com/search/artist?q=artist:\"" + query + "\"");
			String read_line = null;
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int response_code = connection.getResponseCode();
			
			if(response_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response_string = new StringBuilder();
				
				while((read_line = in.readLine()) != null) {
					response_string.append(read_line);
				}
				
				in.close();
				
				JSONObject response_json = new JSONObject(response_string.toString());
				JSONArray data_json = response_json.getJSONArray("data");
				JSONObject artist_json = data_json.getJSONObject(0);
				
				URL tracklist = new URL((String) artist_json.get("tracklist"));
				
				Artist artist = new Artist((int) artist_json.get("id"), (String) artist_json.get("name"), tracklist);
			
				return artist;
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception i) {
			JOptionPane.showMessageDialog(null, "Artist not found. You have to be more accurate.");
		}	
		
		return null;
	}
	
	public ArrayList<Track> getArtistTracks(Artist artist){
		
		try {
			URL url = artist.getTracklist();
			String read_line = null;
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int response_code = connection.getResponseCode();
			
			if(response_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response_string = new StringBuilder();
				
				while((read_line = in.readLine()) != null) {
					response_string.append(read_line);
				}
				
				in.close();
				
				JSONObject response_json = new JSONObject(response_string.toString());
				JSONArray data_json = response_json.getJSONArray("data");
				
				ArrayList<Track> tracks_list = new ArrayList<Track>();
				
				int range = 0;
					
				if(data_json.length() < 7) {
					range = data_json.length();
				}else {
					range = 7;
				}
					
				Random rand = new Random();
				ArrayList<Integer> used_nums = new ArrayList<Integer>();
					
				for(int i = 0;i < range;i++) {
					int random_index = rand.nextInt(data_json.length());
						
					while(used_nums.contains(random_index)) {
						random_index = rand.nextInt(data_json.length());
					}
						
					used_nums.add(random_index);
						
					JSONObject track_json = data_json.getJSONObject(random_index);
					JSONObject artist_json = track_json.getJSONObject("artist");
					JSONObject album_json = track_json.getJSONObject("album");
						
					Track track_object = new Track(track_json.getInt("id"),track_json.getString("title"),artist_json.getString("name"),album_json.getString("title"),track_json.getInt("duration"));
					tracks_list.add(track_object);

				}
				
				return tracks_list;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
