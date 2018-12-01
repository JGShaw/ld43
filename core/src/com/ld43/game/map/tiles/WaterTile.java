package com.ld43.game.map.tiles;

public class WaterTile {

    private int x, y;
    private boolean solid;

    public WaterTile(int x, int y, boolean solid) {

        this.x = x;
        this.y = y;
        this.solid = solid;

    }

    public WaterTile(int x, int y){
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