package dev.sunslihgt.mine_game_2d.player;

import dev.sunslihgt.mine_game_2d.item.Item;

import java.util.ArrayList;

public interface ISlotContainer {
    boolean isMouseHoveringUI(int mX, int mY, boolean inventoryOpen);
    int getMouseSlotIndex(int mX, int mY, boolean inventoryOpen);
    Item getMouseSlotItem(int mX, int mY, boolean inventoryOpen);
    Item getItemWithIndex(int slot);
    void setItemWithIndex(int slot, Item item);
    void setItemCountWithIndex(int slot, int count);
    int countItemId(int id);
    int getInventoryCellsAmount();

    /**
     * Attempt to remove an item from the inventory. Do not remove items if the count is higher than the items in the inventory.
     * @param item Item with count to remove from the inventory
     * @return true if the item was removed, false if not possible.
     */
    boolean removeItem(Item item);

    /**
     * Get a list containing all the items in inventory without duplicates.<br>
     * WARNING ! Items may have a higher count than max stack count !
     */
    ArrayList<Item> getItemsListCopy();

    /**
     * Empty the inventory by deleting every item.
     */
    void clearInventory();

    /**
     * Add items to inventory, according to the inventory's preferences.
     * Furnace-specific: do not fill output + only fill fuel with burnable items.
     * @return The remaining items
     */
    Item addItem(Item newItem);

    /**
     * Add a list of items to inventory, according to the inventory's preferences.
     * @return The list of remaining items
     */
    ArrayList<Item> addItemList(ArrayList<Item> newItems);
}