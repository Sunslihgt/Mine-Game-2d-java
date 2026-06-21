package dev.sunslihgt.mine_game_2d.input;

import com.raylib.Raylib;
import com.raylib.Raylib.KeyboardKey;

public class KeyManager {
    private final static int KEYS_LENGTH = 512; // Actually 0-348 in Jaylib-FFM
	private final boolean[] keys, justPressed, cantPress;
	public boolean up, down, left, right;

	public KeyManager() {
		keys = new boolean[KEYS_LENGTH];
		justPressed = new boolean[KEYS_LENGTH];
		cantPress = new boolean[KEYS_LENGTH];
	}

	public void tick() {
        for (int i = 0; i < KEYS_LENGTH; i++) {
            keys[i] = Raylib.isKeyDown(i);
            if (cantPress[i] && !keys[i])
                cantPress[i] = false;
            else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }

        // Quick access to the movement keys
        up = keys[KeyboardKey.KEY_W];
        down = keys[KeyboardKey.KEY_S];
        left = keys[KeyboardKey.KEY_A];
        right = keys[KeyboardKey.KEY_D];

    }

	public boolean keyJustPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= KEYS_LENGTH)
			return false;
		return justPressed[keyCode];
	}
}
