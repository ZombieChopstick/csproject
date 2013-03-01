package com.ruperthodgkins.csproject;

public class Timer extends Thread {
	private int minutes;
	private int seconds;
	private boolean running;
	
	public Timer() {
		minutes = 0;
		seconds = 0;
		running = false;
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
		while(true) {
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
