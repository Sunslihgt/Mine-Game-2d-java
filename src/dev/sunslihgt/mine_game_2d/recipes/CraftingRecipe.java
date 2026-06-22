package dev.sunslihgt.mine_game_2d.recipes;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes.RecipeCategory;

public record CraftingRecipe(ArrayList<Item> itemsCost, Item craftedItem, RecipeCategory category) {

	// Return a copy of the items costs
	public ArrayList<Item> getItemsCostCopy() {
		ArrayList<Item> itemsCostCopy = new ArrayList<Item>();
		for (Item item : itemsCost) {
			itemsCostCopy.add(item.getCopy());
		}
		return itemsCostCopy;
	}

}
