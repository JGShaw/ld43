package com.ld43.game.map.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.ld43.game.graphics.TextureRegistry;

public class WaterTile extends Tile {

    private Texture solidTexture = TextureRegistry.getTexture("tile-water--dark");
    private Texture emptyTexture = TextureRegistry.getTexture("tile-water--light");

    public WaterTile(int x, int y, boolean solid) {
        super(x, y, solid);
    }

    @Override
    public Texture getTexture() {
        return solid ? solidTexture : emptyTexture;
    }
}
