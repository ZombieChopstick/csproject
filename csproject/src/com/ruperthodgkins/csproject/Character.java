package com.ruperthodgkins.csproject;

import com.badlogic.gdx.graphics.Texture;

public class Character {
	private String name;
	private int level;
	private int xp;
	private int attack;
	private int defence;
	private int strength;
	private int magic;
	private int hp;
	private Texture charPic;
	
	public Character(String name, int lev, int currXP, int att, int def, int str, int mag, int hp) {
		this.name = name;
		level = lev;
		xp = currXP;
		attack = att;
		defence = def;
		strength = str;
		magic = mag;
		this.hp = hp;
	}

	public String getName() {
		return name;
	}


	public int getLevel() {
		return level;
	}
	
	public int getXP() {
		return xp;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getMagic() {
		return magic;
	}
	
	public int getHP() {
		return hp;
	}

	public Texture getCharPic() {
		return charPic;
	}
}
