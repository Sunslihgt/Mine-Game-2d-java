package dev.sunslihgt.mine_game_2d.display;

import com.raylib.Raylib;
import com.raylib.Rectangle;
import com.raylib.RenderTexture;

public class Display {
	
	private final String title;
	private final int width, height;

	RenderTexture screenLightTexture;
	Rectangle screenRect;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		screenRect = new Rectangle(0, 0, width, height);
		
		createDisplay();
	}
	
	private void createDisplay(){
		Raylib.initWindow(width, height, title);
		System.out.println("Fullscreen: " + Raylib.isWindowFullscreen());
		Raylib.setTargetFPS(60);

		Raylib.hideCursor();

		screenLightTexture = Raylib.loadRenderTexture(width, height);
	}

	public RenderTexture getScreenLightTexture() {
		return screenLightTexture;
	}

	public Rectangle getScreenRect() {
		return screenRect;
	}
}
