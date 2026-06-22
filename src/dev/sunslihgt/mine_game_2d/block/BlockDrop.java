package dev.sunslihgt.mine_game_2d.block;

import java.util.ArrayList;
import java.util.Random;

import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;

public record BlockDrop(ItemType itemType, int minDrop, int maxDrop) {

	/*
	 * A block drop is an item type that has a random drop rate in a range (a minimum and a maximum)
	 */

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


}
