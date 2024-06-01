package dev.sunslihgt.mine_game_2d.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private int scroll;
	private boolean mouseInScreen = true;

	public MouseManager() {

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
		int oldScroll = scroll;
		scroll = 0;
		return oldScroll;
	}

	public boolean isMouseInScreen() {
		return mouseInScreen;
	}

	// Implemented methods

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if (e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if (e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("Mouse clicked"); // Same as pressed then released
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInScreen = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInScreen = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

}
