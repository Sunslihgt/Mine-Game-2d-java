package dev.sunslihgt.mine_game_2d.item;

import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.item.item_types_list.*;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class ItemType {
	private final static int ITEM_TYPE_COUNT = 256;

	// Item types list
	public static final ItemType[] itemTypes = new ItemType[ITEM_TYPE_COUNT];
	public static final ItemType dirtItem = new DirtItemType(0);
	public static final ItemType grassItem = new GrassItemType(1);
	public static final ItemType stoneItem = new StoneItemType(2);
	public static final ItemType coalOreItem = new CoalOreItemType(3);
	public static final ItemType ironOreItem = new IronOreItemType(4);
	public static final ItemType rubyOreItem = new RubyOreItemType(5);
	public static final ItemType woodenPickaxeItem = new WoodenPickaxeItemType(6);
	public static final ItemType stonePickaxeItem = new StonePickaxeItemType(7);
	public static final ItemType ironPickaxeItem = new IronPickaxeItemType(8);
	public static final ItemType torchItem = new TorchItemType(9);
	public static final ItemType chestItem = new ChestItemType(10);
	public static final ItemType furnaceItem = new FurnaceItemType(11);
	public static final ItemType coalItem = new CoalItemType(12);
	public static final ItemType ironIngotItem = new IronIngotItemType(13);
	public static final ItemType rubyItem = new RubyItemType(14);
	public static final ItemType stickItem = new StickItemType(15);
	public static final ItemType oakWoodItem = new OakWoodItemType(16);
	public static final ItemType oakPlankItem = new OakPlankItemType(17);
	public static final ItemType oakLeavesItem = new OakLeavesItemType(18);
	public static final ItemType woodenAxeItem = new WoodenAxeItemType(19);
	public static final ItemType stoneAxeItem = new StoneAxeItemType(20);
	public static final ItemType ironAxeItem = new IronAxeItemType(21);
	public static final ItemType woodenShovelItem = new WoodenShovelItemType(22);
	public static final ItemType stoneShovelItem = new StoneShovelItemType(23);
	public static final ItemType ironShovelItem = new IronShovelItemType(24);
	public static final ItemType redTorchItem = new RedTorchItemType(25);
	public static final ItemType greenTorchItem = new BlueTorchItemType(26);
	public static final ItemType blueTorchItem = new GreenTorchItemType(27);


	// ItemType class
	protected final int id;
	protected String name;
	protected Texture texture;
	protected int maxStack;
	protected ToolType toolType;
	protected int toolLvl;
	protected float toolEfficiency;
	protected float fuelBurnTime; // Fuel duration (in nb of items)

	public ItemType(int id, String name, Texture texture, int maxStack, ToolType toolType, int toolLvl, float toolEfficiency, float fuelBurnTime) {
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

	public Texture getTexture() {
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
