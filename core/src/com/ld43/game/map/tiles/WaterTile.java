package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ld43.game.graphics.TextureRegistry;

public class WaterTile extends Tile {

    private TextureRegion emptyTexture = TextureRegistry.getTexture("tile-water--0");
    private TextureRegion solidTexture = TextureRegistry.getTexture("tile-water--1");

    private TextureRegion texture;

    public WaterTile(int x, int y, boolean solid) {
        super(x, y, solid);
        texture = solid ? solidTexture : emptyTexture;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture){
        this.texture = texture;
    }

}
