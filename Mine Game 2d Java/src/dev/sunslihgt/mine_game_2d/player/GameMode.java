package dev.sunslihgt.mine_game_2d.player;

public enum GameMode {

	SURVIVAL(true, true, 9), SPECTATOR(true, false, 15), CREATIVE(true, true, 15);

	boolean visible, gravity;
	int maxSpeed;

	private GameMode(boolean visible, boolean gravity, int maxSpeed) {
		this.visible = visible;
		this.gravity = gravity;
		this.maxSpeed = maxSpeed;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isGravity() {
		return gravity;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
}
