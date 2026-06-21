package dev.sunslihgt.mine_game_2d.block;

import java.util.ArrayList;

import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.block.block_types_list.*;
import dev.sunslihgt.mine_game_2d.item.ToolType;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class BlockType {
	private final static int BLOCK_TYPE_COUNT = 256;
	
	// Block types list
	public static final BlockType[] blockTypes = new BlockType[BLOCK_TYPE_COUNT];
	public static final BlockType airBlock = new AirBlockType(0);
	public static final BlockType dirtBlock = new DirtBlockType(1);
	public static final BlockType grassBlock = new GrassBlockType(2);
	public static final BlockType stoneBlock = new StoneBlockType(3);
	public static final BlockType coalOreBlock = new CoalOreBlockType(4);
	public static final BlockType ironOreBlock = new IronOreBlockType(5);
	public static final BlockType rubyOreBlock = new RubyOreBlockType(6);
	public static final BlockType torchBlock = new TorchBlockType(7);
	public static final BlockType chestBlock = new ChestBlockType(8);
	public static final BlockType furnaceBlock = new FurnaceBlockType(9);
	public static final BlockType oakWoodBlock = new OakWoodBlockType(10);
	public static final BlockType oakPlankBlock = new OakPlankBlockType(11);
	public static final BlockType oakLeavesBlock = new OakLeavesBlockType(12);

	public static final BlockType backgroundAirBlock = new BackgroundAirBlockType(20);
	public static final BlockType backgroundGrassBlock = new BackgroundGrassBlockType(21);
	public static final BlockType backgroundDirtBlock = new BackgroundDirtBlockType(22);
	public static final BlockType backgroundStoneBlock = new BackgroundStoneBlockType(23);
	
	
	
	// BlockType Class
	
	protected final int id;
	protected final String name;
	protected final Texture texture;
	protected final boolean transparent;
	protected final int lightEmitted;
	protected final boolean collide;
	protected final boolean background;
	protected final int hardness;
	protected final ToolType correctTool;
	protected final int minToolLvl;
	protected final boolean isTileEntity;
	
	protected ArrayList<BlockDrop> blockDrops;
	
	public BlockType(int id, String name, Texture texture, boolean transparent, int lightEmited, boolean collide, boolean background, int hardness, ToolType correctTool, int minToolLvl, boolean isTileEntity) {
		this.id = id;
		this.name = name;
		this.texture = texture;
		this.transparent = transparent;
		this.lightEmitted = lightEmited;
		this.collide = collide;
		this.background = background;
		this.hardness = hardness;
		this.correctTool = correctTool;
		this.minToolLvl = minToolLvl;
		this.isTileEntity = isTileEntity;

		blockTypes[id] = this;
	}

	/**
	 * Assign the item drops for all the declared block types.
	 * Call once, after the item types have been statically initialized.
	 */
	public static void initAllDrops() {
		for (int i = 0; i < BLOCK_TYPE_COUNT; i++) {
			if (blockTypes[i] != null) {
				blockTypes[i].initDrops();
			}
		}
	}

	// Overridden to declare drops for a BlockType
	protected void initDrops() {}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public boolean hasCollider() {
		return collide;
	}

	public boolean isBackground() {
		return background;
	}
	
	public int getHardness() {
		return hardness;
	}
	
	public ArrayList<BlockDrop> getBlockDrops() {
		return blockDrops;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public int getLightEmitted() {
		return lightEmitted;
	}
	
}
