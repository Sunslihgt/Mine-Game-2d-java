package dev.sunslihgt.mine_game_2d.block;

import java.util.ArrayList;

import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.block.block_types_list.AirBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.BackgroundAirBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.BackgroundDirtBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.BackgroundGrassBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.BackgroundStoneBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.ChestBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.CoalOreBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.DirtBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.FurnaceBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.GrassBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.IronOreBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.OakLeavesBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.OakPlankBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.OakWoodBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.RubyOreBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.StoneBlock;
import dev.sunslihgt.mine_game_2d.block.block_types_list.TorchBlock;
import dev.sunslihgt.mine_game_2d.item.ToolType;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class BlockType {
	private final static int BLOCK_TYPE_COUNT = 256;
	
	// Block types list
	public static BlockType[] blockTypes = new BlockType[BLOCK_TYPE_COUNT];
	public static final BlockType airBlock = new AirBlock(0);
	public static final BlockType dirtBlock = new DirtBlock(1);
	public static final BlockType grassBlock = new GrassBlock(2);
	public static final BlockType stoneBlock = new StoneBlock(3);
	public static final BlockType coalOreBlock = new CoalOreBlock(4);
	public static final BlockType ironOreBlock = new IronOreBlock(5);
	public static final BlockType rubyOreBlock = new RubyOreBlock(6);
	public static final BlockType torchBlock = new TorchBlock(7);
	public static final BlockType chestBlock = new ChestBlock(8);
	public static final BlockType furnaceBlock = new FurnaceBlock(9);
	public static final BlockType oakWoodBlock = new OakWoodBlock(10);
	public static final BlockType oakPlankBlock = new OakPlankBlock(11);
	public static final BlockType oakLeavesBlock = new OakLeavesBlock(12);

	public static final BlockType backgroundAirBlock = new BackgroundAirBlock(20);
	public static final BlockType backgroundGrassBlock = new BackgroundGrassBlock(21);
	public static final BlockType backgroundDirtBlock = new BackgroundDirtBlock(22);
	public static final BlockType backgroundStoneBlock = new BackgroundStoneBlock(23);
	
	
	
	// BlockType Class
	
	protected final int id;
	protected String name;
	protected Texture texture;
	protected boolean transparent;
	protected int lightEmitted;
	protected boolean collide;
	protected boolean background;
	protected int hardness;
	protected ToolType correctTool;
	protected int minToolLvl;
	protected boolean isTileEntity;
	
	protected ArrayList<BlockDrop> blockDrops;
	
	public BlockType(int id, String name, Texture texture, boolean transparent, int lightEmited, boolean collide, boolean background, int hardness, ToolType correctTool, int minToolLvl) {
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

	public boolean isCollide() {
		return collide;
	}

	public boolean isbackground() {
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
