package com.ld43.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.ld43.game.map.tiles.Tile;

import java.util.List;

public class RoutePlanner extends InputAdapter {
    public static int tileTouchUpX = -1;
    public static int tileTouchUpY = -1;

    public RoutePlanner() {
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        tileTouchUpX = (int) Math.floor(screenX / Tile.getActualSize());
        tileTouchUpY = (int) Math.floor(screenY / Tile.getActualSize());
        return true;
    }

    public static void resetTileTouchUp(){
        tileTouchUpX = -1;
        tileTouchUpY = -1;
    }

}