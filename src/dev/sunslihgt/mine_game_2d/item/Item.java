package dev.sunslihgt.mine_game_2d.item;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.player.GameMode;

public class Item {

	private int count;
	private final ItemType type;

	public Item(int count, ItemType type) {
		this.count = count;
		this.type = type;
	}

	// Left click action
	public boolean leftClickAction(Handler handler) {
		return type.leftClickAction(handler);
	}

	/**
	 * Right click action
	 * @param handler Game Handler
	 * @return true if the item count reaches 0, else false
	 */
	public boolean rightClickAction(Handler handler) {
		boolean consumeItem = type.rightClickAction(handler) && handler.getPlayer().getGameMode() == GameMode.SURVIVAL;
		if (consumeItem) {
			count--;
			if (count <= 0) {
				return true;
			}
		}
		return false;
	}
	
	// Clone
	public Item getCopy() {
		return new Item(count, getType());
	}
	
	// Getters and Setters
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount(int i) {
		count += i;
	}

	public ItemType getType() {
		return type;
	}
	
	public int getId() {
		return type.getId();
	}
}
