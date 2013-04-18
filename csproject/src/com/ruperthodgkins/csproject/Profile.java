package com.ruperthodgkins.csproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Profile {
	private String currentProfile;
	private static Profile instance;
	private Player playerInstance;
	
	public Profile() {
		currentProfile = "Load Profile";
		loadLastProfile();
	}
	
	public static Profile getInstance() {
		if(instance == null) {
			instance = new Profile();
		}
		return instance;
	}
	
	public String getCurrentProfile() {
		return currentProfile;
	}
	
	public Player getPlayerInstance() {
		if(!currentProfile.equals("Load Profile")) {
			playerInstance = new Player();
			playerInstance.setName(currentProfile);
			return playerInstance;
		}
		return null;
	}
	
	public void loadLastProfile() {
		loadProfile("last");
	}
	
	public void loadProfile(String profile) {
		File profileFile = new File("profiles/" + profile + ".txt");
		if(profileFile.exists()) {
			Scanner scan;
			try {
				scan = new Scanner(profileFile);
				currentProfile = scan.next();
				System.out.println("Loaded Profile: " + currentProfile);
				scan.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createProfile(String profile) {
		
	}
}
