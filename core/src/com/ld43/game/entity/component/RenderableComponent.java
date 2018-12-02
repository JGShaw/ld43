package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderableComponent implements Component {

    public TextureRegion texture;
    public int width;
    public int height;
    public float rotation;

    public RenderableComponent(TextureRegion texture, int width, int height, float rotation){
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public RenderableComponent(TextureRegion texture, int width, int height){
        this(texture, width, height, 0);
    }

    public RenderableComponent(TextureRegion texture){
        this(texture, 32, 32);

    }

}
