package com.ruperthodgkins.csproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer extends Thread {
	
	private ArrayList<Socket> clientSessions;
	private ServerSocket serverSock;
	private Socket playerSock;
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.start();
	}
	
	public GameServer() {
		clientSessions = new ArrayList<Socket>();
	}
	
	public void addClientSession(Socket sock) {
		clientSessions.add(sock);
	}
	
	public void removeClientSession(Socket sock) {
		clientSessions.remove(sock);
	}
	
	public void pairPlayers() {
		if(clientSessions.size() % 2 == 0) {
			Socket player1 = clientSessions.get(0);
			Socket player2 = clientSessions.get(1);
			try {
				PrintWriter outs = new PrintWriter(player1.getOutputStream());
				outs.write(player2.getInetAddress().toString());
				PrintWriter outs2 = new PrintWriter(player2.getOutputStream());
				outs2.write(player1.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		try {
			serverSock = new ServerSocket(8080);
			System.out.println("Server running on port 8080");
			System.out.println("Waiting for players to join");
			while(true) {
				playerSock = serverSock.accept();
				GameServerClient clientSession = new GameServerClient(playerSock);
				clientSession.start();
				addClientSession(playerSock);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}