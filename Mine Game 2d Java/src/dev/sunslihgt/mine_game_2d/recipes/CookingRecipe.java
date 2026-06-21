package dev.sunslihgt.mine_game_2d.recipes;

import dev.sunslihgt.mine_game_2d.item.ItemType;

public record CookingRecipe(ItemType cookingItem, ItemType cookedItem, int count) {

}
