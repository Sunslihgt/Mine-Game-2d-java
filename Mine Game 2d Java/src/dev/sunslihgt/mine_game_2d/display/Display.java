package dev.sunslihgt.mine_game_2d.display;

import com.raylib.Raylib;

public class Display {
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay(){
		Raylib.initWindow(width, height, title);
		System.out.println("Fullscreen: " + Raylib.isWindowFullscreen());
		Raylib.setTargetFPS(60);

		Raylib.hideCursor();
	}
}
