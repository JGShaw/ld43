package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class DamageComponent implements Component {

    public float damage;

    public DamageComponent(float damage) {
        this.damage =  damage;
    }

}
