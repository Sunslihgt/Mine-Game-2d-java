package dev.sunslihgt.mine_game_2d.block;

import java.util.ArrayList;
import java.util.Random;

import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;

public class BlockDrop {

	private ItemType itemType;
	private int minDrop, maxDrop;
	
	/*
	 * A block drop is an item type that has a random drop rate in a range (a minimum and a maximum)
	 */
	
	public BlockDrop(ItemType itemType, int minDrop, int maxDrop) {
		this.itemType = itemType;
		this.minDrop = minDrop;
		this.maxDrop = maxDrop;
	}
	
	public ArrayList<Item> getDrops() {
		int dropCount = minDrop;
		if (maxDrop > minDrop) {
			dropCount = new Random().nextInt(maxDrop - minDrop) + minDrop;
		}
		
		ArrayList<Item> drops = new ArrayList<>();
		while (dropCount > 0) {
			int itemCount = Math.min(dropCount, itemType.getMaxStack());
			dropCount -= itemCount;
			drops.add(new Item(itemCount, itemType));
		}
		
		return drops;
	}

	public ItemType getItemType() {
		return itemType;
	}
	
	public int getMinDrop() {
		return minDrop;
	}

	public int getMaxDrop() {
		return maxDrop;
	}
}
