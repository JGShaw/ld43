package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float x;
    public float y;
    public float maxSpeed;

    public VelocityComponent(float x, float y, float maxSpeed) {
        this.x = x;
        this.y = y;
        this.maxSpeed = maxSpeed;
    }
}
