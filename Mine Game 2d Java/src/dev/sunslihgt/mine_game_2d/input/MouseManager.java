package dev.sunslihgt.mine_game_2d.input;

import com.raylib.Raylib;
import com.raylib.Raylib.MouseButton;
import com.raylib.Raylib.MouseCursor;
import dev.sunslihgt.mine_game_2d.Handler;

public class MouseManager {

	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private int scroll;
	private boolean mouseInScreen = true;

	public MouseManager() {
		Raylib.setMouseCursor(MouseCursor.MOUSE_CURSOR_CROSSHAIR);
	}

    public void tick() {
        mouseX = Raylib.getMouseX();
        mouseY = Raylib.getMouseY();
        leftPressed = Raylib.isMouseButtonDown(MouseButton.MOUSE_BUTTON_LEFT);
        rightPressed = Raylib.isMouseButtonDown(MouseButton.MOUSE_BUTTON_RIGHT);
		scroll = Math.round(Raylib.getMouseWheelMoveV().getY());
		mouseInScreen = Raylib.isCursorOnScreen();

		// System.out.printf("Mouse x=%d, y=%d, left=%b, right=%b, scroll=%d, inScreen=%b\n", mouseX, mouseY, leftPressed, rightPressed, scroll, mouseInScreen);
    }

	// Getters

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}

	public boolean isMouseInScreen() {
		return mouseInScreen;
	}

}
