package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class CollisionBoxComponent implements Component {

    public float width;
    public float height;

    public CollisionBoxComponent(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
