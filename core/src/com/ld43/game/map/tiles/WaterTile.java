package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.Texture;

public class WaterTile extends Tile {

    private Texture solidTexture = new Texture("tiles/darkWater.png");
    private Texture emptyTexture = new Texture("tiles/water.png");

    public WaterTile(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    @Override
    public Texture getTexture() {
        return solid ? solidTexture : emptyTexture;
    }
}
