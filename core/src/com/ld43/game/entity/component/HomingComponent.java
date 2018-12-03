package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class HomingComponent implements Component {
    public Entity target;

    public HomingComponent(Entity target){
        this.target = target;
    }
}
