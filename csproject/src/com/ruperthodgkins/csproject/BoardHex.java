package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class BoardHex {
	private Vector2 coordinates;
	private float x;
	private float y;
	private Mesh mesh;
	private ArrayList<Vector2> vertices = new ArrayList<Vector2>();
	private Texture hexPic;
	private Texture hexHoverPic;
	private boolean selected = false;
	
	public BoardHex(Vector2 coord, float x, float y) {
		coordinates = coord;
		this.x = x;
		this.y = y;
		AssetManager manager = AssetsManager.getInstance();
		hexPic = manager.get(AssetsManager.HEXGREEN,Texture.class);
		hexHoverPic = manager.get(AssetsManager.HEXGREENHOVER,Texture.class);
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
	
	public Vector2 getCoordinates() {
		return coordinates;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getHexPic() {
		if(!selected) {
			return hexPic;
		}
		return hexHoverPic;
	}
	
	public Texture getHexHoverPic() {
		return hexHoverPic;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean select) {
		selected = select;
	}
	
	public boolean hit(float x, float y) {
		if(Intersector.isPointInPolygon(vertices, new Vector2(x,Game.getHeight() - y))) {
			return true;
		}
		return false;
	}
	
	public boolean hit(ArrayList<Vector2> vert) {
		float chvertices[] = new float[12];
		int i = 0;
		for(Vector2 v : vert) {
			chvertices[i] = v.x;
			chvertices[i++] = v.y;
			i++;
		}
		float hexvertices[] = new float[12];
		i=0;
		for(Vector2 v: vertices) {
			hexvertices[i] = v.x;
			hexvertices[i++] = v.y;
			i++;
		}
		if(Intersector.overlapConvexPolygons(new Polygon(hexvertices), new Polygon(chvertices)))
			return true;
		else
			return false;
	}
}