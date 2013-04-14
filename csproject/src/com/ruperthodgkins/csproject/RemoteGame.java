package com.ruperthodgkins.csproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RemoteGame implements Runnable {
	
	public Socket player1;
	public Socket player2;
	public Player p1;
	public Player p2;
	
	public RemoteGame(Socket player1, Socket player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public void addPlayer(Socket player) {
		if(player1 == null) player1 = player;
		else player2 = player;
	}
	
	@Override
	public void run() {
		try {
			
			ObjectInputStream player1InStream = new ObjectInputStream(player1.getInputStream());
			ObjectOutputStream player1OutStream = new ObjectOutputStream(player1.getOutputStream());
			ObjectInputStream player2InStream = new ObjectInputStream(player2.getInputStream());
			ObjectOutputStream player2OutStream = new ObjectOutputStream(player2.getOutputStream());
			
			Object player1 = player1InStream.readObject();
			p1 = (Player) player1;
			player2OutStream.writeObject(p1);
			Object player2 = player2InStream.readObject();
			p2 = (Player) player2;
			player1OutStream.writeObject(p1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
