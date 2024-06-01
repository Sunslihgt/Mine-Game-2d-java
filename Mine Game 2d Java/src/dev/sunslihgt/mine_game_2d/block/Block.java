package dev.sunslihgt.mine_game_2d.block;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.Item;

public class Block {

	public final static int BLOCK_WIDTH = 32;

	protected int x, y;
	protected BlockType type;

	private float breakingAmountLeft = 0;
	protected int breakingStage = 0; // Defines the block breaking texture (index + 1) to use
	private boolean breakable = false;

	private int skyLight = 0, blockLight = 0;

	public Block(int x, int y, BlockType type) {
		this.x = x;
		this.y = y;
		this.type = type;

		if (type.getHardness() >= 0) {
			breakingAmountLeft = type.getHardness();
			breakable = true;
		}
	}

	public void tick() {

	}

	public void render(Graphics g, int xOffset, int yOffset) {
		if (type.getTexture() != null) {
			// Texture
			g.drawImage(type.getTexture(), x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH, null);

			// Breaking texture
			if (breakingStage > 0 && breakingStage - 1 < Assets.block_breaking.length) {
				g.drawImage(Assets.block_breaking[breakingStage - 1], x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH, null);
			}
		}

		// Lighting (darkness)
//		if (!type.background) {
//			int darknessRatio = (15 - Math.max(blockLight, skyLight)) * 17;
//			Color lightingColor = new Color(0, 0, 0, darknessRatio);
//			g.setColor(lightingColor);
//			g.fillRect(x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
//		}
	}
	
	// Overriden by some tile entities (eg : chests)
	public boolean rightClickBlock() {
		return false; // Let the player use an item's right click action
	}

	public boolean damageBlock(Item item) {
		if (!breakable) { // Unbreakable block
			return false;
		}

		// Tool
		if (item == null) {
			breakingAmountLeft -= 1;
		} else if (type.correctTool == item.getType().getToolType()) {
			breakingAmountLeft -= item.getType().getToolEfficiency();
		} else {
			breakingAmountLeft -= 1;
		}

		// Break block
		if (breakingAmountLeft < 0) {
			return true;
		}

		// Update breaking ratio
		if (type.getHardness() <= 0) {
			breakingStage = 0;
		} else {
			breakingStage = (int) Math.ceil((1 - breakingAmountLeft / type.getHardness()) * Assets.block_breaking.length);
			System.out.println("Breaking stage: " + breakingStage + ", left: " + breakingAmountLeft + ", hardness: " + type.getHardness());
		}

		return false;
	}
	
	// Overriden by some tile entities
	public void breakBlock() {
		
	}

	public boolean canItemHarvestBlock(Item item) {
		// Check if tool requirements are met
		if (type.minToolLvl > 0) {
			if (item == null) {
				return false; // Tool was needed
			}
			if (type.correctTool != item.getType().getToolType() || item.getType().getToolLvl() < type.minToolLvl) {
				return false; // Tool requirements not met
			}
		}

		return true; // Block is harvestable
	}

	public ArrayList<Item> getDrops() {
		ArrayList<Item> drops = new ArrayList<Item>();
		for (BlockDrop blockDrop : type.getBlockDrops()) {
			if (blockDrop != null) {
				drops.addAll(blockDrop.getDrops());
			}
		}
		return drops;
	}

	public BlockType getType() {
		return type;
	}

	public int getId() {
		return type.getId();
	}

	public int getSkyLight() {
		return skyLight;
	}

	public void setSkyLight(int skyLight) {
		this.skyLight = skyLight;
	}

	public int getBlockLight() {
		return blockLight;
	}

	public void setBlockLight(int blockLight) {
		this.blockLight = blockLight;
	}
}
