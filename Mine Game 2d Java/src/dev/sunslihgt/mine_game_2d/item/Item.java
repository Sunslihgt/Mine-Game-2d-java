package dev.sunslihgt.mine_game_2d.item;

import dev.sunslihgt.mine_game_2d.Handler;

public class Item {

	private int count;
	private ItemType type;

	public Item(int count, ItemType type) {
		this.count = count;
		this.type = type;
	}

	// Left click action
	public boolean leftClickAction(Handler handler) {
		return type.leftClickAction(handler);
	}

	// Right click action
	public boolean rightClickAction(Handler handler) {
		boolean consumeItem = type.rightClickAction(handler);
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
