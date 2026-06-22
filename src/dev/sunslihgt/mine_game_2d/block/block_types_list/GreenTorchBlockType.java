package dev.sunslihgt.mine_game_2d.block.block_types_list;

import com.raylib.Vector3;
import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

import java.util.ArrayList;

public class GreenTorchBlockType extends BlockType {

	public GreenTorchBlockType(int id) {
		super(id, "green torch", Assets.torch_block, true, new Vector3(0, 1f, 0), false, false, 0, ToolType.NONE, 0, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.greenTorchItem, 1, 1));
		blockDrops = drops;
	}
}
