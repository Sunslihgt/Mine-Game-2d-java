package dev.sunslihgt.mine_game_2d.recipes;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.item.ItemType;

public class CookingRecipes {
	
	public static ArrayList<CookingRecipe> smeltingRecipes;

	public static void init() {
		smeltingRecipes = new ArrayList<CookingRecipe>();
		smeltingRecipes.add(new CookingRecipe(ItemType.coalOreItem, ItemType.coalItem, 1));
		smeltingRecipes.add(new CookingRecipe(ItemType.ironOreItem, ItemType.ironIngotItem, 1));
		smeltingRecipes.add(new CookingRecipe(ItemType.rubyOreItem, ItemType.rubyItem, 1));
	}
	
	public static boolean isCookable(ItemType itemType) {
		for (CookingRecipe cookingRecipe : smeltingRecipes) {
			if (cookingRecipe.getCookingItem() == itemType) {
				return true;
			}
		}
		return false;
	}
	
	public static ItemType getCookedItemType(ItemType cookingItemType) {
		for (CookingRecipe cookingRecipe : smeltingRecipes) {
			if (cookingRecipe.getCookingItem() == cookingItemType) {
				return cookingRecipe.getCookedItem();
			}
		}
		System.err.println("Unknown item type cooked in ItemType.getCookedItemType, cookingItemType: " + cookingItemType);
		return null;
	}
	
	public static int getCookedItemCount(ItemType cookingItemType) {
		for (CookingRecipe cookingRecipe : smeltingRecipes) {
			if (cookingRecipe.getCookingItem() == cookingItemType) {
				return cookingRecipe.getCount();
			}
		}
		System.err.println("Unknown item type cooked in ItemType.getCookedItemCount, cookingItemType: " + cookingItemType);
		return 0;
	}
}
