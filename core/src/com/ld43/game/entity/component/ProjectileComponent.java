package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class ProjectileComponent implements Component {

    public float damage;
    public boolean enemy;

    public ProjectileComponent(float damage, boolean enemy){
        this.damage = damage;
        this.enemy = enemy;
    }

}
