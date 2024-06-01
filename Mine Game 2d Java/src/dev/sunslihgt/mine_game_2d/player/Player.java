package dev.sunslihgt.mine_game_2d.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.input.KeyManager;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class Player {

	public static final int PLAYER_WIDTH = 28, PLAYER_HEIGHT = 56;

	// Used to be ACC = 8, JUMP = 25, GRAVITY = 1.5f, MAX_FALL_SPEED = 30
	private static final int PLAYER_ACCELERATION = 3;
	private static final float JUMP_FORCE = 15, GRAVITY = 1.2f, MAX_FALL_SPEED = 30;

	private static final int JUMP_COOLDOWN = 75;
	private static final int MAX_BASE_LIFE = 20;

	private static final Color PLAYER_COLOR = new Color(0x023047);
	
	private static final int clickCooldown = 50;
	private long lastRightClickTime = 0;
//	private boolean lastLeftClicked = false;
	private boolean lastRightClicked = false;

	private float x, y;
	private float velX, velY;
	private boolean touchingGround = false;
	private long touchingGroundTime;
	private GameMode gameMode = GameMode.SURVIVAL;
	private int hp = MAX_BASE_LIFE;

	private boolean movingUp = false, movingDown = false, movingLeft = false, movingRight = false;

	private Handler handler;

	private PlayerCursor playerCursor;
	private PlayerInventory playerInventory;

	public Player(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;

		init();
	}

	private void init() {
		playerCursor = new PlayerCursor(handler);
		playerInventory = new PlayerInventory(handler);
	}

	public void tick() {
		playerCursor.tick();
		playerInventory.tick();

		checkMouseInput();
		checkKeyboardInput();

		move();
	}

	private void move() {
		if (movingLeft) // Moving left
			velX -= PLAYER_ACCELERATION;
		if (movingRight) // Moving right
			velX += PLAYER_ACCELERATION;

		if (velX < -gameMode.maxSpeed) // Slow down if max speed reached
			velX = -gameMode.maxSpeed;
		if (velX > gameMode.maxSpeed)
			velX = gameMode.maxSpeed;

		if (!movingLeft && !movingRight) // Not moving -> slow down
			if (velX < 0 && velX + PLAYER_ACCELERATION < 0)
				velX += PLAYER_ACCELERATION;
			else if (velX > 0 && velX - PLAYER_ACCELERATION > 0)
				velX -= PLAYER_ACCELERATION;
			else
				velX = 0;
		if (!gameMode.gravity) { // Spectator mode -> No gravity
			if (movingUp) // Moving up
				velY -= PLAYER_ACCELERATION;
			if (movingDown) // Moving down
				velY += PLAYER_ACCELERATION;

			if (velY < -gameMode.maxSpeed) // Slow down if max speed reached
				velY = -gameMode.maxSpeed;
			if (velY > gameMode.maxSpeed)
				velY = gameMode.maxSpeed;

			if ((!movingUp && !movingDown) || (movingUp && movingDown)) // Not moving -> slow down
				if (velY < 0 && velY + PLAYER_ACCELERATION < 0)
					velY += PLAYER_ACCELERATION;
				else if (velY > 0 && velY - PLAYER_ACCELERATION > 0)
					velY -= PLAYER_ACCELERATION;
				else
					velY = 0;

			// Move
			x += velX;
			y += velY;

		} else { // Survival mode
			// Jump
			long now = System.currentTimeMillis();
			if (movingUp && touchingGround && touchingGroundTime + JUMP_COOLDOWN < now) {
				velY = -JUMP_FORCE;
			}

			// Gravity
			velY += GRAVITY;

			if (velY > MAX_FALL_SPEED) {// Slow down if max fall speed is reached
				velY = MAX_FALL_SPEED;
			}

			checkCollision();

//			System.out.println(velX + " " + velY);

			x += velX;
			y += velY;
		}
	}

	private void checkKeyboardInput() {
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;

		KeyManager keyManager = handler.getKeyboardManager();

		if (keyManager.left && !keyManager.right)
			movingLeft = true;
		if (keyManager.right && !keyManager.left)
			movingRight = true;

		if (keyManager.up)
			movingUp = true;
		if (keyManager.down)
			movingDown = true;

		// Toggle inventory
		if (handler.getKeyboardManager().keyJustPressed(KeyEvent.VK_E)) {
			playerInventory.toggleinventory();
		}

		// Switch game mode
		if (handler.getKeyboardManager().keyJustPressed(KeyEvent.VK_A)) {
			if (gameMode == GameMode.SURVIVAL) {
				gameMode = GameMode.SPECTATOR;
			} else if (gameMode == GameMode.SPECTATOR) {
				gameMode = GameMode.SURVIVAL;
			}
			System.out.println("Game mode: " + gameMode);
		}

	}

	// Check collisions with nearby blocks until there is no more collisions
	private void checkCollision() {
		int playerBX = Utils.convertPixelToBlock(x + velX);
		int playerBY = Utils.convertPixelToBlock(y + velY);

		boolean oldTouchingGround = touchingGround;
		touchingGround = false;

		boolean newCollision = true;

		while (newCollision) {
			newCollision = checkCollisionNearbyBlocks(playerBX, playerBY);
		}

		if (touchingGround && !oldTouchingGround) {
			touchingGroundTime = System.currentTimeMillis();
		}
	}

	private boolean checkCollisionNearbyBlocks(int playerBX, int playerBY) {
		// Check player collision with nearby blocks and modify player's position and velocity

		// Old player positions
		float opTop = y - PLAYER_HEIGHT;
		float opBottom = y;
		float opLeft = x - PLAYER_WIDTH / 2;
		float opRight = x + PLAYER_WIDTH / 2;

		// New player positions
		float pTop = opTop + velY;
		float pBottom = opBottom + velY;
		float pLeft = opLeft + velX;
		float pRight = opRight + velX;

		for (int blockBX = playerBX - 1; blockBX <= playerBX + 1; blockBX++) {
			for (int blockBY = playerBY - 2; blockBY <= playerBY + 1; blockBY++) {
				Block block = handler.getWorld().getBlock(blockBX, blockBY);
				if (block != null && block.getType().isCollide()) { // Block exists and can collide
					// Get block and sides coordinates
					int blockPX = Utils.convertBlockToPixel(blockBX);
					int blockPY = Utils.convertBlockToPixel(blockBY);

					int bTop = blockPY;
					int bBottom = blockPY + Block.BLOCK_WIDTH;
					int bLeft = blockPX;
					int bRight = blockPX + Block.BLOCK_WIDTH;

					// Check if player and block collides
					if (!(pTop > bBottom || pBottom < bTop || pLeft > bRight || pRight < bLeft)) {
//						System.out.println("Colliding with bBX: " + blockBX + ", bBY: " + blockBY);

						// Obstacle down
						if (pBottom >= bTop && opBottom < bTop && velY != 0) {
//							System.out.println("Collision down");
							velY = 0;
							y = bTop - 0.01f;
							touchingGround = true;
							return true;
						} else if (pLeft <= bRight && opLeft > bRight && velX != 0) { // Obstacle Left
//							System.out.println("Collision left ground: " + touchingGround);
							velX = 0;
							x = bRight + 0.01f + PLAYER_WIDTH / 2;
							return true;
						} else if (pRight >= bLeft && opRight < bLeft && velX != 0) { // Obstacle Right
//							System.out.println("Collision right");
							velX = 0;
							x = bLeft - 0.01f - PLAYER_WIDTH / 2;
							return true;
						} else if (pTop <= bBottom && opTop > bBottom && velY != 0) { // Obstacle Up
//							System.out.println("Collision down");
							velY = 0;
							y = bBottom + 0.01f + PLAYER_HEIGHT;
							return true;
						}
//						return true; // Collision
					}
				}
			}
		}
		return false; // No collision
	}

	private void checkMouseInput() {
		boolean leftClicked = handler.getMouseManager().isLeftPressed();
		boolean rightClicked = handler.getMouseManager().isRightPressed();
		
		// Left click
		if (leftClicked) {
			playerInventory.selectedItemLeftClick();
		}

		// Right click
		if (rightClicked && lastRightClickTime + clickCooldown < System.currentTimeMillis()) {
			lastRightClickTime = System.currentTimeMillis();
			
			// Try to use a block (eg: chest), else try to use an item (eg: place block)
			if (!lastRightClicked) {
				int playerCursorX = playerCursor.getbX();
				int playerCursorY = playerCursor.getbY();
				
				if (!playerInventory.isMouseInInventory()) {
					if (!handler.getWorld().rightClickBlock(playerCursorX, playerCursorY)) {
						// Right click action of item
						if (playerInventory.hasSelectedToolbarItem()) {
							playerInventory.selectedItemRightClick();
						}
					}
				}
				
			} else {
				// Right click action of item
				if (playerInventory.hasSelectedToolbarItem()) {
					playerInventory.selectedItemRightClick();
				}
			}
			
		}
		
//		lastLeftClicked = leftClicked;
		lastRightClicked = rightClicked;
	}

	public void render(Graphics g) {
		int xOffset = handler.getGameCamera().getXOffset();
		int yOffset = handler.getGameCamera().getYOffset();

		g.setColor(PLAYER_COLOR);
		g.fillRect((int) (x - xOffset - PLAYER_WIDTH / 2), (int) (y - yOffset - PLAYER_HEIGHT), PLAYER_WIDTH, PLAYER_HEIGHT);

		playerCursor.render(g);

		playerInventory.render(g);

		if (gameMode == GameMode.SURVIVAL) {
			renderLife(g);
		}
	}

	public void renderLife(Graphics g) {
		int xOffset = handler.getWidth() - 500;
		int heartWidth = Assets.HEART_WIDTH;

		for (int i = 0; i < 10; i++) {
			if (i < hp / 2) {
				g.drawImage(Assets.heart, xOffset + i * heartWidth, 35, heartWidth, heartWidth, null);
			} else if (i == hp / 2 && hp % 2 > 0) {
				g.drawImage(Assets.half_heart, xOffset + i * heartWidth, 35, heartWidth, heartWidth, null);
			} else {
				g.drawImage(Assets.empty_heart, xOffset + i * heartWidth, 35, heartWidth, heartWidth, null);
			}

		}
//		g.drawImage(Assets.half_heart, handler.getWidth() - xOffset + 48, 30, width, width, null);
	}

	public boolean checkCollisionBlockPos(int bX, int bY) {
		// Player positions
		float pTop = y - PLAYER_HEIGHT;
		float pBottom = y;
		float pLeft = x - PLAYER_WIDTH / 2;
		float pRight = x + PLAYER_WIDTH / 2;

		// Block positions
		int bTop = Utils.convertBlockToPixel(bY);
		int bBottom = bTop + Block.BLOCK_WIDTH;
		int bLeft = Utils.convertBlockToPixel(bX);
		int bRight = bLeft + Block.BLOCK_WIDTH;

		if (!(pTop > bBottom || pBottom < bTop || pLeft > bRight || pRight < bLeft)) {
			return true; // Collision
		}
		return false; // No collision
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public PlayerCursor getPlayerCursor() {
		return playerCursor;
	}

	public PlayerInventory getPlayerInventory() {
		return playerInventory;
	}

}
