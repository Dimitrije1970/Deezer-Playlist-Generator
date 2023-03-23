package com.mypackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener{

	public static int WIDTH = 490;
	public static int HEIGHT  = 790;
	
	private JTextField search_edit;
	private JList<String> artists_list_gui;
	private JTable tracks_list_gui;
	private JButton add_button, create_button,remove_all_button,remove_last_button,save_button;
	private JScrollPane scroll_pane,scroll_pane_up;
	private JFileChooser file_chooser;
	
	private ArrayList<Artist> artists_list = new ArrayList<Artist>();
	private DefaultListModel<String> artists_names_list = new DefaultListModel<String>();
	private DefaultTableModel table_data = new DefaultTableModel(0,0);
	
	private RequestHandler rh;
	private Playlist playlist;
	
	public Main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super("Deezer Playlists");
		setSize(WIDTH,HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		JPanel pane = new JPanel();
		pane.setLayout(null);
		
		search_edit = new JTextField();
		search_edit.setBounds(20,20,200,20);
		search_edit.requestFocus();
		pane.add(search_edit);
		
		add_button = new JButton("Add Artist");
		add_button.setBounds(225,20,100,20);
		add_button.setFocusPainted(false);
		add_button.addActionListener(this);
		pane.add(add_button);
		
		create_button = new JButton("Create Playlist");
		create_button.setBounds(330,20,120,20);
		create_button.setFocusPainted(false);
		create_button.addActionListener(this);
		pane.add(create_button);
		
		artists_list_gui = new JList<String>(artists_names_list);
		
		scroll_pane_up = new JScrollPane(artists_list_gui);
		scroll_pane_up.setBounds(20, 60, 430, 200);
		scroll_pane_up.setViewportView(artists_list_gui);
		pane.add(scroll_pane_up);
		
		remove_last_button = new JButton("Remove Last");
		remove_last_button.setBounds(20,270,210,20);
		remove_last_button.setFocusPainted(false);
		remove_last_button.addActionListener(this);
		pane.add(remove_last_button);
		
		remove_all_button = new JButton("Remove All");
		remove_all_button.setBounds(240,270,210,20);
		remove_all_button.setFocusPainted(false);
		remove_all_button.addActionListener(this);
		pane.add(remove_all_button);
		
		String[] cols = new String[] {"Title","Artist","Album"};
		table_data.setColumnIdentifiers(cols);
		
		tracks_list_gui = new JTable(table_data);
		tracks_list_gui.setShowVerticalLines(false);

		scroll_pane = new JScrollPane(tracks_list_gui);
		scroll_pane.setBounds(20, 310, 430, 400);
		scroll_pane.setViewportView(tracks_list_gui);
		pane.add(scroll_pane);
		
		save_button = new JButton("Save Playlist as .txt");
		save_button.setBounds(20,720,430,20);
		save_button.setFocusPainted(false);
		save_button.addActionListener(this);
		pane.add(save_button);
		
		add(pane);
		setVisible(true);
		
		
		rh = new RequestHandler();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add_button) {
			Artist new_artist = rh.search_for_artist(search_edit.getText());
			
			if(!artists_names_list.contains(new_artist.getName())) {
				artists_names_list.addElement(new_artist.getName());
				artists_list.add(new_artist);
			}
		}else if(e.getSource() == remove_last_button) {
			artists_names_list.removeElementAt(artists_names_list.size()-1);
			artists_list.remove(artists_list.size()-1);
			
		}else if(e.getSource() == remove_all_button) {
			artists_names_list.removeAllElements();
			artists_list.removeAll(artists_list);
			
		}else if(e.getSource() == create_button) {
			for(int i = table_data.getRowCount()-1;i > -1;i--) {
				table_data.removeRow(i);
			}
			
			playlist = new Playlist();
			
			for(Artist aArtist : artists_list) {
				playlist.addTracks(rh.getArtistTracks(aArtist));
			}
			
			for(int i = 0;i < playlist.getSize();i++) {
				table_data.addRow(new String[] {playlist.getTrack(i).getName(),playlist.getTrack(i).getAlbum(),playlist.getTrack(i).getArtist()});
			}
		}else if(e.getSource() == save_button) {
			file_chooser = new JFileChooser();
			file_chooser.setCurrentDirectory(new File("C:/Users/Dimitrije/Desktop"));
			file_chooser.showSaveDialog(null);
			
			String directory = file_chooser.getSelectedFile() + ".txt";
			
			if(directory.length() > 4) {
				SaveHandler sh = new SaveHandler(playlist);
				sh.savePlaylist(directory);
			}

		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		new Main();
	}


	
}
