package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class RenderableComponent implements Component {

    public Texture texture;
    public int width;
    public int height;
    public float rotation;

    public RenderableComponent(Texture texture, int width, int height){
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.rotation = (float) Math.PI / 2;
    }

    public RenderableComponent(Texture texture){
        this(texture, 32, 32);

    }

}
