package com.ld43.game.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.ld43.game.entity.component.HealthComponent;
import com.ld43.game.entity.component.PositionComponent;
import com.ld43.game.entity.component.RenderableComponent;
import com.ld43.game.entity.component.UnderwaterComponent;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.input.InputHandler;

public class HealthUpdateSystem extends EntitySystem {
    private final boolean removeOnZero;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

    public HealthUpdateSystem(){
        this(true);
    }

    public HealthUpdateSystem(boolean removeOnZero) {
        this.removeOnZero = removeOnZero;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            HealthComponent health = hm.get(entity);

            health.update(deltaTime);

            if(health.isDead() && removeOnZero) {

                if(health.deathTextureName != null) {

                    Entity dead = new Entity();
                    dead.add(pm.get(entity));
                    RenderableComponent rc = rm.get(entity);
                    rc.texture = TextureRegistry.getTexture(health.deathTextureName);
                    dead.add(rc);
                    dead.add(new UnderwaterComponent());
                    getEngine().addEntity(dead);

                }

                getEngine().removeEntity(entity);

            }
        }
    }
}