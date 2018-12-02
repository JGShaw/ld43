package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class CollisionComponent implements Component {

    public float radius;

    public CollisionComponent(float radius) {
        this.radius = radius;
    }
}
