package com.aetherpass.editor;

import java.awt.*;
import java.util.ArrayList;


public class LevelObject {
	public ArrayList<Point> points = new ArrayList<Point>();
	
	public double angle;
	
	public int type;
	protected boolean collidable;

	protected int selectedVertex = -1;

	protected boolean moving;
	protected int initialMovePointX;
	protected int initialMovePointY;
	protected int lastMoveDistanceX;
	protected int lastMoveDistanceY;
	
	public LevelObject(int x1, int y1, int x2, int y2) {
		points.add(new Point(LevelEditor.snap(x1), LevelEditor.snap(y1)));
		points.add(new Point(LevelEditor.snap(x2), LevelEditor.snap(y2)));
	}
	
	public LevelObject(int x1, int y1, double angle) {
		points.add(new Point(LevelEditor.snap(x1), LevelEditor.snap(y1)));
		this.angle = angle;
	}
	
	public LevelObject(int x1, int y1) {
		points.add(new Point(LevelEditor.snap(x1), LevelEditor.snap(y1)));
	}
	
	public void move(int x, int y) {
		if (!moving) {
			initialMovePointX = LevelEditor.snap(x);
			initialMovePointY = LevelEditor.snap(y);
			lastMoveDistanceX = 0;
			lastMoveDistanceY = 0;
			moving = true;
		}

		x = LevelEditor.snap(x);
		y = LevelEditor.snap(y);

		int dx = x - initialMovePointX - lastMoveDistanceX;
		int dy = y - initialMovePointY - lastMoveDistanceY;
		lastMoveDistanceX = x - initialMovePointX;
		lastMoveDistanceY = y - initialMovePointY;

		if (selectedVertex == -1) {
			for (Point p : points) {
				p.x += dx;
				p.y += dy;

				p.x = LevelEditor.snap(p.x);
				p.y = LevelEditor.snap(p.y);
			}
		} else {
			points.get(selectedVertex).x = x;
			points.get(selectedVertex).y = y;
		}
	}

	public void clicked() {
		// this means that the user clicked somewhere on the map using the move tool
		moving = false;
	}
	
	public boolean contains(int x, int y) {
		return false;
	}

	public boolean removeSelectedVertex() {
		return false;
	}
	
	public void drawEditing(Graphics g, int x, int y) {
		
	}

	public void draw(Graphics g, int x, int y) {

	}

	public boolean shouldRemove() {
		return false;
	}

	protected String getType() {
		switch (type) {
			case LevelEditor.LIGHT:
				return "light";

			case LevelEditor.POLYGON:
				return "polygon";

			case LevelEditor.SPAWN:
				return "spawn";

			case LevelEditor.WALL:
				return "rectangle";
		}

		return "null";
	}

	public boolean collidable() {
		return collidable;
	}

	public String serializeWithPoints(ArrayList<Point> pointList) {
		// I don't want to mess with a json library for such a simple serialization
		StringBuilder s = new StringBuilder();

		// type
		s.append("{");
		s.append("\"type\":\"");
		s.append(getType());
		s.append("\",");

		// vertices
		s.append("\"vertices\": [");
		for (int i = 0; i < pointList.size(); i++) {
			s.append("[");
			s.append(pointList.get(i).x);
			s.append(",");
			s.append(pointList.get(i).y);
			s.append("]");

			if (i < pointList.size() - 1) {
				s.append(",");
			}
		}
		s.append("]");

		s.append("}");

		return s.toString();
	}
	
	public String serialize() {
		return serializeWithPoints(points);
	}
}
