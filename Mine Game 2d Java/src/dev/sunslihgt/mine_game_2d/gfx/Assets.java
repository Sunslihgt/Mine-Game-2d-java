package dev.sunslihgt.mine_game_2d.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	public static final int width = 16, height = 16;
	public static final int HEART_WIDTH = 48;
	
	public static final int CLOUD_HEIGHT = width * 3; // 48
	public static final int SMALL_CLOUD_WIDTH = CLOUD_HEIGHT, MEDIUM_CLOUD_WIDTH = CLOUD_HEIGHT * 2, BIG_CLOUD_WIDTH = CLOUD_HEIGHT * 3;
	
	// Blocks
	public static BufferedImage dirt_block, grass_block, stone_block, coal_ore_block, iron_ore_block, ruby_ore_block;
	public static BufferedImage torch_block, chest_block, furnace_block_off, furnace_block_on;
	public static BufferedImage selectedBlockOutline, oak_wood_block, oak_plank_block, oak_leaves_block;
	public static BufferedImage[] block_breaking;
	
	// Background blocks
	public static BufferedImage background_grass_block, background_dirt_block, background_stone_block;
	
	// Items
	public static BufferedImage dirt_item, grass_item, stone_item, coal_ore_item, iron_ore_item, ruby_ore_item;
	public static BufferedImage stick_item, torch_item, chest_item, furnace_item, coal_item, iron_ingot_item, ruby_item;
	public static BufferedImage wooden_pickaxe_item, stone_pickaxe_item, iron_pickaxe_item;
	public static BufferedImage wooden_axe_item, stone_axe_item, iron_axe_item;
	public static BufferedImage wooden_shovel_item, stone_shovel_item, iron_shovel_item;
	public static BufferedImage oak_wood_item, oak_plank_item, oak_leaves_item;
	
	// UI
	public static BufferedImage empty_life_bar, full_life_bar;
	public static BufferedImage heart, half_heart, empty_heart;
	public static BufferedImage arrow;
	
	// Background
	public static BufferedImage[] smallest_clouds, small_clouds, medium_clouds, big_clouds;
	
	// Font
	public static Font inventory_font;

	public static void init() {
		inventory_font = FontLoader.loadFont("resources/assets/fonts/MinecraftRegular-Bmg3.otf", 22);
		
		// Blocks
		SpriteSheet blockTileSheet = new SpriteSheet(ImageLoader.loadImage("/assets/textures/block_spritesheet_x16_v1.png"));
		
		dirt_block = blockTileSheet.crop(0, 0, width, height);
		grass_block = blockTileSheet.crop(width, 0, width, height);
		
		stone_block = blockTileSheet.crop(0, height, width, height);
		coal_ore_block = blockTileSheet.crop(width, height, width, height);
		iron_ore_block = blockTileSheet.crop(width * 2, height, width, height);
		ruby_ore_block = blockTileSheet.crop(width * 3, height, width, height);
		
		torch_block = blockTileSheet.crop(0, height * 3, width, height);
		chest_block = blockTileSheet.crop(width, height * 3, width, height);
		
		furnace_block_off = blockTileSheet.crop(width * 3, height * 3, width, height);
		furnace_block_on = blockTileSheet.crop(width * 4, height * 3, width, height);

		oak_wood_block = blockTileSheet.crop(0, height * 4, width, height);
		oak_plank_block = blockTileSheet.crop(width, height * 4, width, height);
		oak_leaves_block = blockTileSheet.crop(width * 2, height * 4, width, height);
		
		selectedBlockOutline = blockTileSheet.crop(0, 2 * height, width, height);
		
		block_breaking = new BufferedImage[5];
		block_breaking[0] = blockTileSheet.crop(width, 2 * height, width, height);
		block_breaking[1] = blockTileSheet.crop(width * 2, 2 * height, width, height);
		block_breaking[2] = blockTileSheet.crop(width * 3, 2 * height, width, height);
		block_breaking[3] = blockTileSheet.crop(width * 4, 2 * height, width, height);
		block_breaking[4] = blockTileSheet.crop(width * 5, 2 * height, width, height);
		

		// Background blocks
		SpriteSheet backgroundBlockTileSheet = new SpriteSheet(ImageLoader.loadImage("/assets/textures/background_block_spritesheet_x16_v1.png"));

		background_grass_block = backgroundBlockTileSheet.crop(0, 0, width, height);
		background_dirt_block = backgroundBlockTileSheet.crop(width, 0, width, height);
		background_stone_block = backgroundBlockTileSheet.crop(2 * width, 0, width, height);
		
		
		// Items
		SpriteSheet itemTileSheet = new SpriteSheet(ImageLoader.loadImage("/assets/textures/item_spritesheet_x16_v1.png"));
		
		dirt_item = itemTileSheet.crop(0, 0, width, height);
		grass_item = itemTileSheet.crop(width, 0, width, height);
		
		stone_item = itemTileSheet.crop(0, height, width, height);
		coal_ore_item = itemTileSheet.crop(width, height, width, height);
		iron_ore_item = itemTileSheet.crop(width * 2, height, width, height);
		ruby_ore_item = itemTileSheet.crop(width * 3, height, width, height);

		stick_item = itemTileSheet.crop(0, height * 5, width, height);
		torch_item = blockTileSheet.crop(0, height * 3, width, height);
		chest_item = chest_block;
		furnace_item = furnace_block_off;
		
		oak_leaves_item = itemTileSheet.crop(width * 3, height * 5, width, height);
		oak_plank_item = itemTileSheet.crop(width * 2, height * 5, width, height);
		oak_wood_item = itemTileSheet.crop(width, height * 5, width, height);

		coal_item = itemTileSheet.crop(0, height * 4, width, height);
		iron_ingot_item = itemTileSheet.crop(width, height * 4, width, height);
		ruby_item = itemTileSheet.crop(width * 2, height * 4, width, height);

		wooden_pickaxe_item = itemTileSheet.crop(0, 2 * height, width, height);
		stone_pickaxe_item = itemTileSheet.crop(width, 2 * height, width, height);
		iron_pickaxe_item = itemTileSheet.crop(width * 2, 2 * height, width, height);

		wooden_axe_item = itemTileSheet.crop(width * 3, 2 * height, width, height);
		stone_axe_item = itemTileSheet.crop(width * 4, 2 * height, width, height);
		iron_axe_item = itemTileSheet.crop(width * 5, 2 * height, width, height);
		
		wooden_shovel_item = itemTileSheet.crop(width * 3, 3 * height, width, height);
		stone_shovel_item = itemTileSheet.crop(width * 4, 3 * height, width, height);
		iron_shovel_item = itemTileSheet.crop(width * 5, 3 * height, width, height);
		
		
		// UI
		SpriteSheet uiTileSheet = new SpriteSheet(ImageLoader.loadImage("/assets/textures/ui_spritesheet.png"));

		full_life_bar = uiTileSheet.crop(0, 0, width * 16, height * 2);
		empty_life_bar = uiTileSheet.crop(0, height * 2, width * 16, height * 2);
		
		heart = uiTileSheet.crop(0, 0, width, height);
//		half_heart = uiTileSheet.crop(width, 0, width, height);
		half_heart = uiTileSheet.crop(width * 2, 0, width, height);
		empty_heart = uiTileSheet.crop(width * 3, 0, width, height);
		
		// Background
		smallest_clouds = new BufferedImage[2];
		smallest_clouds[0] = uiTileSheet.crop(0, height, width, height);
		smallest_clouds[1] = uiTileSheet.crop(0, height * 2, width, height);
		
		small_clouds = new BufferedImage[2];
		small_clouds[0] = uiTileSheet.crop(width, height, width, height);
		small_clouds[1] = uiTileSheet.crop(width, height * 2, width, height);

		medium_clouds = new BufferedImage[2];
		medium_clouds[0] = uiTileSheet.crop(width * 2, height, width * 2, height);
		medium_clouds[1] = uiTileSheet.crop(width * 2, height * 2, width * 2, height);

		big_clouds = new BufferedImage[2];
		big_clouds[0] = uiTileSheet.crop(width * 4, height, width * 3, height);
		big_clouds[1] = uiTileSheet.crop(width * 4, height * 2, width * 3, height);
		
		arrow = uiTileSheet.crop(0, height * 3, width, height);
	}
}
