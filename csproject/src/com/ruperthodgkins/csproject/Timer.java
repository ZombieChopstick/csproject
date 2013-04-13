package com.ruperthodgkins.csproject;

public class Timer extends Thread {
	private static Timer instance;
	private int minutes;
	private int seconds;
	private boolean running;
	
	private Timer() {
		minutes = 0;
		seconds = 0;
		running = false;
	}
	
	public static Timer getInstance() {
		if(instance == null) {
			instance = new Timer();
		}
		return instance;
	}
	
	public void startTimer() {
		if(!running) {
			running = true;
			run();
		}
	}
	
	public void stopTimer() {
		if(running) {
			running = false;
		}
	}
	
	public void run() {
		while(running) {
			try {
				Thread.sleep(1000);
				seconds++;
				if(seconds == 60) {
					seconds = 0;
					minutes++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void end() {
		instance = null;
	}
	
	public String getGameTime() {
		if(seconds < 10 && minutes < 10)
			return "0" + minutes + ":" + "0" + seconds;
		else if (seconds > 10 && minutes < 10)
			return "0" + minutes + ":" + seconds;
		else if(seconds < 10 && minutes > 10)
			return minutes + ":0" + seconds;
		else
			return minutes + ":" + seconds;
	}
}
