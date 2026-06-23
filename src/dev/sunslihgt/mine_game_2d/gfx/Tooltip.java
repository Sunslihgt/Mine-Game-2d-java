package dev.sunslihgt.mine_game_2d.gfx;

import com.raylib.*;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.font.Text;
import dev.sunslihgt.mine_game_2d.gfx.font.Text.FontAnchor;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.utils.Utils;

import java.util.Locale;

public class Tooltip {
    // Background
    private final static int bgSrcSize = Assets.TOOLTIP_BACKGROUND_SIZE; // Background source size
    private final static int bgSrcCorner = Assets.TOOLTIP_BACKGROUND_CORNER; // Background corner source size
    private final static int bgDstCorner = bgSrcCorner * (Block.BLOCK_WIDTH / Assets.width); // Background corner destination size
    private final static int minBackgroundSize = bgDstCorner * 2 + 6;
    private final static int xOffset = 16;

    // Text
    private final static int textPaddingX = 10, textPaddingY = 6;

    private static Handler handler;

    public static void init(Handler handler) {
        Tooltip.handler = handler;
    }

    public static void drawToolTip(int mouseX, int mouseY, Item item) {
        int x = mouseX;
        int y = mouseY;
        String text = Utils.capitalizeWords(item.getName());

        Vector2 textDimensions = Text.getStringDimensions(text, Assets.tooltip_font);
        int width = Math.max(minBackgroundSize, (int) textDimensions.getX() + textPaddingX * 2);
        int height = Math.max(minBackgroundSize, (int) textDimensions.getY() + textPaddingY * 2);

        // Move root position according to screen size and tooltip size
        int screenWidth = handler.getWidth();
        int screenHeight = handler.getHeight();
        if (x + width + xOffset > screenWidth && x - width - xOffset >= 0) {
            x = x - width - xOffset;
        } else {
            x = x + xOffset;
        }
        if (y + height > screenHeight && height <= screenHeight) {
            y = screenHeight - height;
        }
//        System.out.printf("Drawing tooltip for %s at x=%d, y=%d\n", text, x, y);

        drawBackground(x, y, width, height);
        Text.drawString(
            text, x + textPaddingX, y + textPaddingY,
            Assets.tooltip_font, FontAnchor.TOP_LEFT, Raylib.WHITE
        );
    }

    /**
     * Draw the tooltip background in 9 steps (preserve corners, stretch sides and center)
     */
    private static void drawBackground(int x, int y, int width, int height) {
        // Top left
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(0, 0, bgSrcCorner, bgSrcCorner),
            new Rectangle(x, y, bgDstCorner, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Top center
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcCorner, 0, bgSrcSize - bgSrcCorner * 2, bgSrcCorner),
            new Rectangle(x + bgDstCorner, y, width - bgDstCorner * 2, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Top right
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcSize - bgSrcCorner, 0, bgSrcCorner, bgSrcCorner),
            new Rectangle(x + width - bgDstCorner, y, bgDstCorner, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );

        // Bottom left
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(0, bgSrcSize - bgSrcCorner, bgSrcCorner, bgSrcCorner),
            new Rectangle(x, y + height - bgDstCorner, bgDstCorner, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Bottom center
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcCorner, bgSrcSize - bgSrcCorner, bgSrcSize - bgSrcCorner * 2, bgSrcCorner),
            new Rectangle(x + bgDstCorner, y + height - bgDstCorner, width - bgDstCorner * 2, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Bottom right
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcSize - bgSrcCorner, bgSrcSize - bgSrcCorner, bgSrcCorner, bgSrcCorner),
            new Rectangle(x + width - bgDstCorner, y + height - bgDstCorner, bgDstCorner, bgDstCorner),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );

        // Middle left
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(0, bgSrcCorner, bgSrcCorner, bgSrcSize - bgSrcCorner * 2),
            new Rectangle(x, y + bgDstCorner, bgDstCorner, height - bgDstCorner * 2),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Middle center
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcCorner, bgSrcCorner, bgSrcSize - bgSrcCorner * 2, bgSrcSize - bgSrcCorner * 2),
            new Rectangle(x + bgDstCorner, y + bgDstCorner, width - bgDstCorner * 2, height - bgDstCorner * 2),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
        // Middle right
        Raylib.drawTexturePro(
            Assets.tooltip_background,
            new Rectangle(bgSrcSize - bgSrcCorner, bgSrcCorner, bgSrcCorner, bgSrcSize - bgSrcCorner * 2),
            new Rectangle(x + width - bgDstCorner, y + bgDstCorner, bgDstCorner, height - bgDstCorner * 2),
            Utils.VECTOR2_ZERO,
            0,
            Raylib.WHITE
        );
    }
}
