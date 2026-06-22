package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import com.raylib.Vector3;
import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class FurnaceBlockType extends BlockType {

	public FurnaceBlockType(int id) {
		super(id, "furnace", Assets.furnace_block_off, true, Utils.VECTOR3_ZERO, false, false, 180, ToolType.PICKAXE, 0, true);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.furnaceItem, 1, 1));
		blockDrops = drops;
	}
}
