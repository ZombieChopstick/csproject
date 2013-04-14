package com.ruperthodgkins.csproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GameServer extends Thread {
	
	private HashMap<String,Socket> playerlist;
	private ServerSocket serverSock;
	private Socket playerSock;
	private BufferedReader ins;
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.start();
	}
	
	public GameServer() {
		playerlist = new HashMap<String,Socket>();
	}
	
	public void addPlayer(String p, Socket sock) {
		playerlist.put(p,sock);
	}
	
	public void removePlayer(String p) {
		playerlist.remove(p);
	}
	
	public void run() {
		try {
			serverSock = new ServerSocket(8000);
			System.out.println("Server running on port 8000");
			System.out.println("Waiting for players to join");
			while(true) {
				playerSock = serverSock.accept();
				ins = new BufferedReader(new InputStreamReader(playerSock.getInputStream()));
				String playerName = ins.readLine();
				addPlayer(playerName,playerSock);
				System.out.println(playerName + " has joined.");
				ins.close();
				
				if(playerlist.size() > 1) {
					Socket[] players = (Socket[]) playerlist.values().toArray();
					BufferedWriter p1Confirm = new BufferedWriter(new OutputStreamWriter(players[0].getOutputStream()));
					BufferedWriter p2Confirm = new BufferedWriter(new OutputStreamWriter(players[1].getOutputStream()));
					p1Confirm.write("JOINED");
					p2Confirm.write("JOINED");
					Thread t = new Thread(new RemoteGame(players[0],players[1]));
					t.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}