package dev.sunslihgt.mine_game_2d.utils;

import com.raylib.Vector2;
import com.raylib.Vector3;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.world.Chunk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {
	/* Vectors */
	public final static Vector2 VECTOR2_ZERO = new Vector2(0, 0);
	public final static Vector3 VECTOR3_ZERO = new Vector3(0, 0, 0);
	public static final List<int[]> neighbors4Ints = List.of(
			new int[] { 0,  1},
			new int[] { 0, -1},
			new int[] { 1,  0},
			new int[] {-1,  0}
	);
	public static final List<int[]> neighbors8Ints = List.of(
			new int[] {-1,  1},
			new int[] { 0,  1},
			new int[] { 1,  1},
			new int[] {-1,  0},
			new int[] { 1,  0},
			new int[] {-1, -1},
			new int[] { 0, -1},
			new int[] { 1, -1}
	);
	public static final List<Vector2> neighbors4Vectors = List.of(
			new Vector2( 0,  1),
			new Vector2( 0, -1),
			new Vector2( 1,  0),
			new Vector2(-1,  0)
	);
	public static final List<Vector2> neighbors8Vectors = List.of(
			new Vector2(-1,  1),
			new Vector2( 0,  1),
			new Vector2( 1,  1),
			new Vector2(-1,  0),
			new Vector2( 1,  0),
			new Vector2(-1, -1),
			new Vector2( 0, -1),
			new Vector2( 1, -1)
	);

	/**
	 * Euclidean length for the given vector 2D
	 */
	public static double magnitude(Vector2 vector2) {
		return Math.sqrt(vector2.getX() * vector2.getX() + vector2.getY() * vector2.getY());
	}

	/**
	 * Euclidean length for the given vector 3D
	 */
	public static double magnitude(Vector3 vector3) {
		return Math.sqrt(vector3.getX() * vector3.getX() + vector3.getY() * vector3.getY() + vector3.getZ() * vector3.getZ());
	}

	/**
	 * Manhattan distance between two 2D points
	 */
	public static int distance2d(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/* Maximum */
	public static int maxInt(Integer... vals) {
		return Collections.max(Arrays.asList(vals));
	}

	public static float maxFloat(Float... vals) {
		return Collections.max(Arrays.asList(vals));
	}

	/* Block, Chunk and Chunk index conversion */
	public static int convertToChunkIndex(int bX) {
		return Math.floorDiv(bX, Chunk.CHUNK_WIDTH);
	}
	
	public static int convertToBlockX(int cX, int chunkX) {
		return chunkX * Chunk.CHUNK_WIDTH + cX;
	}
	
	public static int convertToChunkBlockX(int bX) {
		int cX = bX % Chunk.CHUNK_WIDTH;
		if (cX < 0) {
			cX = 16 + cX;
		}
		return cX;
	}
	
	/* Block and Pixel conversion */
	public static int convertPixelToBlock(int pCoord) {
		return Math.floorDiv(pCoord, Block.BLOCK_WIDTH);
	}
	
	public static int convertPixelToBlock(float pCoord) {
		return convertPixelToBlock((int) pCoord);
	}
	
	public static int convertBlockToPixel(int bCoord) {
		return bCoord * Block.BLOCK_WIDTH;
	}

	/**
	 * Capitalize each word in a space separated string.
	 * <a href="https://www.geeksforgeeks.org/java/java-program-to-capitalize-the-first-letter-of-each-word-in-a-string">Source: GeeksForGeeks</a>
	 */
	public static String capitalizeWords(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		// Split the input string into words using whitespace
		String[] words = input.split("\\s");

		// Create a StringBuilder
		StringBuilder result = new StringBuilder();

		for (String word : words) {
			// Capitalize the first letter of each word and append the rest of the word
			result.append(Character.toUpperCase(word.charAt(0)))
				.append(word.substring(1).toLowerCase())
				.append(" "); // Add a space between words
		}

		// Remove the trailing space and return the capitalized string
		return result.toString().trim();
	}
}
