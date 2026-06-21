package dev.sunslihgt.mine_game_2d.block;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.tile_entities_list.ChestTileEntityType;
import dev.sunslihgt.mine_game_2d.block.tile_entities_list.FurnaceTileEntityType;

import java.io.InvalidClassException;

public class BlockFactory {
    /**
     * Create a block according to the given blockType
     * @param x World position
     * @param y World position
     * @param blockType The blockType to use
     * @param handler Game Handle
     * @return A new instance of a block
     */
    public static Block createBlock(int x, int y, BlockType blockType, Handler handler) throws InvalidClassException {
        if (blockType.isTileEntity) {
            if (blockType == BlockType.chestBlock) {
                return new ChestTileEntityType(x, y, handler);
            } else if (blockType == BlockType.furnaceBlock) {
                return new FurnaceTileEntityType(x, y, handler);
            } else {
                throw new InvalidClassException("Tile entity class was not found for block type: " + blockType.name);
            }
        } else {
            return new Block(x, y, blockType);
        }
    }
}
