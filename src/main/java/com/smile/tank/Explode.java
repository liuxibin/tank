package com.smile.tank;

import com.smile.resource.ResourceLoad;

import java.awt.*;

public class Explode {
	public static int WIDTH = ResourceLoad.explodes[0].getWidth();
	public static int HEIGHT = ResourceLoad.explodes[0].getHeight();

	private int x, y;

	private boolean live = true;
	
	//private boolean living = true;
	
	private int step = 0;
	
	public Explode(int x, int y) {
		this.x = x - (WIDTH/2);
		this.y = y - (HEIGHT/2);
	}

	public void paint(Graphics g) {
		g.drawImage(ResourceLoad.explodes[step++], x, y, null);
		if(step >= ResourceLoad.explodes.length) {
			this.live = false;
		}
	}

	public boolean isLive() {
		return live;
	}
}
