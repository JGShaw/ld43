package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class ProjectileComponent implements Component {

    public float damage;

    public ProjectileComponent(float damage){
        this.damage = damage;
    }

}
