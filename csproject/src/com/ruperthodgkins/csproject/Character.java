package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Polygon;

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
	private int x;
	private int y;
	private Polygon bbox;
	private float[] vertices = {31.5f, 72f, 63f, 54f, 63f, 18f, 31.5f, 0f, 0f, 18f, 0f, 54f, 31.5f, 72f};
	private float[] vertices2 = {0,0,100,100,100,200,0,200,0,0};
	private Mesh mesh;
	
	/* Creates a new character with the specified arguments
	 * @param String name
	 * @param Texture picture
	 * @param int level
	 * @param int currentXp
	 * @param int attack
	 * @param int defence
	 * @param int strength
	 * @param int magic
	 * @param int hp
	 */
	public Character(String name, Texture pic, int lev, int currXP, int att, int def, int str, int mag, int hp) {
		this.name = name;
		level = lev;
		xp = currXP;
		attack = att;
		defence = def;
		strength = str;
		magic = mag;
		this.hp = hp;
		charPic = pic;
		x = 0;
		y = 0;
		bbox = new Polygon(vertices2);
		mesh = new Mesh(true,3,3,new VertexAttribute(Usage.Position,3,"a_position"));
		mesh.setVertices(new float[] { 100f, 100f, 0,
                120f, 200f, 0,
                80, 200f, 0 });   
		mesh.setIndices(new short[] { 0, 1, 2 });
	}
	
	public Character(int x, int y, String name, Texture pic, int lev, int currXP, int att, int def, int str, int mag, int hp) {
		this.name = name;
		level = lev;
		xp = currXP;
		attack = att;
		defence = def;
		strength = str;
		magic = mag;
		this.hp = hp;
		charPic = pic;
		this.x = x;
		this.y = y;
		bbox = new Polygon(vertices2);
		mesh = new Mesh(true,6,6,new VertexAttribute(Usage.Position,3,"a_position"));
		//mesh.setVertices(new float[] { 700f, 400f, 0,
        //        720f, 200f, 0,
        //        680, 200f, 0 });     
		mesh.setVertices(new float[] { 800f, 400f, 0, x + 63f, y + 54f, 0, x + 63f, y + 18f, 0, x - 31.5f, y, 0, x, y + 18f, 0, x, y+54f, 0});
		//mesh.setIndices(new short[] { 0, 1, 2 });
		mesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5 });
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}
}
