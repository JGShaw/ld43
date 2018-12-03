package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class SeaMineComponent implements Component {
    public float z;
    public float zVel;
    public float gravity;

    public SeaMineComponent(float z, float zVel, float gravity){
        this.z = z;
        this.zVel = zVel;
        this.gravity = gravity;
    }

}
