package dev.sunslihgt.mine_game_2d.block;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

public class BlockType {
	
	// Block types list
	public static BlockType[] blockTypes = new BlockType[256];
	public static BlockType airBlock = new AirBlock(0);
	public static BlockType dirtBlock = new DirtBlock(1);
	public static BlockType grassBlock = new GrassBlock(2);
	public static BlockType stoneBlock = new StoneBlock(3);
	public static BlockType coalOreBlock = new CoalOreBlock(4);
	public static BlockType ironOreBlock = new IronOreBlock(5);
	public static BlockType rubyOreBlock = new RubyOreBlock(6);
	public static BlockType torchBlock = new TorchBlock(7);
	public static BlockType chestBlock = new ChestBlock(8);
	public static BlockType furnaceBlock = new FurnaceBlock(9);
	public static BlockType oakWoodBlock = new OakWoodBlock(10);
	public static BlockType oakPlankBlock = new OakPlankBlock(11);
	public static BlockType oakLeavesBlock = new OakLeavesBlock(12);

	public static BlockType backgroundAirBlock = new BackgroundAirBlock(20);
	public static BlockType backgroundGrassBlock = new BackgroundGrassBlock(21);
	public static BlockType backgroundDirtBlock = new BackgroundDirtBlock(22);
	public static BlockType backgroundStoneBlock = new BackgroundStoneBlock(23);
	
	
	
	// BlockType Class
	
	protected final int id;
	protected String name;
	protected BufferedImage texture;
	protected boolean transparent;
	protected int lightEmited;
	protected boolean collide;
	protected boolean background;
	protected int hardness;
	protected ToolType correctTool;
	protected int minToolLvl;
	
	protected ArrayList<BlockDrop> blockDrops;
	
	public BlockType(int id, String name, BufferedImage texture, boolean transparent, int lightEmited, boolean collide, boolean background, int hardness, ToolType correctTool, int minToolLvl) {
		this.id = id;
		this.name = name;
		this.texture = texture;
		this.transparent = transparent;
		this.lightEmited = lightEmited;
		this.collide = collide;
		this.background = background;
		this.hardness = hardness;
		this.correctTool = correctTool;
		this.minToolLvl = minToolLvl;
		
		blockTypes[id] = this;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedImage getTexture() {
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

	public int getLightEmited() {
		return lightEmited;
	}
	
}
