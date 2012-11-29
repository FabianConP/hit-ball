package com.example.code;

public class Hole {
	private int id;
	private boolean state;
	private boolean mine;
	private boolean extra;

	public Hole(int id, boolean state, boolean mine, boolean extra) {
		this.id = id;
		this.state = state;
		this.mine = mine;
		this.extra = extra;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isExtra() {
		return extra;
	}

	public void setExtra(boolean extra) {
		this.extra = extra;
	}

}
