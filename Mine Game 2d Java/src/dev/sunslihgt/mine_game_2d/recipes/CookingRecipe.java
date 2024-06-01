package dev.sunslihgt.mine_game_2d.recipes;

import dev.sunslihgt.mine_game_2d.item.ItemType;

public class CookingRecipe {
	private ItemType cookingItem;
	private ItemType cookedItem;
	private int count;
	
	public CookingRecipe(ItemType cookingItem, ItemType cookedItem, int count) {
		this.cookingItem = cookingItem;
		this.cookedItem = cookedItem;
		this.count = count;
	}

	public ItemType getCookingItem() {
		return cookingItem;
	}

	public ItemType getCookedItem() {
		return cookedItem;
	}

	public int getCount() {
		return count;
	}
}
