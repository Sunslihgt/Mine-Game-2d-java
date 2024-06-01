package dev.sunslihgt.mine_game_2d.item;

import java.awt.image.BufferedImage;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.item.item_types_list.ChestItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.CoalItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.CoalOreItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.DirtItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.FurnaceItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.GrassItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.IronAxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.IronIngotItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.IronOreItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.IronPickaxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.IronShovelItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.OakLeavesItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.OakPlankItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.OakWoodItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.RubyItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.RubyOreItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.StickItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.StoneAxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.StoneItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.StonePickaxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.StoneShovelItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.TorchItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.WoodenAxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.WoodenPickaxeItem;
import dev.sunslihgt.mine_game_2d.item.item_types_list.WoodenShovelItem;

public class ItemType {

	// Item types list
	public static ItemType[] itemTypes = new ItemType[256];
	public static ItemType dirtItem = new DirtItem(0);
	public static ItemType grassItem = new GrassItem(1);
	public static ItemType stoneItem = new StoneItem(2);
	public static ItemType coalOreItem = new CoalOreItem(3);
	public static ItemType ironOreItem = new IronOreItem(4);
	public static ItemType rubyOreItem = new RubyOreItem(5);
	public static ItemType woodenPickaxeItem = new WoodenPickaxeItem(6);
	public static ItemType stonePickaxeItem = new StonePickaxeItem(7);
	public static ItemType ironPickaxeItem = new IronPickaxeItem(8);
	public static ItemType torchItem = new TorchItem(9);
	public static ItemType chestItem = new ChestItem(10);
	public static ItemType furnaceItem = new FurnaceItem(11);
	public static ItemType coalItem = new CoalItem(12);
	public static ItemType ironIngotItem = new IronIngotItem(13);
	public static ItemType rubyItem = new RubyItem(14);
	public static ItemType stickItem = new StickItem(15);
	public static ItemType oakWoodItem = new OakWoodItem(16);
	public static ItemType oakPlankItem = new OakPlankItem(17);
	public static ItemType oakLeavesItem = new OakLeavesItem(18);
	public static ItemType woodenAxeItem = new WoodenAxeItem(19);
	public static ItemType stoneAxeItem = new StoneAxeItem(20);
	public static ItemType ironAxeItem = new IronAxeItem(21);
	public static ItemType woodenShovelItem = new WoodenShovelItem(22);
	public static ItemType stoneShovelItem = new StoneShovelItem(23);
	public static ItemType ironShovelItem = new IronShovelItem(24);
	

	// ItemType class
	protected final int id;
	protected String name;
	protected BufferedImage texture;
	protected int maxStack;
	protected ToolType toolType;
	protected int toolLvl;
	protected float toolEfficiency;
	protected float fuelBurnTime; // Fuel duration (in nb of items)

	public ItemType(int id, String name, BufferedImage texture, int maxStack, ToolType toolType, int toolLvl, float toolEfficiency, float fuelBurnTime) {
		this.id = id;
		this.name = name;
		this.texture = texture;
		this.maxStack = maxStack;
		this.toolType = toolType;
		this.toolLvl = toolLvl;
		this.toolEfficiency = toolEfficiency;
		this.fuelBurnTime = fuelBurnTime;

		itemTypes[id] = this;
	}
	
	public boolean leftClickAction(Handler handler) {
		return false;
	}
	
	public boolean rightClickAction(Handler handler) {
		return false;
	}

	// Getters
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public int getMaxStack() {
		return maxStack;
	}
	
	public ToolType getToolType() {
		return toolType;
	}

	public int getToolLvl() {
		return toolLvl;
	}

	public float getToolEfficiency() {
		return toolEfficiency;
	}
	
	public float getFuelBurnTime() {
		return fuelBurnTime;
	}

}
