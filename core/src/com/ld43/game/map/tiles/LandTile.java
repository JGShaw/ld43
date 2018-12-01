package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.Texture;

public class LandTile extends Tile {

    Texture tile = new Texture("tiles/land.png");

    public LandTile(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    @Override
    public Texture getTexture() {
        return tile;
    }
}
