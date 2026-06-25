package dev.sunslihgt.mine_game_2d.input;

import com.raylib.Raylib;
import com.raylib.Raylib.MouseButton;

public class MouseManager {

	private boolean leftPressed, rightPressed;
	private boolean leftJustPressed, rightJustPressed;
	private int mouseX, mouseY;
	private int scroll;
	private boolean mouseInScreen = true;

    public void tick() {
        mouseX = Raylib.getMouseX();
        mouseY = Raylib.getMouseY();
		boolean lastLeftPressed = leftPressed;
		boolean lastRightPressed = rightPressed;
        leftPressed = Raylib.isMouseButtonDown(MouseButton.MOUSE_BUTTON_LEFT);
        rightPressed = Raylib.isMouseButtonDown(MouseButton.MOUSE_BUTTON_RIGHT);
		leftJustPressed = leftPressed && !lastLeftPressed;
		rightJustPressed = rightPressed && !lastRightPressed;

		scroll = Math.round(Raylib.getMouseWheelMoveV().getY());
		mouseInScreen = Raylib.isCursorOnScreen();

		// System.out.println("Lpressed=" + isLeftJustPressed() + ", Ldown=" + isLeftDown() + ", Rpressed=" + isRightJustPressed() + ", Rdown=" + isRightDown());
        // System.out.printf("Mouse x=%d, y=%d, left=%b, right=%b, scroll=%d, inScreen=%b\n", mouseX, mouseY, leftPressed, rightPressed, scroll, mouseInScreen);
    }

	// Getters

	public boolean isLeftDown() {
		return leftPressed;
	}

	public boolean isRightDown() {
		return rightPressed;
	}
	public boolean isLeftJustPressed() {
		return leftJustPressed;
	}

	public boolean isRightJustPressed() {
		return rightJustPressed;
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
