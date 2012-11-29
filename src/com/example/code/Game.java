package com.example.code;

public class Game {
	private int score;
	private String difficulty;
	private Hole[] hole;
	private int timeCounter;
	private boolean sound;
	private boolean vibrate;

	public Game(int score, String difficulty, int timeCounter, boolean sound, boolean vibrate) {
		this.score = score;
		this.difficulty = difficulty;
		this.sound = sound;
		this.vibrate = vibrate;
		hole = new Hole[6];
		for (int i = 0; i < hole.length; i++) {
			hole[i] = new Hole(i, false, false, false);
		}
		this.timeCounter = timeCounter;
	}
	public Game(String difficulty, boolean sound, boolean vibrate) {
		this.score = 0;
		this.difficulty = difficulty;
		this.sound = sound;
		this.vibrate = vibrate;
		hole = new Hole[6];
		for (int i = 0; i < hole.length; i++) {
			hole[i] = new Hole(i, false, false, false);
		}
		this.timeCounter = 30;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Hole[] getHole() {
		return hole;
	}

	public void setHole(Hole[] hole) {
		this.hole = hole;
	}

	public int getTimeCounter() {
		return timeCounter;
	}

	public void setTimeCounter(int timeCounter) {
		this.timeCounter = timeCounter;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}

	public boolean isVibrate() {
		return vibrate;
	}

	public void setVibrate(boolean vibrate) {
		this.vibrate = vibrate;
	}

	public void setNoBall(int n){
		this.hole[n].setState(true);
		this.hole[n].setExtra(false);
		this.hole[n].setMine(false);
	}
	
}
