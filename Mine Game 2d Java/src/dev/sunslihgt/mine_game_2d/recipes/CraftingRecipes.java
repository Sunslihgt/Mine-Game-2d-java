package dev.sunslihgt.mine_game_2d.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;

public class CraftingRecipes {
	
	public static ArrayList<CraftingRecipe> craftingRecipes;
	
	public enum RecipeCategory {
		ALL, TOOL, BLOCK, MISCELLANEOUS
	}
	
	public static void init() {
		craftingRecipes = new ArrayList<CraftingRecipe>();
		
		ArrayList<Item> torchCost = new ArrayList<Item>();
		torchCost.add(new Item(1, ItemType.stickItem));
		torchCost.add(new Item(1, ItemType.coalItem));
		craftingRecipes.add(new CraftingRecipe(torchCost, new Item(4, ItemType.torchItem), RecipeCategory.MISCELLANEOUS));
		
		ArrayList<Item> oakPlankCost = new ArrayList<Item>();
		oakPlankCost.add(new Item(1, ItemType.oakWoodItem));
		craftingRecipes.add(new CraftingRecipe(oakPlankCost, new Item(4, ItemType.oakPlankItem), RecipeCategory.BLOCK));
		
		ArrayList<Item> stickCost = new ArrayList<Item>();
		stickCost.add(new Item(1, ItemType.oakPlankItem));
		craftingRecipes.add(new CraftingRecipe(stickCost, new Item(2, ItemType.stickItem), RecipeCategory.MISCELLANEOUS));
		
		ArrayList<Item> woodenPickaxeCost = new ArrayList<Item>();
		woodenPickaxeCost.add(new Item(2, ItemType.stickItem));
		woodenPickaxeCost.add(new Item(3, ItemType.oakPlankItem));
		craftingRecipes.add(new CraftingRecipe(woodenPickaxeCost, new Item(1, ItemType.woodenPickaxeItem), RecipeCategory.TOOL));
		
		ArrayList<Item> stonePickaxeCost = new ArrayList<Item>();
		stonePickaxeCost.add(new Item(2, ItemType.stickItem));
		stonePickaxeCost.add(new Item(3, ItemType.stoneItem));
		craftingRecipes.add(new CraftingRecipe(stonePickaxeCost, new Item(1, ItemType.stonePickaxeItem), RecipeCategory.TOOL));
		
		ArrayList<Item> ironPickaxeCost = new ArrayList<Item>();
		ironPickaxeCost.add(new Item(2, ItemType.stickItem));
		ironPickaxeCost.add(new Item(3, ItemType.ironIngotItem));
		craftingRecipes.add(new CraftingRecipe(ironPickaxeCost, new Item(1, ItemType.ironPickaxeItem), RecipeCategory.TOOL));
	}
	
	public static boolean isCraftAffordable(CraftingRecipe craft, ArrayList<Item> itemsAvailable) {
		ArrayList<Item> craftCost = craft.getItemsCost();
		
		for (Item craftItem : craftCost) {
			int itemRemaining = craftItem.getCount();
			for (Iterator<Item> iterator = itemsAvailable.iterator(); iterator.hasNext();) {
				if (itemRemaining <= 0) {
					break;
				}
				Item availableItem = (Item) iterator.next();
				if (craftItem.getId() == availableItem.getId()) {
					int itemUsed = Math.min(itemRemaining, availableItem.getCount());
					itemRemaining -= itemUsed;
					if (availableItem.getCount() - itemUsed <= 0) {
						iterator.remove();
					} else {
						availableItem.addCount(-itemUsed);
					}
				}
			}
			
			if (itemRemaining > 0) {
				return false;
			}
		}
		
		return true;
	}
	
	// Return a list containing every affordable crafts
	public static ArrayList<CraftingRecipe> getAvailableCraftsList(ArrayList<Item> itemsAvailable) {
		ArrayList<CraftingRecipe> availableCrafts = new ArrayList<CraftingRecipe>(craftingRecipes);
		for (Iterator<CraftingRecipe> iterator = availableCrafts.iterator(); iterator.hasNext();) {
			CraftingRecipe craftingRecipe = (CraftingRecipe) iterator.next();
			if (!isCraftAffordable(craftingRecipe, itemsAvailable)) {
				iterator.remove();
			}
		}
		return availableCrafts;
	}
	
	// Return a list containing every affordable crafts of a given category 
	public static ArrayList<CraftingRecipe> getAvailableCraftsList(ArrayList<Item> itemsAvailable, int categoryIndex) {
		RecipeCategory category = getRecipeCategory(categoryIndex);
		
		ArrayList<CraftingRecipe> availableCrafts = new ArrayList<CraftingRecipe>(craftingRecipes);
		for (Iterator<CraftingRecipe> iterator = availableCrafts.iterator(); iterator.hasNext();) {
			CraftingRecipe craftingRecipe = (CraftingRecipe) iterator.next();
			if (category != RecipeCategory.ALL && category != craftingRecipe.getCategory()) {
				iterator.remove();
			} else if (!isCraftAffordable(craftingRecipe, itemsAvailable)) {
				iterator.remove();
			}
		}
		return availableCrafts;
	}
	
	public static ArrayList<CraftingRecipe> getCraftingRecipesCopy() {
		ArrayList<CraftingRecipe> newList = new ArrayList<CraftingRecipe>();
		Collections.copy(craftingRecipes, newList);
		return newList;
	}
	
	public static RecipeCategory getRecipeCategory(int categoryIndex) {
		switch (categoryIndex) {
			case 1:
				return RecipeCategory.TOOL;
			case 2:
				return RecipeCategory.BLOCK;
			case 3:
				return RecipeCategory.MISCELLANEOUS;
			default:
				return RecipeCategory.ALL;
			
		}
	}
}
