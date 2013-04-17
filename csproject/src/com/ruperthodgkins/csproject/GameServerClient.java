package com.ruperthodgkins.csproject;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class GameServerClient extends Thread {
	Socket conn = null;
	
	public GameServerClient(Socket s) {
		conn = s;
	}
	
	public void run() {
		try {
			Scanner scanin = new Scanner(conn.getInputStream());
			String line=null;
	        int lineCount=0;
	        String[] lines = new String[32];
			while(true) {
				//write to stream, send out to different client when allocated to game
				line = scanin.nextLine();
                if(line.length()==0) break;
                lines[lineCount] = line;
                lineCount+=1;
			}
			if(lines[0] !=null || lines[0] !="") System.out.println(lines[0] + " has joined the game.");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
}
