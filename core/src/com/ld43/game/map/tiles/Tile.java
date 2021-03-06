package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.ld43.game.Ld43;
import com.ld43.game.state.LevelState;

public abstract class Tile {

    public static final int TILE_WIDTH = 32;

    private int x, y, tileX, tileY;
    protected boolean solid;

    public Tile(int tileX, int tileY, boolean solid) {
        this.tileX = tileX;
        this.x = tileX * TILE_WIDTH + TILE_WIDTH / 2;
        this.tileY = tileY;
        this.y = tileY * TILE_WIDTH + TILE_WIDTH / 2;
        this.solid = solid;
    }

    public abstract TextureRegion getTexture();

    public Tile(int x, int y){
        this(x, y, false);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean isSolid() {
        return solid;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public static float getActualSize() {
        return Gdx.graphics.getHeight() /(float) LevelState.NUM_OF_TILES;
    }

    public abstract void setTexture(TextureRegion texture);
}
