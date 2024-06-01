package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class TorchBlock extends BlockType {

	public TorchBlock(int id) {
		super(id, "torch", Assets.torch_block, true, 13, false, false, 0, ToolType.NONE, 0);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.torchItem, 1, 1));
		super.blockDrops = drops;
	}
}
