package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tile {

    private int x, y;
    private boolean solid;

    public Tile(int x, int y, boolean solid) {

        this.x = x;
        this.y = y;
        this.solid = solid;

    }

    public abstract Texture getTexture();

    public Tile(int x, int y){
        this(x, y, false);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
