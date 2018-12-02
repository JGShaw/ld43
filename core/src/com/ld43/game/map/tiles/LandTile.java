package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ld43.game.graphics.TextureRegistry;

public class LandTile extends Tile {

    TextureRegion texture = TextureRegistry.getTexture("tile-land");

    public LandTile(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    @Override
    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

}
