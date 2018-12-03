package com.ld43.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.state.LevelState;

public class InputHandler extends InputAdapter {
    public static float touchDownX = -1;
    public static float touchDownY = -1;

    public static int tileTouchUpX = -1;
    public static int tileTouchUpY = -1;

    public static boolean running;

    public static int scrolled;

    public static boolean[] isKeyDown = new boolean[65536];
    public static boolean nextState;

    @Override
    public boolean keyUp (int down) {
        isKeyDown[down] = false;
        if(down == 62){
            running = true;
        }
        return false;
    }

    @Override
    public boolean keyDown (int down) {
        isKeyDown[down] = true;
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;

        float virtualHeight = LevelState.NUM_OF_TILES * Tile.TILE_WIDTH;

        touchDownX = screenX * (virtualHeight / Gdx.graphics.getWidth());
        touchDownY = screenY * (virtualHeight / Gdx.graphics.getHeight());
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

    public static void resetTouchDown() {
        touchDownX = -1;
        touchDownY = -1;
    }

    public static void resetTileTouchDown(){
        tileTouchUpX = -1;
        tileTouchUpY = -1;
    }

}
