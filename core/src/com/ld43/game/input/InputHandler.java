package com.ld43.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.ld43.game.map.tiles.Tile;

import java.util.List;

public class InputHandler extends InputAdapter {
    public static int tileTouchUpX = -1;
    public static int tileTouchUpY = -1;

    public static int boatNumber = 0;

    public static int scrolled;

    @Override
    public  boolean keyUp(int key) {
        boatNumber = key - Input.Keys.NUM_0;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = Gdx.graphics.getHeight() - screenY;
        tileTouchUpX = (int) Math.floor(screenX / Tile.getActualSize());
        tileTouchUpY = (int) Math.floor(screenY / Tile.getActualSize());
        return true;
    }

    @Override
    public boolean scrolled (int amount) {
        scrolled = amount;
        return false;
    }

    public static void resetTileTouchDown(){
        tileTouchUpX = -1;
        tileTouchUpY = -1;
    }
}
