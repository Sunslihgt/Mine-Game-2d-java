package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import com.raylib.Vector3;
import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class TorchBlockType extends BlockType {

	public TorchBlockType(int id) {
		super(id, "torch", Assets.torch_block, true, new Vector3(1f, 1f, 1f), false, false, 0, ToolType.NONE, 0, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.torchItem, 1, 1));
		blockDrops = drops;
	}
}
