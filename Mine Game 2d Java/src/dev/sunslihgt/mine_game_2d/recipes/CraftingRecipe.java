package dev.sunslihgt.mine_game_2d.recipes;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes.RecipeCategory;

public class CraftingRecipe {
	
	private ArrayList<Item> itemsCost;
	private Item craftedItem;
	private RecipeCategory category;
	
	public CraftingRecipe(ArrayList<Item> itemsCost, Item craftedItem, RecipeCategory category) {
		this.itemsCost = itemsCost;
		this.craftedItem = craftedItem;
		this.category = category;
	}

	// Return a copy of the items costs
	public ArrayList<Item> getItemsCostCopy() {
		ArrayList<Item> itemsCostCopy = new ArrayList<Item>();
		for (Item item : itemsCost) {
			itemsCostCopy.add(item.getCopy());
		}
		return itemsCostCopy;
	}

	public ArrayList<Item> getItemsCost() {
		return itemsCost;
	}

	public Item getCraftedItem() {
		return craftedItem;
	}
	
	public RecipeCategory getCategory() {
		return category;
	}
}
