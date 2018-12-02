package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.HealthComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.VelocityComponent;
import com.ld43.game.input.RoutePlanner;

public class HealthUpdateSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    public HealthUpdateSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            HealthComponent health = hm.get(entity);

            if(RoutePlanner.scrolled != 0){
                health.takeDamage(20);
            }

            health.update(deltaTime);

        }

        RoutePlanner.scrolled = 0;
    }
}