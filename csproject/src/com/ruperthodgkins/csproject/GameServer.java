package com.ruperthodgkins.csproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GameServer extends Thread {
	
	private HashMap<Player,Socket> playerlist;
	private ServerSocket serverSock;
	private Socket playerSock;
	private BufferedReader ins;
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.start();
	}
	
	public GameServer() {
		playerlist = new HashMap<Player,Socket>();
	}
	
	public void addPlayer(Player p, Socket sock) {
		playerlist.put(p,sock);
	}
	
	public void removePlayer(Player p) {
		playerlist.remove(p);
	}
	
	public void run() {
		try {
			serverSock = new ServerSocket(8000);
			playerSock = serverSock.accept();
			ins = new BufferedReader(new InputStreamReader(playerSock.getInputStream()));
			Player p = new Player();
			p.setName(ins.readLine());
			addPlayer(p,playerSock);
			System.out.println(playerlist);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}