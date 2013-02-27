package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

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
	private Mesh mesh;
	private ArrayList<Vector2> vertices = new ArrayList<Vector2>();
	
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
		mesh = new Mesh(true,6,6,new VertexAttribute(Usage.Position,3,"a_position")); 
		float[] v = { this.x, this.y+18f, 0, 
				   this.x, this.y + 54f, 0, 
				   this.x + 31.5f, this.y +72f, 0, 
				   this.x + 63f, this.y + 54f, 0, 
				   this.x + 63f, this.y + 18f, 0, 
				   this.x + 31.5f, this.y, 0};
		mesh.setVertices(v);
		mesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5});
		vertices.add(new Vector2(this.x,this.y+18f));
		vertices.add(new Vector2(this.x,this.y+54f));
		vertices.add(new Vector2(this.x+31.5f,this.y+72f));
		vertices.add(new Vector2(this.x+63f,this.y+54f));
		vertices.add(new Vector2(this.x+63f,this.y+18f));
		vertices.add(new Vector2(this.x+31.5f,this.y));
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
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		float[] v = { this.x, this.y+18f, 0, 
				   this.x, this.y + 54f, 0, 
				   this.x + 31.5f, this.y +72f, 0, 
				   this.x + 63f, this.y + 54f, 0, 
				   this.x + 63f, this.y + 18f, 0, 
				   this.x + 31.5f, this.y, 0};
		mesh.setVertices(v);
		mesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5});
		vertices.clear();
		vertices.add(new Vector2(this.x,this.y+18f));
		vertices.add(new Vector2(this.x,this.y+54f));
		vertices.add(new Vector2(this.x+31.5f,this.y+72f));
		vertices.add(new Vector2(this.x+63f,this.y+54f));
		vertices.add(new Vector2(this.x+63f,this.y+18f));
		vertices.add(new Vector2(this.x+31.5f,this.y));
	}
	
	public boolean hit(int x, int y) {
		if(Intersector.isPointInPolygon(vertices, new Vector2(x,Game.getHeight() - y))) {
			return true;
		}
		return false;
	}
}